package link.codegen.salesforce.utils

import SalesforceAuth
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.HttpURLConnection
import java.net.IDN
import java.net.URI
import java.net.URL
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KVisibility
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField

class SalesforceClient(
    private val auth: SalesforceAuth = SalesforceAuth(),
    private val mapper: ObjectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(
            JavaTimeModule()
                .addDeserializer(
                    ZonedDateTime::class.java,
                    InstantDeserializer.ZONED_DATE_TIME,
                )
                .addDeserializer(
                    LocalDate::class.java,
                    LocalDateDeserializer.INSTANCE,
                )
                .addDeserializer(
                    LocalTime::class.java,
                    LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'")),
                ),
        ),
    private val apiVersion: String = "v53.0",
) {

    fun <T: SObjectInterface> getAll(clazz: Class<T>): SfResponse<T> =
        getResponse("SELECT ${getFields<T>(clazz).joinToString()} FROM ${clazz.simpleName}", clazz)

    fun <T: SObjectInterface> getWhere(clazz: Class<T>, where: String): SfResponse<T> =
        getResponse("SELECT ${getFields<T>(clazz).joinToString()} FROM ${clazz.simpleName} WHERE $where", clazz)

    fun <T: SObjectInterface> count(clazz: Class<T>): Int =
        getResponse("SELECT COUNT() FROM+${clazz.simpleName}", clazz).totalSize

    private fun <T: SObjectInterface> getResponse(query: String, clazz: Class<T>): SfResponse<T> {

        val url = query.constructUrl()
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.setRequestProperty("accept", "application/json")
        connection.setRequestProperty("Authorization", "Bearer ${auth.accessToken}")

        val wrappedResponseType = mapper.typeFactory.constructParametricType(SfResponse::class.java, clazz)

        return mapper.readValue(connection.inputStream, wrappedResponseType)
    }

    private fun <T: SObjectInterface> getFields(clazz: Class<T>): List<String> {
        val propertiesWeWant = clazz.kotlin.memberProperties
            .filter { prop ->
                prop.visibility == KVisibility.PUBLIC && prop.name != "attributes"
            }

        val bodyProps = propertiesWeWant.mapNotNull { it.javaField?.getAnnotation(JsonProperty::class.java)?.value }
        val constructorProps = clazz.kotlin.primaryConstructor?.parameters
            ?.filter { it.name in (propertiesWeWant.map { it.name }) }
            ?.map { it.findAnnotation<JsonProperty>()!!.value }
            ?: emptyList()

        return bodyProps + constructorProps
    }

    private fun String.constructUrl() = URL("${auth.instanceUrl}/services/data/$apiVersion/query?q=$this")
        .toUri().toURL()// for proper url encoding

    /**
     * URI is used for URL encoding.
     */
    private fun URL.toUri(): URI = URI(
        this.protocol,
        this.userInfo,
        IDN.toASCII(this.host),
        this.port,
        this.path,
        this.query,
        this.ref
    )
}

class SfResponse<T: SObjectInterface>(
    val totalSize: Int,
    val done: Boolean,
    val records: List<T>,
)
