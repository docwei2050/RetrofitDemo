package com.docwei.retrofitdemo

import com.docwei.retrofitdemo.model.CourseInfo
import com.docwei.retrofitdemo.model.ExtendItem
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.SkipCallbackExecutor
import retrofit2.http.*
import kotlin.coroutines.Continuation

/**
 * Created by liwk on 2021/10/2.
 */
interface Api {

    @GET("tab/extend/v2")
    fun getExtendInfo1(@Query("a") a:String): Call<ExtendItem>




    @GET("tab/extend/v2")
    fun getExtendInfo2(@Query("a") a:String): Observable<ExtendItem>



    @GET("tab/extend/v2")
    suspend fun getExtendInfo3(@Query("a") a:String): ExtendItem

    @SkipCallbackExecutor
    @GET("tab/extend/v2")
    fun getExtendInfo4(@Query("a") a:String,callback:Continuation<ExtendItem>): Call<ExtendItem>







    @POST("app/courses/behaviour")
    fun getCourse(@Body info: CourseInfo): Observable<Int>


    @FormUrlEncoded
    @POST("tab/extend/v2")
    fun getExtendInfo4(@Field("a") a:Int): Observable<ExtendItem>






}


