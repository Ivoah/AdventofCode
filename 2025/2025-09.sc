import scala.io.Source

case class Tile(x: Long, y: Long)
case class Rect(c1: Tile, c2: Tile) {
  val area: Long = (math.abs(c2.x - c1.x) + 1) * (math.abs(c2.y - c1.y) + 1)
}

val tiles = Source.fromFile("9.txt")
  .getLines
  .map {case s"$x,$y" => Tile(x.toLong, y.toLong)}
  .toSeq

val rects = tiles
  .combinations(2)
  .map {case Seq(t1, t2) => Rect(t1, t2)}
  .toSeq

println(rects.map(_.area).max)
