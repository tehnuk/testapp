package com.zufr.testapp.characters

import com.zufr.testapp.network.NetworkLayer
import com.zufr.testapp.network.response.GetCharactersPageResponse


class CharactersRepository {

    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}