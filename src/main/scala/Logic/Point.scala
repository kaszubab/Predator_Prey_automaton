package Logic

class Point(val x: Int, val y: Int) {
  override def toString: String = s"($x, $y)"

  override def hashCode(): Int = {
    x*17 + y*133
  }

}

object Point{
  def apply(x: Int, y: Int): Point = new Point(x, y)
}