package io.kokoichi.sample.sakamichiapp.presentation.blog

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BlogViewModelWithMockTest {
    private lateinit var viewModel: BlogViewModelWihMock

    @Before
    fun setUp() {
        viewModel = BlogViewModelWihMock(mockk())

        // Initialize the members
        viewModel.setBlogs(blogs = viewModel.fakeGetBlogsApi())
    }

    @Test
    fun `sortBlogs(), sort lastUpdatedAt time correctly`() {
        // Arrange
        viewModel.setIsSortTime(true)

        // Act
        viewModel.sortBlogs()

        // Assert
        val blogs = viewModel.uiState.value.blogs
        for (i in 0..blogs.size - 2) {
            assertThat(blogs[i].lastUpdatedAt)
                .isGreaterThanOrEqualTo(blogs[i+1].lastUpdatedAt)
        }
    }

}