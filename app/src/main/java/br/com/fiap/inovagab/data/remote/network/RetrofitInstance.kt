package br.com.fiap.inovagab.data.remote.network
import br.com.fiap.inovagab.data.remote.api.IdeaService

import br.com.fiap.inovagab.data.remote.api.AuthService
import br.com.fiap.inovagab.data.remote.api.StrategicGuidanceService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import br.com.fiap.inovagab.data.remote.api.ProjectService
import br.com.fiap.inovagab.data.remote.api.TeamService

object RetrofitInstance {

    private const val BASE_URL = "http://192.168.0.224:5223/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    val authApi: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val ideaApi: IdeaService by lazy {
        retrofit.create(IdeaService::class.java)
    }

    val projectApi: ProjectService by lazy {
        retrofit.create(ProjectService::class.java)
    }

    val api: StrategicGuidanceService by lazy {
        retrofit.create(StrategicGuidanceService::class.java)
    }

    val teamApi: TeamService by lazy {
        retrofit.create(TeamService::class.java)
    }
}