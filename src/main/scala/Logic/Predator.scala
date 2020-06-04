package Logic

case class Predator(initialPosition: Point) extends Animal(initialPosition){
  override def toString: String = "Predator " + super.toString
}

