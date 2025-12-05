import scala.io.Source

case class Floor(floor: Seq[Seq[Boolean]]) {
  private val dirs = Seq((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))

  override def toString(): String = {
    (0 until floor.length).map { r =>
      (0 until floor(r).length).map { c =>
        if (floor(r)(c) && neighbors(r, c) < 4) "x"
        else if (floor(r)(c)) "@"
        else "."
      }.mkString
    }.mkString("\n")
  }

  def neighbors(r: Int, c: Int): Int = {
    dirs.count(d => floor.lift(r + d._1).flatMap(row => row.lift(c + d._2)).getOrElse(false))
  }

  def accessible: Set[(Int, Int)] = {
    (0 until floor.length).flatMap(r => (0 until floor(r).length).flatMap(c => if (floor(r)(c) && neighbors(r, c) < 4) Some(r, c) else None)).toSet
  }

  def without(places: Set[(Int, Int)]): Floor = {
    Floor((0 until floor.length).map(r => (0 until floor(r).length).map(c => !places.contains(r, c) && floor(r)(c))))
  }
}

val floors = Iterator.iterate(Floor(
  Source.fromFile("4.txt")
  .getLines
  .map(_.map(_ == '@').toSeq)
  .toSeq
)) { floor =>
  floor.without(floor.accessible)
}.takeWhile(_.accessible.size > 0).toSeq

println(floors.head.accessible.size)
println(floors.map(_.accessible.size).sum)
