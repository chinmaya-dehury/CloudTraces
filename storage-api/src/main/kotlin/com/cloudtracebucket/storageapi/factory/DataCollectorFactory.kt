package com.cloudtracebucket.storageapi.factory

import com.cloudtracebucket.storageapi.pojo.request.DataCollectorRequest
import org.springframework.stereotype.Component

@Component
class DataCollectorFactory {

    fun createRequest(uuid: String, existingHeaderId: Long, insertTime: String) = DataCollectorRequest(
        uuid,
        existingHeaderId,
        insertTime
    )
}
