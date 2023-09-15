package link.codegen.salesforce.utils

import SalesforceAuth
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BadRefreshTokenIntegrationTests {
    @Test
    fun demoTokenReturnsSpecificMessage() {
        val refreshToken = "demoRefreshToken"
        val actualException = Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) { SalesforceAuth(refreshToken) }
        Assertions.assertTrue(actualException.message!!.contains("demoRefreshToken"))
        Assertions.assertFalse(actualException.message!!.startsWith("Unexpected error occurred"))
    }

    @Test
    fun invalidTokenReturnsSpecificMessage() {
        val refreshToken = "invalidRefreshToken"
        val actualException = Assertions.assertThrows(
            IllegalArgumentException::class.java
        ) { SalesforceAuth(refreshToken) }
        Assertions.assertTrue(actualException.message!!.contains("expired access/refresh token"))
        Assertions.assertFalse(actualException.message!!.startsWith("Unexpected error occurred"))
    }

    @Test
    fun validToken() {
        val auth = SalesforceAuth()
        Assertions.assertNotNull(auth.accessToken)
        Assertions.assertNotNull(auth.instanceUrl)
    }
}
