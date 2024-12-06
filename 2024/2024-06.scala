import scala.io.Source
import scala.annotation.tailrec

enum Direction(val dx: Int, val dy: Int) {
  def rotated: Direction = {
    this match {
      case Up => Right
      case Right => Down
      case Down => Left
      case Left => Up
    }
  }

  case Up extends Direction(0, -1)
  case Right extends Direction(1, 0)
  case Down extends Direction(0, 1)
  case Left extends Direction(-1, 0)
}

extension (t: (Int, Int)) {
  def +(dir: Direction): (Int, Int) = (t._1 + dir.dx, t._2 + dir.dy)
}

@main
def main202406() = {
  val map = Source.fromFile("6.txt").getLines.map(_.toSeq).toSeq
  val start = (
    (
      map.map(_.indexOf('^')).find(_ >= 0).get,
      map.indexWhere(_.contains('^'))
    ),
    Direction.Up
  )
  
  @tailrec
  def step(map: Seq[Seq[Char]], positions: Set[((Int, Int), Direction)], last: ((Int, Int), Direction)): (Set[((Int, Int), Direction)], Boolean) = {
    val forward = (last._1 + last._2, last._2)
    val rotated = (last._1, last._2.rotated)
    map.lift(forward._1._2).flatMap(_.lift(forward._1._1)) match {
      case Some('#') if positions.contains(rotated) => (positions, true)
      case Some('#') => step(map, positions + rotated, rotated)
      case Some(_) if positions.contains(forward) => (positions, true)
      case Some(_) => step(map, positions + forward, forward)
      case None => (positions, false)
    }
  }

  val (path, loop) = step(map, Set(start), start)
  val positions = path.map(_._1)

  println(positions.size)

  val loops = positions.count { case (x, y) =>
    if ((x, y) == start._1) false
    else {
      val newMap = map.patch(y, Seq(map(y).patch(x, Seq('#'), 1)), 1)
      val (newPath, loop) = step(newMap, Set(start), start)
      loop
    }
  }
  println(loops)
}
