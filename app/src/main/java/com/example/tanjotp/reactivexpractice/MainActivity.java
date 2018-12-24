package com.example.tanjotp.reactivexpractice;

import org.w3c.dom.Text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private String greeting = "Hello From RxJava";
    private io.reactivex.Observable<String> myObservable;
    private Observer<String> myObserver;
    private TextView mTextView;
    private Disposable mDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=findViewById(R.id.greetingsText);
        myObservable = io.reactivex.Observable.just(greeting);

        myObservable.subscribeOn(Schedulers.io());

        myObservable.observeOn(AndroidSchedulers.mainThread());

        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(final Disposable d) {
                Log.i(TAG, "onSubscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(final String value) {
                Log.i(TAG, "onNext");
                mTextView.setText(value);
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

        myObservable.subscribe(myObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
