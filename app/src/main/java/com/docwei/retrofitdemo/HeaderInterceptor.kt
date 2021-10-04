package com.docwei.retrofitdemo

import android.text.TextUtils
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

internal class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val jsonObject=JSONObject("{\"platform\":\"android\",\"uid\":\"0\",\"swvc\":371,\"swvn\":\"3.7.1\",\"model\":\"samsung+SM-G9600\",\"resolution\":\"1440x2960\",\"la\":\"CN_zh\",\"sysv\":\"10\",\"pid\":\"naiIBq91\",\"pkg\":\"cn.codemao.android.kids.lite\",\"policy\":true,\"channel\":\"sumsung\"}")
        builder.addHeader("X-CodeMao-Mobile-Requested", jsonObject.toString())
        builder.addHeader("Device-Data", "eyJkZXZpY2VOYW1lIjoic2Ftc3VuZyIsImRldmljZU1vZGVsIjoiU00tRzk2MDAiLCJkZXZpY2VUeXBlIjowLCJkZXZpY2VJZCI6IiIsInVtZW5nSWQiOiI1NTk0MWU1ZTI3NzY3OGIwIiwiZGV2aWNlU24iOiJmZmZmZmZmZi1mMzcyLWJjMzUtMDAwMC0wMDAwNWM2ZDY1ZjIifQ==")
        builder.addHeader("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJDb2RlbWFvIEF1dGgiLCJ1c2VyX3R5cGUiOiJzdHVkZW50IiwiZGV2aWNlX2lkIjoxNzgzMzYsInVzZXJfaWQiOjg0OTgyNDQsImlzcyI6IkF1dGggU2VydmljZSIsInBpZCI6Im5haUlCcTkxIiwiZXhwIjoxNjM3MTMzMTg5LCJpYXQiOjE2MzMyNDUxODksImp0aSI6IjljNDMyNzQ0LWY1ODgtNGJiNi05MjIwLWY5MWI0OTI3ZTJlNiJ9.GdXFX2S9WA5dVUTxyn5rXAMYTeHZ9dDEygMC8F5jJhg")
        val request = builder.build()
        return chain.proceed(request)
    }
}