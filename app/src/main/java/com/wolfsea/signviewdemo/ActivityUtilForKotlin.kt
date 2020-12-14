package com.wolfsea.signviewdemo
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 *@desc 跳转到其他的Activity
 *@author:liuliheng
 *@time: 2020/12/14 13:08
 **/
fun gotoActivity(context: Context,clazz: Class<out AppCompatActivity>) {
    val intent = Intent(context, clazz)
    context.startActivity(intent)
}