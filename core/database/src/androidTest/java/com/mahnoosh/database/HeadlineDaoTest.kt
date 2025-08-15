package com.mahnoosh.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.mahnoosh.database.data.NewsDatabase
import com.mahnoosh.database.data.dao.HeadlineDao
import com.mahnoosh.database.data.model.HeadlineEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HeadlineDaoTest {

    private lateinit var db: NewsDatabase
    private lateinit var headlineDao: HeadlineDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java
        ).build()
        headlineDao = db.headlineDao()
    }

    @After
    fun teardown() = db.close()

    private val entities = listOf(
        createHeadlineEntity(
            id = "5e02ce47c8deb5a1c2954651dcafb5d3",
            title = "Spain Is Going Its Own Way",
            description = "The government’s humane and pragmatic approach to immigration is an example for others to emulate.",
            content = "Spain is having a moment bucking Western political trends. The country has recently recognized Palestine as a state, resisted President Trump’s demand that NATO members increase their defense spending to 5 percent of gross domestic product and double... [1687 chars]",
            url = "https://www.nytimes.com/2025/08/11/opinion/spain-immigration-sanchez-amnesty.html",
            image = "https://static01.nyt.com/images/2025/08/11/multimedia/11encarnacion-jvgf/11encarnacion-jvgf-facebookJumbo.jpg",
            publishedAt = "2025-08-11T05:00:05Z",
        )
    )

    @Test
    fun getHeadlines() = runTest {
        insertHeadlines()

        val headlines = headlineDao.getAllHeadlines()

        headlines.test {
            assertEquals(awaitItem(), entities)
        }
    }

    private suspend fun insertHeadlines() {
        headlineDao.upsertHeadlines(entities)
    }

    private fun createHeadlineEntity(
        id: String,
        title: String?,
        description: String?,
        content: String?,
        url: String?,
        image: String?,
        publishedAt: String?,
    ) = HeadlineEntity(
        id = id,
        title = title,
        description = description,
        content = content,
        url = url,
        image = image,
        publishedAt = publishedAt
    )
}