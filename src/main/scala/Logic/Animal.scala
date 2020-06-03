package Logic

class Animal(var position: Point) {
  private val rand = scala.util.Random


  def move(freePositions: Array[Point]): Unit = {
    position = freePositions(rand.nextInt(freePositions.length))
  }

  override def toString: String = position.toString
}

object Animal{
  def apply(position: Point): Animal = new Animal(position)
}