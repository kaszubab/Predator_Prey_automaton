package UI

import java.io.File

import scalafx.application.JFXApp.Stage
import Logic.{Animal, Point, Simulation, SimulationParams}
import UI.ScalaFXSimulation.stage
import javafx.scene.chart.XYChart
import javax.imageio.ImageIO
import scalafx.animation.AnimationTimer
import scalafx.application
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.embed.swing.SwingFXUtils
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.image.WritableImage
import scalafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import scalafx.scene.paint.Color


class CellAutomataVisualizer(params: SimulationParams) extends Pane{

  val canvasWidth = 800
  val canvasHeight = 600

  val mainWindow = new BorderPane()
  val canvas = new Canvas(canvasWidth,canvasHeight)
  val gc = canvas.getGraphicsContext2D
  val simulation = new Simulation(params)
  var day = 0
  var filename :String = ""
  var running = false


  visualizeState(simulation.resultLists())

  val animalData = Seq(("Herbivores",ObservableBuffer(Seq(
    (0, params.mapHeight * params.mapWidth * params.initialPreyPercentage /100)
  ) map  {
    case(x,y) => new XYChart.Data[Number, Number](x,y)
  })),
    ("Carnivores",ObservableBuffer(Seq(
      (0, params.mapHeight * params.mapWidth * params.initialPredatorPercentage /100)
    ) map  {
      case(x,y) => new XYChart.Data[Number, Number](x,y)
    }))
  )



  val animalsChart = SimulationLineChart("Animal population", animalData)

  var last = 0L
  val timer = AnimationTimer( t => {
    if (last > 0) {
      if ((t - last)/ 1e9 > 0.2) {
        val lists = simulation.nextState()
        visualizeState(lists)
        actualizeCharts(lists)
        day += 1

        last = t
      }
    }
    else {
      last = t
    }
  })




 def addSquare(position: Point, color: Color) = {
   gc.setFill(color)
   gc.fillRect(position.x * canvasWidth / params.mapWidth, position.y * canvasHeight / params.mapHeight,
     canvasWidth/params.mapWidth, canvasHeight/params.mapHeight)
 }

  def visualizeState(lists:(List[Animal], List[Animal])) {
    gc.setFill(Color.White)
    gc.fillRect(0,0, canvasWidth, canvasHeight);
    lists._1 foreach  {
      (animal) => addSquare(animal.position, Color.Red)
    }

    lists._2 foreach  {
      (animal) => addSquare(animal.position, Color.Blue)
    }
  }

  def actualizeCharts(lists: (List[Animal], List[Animal])): Unit =
  {
    SimulationLineChart.addPointToLine(animalData.head._2, (day, lists._1.size))
    SimulationLineChart.addPointToLine(animalData(1)._2, (day, lists._2.size))
    SimulationLineChart
  }



  val stopButton = new Button(){
    text = "Start simulation"
    onMouseClicked = (_) => {
      if (running) {
        timer.stop()
        running = false
        text = "Start simulation"
      } else {
        timer.start()
        running = true
        text = "Stop simulation"
      }

    }

  }


  val filenameBox = new TextField(){
    promptText = "Enter filename"
    text.onChange( (_,_, newValue) =>{
      filename = util.Try(newValue).getOrElse("chart.png")
    })
  }

  val saveChartToPng = new Button(){
    text = "Save chart"
    onMouseClicked = (_) => {
      val img = animalsChart.snapshot(null, new WritableImage(600,300))
      val file = new File(filename + ".png")
      ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file)
    }
  }

  val rightWindow = new VBox() {
    padding = Insets(5,5,5,5)
    spacing = 10
  }

  private val buttonPanel = new HBox() {
    padding = Insets(5,5,5,5)
    spacing = 5
    children.addAll(
      stopButton,
      saveChartToPng,
      filenameBox
    )
  }

  rightWindow.children.addAll(
    animalsChart,
    buttonPanel
  )

  val centralPane = new HBox() {
    padding = Insets(5,5,5,5)
    spacing = 10
  }
  centralPane.children.addAll(
    canvas,
    rightWindow
  )

  mainWindow.top = centralPane
  children.add(mainWindow)


}





