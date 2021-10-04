package com.docwei.retrofitdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.docwei.retrofitdemo.model.CourseInfo
import com.docwei.retrofitdemo.model.ExtendItem
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val tv1 = findViewById<TextView>(R.id.tv)

        btn1.setOnClickListener {

            HttpManager.getInstance()
                .create(Api::class.java).getExtendInfo1("11")
                .enqueue(object : Callback<ExtendItem> {
                    override fun onResponse(call: Call<ExtendItem>, response: Response<ExtendItem>) {
                        //主线程
                        Log.e("test", "响应-->" + response.body()?.title)

                    }
                    override fun onFailure(call: Call<ExtendItem>, t: Throwable) {
                        Log.e("test", "请求失败")
                    }
                })



        }
        btn2.setOnClickListener {

            HttpManager.getInstance()
                .create(Api::class.java).getExtendInfo2("11")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ExtendItem> {
                    override fun onSubscribe(d: Disposable) {
                        Log.e("test", "开始请求可以显示loading")
                    }
                    override fun onNext(t: ExtendItem) {
                        Log.e("test", "请求success-->" + t.title)
                    }
                    override fun onError(e: Throwable) {
                        Log.e("test", "请求出错", e)
                    }
                    override fun onComplete() {
                        Log.e("test", "完成请求")
                    }
                })



        }

        btn3.setOnClickListener {


            lifecycleScope.launch {
                try {
                    // continuation.resumeWithException(t)
                    val extendItem = HttpManager.getInstance().create(Api::class.java).getExtendInfo3("11")

                    tv1.setText(extendItem.title)
                } catch (e: Exception) {
                    Log.e("test", "test", e)
                }

            }



        }

        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                Thread.sleep(100)
                emitter.onNext("ok")
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(t: String) {
                }
                override fun onError(e: Throwable) {
                }
                override fun onComplete() {
                }
            })





        btn4.setOnClickListener {
            val info = "{\"bcmSubmit\":0,\"courseId\":2606,\"packageId\":1241,\"playTime\":0,\"termId\":2717}"
            HttpManager.getInstance()
                .create(Api::class.java).getCourse(Gson().fromJson(info, CourseInfo::class.java))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        Log.e("test", "用户的onSubscribe")
                    }
                    override fun onNext(t: Int) {
                        Log.e("test", "用户的onNext-->" + t)
                    }
                    override fun onError(e: Throwable) {
                        Log.e("test", "请求出错", e)
                    }
                    override fun onComplete() {
                        Log.e("test", "用户的onComplete")
                    }

                })
        }


        val mediaType: MediaType = MediaType.parse("application/json; charset=UTF-8")!!
        val requestBody =
            "{\"bcmSubmit\":0,\"courseId\":2606,\"packageId\":1241,\"playTime\":0,\"termId\":2717}"
        val request: Request = Request.Builder()
            .url("https://codecamp-app.codemao.cn/app/courses/behaviour")
            .post(RequestBody.create(mediaType, requestBody))
            .build()
        HttpManager.getInstance().mOkHttpClient.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("test", "okhttp-->fail",e)
            }
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.code() == 200 && response.isSuccessful) {
                    val value = response.body()?.string()
                    Log.e("test", "okhttp-->${value}")
                }
            }

        })


        val api2 = Proxy.newProxyInstance(
            classLoader,
            arrayOf(Api::class.java),
            object : InvocationHandler {
                override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
                    return ExtendItem(null, null);
                }
            })
        Log.e("test", "${api2 is Api}")
    }
}