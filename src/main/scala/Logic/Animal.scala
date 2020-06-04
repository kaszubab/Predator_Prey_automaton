package Logic

import scala.collection.mutable.ListBuffer

abstract class Animal(var position: Point) {
  private val rand = scala.util.Random


  def move(freePositions: ListBuffer[Point]): Unit = {
    position = freePositions(rand.nextInt(freePositions.size))
  }

  override def toString: String = position.toString
}
