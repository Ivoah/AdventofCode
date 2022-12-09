case class v2(x: Int, y: Int) {
  def +(other: v2): v2 = v2(this.x + other.x, this.y + other.y)
  def dist(other: v2): Double = math.sqrt(math.pow(this.x - other.x, 2) + math.pow(this.y - other.y, 2))
  def direction(other: v2): v2 = v2((other.x - this.x).sign, (other.y - this.y).sign)
}

object v2 {
  def fromDirection(direction: String): v2 = direction match {
    case "U" => v2(0, 1)
    case "D" => v2(0, -1)
    case "L" => v2(-1, 0)
    case "R" => v2(1, 0)
  }
}

def moveHead(rope: Seq[v2], move: v2): Seq[v2] = {
  val new_head = rope.head + move
  rope match {
    case head :: Nil =>
      new_head :: Nil
    case head :: tail =>
      new_head +: moveHead(tail, if (tail.head.dist(new_head) >= 1.9) tail.head.direction(new_head) else v2(0, 0))
  }
}

@main
def main() = {
  val moves = io.Source.fromFile("9.txt").getLines().flatMap {
    case s"$direction $steps" => Seq.fill(steps.toInt)(v2.fromDirection(direction))
  }.toSeq

  println(moves.scanLeft(Seq.fill(2)(v2(0, 0)))(moveHead).map(_.last).toSet.size)
  println(moves.scanLeft(Seq.fill(10)(v2(0, 0)))(moveHead).map(_.last).toSet.size)

  // println((for (y <- tail_positions.map(_.y).max to tail_positions.map(_.y).min by -1) yield {
  //   (for (x <- tail_positions.map(_.x).min to tail_positions.map(_.x).max) yield {
  //     if ((x, y) == (0, 0)) "s"
  //     else if (tail_positions.contains(v2(x, y))) "#"
  //     else "."
  //   }).mkString("")
  // }).mkString("\n"))
}
