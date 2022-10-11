package jp.mydns.kokoichi0206.sakamichiapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.QuizRecord
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class QuizRecordDaoTest {

    // Run tests synchronously
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: QuizRecordDatabase
    private lateinit var dao: QuizRecordDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuizRecordDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.quizRecordDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getRecordsByGroup() = runBlockingTest {
        // Arrange
        val record = QuizRecord(
            groupName = "NOGIZAKA",
            type = "BIRTHDAY",
            correctNum = 1,
            totalNum = 5,
        )
        dao.insertQuizRecord(record)

        // Act
        val result = dao.getRecordsByGroup("NOGIZAKA")

        // Assert
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].groupName).isEqualTo("NOGIZAKA")
        assertThat(result[0].type).isEqualTo("BIRTHDAY")
        assertThat(result[0].correctNum).isEqualTo(1)
        assertThat(result[0].totalNum).isEqualTo(5)
    }

    @Test
    fun getRecordsByGroupForNoRecord_returnsEmpty() = runBlockingTest {
        // Arrange

        // Act
        val result = dao.getRecordsByGroup("NOGIZAKA")

        // Assert
        assertThat(result.isEmpty())
    }

    @Test
    fun getRecordByGroupAndTag() = runBlockingTest {
        // Arrange
        val record = QuizRecord(
            groupName = "NOGIZAKA",
            type = "BIRTHDAY",
            correctNum = 1,
            totalNum = 5,
        )
        dao.insertQuizRecord(record)

        // Act
        val result = dao.getRecordByGroupAndType("NOGIZAKA", "BIRTHDAY")

        // Assert
        assertThat(result?.groupName).isEqualTo("NOGIZAKA")
        assertThat(result?.type).isEqualTo("BIRTHDAY")
        assertThat(result?.correctNum).isEqualTo(1)
        assertThat(result?.totalNum).isEqualTo(5)
    }

    @Test
    fun getRecordByGroupAndTagForNoRecord_returnsNull() = runBlockingTest {
        // Arrange

        // Act
        val result = dao.getRecordByGroupAndType("NOGIZAKA", "BIRTHDAY")

        // Assert
        assertNull(result)
    }
}
