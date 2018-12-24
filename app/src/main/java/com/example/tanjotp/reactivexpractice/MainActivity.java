package com.example.tanjotp.reactivexpractice;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private String[] greetings = {"Hello Apple","Hello Banana","Hello Orange" };
    private io.reactivex.Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObservable = io.reactivex.Observable.fromArray(greetings);

        mCompositeDisposable.add(
                myObservable.
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );
    }

    private DisposableObserver getObserver(){
        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                Log.i(TAG, "onNext invoked " + s);
            }

            @Override
            public void onError(final Throwable e) {
                Log.i(TAG, "onError invoked");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete invoked");
            }
        };

        return myObserver;
    }
}
