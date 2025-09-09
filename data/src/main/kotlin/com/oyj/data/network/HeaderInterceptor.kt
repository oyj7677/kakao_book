package com.oyj.data.network

import com.oyj.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().run {
            addHeader(AUTHORIZATION, AUTHORIZATION_VALUE)
            chain.proceed(build())
        }
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val AUTHORIZATION_VALUE = "KakaoAK ${BuildConfig.KAKAO_NATIVE_APP_KEY}"
    }
}