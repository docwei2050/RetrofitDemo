package com.docwei.retrofitdemo.model;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class MyObservable extends Observable<String> {
    @Override
    protected void subscribeActual(Observer<? super String> observer) {
        Log.e("test","subscribeActual被调用");
        try {
            Thread.sleep(4000);
            observer.onNext("you are a winner--》"+Thread.currentThread().getName());
            //onNext要在onComplete之前先调用哈
            observer.onComplete();
        } catch (InterruptedException e) {
            e.printStackTrace();
            observer.onError(e);
        }

    }
}
