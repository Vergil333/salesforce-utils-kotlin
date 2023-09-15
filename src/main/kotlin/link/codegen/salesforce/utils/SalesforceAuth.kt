import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.ZonedDateTime

class SalesforceAuth(
    private val refreshToken: String = "yourRefreshToken",
) {
    var accessToken: String
        get() {
            if (isValid().not()) update()
            return field
        }
    var instanceUrl: String

    private var validUntil: ZonedDateTime
    private var mapper: ObjectMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    init {
        val response = getAccessToken()
        this.accessToken = response.accessToken
        this.instanceUrl = response.instanceUrl
        this.validUntil = ZonedDateTime.now().plusHours(2).minusMinutes(5)
    }

    private fun getAccessToken(): AuthResponse {

        val url = URL("http://api.codegen.link/salesforce/login/refresh")
        val connection: HttpURLConnection = (url.openConnection() as HttpURLConnection).also {
            it.setRequestProperty("Content-Type", "application/json")
            it.setRequestProperty("Accept", "application/json")
            it.requestMethod = "POST"
            it.doOutput = true
        }

        val requestBody = AuthRequest(refreshToken = this.refreshToken)
        DataOutputStream(connection.outputStream)
            .use { it.writeBytes(mapper.writeValueAsString(requestBody)) }

        return connection.getResponse()
    }

    private fun HttpURLConnection.getResponse(): AuthResponse {
        val responseCode = this.responseCode

        if (responseCode in 200..299) {
            // Success
            return mapper.readValue(this.inputStream, object : TypeReference<AuthResponse>() {})
        } else {
            // Error
            val errorStream = this.errorStream ?: throw Exception("No error stream available")
            val errorResponse = mapper.readValue(errorStream, ErrorResponse::class.java)
            val errorMessage = errorResponse.message

            // Handle specific error codes or map it to a specific exception
            when (responseCode) {
                400 -> throw IllegalArgumentException(errorMessage)
                else -> throw InternalError("Unexpected error occurred: $errorMessage")
            }
        }
    }

    private fun isValid() = this.validUntil > ZonedDateTime.now()

    private fun update() {
        val response = getAccessToken()
        this.accessToken = response.accessToken
        this.instanceUrl = response.instanceUrl
        this.validUntil = ZonedDateTime.now().plusHours(2).minusMinutes(5)
    }
}

private data class AuthRequest(
    val refreshToken: String,
)

private data class AuthResponse(
    val accessToken: String,
    val instanceUrl: String,
)

private data class ErrorResponse(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
)
