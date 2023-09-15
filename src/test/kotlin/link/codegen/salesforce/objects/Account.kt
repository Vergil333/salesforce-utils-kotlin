package link.codegen.salesforce.objects

import link.codegen.salesforce.utils.AbstractSObject
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * @property accountNumber Text(40)
 * @property accountSource Picklist
 * @property active__c Picklist
 * @property annualRevenue Currency(18, 0)
 * @property billingAddress Address
 * @property cleanStatus Picklist
 * @property customerPriority__c Picklist
 * @property dandbCompanyId Lookup(D&B Company)
 * @property description Long Text Area(32000)
 * @property dunsNumber Text(9)
 * @property fax Fax
 * @property industry Picklist
 * @property jigsaw Text(20)
 * @property jigsawCompanyId External Lookup
 * @property lastActivityDate Date
 * @property lastReferencedDate Date/Time
 * @property lastViewedDate Date/Time
 * @property masterRecordId Lookup(Account)
 * @property naicsCode Text(8)
 * @property naicsDesc Text(120)
 * @property name Name
 * @property numberOfEmployees Number(8, 0)
 * @property numberofLocations__c Number(3, 0)
 * @property operatingHoursId Lookup(Operating Hours)
 * @property ownership Picklist
 * @property parentId Hierarchy
 * @property phone Phone
 * @property photoUrl URL(255)
 * @property rating Picklist
 * @property sLAExpirationDate__c Date
 * @property sLASerialNumber__c Text(10)
 * @property sLA__c Picklist
 * @property shippingAddress Address
 * @property sic Text(20)
 * @property sicDesc Text(80)
 * @property site Text(80)
 * @property tickerSymbol Content(20)
 * @property tradestyle Text(255)
 * @property type Picklist
 * @property upsellOpportunity__c Picklist
 * @property website URL(255)
 * @property yearStarted Text(4)
 */
class Account (
    @JsonProperty("AccountNumber") val accountNumber: String? = null,
    @JsonProperty("AccountSource") val accountSource: String? = null,
    @JsonProperty("Active__c") val active__c: String? = null,
    @JsonProperty("AnnualRevenue") val annualRevenue: Double? = null,
    @JsonProperty("BillingAddress") val billingAddress: Map<String, Any?>? = null,
    @JsonProperty("CleanStatus") val cleanStatus: String? = null,
    @JsonProperty("CustomerPriority__c") val customerPriority__c: String? = null,
    @JsonProperty("DandbCompanyId") val dandbCompanyId: String? = null,
    @JsonProperty("Description") val description: String? = null,
    @JsonProperty("DunsNumber") val dunsNumber: String? = null,
    @JsonProperty("Fax") val fax: String? = null,
    @JsonProperty("Industry") val industry: String? = null,
    @JsonProperty("Jigsaw") val jigsaw: String? = null,
    @JsonProperty("JigsawCompanyId") val jigsawCompanyId: String? = null,
    @JsonProperty("LastActivityDate") val lastActivityDate: LocalDate? = null,
    @JsonProperty("LastReferencedDate") val lastReferencedDate: ZonedDateTime? = null,
    @JsonProperty("LastViewedDate") val lastViewedDate: ZonedDateTime? = null,
    @JsonProperty("MasterRecordId") val masterRecordId: String? = null,
    @JsonProperty("NaicsCode") val naicsCode: String? = null,
    @JsonProperty("NaicsDesc") val naicsDesc: String? = null,
    @JsonProperty("Name") val name: String,
    @JsonProperty("NumberOfEmployees") val numberOfEmployees: Double? = null,
    @JsonProperty("NumberofLocations__c") val numberofLocations__c: Double? = null,
    @JsonProperty("OperatingHoursId") val operatingHoursId: String? = null,
    @JsonProperty("Ownership") val ownership: String? = null,
    @JsonProperty("ParentId") val parentId: Map<String, Any?>? = null,
    @JsonProperty("Phone") val phone: String? = null,
    @JsonProperty("PhotoUrl") val photoUrl: String? = null,
    @JsonProperty("Rating") val rating: String? = null,
    @JsonProperty("SLAExpirationDate__c") val sLAExpirationDate__c: LocalDate? = null,
    @JsonProperty("SLASerialNumber__c") val sLASerialNumber__c: String? = null,
    @JsonProperty("SLA__c") val sLA__c: String? = null,
    @JsonProperty("ShippingAddress") val shippingAddress: Map<String, Any?>? = null,
    @JsonProperty("Sic") val sic: String? = null,
    @JsonProperty("SicDesc") val sicDesc: String? = null,
    @JsonProperty("Site") val site: String? = null,
    @JsonProperty("TickerSymbol") val tickerSymbol: String? = null,
    @JsonProperty("Tradestyle") val tradestyle: String? = null,
    @JsonProperty("Type") val type: String? = null,
    @JsonProperty("UpsellOpportunity__c") val upsellOpportunity__c: String? = null,
    @JsonProperty("Website") val website: String? = null,
    @JsonProperty("YearStarted") val yearStarted: String? = null,
) : AbstractSObject(objectName = "ACCOUNT")
