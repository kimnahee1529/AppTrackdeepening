package com.nhkim.miseya.retrofit

import com.nhkim.miseya.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//Retrofit Client
object NetWorkClient {
    private const val DUST_BASE_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/" // BASE_URL은 "/"를 넣어야 오류가 안 생긴대!!!! 왜 강의자료랑 사이트랑 URL이 다르지?!?!!!??

    private fun createOkHttpClient(): OkHttpClient {
        val intercepter = HttpLoggingInterceptor()

        //통신이 잘 안 될 때 디버깅을 위한 용도로 넣은 것
        if (BuildConfig.DEBUG){
            intercepter.level = HttpLoggingInterceptor.Level.BODY
        } else{
            intercepter.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .connectTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(intercepter)
            .build()
    }

    private val dustRetrofit = Retrofit.Builder()
        .baseUrl(DUST_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    val dustNetWork: NetworkInterface = dustRetrofit.create(NetworkInterface::class.java)
}