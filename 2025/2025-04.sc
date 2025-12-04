import scala.io.Source

val floor = Source.fromFile("4.txt")
  .getLines
  .map(_.map(_ == '@').toSeq)
  .toSeq

def neighbors(r: Int, c: Int): Int = {
  val dirs = Seq((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
  dirs.count(d => floor.lift(r + d._1).flatMap(row => row.lift(c + d._2)).getOrElse(false))
}

println((0 until floor.length).flatMap(r => (0 until floor(r).length).map(c => floor(r)(c) && neighbors(r, c) < 4)).count(identity))
// for (r <- 0 until floor.length) {
//   for (c <- 0 until floor(r).length) {
//     if (neighbors(r, c) < 4) print("x")
//     else if (floor(r)(c)) print("@")
//     else print(".")
//   }
//   println()
// }
