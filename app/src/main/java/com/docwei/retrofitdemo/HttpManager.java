package com.docwei.retrofitdemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import gson.GsonConverterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rxjava2.RxJava2CallAdapterFactory;
import scalars.ScalarsConverterFactory;


/**
 * Created by LiuG on 2018/11/5.
 */

public class HttpManager {
    private static final int CONNECT_TIME_OUT = 20; //连接时间
    private static final int READ_TIME_OUT = 20;    //读取时间
    private static final int WRITE_TIME_OUT = 20;   //写入时间
    private Retrofit mRetrofit;
    public OkHttpClient mOkHttpClient;
    private Map<Class<?>, Object> mMap = new HashMap<>();

    private static class SingletonInstance {
        private static final HttpManager INSTANCE = new HttpManager();
    }
    public static HttpManager getInstance() {
        return SingletonInstance.INSTANCE;
    }
    private HttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor());
        mOkHttpClient=builder.build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl("https://codecamp-app.codemao.cn")
                .addConverterFactory(ScalarsConverterFactory.create())//转换为String对象
                .addConverterFactory(GsonConverterFactory.create())//转换为Gson对象
                //接口返回值适配
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
    public <T> T create(Class<T> cls) {
        if (!mMap.containsKey(cls)) {
            T t = mRetrofit.create(cls);
            mMap.put(cls, t);
        }
        return (T) mMap.get(cls);
    }

}

