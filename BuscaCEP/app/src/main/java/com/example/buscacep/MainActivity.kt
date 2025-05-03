package com.example.buscacep

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.buscacep.api.ApiCep
import com.example.buscacep.databinding.ActivityMainBinding
import com.example.buscacep.model.Endereco
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#FF018786")
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF018786")))

        // Configurr o Retrofit
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://viacep.com.br/ws/")
            .build()
            .create(ApiCep::class.java)

        binding.btnBuscarCEP.setOnClickListener {
                val cep = binding.edtCep.text.toString()

            if (cep.isEmpty()){
                Toast.makeText(this,"Preencha o CEP! ;)", Toast.LENGTH_SHORT).show()
            } else {

                retrofit.setEndereco(cep).enqueue(object : Callback<Endereco>{
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        if (response.code() == 200){
                            val logradouro = response.body()?.logradouro.toString()
                            val bairro = response.body()?.bairro.toString()
                            val localidade = response.body()?.localidade.toString()
                            val uf = response.body()?.uf.toString()
                            setFormulários(logradouro, bairro, localidade, uf)
                        } else {
                            Toast.makeText(applicationContext,"CEP inválido!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        Toast.makeText(applicationContext,"ERRO inesperado!", Toast.LENGTH_SHORT).show()
                    }

                })

            }
        }
    }

    private fun setFormulários(logradouro: String, bairro: String, localidade: String, uf: String){
        binding.edtLogradouro.setText(logradouro)
        binding.edtBairro.setText(bairro)
        binding.edtCidade.setText(localidade)
        binding.edtEstado.setText(uf)
    }
}