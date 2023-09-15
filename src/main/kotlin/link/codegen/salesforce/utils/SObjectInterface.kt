package link.codegen.salesforce.utils

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

interface SObjectInterface

data class SObjectAttributes(
    val type: String,
    val url: String? = null,
)

abstract class AbstractSObject(
    objectName: String,
) : SObjectInterface {
    val attributes: SObjectAttributes = SObjectAttributes(type = objectName)

    @JsonProperty("Id")
    val id: String? = null
    @JsonProperty("CreatedById")
    var createdById: String? = null
    @JsonProperty("CreatedDate")
    var createdDate: ZonedDateTime? = null
    @JsonProperty("IsDeleted")
    var isDeleted: Boolean? = null
    @JsonProperty("LastModifiedById")
    var lastModifiedById: String? = null
    @JsonProperty("LastModifiedDate")
    var lastModifiedDate: ZonedDateTime? = null
    @JsonProperty("OwnerId")
    var ownerId: String? = null
    @JsonProperty("SystemModstamp")
    var systemModstamp: ZonedDateTime? = null
    @JsonProperty("UserRecordAccessId")
    var userRecordAccessId: String? = null
}
