package com.wolfsea.signviewdemo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_entrance.*

const val TAG = "EntranceActivity"

class EntranceActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

        Log.d(TAG, "override fun onCreate(savedInstanceState: Bundle?)")
    }

    override fun onContentChanged() {

        super.onContentChanged()

        Log.d(TAG, "override fun onContentChanged()")

        java_btn.setOnClickListener(this)
        kotlin_btn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.kotlin_btn -> {

                gotoActivity(this,KotlinStyleActivity::class.java)
            }
            R.id.java_btn -> {

                gotoActivity(this, MainActivity::class.java)
            }
            else->{}
        }
    }
}