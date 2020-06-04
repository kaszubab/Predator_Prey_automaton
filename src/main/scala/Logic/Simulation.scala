package Logic

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import util.control.Breaks._


class Simulation(val params: SimulationParams) {
  private val worldMap = new mutable.HashMap[Point, Animal]()
  private val rand = scala.util.Random

  private def setUpSimulation(): Unit = {
    val size = params.mapHeight * params.mapWidth
    val preyCount = (size * params.initialPreyPercentage / 100).toInt
    val predatorCount = (size * params.initialPredatorPercentage / 100).toInt
    var i = 0

    while (i < preyCount){
      val point = Point(rand.nextInt(params.mapWidth), rand.nextInt(params.mapHeight))

      breakable {
        if (worldMap.contains(point)) {
          break
        }
      }

      val animal = Prey(point)
      worldMap(point) = animal
      i += 1
    }

    i = 0
    while (i < predatorCount){
      val point = Point(rand.nextInt(params.mapWidth), rand.nextInt(params.mapHeight))

      breakable {
        if (worldMap.contains(point)) {
          break
        }
      }

      val animal = Predator(point)
      worldMap(point) = animal
      i += 1
    }

  }

  setUpSimulation()

  def findNearbyPrey(position: Point): Option[Point] = {
    for(i <- -1 to 1){
      for(j <- -1 to 1){
        breakable{
          if(i == 0 && j == 0){
            break
          }
        }
        val animal = worldMap.getOrElse(position.add(new Point(i, j)), None)

        animal match {
          case Prey(pos) => return Some(pos)
          case other => Unit
        }

      }
    }

    None
  }

  def findSafePosition(position: Point): Option[Point] = {
    var pos = position
    var freePosition = position
    for(i <- -1 to 1){
      for(j <- -1 to 1){
        breakable{
          if(i == 0 && j == 0){
            break
          }
        }

        pos = position.add(new Point(i, j))
        val animal = worldMap.getOrElse(pos, None)

        animal match {
          case Predator(pos) => return None
          case None => freePosition = pos
          case other => Unit
        }

      }
    }

    Some(freePosition)
  }


  def findFreePositions(position: Point): Option[ListBuffer[Point]] = {
    val freePositions = new ListBuffer[Point]
    var pos = position

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        breakable {
          if (i == 0 && j == 0) {
            break
          }
        }

        pos = position.add(new Point(i, j))
        if (!worldMap.contains(pos) ){
          freePositions.append(pos)
        }
      }
    }

    if(freePositions.nonEmpty) {
      Some(freePositions)
    } else {
      None
    }
  }

  def resultLists(): (List[Animal], List[Animal]) ={
    val preys = new ListBuffer[Animal]
    val predators = new ListBuffer[Animal]

    worldMap.foreach{
      case (point, animal) =>
        animal match {
          case Prey(initialPosition) => preys.append(animal)
          case Predator(initialPosition) => predators.append(animal)
        }
    }

    (preys.toList, predators.toList)
  }

  def nextState(): (List[Animal], List[Animal]) = {
    val result = resultLists()

    val animalsToRemove = new ListBuffer[Point]
    val animalToAdd = new ListBuffer[Animal]
    worldMap.foreach{

      case (point, animal) =>
        animal match {
          case Predator(position) =>
            findNearbyPrey(position) match {
              case Some(point) =>
                animalsToRemove.append(point)
                animal.move(ListBuffer(point))
                animalToAdd.append(Predator(position))
              case None =>
                animalsToRemove.append(position)
            }
          case other => Unit
        }

    }

    for(p <- animalsToRemove) {
      worldMap.remove(p)
    }

    worldMap.foreach {
      case (point, animal) =>
        animal match {
          case Prey(position) =>
            findSafePosition(position) match {
              case Some(point) =>
                animalToAdd.append(Prey(point))
              case None => Unit
            }
        }
    }

    for(animal <- animalToAdd){
      if(!worldMap.contains(animal.position)){
        worldMap(animal.position) = animal
      }
    }

    worldMap.foreach{
      case (point, animal) =>
        findFreePositions(point) match {
          case Some(list) => animal.move(list)
          case None => Unit
        }
    }

    result
  }



}