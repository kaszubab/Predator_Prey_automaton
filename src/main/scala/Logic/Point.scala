package Logic

class Point(val x: Int, val y: Int) {

  def add(other: Point): Point = {
    new Point(x + other.x, y + other.y)
  }

  def precedes(other: Point): Boolean ={
    if(this.x <= other.x && this.y <= other.y){
      true
    }
    else{
      false
    }
  }

  def follows(other: Point): Boolean ={
    if(this.x >= other.x && this.y >= other.y){
      true
    }
    else{
      false
    }
  }

  override def toString: String = s"($x, $y)"

  override def hashCode(): Int = {
    x*17 + y*133
  }

  override def equals(other: Any): Boolean = {
    other match {
      case other: Point =>
        (this.x == other.x) && (this.y == other.y)

      case _ => false
    }
  }
}

object Point{
  def apply(x: Int, y: Int): Point = new Point(x, y)
}