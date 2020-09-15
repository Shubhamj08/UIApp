package com.android.uiapp

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

enum class LabelClass(val label: Int){
    OFF(R.string.label_off),
    LOW(R.string.label_low),
    MEDIUM(R.string.label_medium),
    HIGH(R.string.label_high);

    fun next() = when(this){
        OFF -> LOW
        LOW -> MEDIUM
        MEDIUM -> HIGH
        HIGH -> OFF
    }
}


private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

class MyCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var labelLowColor = 0
    private var labelMediumColor = 0
    private var labelMaxColor = 0

    private var radius = 0.0f
    private var currentPos = LabelClass.OFF

    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    init {
        isClickable = true

        context.withStyledAttributes(attrs, R.styleable.MyCustomView){
            labelLowColor = getColor(R.styleable.MyCustomView_labelColor1, 0)
            labelMediumColor = getColor(R.styleable.MyCustomView_labelColor2, 0)
            labelMaxColor = getColor(R.styleable.MyCustomView_labelColor3, 0)
        }
    }

    override fun performClick(): Boolean {
        if(super.performClick()) return true

        currentPos = currentPos.next()
        contentDescription = resources.getString(currentPos.label)

        invalidate()
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (min(w, h) / 2 * 0.8).toFloat()
    }

    private fun PointF.computeXYForLabel(pos: LabelClass, radius: Float){
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = when(currentPos){
            LabelClass.OFF -> Color.GRAY
            LabelClass.LOW -> labelLowColor
            LabelClass.MEDIUM -> labelMediumColor
            LabelClass.HIGH -> labelMaxColor
        }


        canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), radius, paint)

        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForLabel(currentPos, markerRadius)
        paint.color = Color.BLACK
        canvas?.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)

        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for(i in LabelClass.values()){
            pointPosition.computeXYForLabel(i, labelRadius)
            val label = resources.getString(i.label)
            canvas?.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }
}