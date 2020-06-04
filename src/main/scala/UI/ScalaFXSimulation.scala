package UI

import java.beans.EventHandler

import Logic.SimulationParams
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField, TextFormatter}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color.Red
import scalafx.scene.text.Text

object ScalaFXSimulation extends JFXApp {

  stage = new PrimaryStage {
    title = "Predator-Prey Simulator"

    var mapWidth = 5
    var mapHeight = 5
    var preyPerc = 10.0
    var predatorPerc = 10.0

    scene = new Scene  {
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



      pane.top = new VBox(){
        alignment = Center
        children.addAll(
          text1,
          text2
        )
      }


      val simulationWidth = new TextField{
        text.onChange( (_,_, newValue) =>{
          mapWidth = util.Try(newValue.toInt).getOrElse(10)
        })
        promptText = "Map width"
      }

      val simulationHeight = new TextField{
        text.onChange( (_,_, newValue) =>{
          mapHeight = util.Try(newValue.toInt).getOrElse(10)
        })
        promptText = "Map height"
      }

      val preyPercentage = new TextField{
        text.onChange( (_,_, newValue) =>{
          preyPerc = util.Try(newValue.toDouble).getOrElse(10.0)
        })
        promptText = "prey Percentage"
      }

      val predatorPercentage = new TextField{
        text.onChange( (_,_, newValue) =>{
          predatorPerc = util.Try(newValue.toDouble).getOrElse(10.0)
        })
        promptText = "predators"
      }


      pane.center = new VBox(){
        padding =  Insets(5, 5, 5 ,5)
        spacing = 10
        alignment = Center
        children.addAll(
          simulationWidth,
          simulationHeight,
          predatorPercentage,
          preyPercentage
        )
      }


      val cellAutomata = new Button("Start cell automata"){
        onMouseClicked = (_) => {
          val params = new SimulationParams(mapWidth, mapHeight, preyPerc, predatorPerc)
          root = new CellAutomataVisualizer(params)
          stage.sizeToScene()

        }
      }

      val differentialEquations = new Button("Start differential equations"){
        onMouseClicked = (_) => {
        }
      }


      pane.bottom = new VBox(){
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
