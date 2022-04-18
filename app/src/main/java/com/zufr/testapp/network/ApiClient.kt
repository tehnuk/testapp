package com.zufr.testapp.network

import com.zufr.testapp.network.response.GetCharactersPageResponse
import retrofit2.Response

class ApiClient (
    private val ramService: RAMService
    )
//        {
//            suspend fun getCharacterById(characterId: Int): Response<GetCharacterByIdResponse> {
//                return ramService.getCharacterById(characterId)
//        }
  {
        suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
            return safeApiCall { ramService.getCharacterById(characterId) }
        }
        suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharactersPageResponse> {
            return safeApiCall { ramService.getCharactersPage(pageIndex) }
        }


        private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
            return try {
                SimpleResponse.success(apiCall.invoke())
            } catch (e: Exception) {
                SimpleResponse.failure(e)
            }
        }
    }
