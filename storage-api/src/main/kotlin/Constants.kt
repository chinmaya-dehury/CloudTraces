import com.cloudtracebucket.storageapi.pojo.enums.TraceType

object Constants {
    val MINIO_INTERNAL_HOST: String = System.getenv("MINIO_INTERNAL_HOST")
    val MINIO_BUCKET_NAME: String = System.getenv("MINIO_BUCKET_NAME")
    val DATA_COLLECTOR_URL: String = System.getenv("DATA_COLLECTOR_URL")
    val DATA_COLLECTOR_USERNAME: String? = System.getenv("DATA_COLLECTOR_USERNAME")
    val DATA_COLLECTOR_PASSWORD: String? = System.getenv("DATA_COLLECTOR_PASSWORD")

    val targetTableMap: Map<TraceType, String> = mapOf(
        TraceType.SERVERLESS_PLATFORM to "serverless_platform",
        TraceType.CLOUD_STORAGE to "cloud_storage",
        TraceType.CLOUD_CLUSTER to "cloud_cluster",
        TraceType.CLOUD_INSTANCES to "cloud_instances",
        TraceType.MACHINES to "machines",
        TraceType.CLOUD_JOB to "cloud_job",
        TraceType.RESOURCES to "resources",
        TraceType.COLLECTIONS to "collections"
    )
}
