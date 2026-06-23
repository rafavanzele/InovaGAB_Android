package br.com.fiap.inovagab.data.remote.network

import br.com.fiap.inovagab.data.remote.api.StrategicGuidanceService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://6a0b70685aa893e1015a414d.mockapi.io/"

    val api: StrategicGuidanceService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(StrategicGuidanceService::class.java)

    }
}