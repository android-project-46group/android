package jp.mydns.kokoichi0206.data.remote

import com.squareup.moshi.JsonDataException
import jp.mydns.kokoichi0206.data.remote.dto.MemberDto
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class SakamichiApiImplTest {

    private val mockWebServer = MockWebServer()

    lateinit var api: SakamichiApi

    @Before
    fun setUp() {
        api = createSakamichiApi(mockWebServer.url("/").toString())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get_members success`() = runBlocking {
        // Arrange
        val response = MockResponse()
            .setBody("{\"members\":[{\"id\":0,\"name\":\"名前 です\",\"birthday\":\"12/26/1997\",\"height\":\"157.5cm\",\"blood_type\":\"Type O\",\"generation\":\"1st generation\",\"blog_url\":\"https://hinatazaka46.com/s/official/diary/member/list?ima=0000\\u0026ct=2\",\"img_url\":\"https://kokoichi0206.mydns.jp/imgs/hinata/namae.jpeg\"}]}")
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(response)
        val expected = listOf(
            MemberDto(
                birthday = "12/26/1997",
                blogUrl = "https://hinatazaka46.com/s/official/diary/member/list?ima=0000&ct=2",
                bloodType = "Type O",
                generation = "1st generation",
                height = "157.5cm",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/hinata/namae.jpeg",
                userId = 0,
                nameName = "名前 です",
            )
        )

        // Act
        val resp = api.getMembers(
            groupName = "hinatazaka",
            apiKey = "valid_api_key",
        )

        // Assert
        assertNotNull(resp)
        assertEquals(expected, resp.members)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("GET", recordedRequest.method)
        assertEquals("/api/v1/members?gn=hinatazaka&key=valid_api_key", recordedRequest.path)
    }

    @Test(expected = JsonDataException::class)
    fun `get_members with undecodable response should throw exception`() = runBlocking {
        // Arrange
        val response = MockResponse()
            .setBody("{\"error\":\"Something unexpected happened.\"}")
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(response)

        // Act
        val resp = api.getMembers(
            groupName = "hinatazaka",
            apiKey = "valid_api_key",
        )

        // Assert
        // Exception 'moshi.JsonDataException' is expected
        // and this is checked in @Test annotation.
    }
}