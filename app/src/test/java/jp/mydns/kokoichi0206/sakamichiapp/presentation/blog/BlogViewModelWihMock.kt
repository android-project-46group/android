package jp.mydns.kokoichi0206.sakamichiapp.presentation.blog

import usecase.get_blogs.GetBlogsUseCase

/**
 * Mock ViewModel of MemberList Screen
 */
class BlogViewModelWihMock(
    getBlogsUseCase: usecase.get_blogs.GetBlogsUseCase
) : BlogViewModel (
    getBlogsUseCase
) {
    /**
     * Api return example
     *
     * @return MutableList of Member data class
     */
    fun fakeGetBlogsApi(): MutableList<jp.mydns.kokoichi0206.model.Blog> {
        val fakeRes = mutableListOf<jp.mydns.kokoichi0206.model.Blog>()
        val fakeResStr = arrayOf(
            arrayOf("松田好花", "https://blog.example.com", "https://img.url", "2021/10/23 11:12"),
            arrayOf("金村美玖", "https://blog.example.com", "https://img.url", "2021/11/13 13:12"),
            arrayOf("丹生明里", "https://blog.example.com", "https://img.url", "2021/10/23 11:16"),
            arrayOf("松田好花", "https://blog.example.com", "https://img.url", "2021/03/23 23:23"),
            arrayOf("渡邉美穂", "https://blog.example.com", "https://img.url", "2020/12/23 21:12"),
            arrayOf("影山優佳", "https://blog.example.com", "https://img.url", "2021/11/04 04:12"),
            arrayOf("高本彩花", "https://blog.example.com", "https://img.url", "2021/09/09 09:09"),
            arrayOf("加藤史帆", "https://blog.example.com", "https://img.url", "2021/10/12 12:12"),
            arrayOf("山口陽世", "https://blog.example.com", "https://img.url", "2021/11/03 19:42"),
            arrayOf("上村ひなの", "https://blog.example.com", "https://img.url", "2021/10/16 15:52"),
        )
        fakeResStr.forEach {
            fakeRes.add(
                jp.mydns.kokoichi0206.model.Blog(
                    name = it[0],
                    blogUrl = it[1],
                    lastBlogImg = it[2],
                    lastUpdatedAt = it[3],
                )
            )
        }
        return fakeRes
    }
}
