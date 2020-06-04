package Logic

class Point(val x: Int, val y: Int) {

  def add(other: Point): Point = {
    new Point(x + other.x, y + other.y)
  }

  override def toString: String = s"($x, $y)"

  override def hashCode(): Int = {
    x*17 + y*133
  }

}

object Point{
  def apply(x: Int, y: Int): Point = new Point(x, y)
}