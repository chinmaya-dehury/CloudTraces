package com.cloudtracebucket.storageapi.hibernate

import com.cloudtracebucket.storageapi.pojo.enums.TraceType
import com.cloudtracebucket.storageapi.utils.PostgreSQLEnumUtil
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column
import javax.persistence.EnumType
import javax.persistence.Enumerated
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef

@Entity(name = "existing_headers")
@Where(clause = "delete_time IS NULL")
@TypeDef(name = "trace_types", typeClass = PostgreSQLEnumUtil::class)
class ExistingHeaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "provider")
    var provider: String? = null

    @Column(name = "file_headers")
    var headersListAsString: String? = null

    @Column(name = "dynamic_schema_name")
    var dynamicSchemaName: String? = null

    @Column(name = "trace_type")
    @Type(type = "trace_types")
    @Enumerated(EnumType.STRING)
    var traceType: TraceType? = null

    @Column(name = "target_schema")
    var targetSchema: String? = null

    @Column(name = "create_time", insertable = false, updatable = false)
    var createTime: LocalDateTime? = null

    @Column(name = "update_time", insertable = false, updatable = false)
    var updateTime: LocalDateTime? = null

    @Column(name = "delete_time")
    var deleteTime: LocalDateTime? = null
}
