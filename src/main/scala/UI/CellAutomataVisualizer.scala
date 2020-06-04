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
  var day = 0

  val animalData = Seq(("Herbivores",ObservableBuffer(Seq(
    (0, params.mapHeight * params.mapWidth * params.initialPreyPercentage /100)
  ) map  {
    case(x,y) => XYChart.Data[Number, Number](x,y)
  })),
    ("Carnivores",ObservableBuffer(Seq(
      (0, params.mapHeight * params.mapWidth * params.initialPredatorPercentage /100)
    ) map  {
      case(x,y) => XYChart.Data[Number, Number](x,y)
    }))
  )

  val animalsChart = SimulationLineChart("Total number of animals", animalData)

  var last = 0L
  val timer = AnimationTimer( t => {
    if (last > 0) {
      if ((t - last)/ 1e9 > 2) {
        val lists = simulation.nextState()

        gc.setFill(Color.White)
        gc.fillRect(0,0, canvasWidth, canvasHeight);
        lists._1 map {
          (animal) => addSquare(animal.position, Color.Red)
        }

        lists._2 map {
          (animal) => addSquare(animal.position, Color.Blue)
        }
        day += 1

        println("Ilości w dniu: " + day + " " + lists._1.size + "  " + lists._2.size)
        /*
        animalData foreach {
          case ("herbivores", herbs) => herbs.add( new XYChart.Data[Number, Number](day, 2))
          case ("carnivores", herbs) => herbs.add( new XYChart.Data[Number, Number](day, 2))
          case _ => None
        }

        animalData foreach  {
          (x) => println(x)
        }
        */
        last = t
      }
    }
    else {
      last = t
    }
  })

  timer.start()



 def addSquare(position: Point, color: Color) = {
   gc.setFill(color)
   gc.fillRect(position.x * canvasWidth / params.mapWidth, position.y * canvasHeight / params.mapHeight,
     canvasWidth/params.mapWidth, canvasHeight/params.mapHeight)
 }

  mainWindow.top = canvas
  mainWindow.right = animalsChart
  children.add(mainWindow)


}

