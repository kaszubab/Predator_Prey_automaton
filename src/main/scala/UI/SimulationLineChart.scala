package UI

import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import javafx.scene.chart.XYChart.Data


object SimulationLineChart  {

  def apply(title: String, data: Seq[(String, ObservableBuffer[Data[Number, Number]])]): LineChart[Number, Number] = {
    val xAxis = NumberAxis()
    xAxis.label = title
    val yAxis = NumberAxis()
    val chart = LineChart(xAxis, yAxis)

    data foreach {
      case (day, count) =>
        val s = XYChart.Series[Number, Number](day, count)
        chart.getData.add(s)
    }

     // chart.lookup(".default-color0.chart-symbol")
    chart
  }
}