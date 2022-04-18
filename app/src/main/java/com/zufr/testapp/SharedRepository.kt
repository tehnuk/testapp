package com.zufr.testapp

import com.zufr.testapp.network.GetCharacterByIdResponse
import com.zufr.testapp.network.NetworkLayer

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

//        if (request.isSuccessful) {
//            return request.body()!!
//        }
//
//        return null
        if (request.failed) {
            return null
        }
        if (!request.isSuccessful) {
            return null
        }

        return request.body
    }
}