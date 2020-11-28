package com.wolfsea.signviewdemo.base;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁Disposable,防止内存泄漏.
        compositeDisposable.dispose();
    }

    /**
     *@desc 添加Disposable接口
     *@author:liuliheng
     *@time: 2020/11/28 17:46
    **/
    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
}