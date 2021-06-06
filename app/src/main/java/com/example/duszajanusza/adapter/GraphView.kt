package com.example.duszajanusza.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.absoluteValue

const val TEXT_SIZE = 40.0f

class GraphView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {

    private val dataSet = mutableListOf<DataPoint>()
    private var xMin = 0.0f
    private var xMax = 0.0f
    private var yMin = 0.0f
    private var yMax = 0.0f

    private val textAndPointPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
        style = Paint.Style.FILL_AND_STROKE
        textSize = TEXT_SIZE
    }

    private val axisLabelPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 1.5f
        style = Paint.Style.FILL_AND_STROKE
        textSize = TEXT_SIZE / 2
    }

    private val positiveDataPointLinePaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 8f
        isAntiAlias = true
    }

    private val negativeDataPointLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 8f
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 10f
    }

    fun setData(newDataSet: List<DataPoint>) {
        xMin = newDataSet.minByOrNull { it.xVal }?.xVal ?: 0.0f
        xMax = newDataSet.maxByOrNull { it.xVal }?.xVal ?: 0.0f
        yMin = newDataSet.minByOrNull { it.yVal }?.yVal ?: 0.0f
        yMax = newDataSet.maxByOrNull { it.yVal }?.yVal ?: 0.0f
        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // draw axis
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(
            0f,
            height.toFloat() / 2,
            width.toFloat(),
            height.toFloat() / 2,
            axisLinePaint
        )

        // draw x axis labels
        dataSet.forEach { currentDataPoint ->
            val realX = currentDataPoint.xVal.toRealX()
            canvas.drawText(
                currentDataPoint.xVal.toInt().toString(),
                realX - 5,
                height.toFloat() / 2 + TEXT_SIZE,
                axisLabelPaint
            )
            canvas.drawLine(
                realX,
                height.toFloat() / 2 - 20,
                realX,
                height.toFloat() / 2 + 20,
                axisLabelPaint
            )
        }

        // draw chart
        dataSet.forEachIndexed { index, currentDataPoint ->
            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                val startY = toY(currentDataPoint.yVal)
                val endX = nextDataPoint.xVal.toRealX()
                val endY = toY(nextDataPoint.yVal)
                if (endY < height / 2) {
                    canvas.drawLine(startX, startY, endX, endY, positiveDataPointLinePaint)
                } else {
                    canvas.drawLine(startX, startY, endX, endY, negativeDataPointLinePaint)
                }
            }
        }

        // second loop to draw descriptive elements on top of chart
        dataSet.forEachIndexed { index, currentDataPoint ->
            val realX = currentDataPoint.xVal.toRealX()
            val realY = toY(currentDataPoint.yVal)

            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val endX = nextDataPoint.xVal.toRealX()
                val endY = toY(nextDataPoint.yVal)

                if (currentDataPoint.yVal !== nextDataPoint.yVal) {
                    canvas.drawText(
                        nextDataPoint.yVal.toString(),
                        endX,
                        endY + TEXT_SIZE,
                        textAndPointPaint
                    )
                }
                canvas.drawCircle(realX, realY, 4f, textAndPointPaint)
            }
        }
    }

    private fun Float.toRealX() = toFloat() / xMax * width

    private fun toY(value: Float): Float {
        if (value > 0) {
            return (height / 2) - (value / yMax * height / 2)
        }
        return (height / 2) + (value.absoluteValue / yMax * height / 2)
    }
}

data class DataPoint(
    val xVal: Float,
    val yVal: Float,
)