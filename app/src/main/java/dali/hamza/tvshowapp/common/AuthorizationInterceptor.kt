package dali.hamza.tvshowapp.common

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response


class AuthorizationInterceptor(private val headers: HashMap<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain
            .request()
            .newBuilder()
            .headers(Headers.of(headers))
            .build())
    }


}