package com.wolfsea.signviewdemo.base
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseActivityForKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        //销毁Disposable,防止内存泄漏
        compositeDisposable.dispose()
    }

    /**
     *@desc 添加Disposable方法
     *@author:liuliheng
     *@time: 2020/12/14 15:08
    **/
    protected fun addDisposable(disposable : Disposable) {
        compositeDisposable.addAll(disposable)
    }

    private val compositeDisposable = CompositeDisposable()
}