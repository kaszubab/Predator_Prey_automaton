package Logic

case class Prey(initialPosition: Point) extends Animal(initialPosition) {
  override def toString: String = "Prey " + super.toString
}
