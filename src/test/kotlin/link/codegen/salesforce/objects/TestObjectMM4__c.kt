package link.codegen.salesforce.objects

import link.codegen.salesforce.utils.AbstractSObject
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @property name Text(80)
 * @property phone__c Phone
 */
class TestObjectMM4__c (
    @JsonProperty("Name") val name: String? = null,
    @JsonProperty("phone__c") val phone__c: String? = null,
) : AbstractSObject(objectName = "TEST_OBJECT_MM4__C")
