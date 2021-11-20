package io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.kokoichi.sample.sakamichiapp.data.repository.FakeQuizRecordRepository
import io.kokoichi.sample.sakamichiapp.domain.model.QuizRecord
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAccuracyRateByGroupTest {

    // Run tests synchronously
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var usecase: GetAccuracyRateByGroupUseCase
    lateinit var fakeRepo: FakeQuizRecordRepository

    @Before
    fun setUp() {
        fakeRepo = FakeQuizRecordRepository()
        usecase = GetAccuracyRateByGroupUseCase(fakeRepo)
    }

    @Test
    fun `GetAccuracyUseCase() for one record returns correct`() = runBlockingTest {
        // Arrange
        fakeRepo.insertRecord(
            QuizRecord("NOGIZAKA", "BIRTHDAY", 1, 5)
        )

        // Act
        val result = usecase("NOGIZAKA")

        // Assert
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(1)
        assertThat(result[1]).isEqualTo(5)
    }

    @Test
    fun `GetAccuracyUseCase() for records returns correct`() = runBlockingTest {
        // Arrange
        fakeRepo.insertRecord(
            QuizRecord("NOGIZAKA", "BIRTHDAY", 1, 5)
        )
        fakeRepo.insertRecord(
            QuizRecord("NOGIZAKA", "BIRTHDAY", 4, 5)
        )

        // Act
        val result = usecase("NOGIZAKA")

        // Assert
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(5)
        assertThat(result[1]).isEqualTo(10)
    }

    @Test
    fun `GetAccuracyUseCase() for no record returns zero value`() = runBlockingTest {
        // Arrange

        // Act
        val result = usecase("NOGIZAKA")

        // Assert
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(0)
        assertThat(result[1]).isEqualTo(0)
    }
}
