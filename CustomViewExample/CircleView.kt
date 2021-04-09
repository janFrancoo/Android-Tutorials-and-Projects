package com.janfranco.customviewexample

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paint: Paint = Paint()
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f
    private var isCenter = false

    init {
        val attrArray: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.circleview,
            0,
            0
        )

        paint.color = attrArray.getColor(
            R.styleable.circleview_circle_background,
            ContextCompat.getColor(context, android.R.color.background_dark)
        )
        isCenter = attrArray.getBoolean(R.styleable.circleview_onCenter, false)
        radius = attrArray.getDimension(R.styleable.circleview_circle_radius, 140f)
        paint.strokeWidth = 40f
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        isCenter.let {
            centerX = (width / 2).toFloat()
            centerY = (height / 2).toFloat()
        }
        canvas?.drawCircle(centerX, centerY, radius, paint)
        super.onDraw(canvas)
    }

}
