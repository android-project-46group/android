package io.kokoichi.sample.sakamichiapp.presentation.quiz

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class QuizViewModelWithMockTest {
    private lateinit var viewModel: QuizViewModelWihMock

    @Before
    fun setUp() {
        viewModel = QuizViewModelWihMock(mockk(), mockk())

        // Initialize the members
        viewModel.setMembers(members = viewModel.fakeGetMembersApi())
    }

    @Test
    fun `generateQuizzes(), generate correct number of quizzes`() {
        // Arrange
        viewModel.setQuizType(QuizType.BIRTHDAY)
        viewModel.setQuizNum(5)

        // Act
        val quizzes = viewModel.generateQuizzes()

        // Assert
        assertThat(quizzes.size).isEqualTo(5)
    }

    @Test
    fun `generateQuizzes(), generate no duplicate choices`() {
        // Arrange
        viewModel.setQuizType(QuizType.BLOOD_TYPE)
        viewModel.setQuizNum(5)

        // Act
        val quizzes = viewModel.generateQuizzes()

        // Assert
        assertThat(quizzes.size).isEqualTo(5)
        quizzes.forEach { quiz ->
            assertThat(quiz.choices.distinct().size).isEqualTo(4)
        }
    }

    @Test
    fun `generateQuizzes() for generation, generate correct quiz choices`() {
        // Arrange
        viewModel.setQuizType(QuizType.GENERATION)
        viewModel.setQuizNum(5)

        // Act
        val quizzes = viewModel.generateQuizzes()

        // Assert
        val generationReg = """\dæç""".toRegex()
        quizzes.forEach { quiz ->
            quiz.choices.forEach { choice ->
                val isMatched = generationReg.matches(choice)
                assertThat(isMatched).isTrue()
            }
        }
    }

    @Test
    fun `generateQuizzes() for bloodType, generate correct quiz choices`() {
        // Arrange
        viewModel.setQuizType(QuizType.BLOOD_TYPE)
        viewModel.setQuizNum(5)

        // Act
        val quizzes = viewModel.generateQuizzes()

        // Assert
        val bloodTypeReg = """(Aå|Bå|Oå|ABå|äžæ)""".toRegex()
        quizzes.forEach { quiz ->
            quiz.choices.forEach { choice ->
                val isMatched = bloodTypeReg.matches(choice)
                assertThat(isMatched).isTrue()
            }
        }
    }

    @Test
    fun `generateQuizzes() for birthday, generate correct quiz choices`() {
        // Arrange
        viewModel.setQuizType(QuizType.BIRTHDAY)
        viewModel.setQuizNum(5)

        // Act
        val quizzes = viewModel.generateQuizzes()

        // Assert
        val birthdayReg = """\d{4}å¹Ž\d{1,2}æ\d{1,2}æ¥""".toRegex()
        quizzes.forEach { quiz ->
            quiz.choices.forEach { choice ->
                val isMatched = birthdayReg.matches(choice)
                assertThat(isMatched).isTrue()
            }
        }
    }

    @Test
    fun `generateQuizzes() for height, generate correct quiz choices`() {
        // Arrange
        viewModel.setQuizType(QuizType.HEIGHT)
        viewModel.setQuizNum(5)

        // Act
        val quizzes = viewModel.generateQuizzes()

        // Assert
        val heightReg = """1\d{2}(\.\d)?cm""".toRegex()     // 162.5cm
        quizzes.forEach { quiz ->
            quiz.choices.forEach { choice ->
                val isMatched = heightReg.matches(choice)
                assertThat(isMatched).isTrue()
            }
        }
    }

    @Test
    fun `getAnsByQuizType() for generation, returns correct type`() {
        // Arrange
        viewModel.setQuizType(QuizType.GENERATION)

        // Act
        val ans = viewModel.getAnsByQuizType(viewModel.uiState.value.members[0])

        // Assert
        val generationReg = """\dæç""".toRegex()
        assertThat(generationReg.matches(ans)).isTrue()
    }

    @Test
    fun `getAnsByQuizType() for bloodType, returns correct type`() {
        // Arrange
        viewModel.setQuizType(QuizType.BLOOD_TYPE)

        // Act
        val ans = viewModel.getAnsByQuizType(viewModel.uiState.value.members[0])

        // Assert
        val bloodTypeReg = """(Aå|Bå|Oå|ABå|äžæ)""".toRegex()
        assertThat(bloodTypeReg.matches(ans)).isTrue()
    }

    @Test
    fun `getAnsByQuizType() for birthday, returns correct type`() {
        // Arrange
        viewModel.setQuizType(QuizType.BIRTHDAY)

        // Act
        val ans = viewModel.getAnsByQuizType(viewModel.uiState.value.members[0])

        // Assert
        val birthdayReg = """\d{4}å¹Ž\d{1,2}æ\d{1,2}æ¥""".toRegex()
        assertThat(birthdayReg.matches(ans)).isTrue()
    }

    @Test
    fun `getAnsByQuizType() for height, returns correct type`() {
        // Arrange
        viewModel.setQuizType(QuizType.HEIGHT)

        // Act
        val ans = viewModel.getAnsByQuizType(viewModel.uiState.value.members[0])

        // Assert
        val heightReg = """1\d{2}(\.\d)?cm""".toRegex()
        assertThat(heightReg.matches(ans)).isTrue()
    }

    @Test
    fun `isLastQuiz() for the last one, returns true`() {
        // Arrange
        // CAUTION: 0 <= quizProgress <= quizNum - 1
        viewModel.setQuizNum(5)
        viewModel.setQuizProgress(4)

        // Act
        val result = viewModel.isLastQuiz()

        // Assert
        assertThat(result).isTrue()
    }

    @Test
    fun `isLastQuiz() for not the last ones, returns false`() {
        // Arrange
        viewModel.setQuizNum(5)
        viewModel.setQuizProgress(3)

        // Act
        val result = viewModel.isLastQuiz()

        // Assert
        assertThat(result).isFalse()
    }
}