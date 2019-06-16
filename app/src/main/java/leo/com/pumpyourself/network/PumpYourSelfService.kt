package leo.com.pumpyourself.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PumpYourSelfService {

  private const val BASE_URL = "http://upe.pl.ua:8080/"

  val service: PumpYourselfApi = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(OkHttpClient())
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(PumpYourselfApi::class.java)
}