package com.cloudtracebucket.storageapi.hibernate

import java.time.LocalDateTime
import javax.persistence.*
import org.hibernate.annotations.Where

@Entity(name = "file_meta")
@Where(clause = "delete_time IS NULL")
class FileMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "file_name")
    var fileName: String? = null

    @Column(name = "file_size")
    var fileSize: Long? = null

    @Column(name = "file_format")
    var fileFormat: String? = null

    @Column(name = "provider")
    var provider: String? = null

    @OneToOne(targetEntity = ExistingHeaders::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "headers_schema_id")
    var existingHeader: ExistingHeaders? = null

    @Column(name = "upload_time", insertable = false, updatable = false)
    var uploadTime: LocalDateTime? = null

    @Column(name = "update_time", insertable = false, updatable = false)
    var updateTime: LocalDateTime? = null

    @Column(name = "delete_time")
    var deleteTime: LocalDateTime? = null
}
