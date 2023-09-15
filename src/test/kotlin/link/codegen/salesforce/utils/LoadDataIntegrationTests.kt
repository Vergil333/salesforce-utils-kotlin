package link.codegen.salesforce.utils

import SalesforceAuth
import link.codegen.salesforce.objects.Account
import link.codegen.salesforce.objects.TestObjectMM4__c
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

internal class LoadDataIntegrationTests {
    private val auth: SalesforceAuth = SalesforceAuth()

    private val client: SalesforceClient = SalesforceClient(auth)

    // Account tests
    @Test
    fun loadAllAccounts() {
        val response: SfResponse<Account> = client.getAll(Account::class.java)
        Assertions.assertEquals(13, response.totalSize)
    }

    @Test
    fun loadAllAccountNames() {
        val expectedAccountNames = listOf(
            "University of Arizona",
            "GenePoint",
            "United Oil & Gas, Singapore",
            "Grand Hotels & Resorts Ltd",
            "United Oil & Gas Corp.",
            "Pyramid Construction Inc.",
            "sForce",
            "Burlington Textiles Corp of America",
            "Express Logistics and Transport",
            "Edge Communications",
            "Sample Account for Entitlements",
            "United Oil & Gas, UK",
            "Dickenson plc"
        )
        val response: SfResponse<Account> = client.getAll(Account::class.java)
        val accountNames: List<String> = response.records.map(Account::name).toList()
        Assertions.assertArrayEquals(expectedAccountNames.toTypedArray(), accountNames.toTypedArray())
    }

    @Test
    fun testZonedDateTimeDeserialization() {
        val expectedZonedDateTime: ZonedDateTime = ZonedDateTime.of(2022, 11, 25, 8, 44, 48, 0, ZoneOffset.UTC)
        val response: SfResponse<Account> = client.getAll(Account::class.java)
        val actualZonedDateTime: ZonedDateTime? = response.records
            .first { account -> account.name == "sForce" }
            .createdDate
        Assertions.assertEquals(expectedZonedDateTime, actualZonedDateTime)
    }

    @Test
    fun testLocalDateDeserialization() {
        val expectedLocalDate: LocalDate = LocalDate.of(2023, 6, 22)
        val response: SfResponse<Account> = client.getAll(Account::class.java)
        val actualLocalDate: LocalDate? = response.records.first { account -> account.name == "GenePoint" }
            .sLAExpirationDate__c
        Assertions.assertEquals(expectedLocalDate, actualLocalDate)
    }

    @Disabled("Account doesn't have Time field, so skip this one")
    @Test
    fun testLocalTimeDeserialization() {
        val expectedLocalTime: LocalTime = LocalTime.of(1, 1, 1)
        val response: SfResponse<Account> = client.getAll(Account::class.java)
        //        LocalTime actualLocalTime = response.records
        val actualLocalTime: LocalTime = LocalTime.of(1, 1, 1)
        Assertions.assertEquals(expectedLocalTime, actualLocalTime)
    }

    // Custom object tests
    @Test
    fun loadAllCustomObject() {
        val response: SfResponse<TestObjectMM4__c> = client.getAll(TestObjectMM4__c::class.java)
        Assertions.assertEquals(1, response.totalSize)
    }
}
