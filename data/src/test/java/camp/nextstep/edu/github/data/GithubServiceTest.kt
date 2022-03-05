package camp.nextstep.edu.github.data

import camp.nextstep.edu.github.data.remote.service.GithubService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

internal class GithubServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var service: GithubService

    @BeforeEach
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubService::class.java)
    }

    @Test
    @DisplayName("깃허브 API 성공 데이터 확인")
    fun test1() = runBlocking {
        // given
        val response = MockResponse()
            .setBody(File("src/test/resources/repository_success.json").readText())
        server.enqueue(response)

        // when
        val actual = service.getRepositories()

        // then
        val firstItem = actual[0]
        val secondItem = actual[1]

        assertAll(
            { assertThat(firstItem.fullName).isEqualTo("blackJin") },
            { assertThat(firstItem.description).isEqualTo("description") },
            { assertThat(secondItem.fullName).isEqualTo(null) },
            { assertThat(secondItem.description).isEqualTo(null) },
            { assertThat(actual.size).isEqualTo(2) }
        )
    }
}