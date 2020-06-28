package com.example.cardbe.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

    companion object {

        /** Retorna uma Instância do Client Retrofit para Requisições
         * @param path Caminho Principal da API
         */
        fun getRetrofitInstance(path : String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun request() : Endpoint{
            val retrofitClient = getRetrofitInstance("http://192.168.1.68:8000/api/")
            return retrofitClient.create(Endpoint::class.java)
        }
    }
}