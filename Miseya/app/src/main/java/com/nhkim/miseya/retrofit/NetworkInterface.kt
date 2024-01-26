package com.nhkim.miseya.retrofit

import Dust
import retrofit2.http.GET
import retrofit2.http.QueryMap

//어느 주소로 어떤 요청 변수를 주고 어떤 값을 받는지
interface NetworkInterface {
    @GET("getCtprvnRltmMesureDnsty") //시도별 실시간 측정정보 조회 주소
    suspend fun getDust(@QueryMap param: HashMap<String, String>): Dust //HashMap에 요청변수들이 들어감 ex)서비스키, 페이지 번호, 시도명 ...
}