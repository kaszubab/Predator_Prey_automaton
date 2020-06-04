import Logic.{Simulation, SimulationParams}
import javafx.scene.control.TextField
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, Pane, StackPane, VBox}
import scalafx.scene.text.{Font, Text}
import scalafx.scene.paint.Color._

object ScalaFXSimulation extends JFXApp {

  val simulation = new Simulation(new SimulationParams(10,10, 1, 1))

  for(n <- 1 to 3){
    println("-----------")

    val (preys, predators) = simulation.nextState()

    predators.foreach{
      p => println(p)
    }

    preys.foreach{
      p =>
        println(p)
    }
    println("-----------")
  }



  stage = new PrimaryStage {
    title = "Predator-Prey Simulator"

    scene = new Scene (500, 400) {
      val pane = new BorderPane()
      pane.padding = Insets(top = 5, bottom = 5, left = 5, right = 5)
      val text1 = new Text {
        text = "Welcome to Predator and Prey simulation"
        fill = Red
        style = "-fx-font: normal bold 15pt sans-serif"
      }

      val text2 = new Text {
        text = "Choose the mode of simulation"
        fill = Red
        style = "-fx-font: normal bold 15pt sans-serif"
      }

      val cellAutomata = new Button("Start cell automata"){
        onMouseClicked = (_) => {
          root = new CellAutomataVisualizer
        }
      }

      val differentialEquations = new Button("Start differential equations"){
        onMouseClicked = (_) => {
        }
      }



      pane.top = new VBox(){
        alignment = Center
        children.addAll(
          text1,
          text2
        )
      }

      pane.center = new VBox(){
        padding =  Insets(5, 5, 5 ,5)
        spacing = 10
        alignment = Center
        children.addAll(
          cellAutomata,
          differentialEquations
        )
      }


      root = pane
    }
  }
}
