package com.example.buscacep.api

import com.example.buscacep.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiCep {
    @GET("ws/{cep}/json/")
    fun setEndereco(@Path("cep") cep: String): Call<Endereco>

}