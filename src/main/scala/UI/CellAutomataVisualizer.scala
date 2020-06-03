package UI

import Logic.{Animal, Point, Simulation, SimulationParams}
import scalafx.animation.AnimationTimer
import scalafx.collections.ObservableBuffer
import scalafx.scene.canvas.Canvas
import scalafx.scene.chart.PieChart.Data
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import scalafx.scene.layout.{BorderPane, Pane}
import scalafx.scene.paint.Color

class CellAutomataVisualizer(params: SimulationParams) extends Pane{

  val canvasWidth = 800
  val canvasHeight = 600

  val mainWindow = new BorderPane()
  val canvas = new Canvas(canvasWidth,canvasHeight)
  val gc = canvas.getGraphicsContext2D
  val simulation = new Simulation(params)


  var last = 0L
  val timer = AnimationTimer( t => {
    if (last > 0) {
      if ((t - last)/ 1e9 > 2) {
        val lists = simulation.nextState()

        gc.setFill(Color.White)
        gc.fillRect(0,0, canvasWidth, canvasHeight);

        lists._1 foreach(animal => {
          addSquare(animal.position, Color.Red)
        })

        lists._2 foreach(animal => {
          addSquare(animal.position, Color.Blue)
        })

        last = t
      }
    }
    else {
      last = t
    }
  })

  timer.start()



 def addSquare(position: Point, color: Color) = {
   println(canvasWidth/params.mapWidth + "   " + canvasHeight/params.mapHeight)

   gc.setFill(color)
   gc.fillRect(position.x * canvasWidth / params.mapWidth, position.y * canvasHeight / params.mapHeight,
     canvasWidth/params.mapWidth, canvasHeight/params.mapHeight)
 }

  mainWindow.top = canvas
  children.add(mainWindow)


}
/*
object LineChart {
  def apply(ser : Seq[(String, ObservableBuffer[Data[Number, Number]])]): LineChart[Number, Number] = {
    val xAx = NumberAxis()
    val yAx = NumberAxis()
    val chart = new LineChart(xAx, yAx)

    ser foreach {
      case (name, buff) =>
        val unit = XYChart.Series[Number, Number](name, buff)
        chart.getData.add(unit)

    }

    chart
  }
}
*/