package com.oyj.di.network

import com.oyj.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
        newBuilder.addHeader(AUTHORIZATION, AUTHORIZATION_VALUE).build()
        val request = newBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val AUTHORIZATION_VALUE = "KakaoAK ${BuildConfig.KAKAO_NATIVE_APP_KEY}"
    }
}