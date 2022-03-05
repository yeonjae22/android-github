package camp.nextstep.edu.github.data.remote.service

import camp.nextstep.edu.github.data.remote.model.RepositoryDto
import retrofit2.http.GET

internal interface GithubService {

    @GET("repositories")
    suspend fun getRepositories(): List<RepositoryDto>
}