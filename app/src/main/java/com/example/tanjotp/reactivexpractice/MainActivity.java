package com.example.tanjotp.reactivexpractice;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private io.reactivex.Observable<Integer> myObservable = io.reactivex.Observable.just(1,2,3,5,8,2,9,4);
    private DisposableObserver<Student> myObserver;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(4)
                .subscribe(new Observer<Integer>() {
                               @Override
                               public void onSubscribe(final Disposable d) {
                               }

                               @Override
                               public void onNext(final Integer integer) {
                                   Log.i(TAG, "onNext" + integer);
                               }

                               @Override
                               public void onError(final Throwable e) {
                               }

                               @Override
                               public void onComplete() {
                               }
                           });

                        //myObservable = myObservable.range(1,20);

        /*myObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(final Integer integer) throws Exception {
                        return integer%3==0;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final Integer integer) {
                        Log.i(TAG, "onNext " + integer);

                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*
        myObservable = io.reactivex.Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(final ObservableEmitter<Student> emitter) throws Exception {
                ArrayList<Student> studentArrayList = getStudents();

                for(Student student:studentArrayList){
                    emitter.onNext(student);
                }
                emitter.onComplete();
            }
        });

        mCompositeDisposable.add(
                myObservable.
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())

                        .flatMap(new Function<Student, ObservableSource<?>>() {
                            @Override

                            public ObservableSource<?> apply(final Student student) throws Exception {
                                Student studentDisguisedAsTeacher=new Student();
                                studentDisguisedAsTeacher.setName(student.getName());
                                studentDisguisedAsTeacher.setName("Teacher disguised as StudentModel");
                                return io.reactivex.Observable.just(student,studentDisguisedAsTeacher);
                            }
                        })
                        .map(new Function<Student, Student>() { //map" input item , output item ||| flatmap" input item, outputs observable
                            @Override
                            public Student apply(final Student student) throws Exception {
                                student.setName(student.getName().toUpperCase());
                                return student;
                            }
                        })
                        .subscribeWith(getObserver())

        ); */
    }

    private DisposableObserver getObserver(){
        myObserver = new DisposableObserver<Student>() {
            @Override
            public void onNext(final Student s) {
                Log.i(TAG, "onNext invoked " + s.getName());
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


    private ArrayList<Student> getStudents() {

        ArrayList<Student> students = new ArrayList<>();

        Student student1 = new Student();
        student1.setName(" student 1");
        student1.setEmail(" student1@gmail.com ");
        student1.setAge(27);
        students.add(student1);

        Student student2 = new Student();
        student2.setName(" student 2");
        student2.setEmail(" student2@gmail.com ");
        student2.setAge(20);
        students.add(student2);

        Student student3 = new Student();
        student3.setName(" student 3");
        student3.setEmail(" student3@gmail.com ");
        student3.setAge(20);
        students.add(student3);

        Student student4 = new Student();
        student4.setName(" student 4");
        student4.setEmail(" student4@gmail.com ");
        student4.setAge(20);
        students.add(student4);

        Student student5 = new Student();
        student5.setName(" student 5");
        student5.setEmail(" student5@gmail.com ");
        student5.setAge(20);
        students.add(student5);

        return students;


    }
}
