package UI

import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import javafx.scene.chart.XYChart.Data


object SimulationLineChart  {

  def apply(title: String, series: Seq[(String, ObservableBuffer[Data[Number, Number]])]): LineChart[Number, Number] = {
    val xAxis = NumberAxis()
    xAxis.setAutoRanging(true)
    xAxis.forceZeroInRange = false
    xAxis.label = title
    val yAxis = NumberAxis()
    yAxis.setAutoRanging(true)
    val chart = LineChart(xAxis, yAxis)


    series foreach {
      case (name, buffer) =>
        val s = XYChart.Series[Number, Number](name, buffer)
        chart.getData.add(s)
    }
    chart
  }

  def addPointToLine(buffer: ObservableBuffer[Data[Number, Number]], point: (Long, Long)): ObservableBuffer[Data[Number, Number]] = {
    val (x, y) = point
    buffer.add(new Data[Number, Number](x,y))
    if (buffer.size > 5)
      buffer.remove(0)
    buffer
  }

}