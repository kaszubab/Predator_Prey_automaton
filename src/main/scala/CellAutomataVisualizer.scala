import scalafx.scene.canvas.Canvas
import scalafx.scene.effect.BlendMode.Green
import scalafx.scene.layout.{BorderPane, Pane}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.Paint

class CellAutomataVisualizer extends Pane{

  val mainWindow = new BorderPane()
  val canvas = new Canvas(1000,800)

  val gc = canvas.getGraphicsContext2D
  gc.setFill(GreenYellow)
  gc.fillRect(0,0,10,10)

  mainWindow.getChildren.add(canvas)
  children.add(mainWindow)

}
