package com.wolfsea.signviewdemo
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.wolfsea.signviewdemo.base.BaseActivityForKotlin
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_kotlin_style.*
import kotlinx.android.synthetic.main.bottom_btn_layout.*
import java.util.concurrent.Callable

class KotlinStyleActivity : BaseActivityForKotlin(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_style)
    }

    override fun onContentChanged() {

        super.onContentChanged()
        store_show_btn.setOnClickListener(this)
        redraw_btn.setOnClickListener(this)
        cut_store_show_btn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.redraw_btn -> {

                sign_view_for_kotlin.redraw()
            }
            R.id.store_show_btn -> {

                storeBitmap(sign_view_for_kotlin.convertSignature2Bitmap(false))
            }
            R.id.cut_store_show_btn -> {

                storeBitmap(sign_view_for_kotlin.convertSignature2Bitmap(true))
            }
            else -> {}
        }
    }

    /**
     *@desc 存储Bitmap
     *@author:liuliheng
     *@time: 2020/12/14 15:31
     **/
    private fun storeBitmap(bitmap: Bitmap) {
        addDisposable(Observable.fromCallable(Callable {

            FileUtils.saveBitmap(this, bitmap)
            true
        } as Callable<Boolean>).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    gotoActivity(this@KotlinStyleActivity, ShowSignatureActivity::class.java)
                })
    }

}