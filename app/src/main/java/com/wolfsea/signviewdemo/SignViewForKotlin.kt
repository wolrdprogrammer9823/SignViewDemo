package com.wolfsea.signviewdemo
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.concurrent.CopyOnWriteArrayList

/**
 *@desc  用户签名
 *@author liuliheng
 *@time 2020/12/14  13:30
 **/

class SignViewForKotlin : View {

    private lateinit var totalXAlias: ArrayList<CopyOnWriteArrayList<Float>>
    private lateinit var totalYAlias: ArrayList<CopyOnWriteArrayList<Float>>

    private lateinit var xAlias: CopyOnWriteArrayList<Float>
    private lateinit var yAlias: CopyOnWriteArrayList<Float>

    private lateinit var paint: Paint

    constructor(context: Context) : super(context) {
        init()
        Log.d(TAG, "constructor(context: Context)")
    }

    constructor(context: Context, attributeSet : AttributeSet?) : super(context, attributeSet) {
        init()
        Log.d(TAG, "constructor(context: Context, attributeSet : AttributeSet?)")
    }

    constructor(context: Context, attributeSet : AttributeSet?, defaultStyle: Int)
               : super(context, attributeSet, defaultStyle) {
        init()
        Log.d(TAG, "constructor(context: Context, attributeSet : AttributeSet?, defaultStyle: Int)")
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        drawTotalSignatures(canvas)
        drawSingleSignature(canvas, xAlias, yAlias)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {

                performClick()

                totalXAlias.add(CopyOnWriteArrayList(xAlias))
                totalYAlias.add(CopyOnWriteArrayList(yAlias))

                xAlias.clear()
                yAlias.clear()

                xAlias.add(event?.x)
                yAlias.add(event?.y)
            }
            MotionEvent.ACTION_MOVE -> {

                xAlias.add(event?.x)
                yAlias.add(event?.y)
                invalidate()
            }
            else -> {}
        }
        return true
    }

    override fun performClick() = super.performClick()

    /**
     *@desc 绘制所有的轨迹
     *@author:liuliheng
     *@time: 2020/12/14 13:58
    **/
    private fun drawTotalSignatures(canvas: Canvas?) {
        for (i in totalXAlias.indices) {

            drawSingleSignature(canvas, totalXAlias[i], totalYAlias[i])
        }
    }

    /**
     *@desc 绘制单一轨迹
     *@author:liuliheng
     *@time: 2020/12/14 13:55
     **/
    private fun drawSingleSignature(canvas: Canvas?, xAlias: CopyOnWriteArrayList<Float>,
                                    yAlias: CopyOnWriteArrayList<Float>) {
        for (i in 0 until xAlias.size - 1) {

            val x0 = xAlias[i]
            val y0 = yAlias[i]

            val x1 = xAlias[i + 1]
            val y1 = yAlias[i + 1]

            canvas?.drawLine(x0, y0, x1, y1, paint)
        }
    }

    /**
     *@desc 重新绘制
     *@author:liuliheng
     *@time: 2020/12/14 14:49
    **/
    fun redraw() {

        xAlias.clear()
        yAlias.clear()

        totalXAlias.clear()
        totalYAlias.clear()

        invalidate()
    }

    /**
     *@desc 把轨迹转换为Bitmap
     *@author:liuliheng
     *@time: 2020/12/14 15:00
    **/
    fun convertSignature2Bitmap(cutBitmap: Boolean) : Bitmap{

        val width = measuredWidth
        val height = measuredHeight
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        if (cutBitmap) {

            canvas.clipRect(0, height.div(3), width, height.times(2).div(3))
        }
        this.draw(canvas)

        return bitmap
    }

    /**
     *@desc 初始化方法
     *@author:liuliheng
     *@time: 2020/12/14 13:46
    **/
    private fun init() {

        paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.isDither = true
        paint.strokeWidth = 10F

        totalXAlias = ArrayList()
        totalYAlias = ArrayList()

        xAlias = CopyOnWriteArrayList()
        yAlias = CopyOnWriteArrayList()
    }

}