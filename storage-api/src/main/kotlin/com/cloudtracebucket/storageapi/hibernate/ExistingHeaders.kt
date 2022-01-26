package com.cloudtracebucket.storageapi.hibernate

import com.cloudtracebucket.storageapi.hibernate.enums.TraceType
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column
import javax.persistence.ElementCollection

@Entity(name = "existing_headers")
@Where(clause = "delete_time IS NULL")
class ExistingHeaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "provider")
    var provider: String? = null

    @ElementCollection
    @Column(name = "headers")
    var headers: List<String>? = listOf()

    @Column(name = "dynamic_schema_name")
    var dynamicSchemaName: String? = null

    @Column(name = "trace_types")
    var traceType: TraceType? = null

    @Column(name = "create_time", insertable = false, updatable = false)
    var createTime: LocalDateTime? = null

    @Column(name = "update_time", insertable = false, updatable = false)
    var updateTime: LocalDateTime? = null

    @Column(name = "delete_time")
    var deleteTime: LocalDateTime? = null
}
