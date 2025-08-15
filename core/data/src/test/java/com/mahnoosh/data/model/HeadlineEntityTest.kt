package com.mahnoosh.data.model

import com.mahnoosh.network.data.model.HeadlineDTO
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HeadlineEntityTest {

    @Test
    fun testMapToDatabaseModel() {
        val remoteModel = HeadlineDTO(
            id = "sample",
            title = "",
            description = "",
            content = "",
            url = "",
            image = "",
            publishedAt = ""
        )
        val localModel = remoteModel.toHeadlineEntity()

        assertEquals(remoteModel.id, localModel.id)
        assertEquals(remoteModel.title, localModel.title)
        assertEquals(remoteModel.description, localModel.description)
        assertEquals(remoteModel.content, localModel.content)
        assertEquals(remoteModel.url, localModel.url)
        assertEquals(remoteModel.image, localModel.image)
        assertEquals(remoteModel.publishedAt, localModel.publishedAt)
    }
}