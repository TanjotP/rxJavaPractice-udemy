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
    private String greeting = "Hello From RxJava";
    private io.reactivex.Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private TextView mTextView;

    //private Disposable mDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=findViewById(R.id.greetingsText);
        myObservable = io.reactivex.Observable.just(greeting);

        myObservable.subscribeOn(Schedulers.io());

        myObservable.observeOn(AndroidSchedulers.mainThread());

        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                Log.i(TAG, "onNext");
                mTextView.setText(s);
            }

            @Override
            public void onError(final Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                 Log.i(TAG, "onComplete");

            }
        };

        mCompositeDisposable.add(myObserver);
        myObservable.subscribe(myObserver);

        myObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(final String s) {
                Log.i(TAG, "onNext");
                mTextView.setText(s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(final Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");

            }
        };

        mCompositeDisposable.add(myObserver2);
        myObservable.subscribe(myObserver2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
