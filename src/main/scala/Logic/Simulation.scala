package Logic

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import util.control.Breaks._


class Simulation(val params: SimulationParams) {
  private val worldMap = new mutable.HashMap[Point, Animal]()
  private var animalList = new ListBuffer[Animal]()

  private val rand = scala.util.Random

  private def setUpSimulation(): Unit = {
    val size = params.mapHeight * params.mapWidth
    val preyCount = (size * params.initialPreyPercentage / 100).toInt
    val predatorCount = (size * params.initialPredatorPercentage / 100).toInt
    val tempSize = predatorCount + preyCount
    var i = 0

    System.out.println("prey " + preyCount)
    System.out.println("predator " + predatorCount)
    System.out.println("temp " + tempSize)

    while (i < tempSize){
      val point = Point(rand.nextInt(params.mapWidth), rand.nextInt(params.mapHeight))

      breakable {
        if (worldMap.contains(point)) {
          break
        }
      }

      val animal = Animal(point)
      worldMap(point) = animal
      animalList += animal
      i += 1
    }
  }

  setUpSimulation()

  def nextState(): (List[Animal], List[Animal]) = {

    for (a <- animalList){

      a.move( Array(Point(a.position.x + 1, a.position.y +  1)))
    }


    Tuple2(animalList.toList, animalList.toList)
  }


}