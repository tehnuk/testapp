package com.zufr.testapp.network
import com.zufr.testapp.network.response.GetCharactersPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RAMService {
        //        @GET("character/")
        @GET("character/{character-id}")
//        fun getCharacterById(): Call<Any>
//        fun listRepos(@Path("user") user: String?): Call<List<Repo?>?>?
//        fun getCharacterById(
        suspend fun getCharacterById(
                @Path("character-id") characterId: Int
//        ): Call<GetCharacterByResponse>
        ): Response<GetCharacterByIdResponse>

        @GET("character")
        suspend fun getCharactersPage(
                @Query("page") pageIndex: Int
        ): Response<GetCharactersPageResponse>

}