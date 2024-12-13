package aoc202408

import scala.io.Source

@main
def main() = {
  val map = Source.fromFile("8.txt").getLines.toSeq
  val antennas = (for (
    (row, y) <- map.zipWithIndex;
    (c, x) <- row.zipWithIndex
  ) yield {
    if (c == '.') None
    else Some(c -> (x, y))
  }).flatten.toSeq.groupMap(_._1)(_._2)

  val width = map.head.length
  val height = map.length
  
  val antenna_pairs = antennas.map(_._2).flatMap(_.combinations(2)).toSeq
  val antinodes = antenna_pairs.map { case Seq(p1, p2) =>
    val dx = p2._1 - p1._1
    val dy = p2._2 - p1._2
    Seq(
      LazyList.from(0).map { i =>
        (p1._1 - dx*i, p1._2 - dy*i)
      }.takeWhile{case (x, y) => x >= 0 && x < width && y >= 0 && y < height},
      LazyList.from(0).map { i =>
        (p2._1 + dx*i, p2._2 + dy*i)
      }.takeWhile{case (x, y) => x >= 0 && x < width && y >= 0 && y < height},
    )
  }

  // println(map.zipWithIndex.map {
  //   case (row, y) => row.zipWithIndex.map {
  //     case (c, _) if c != '.' => c
  //     case (_, x) if antinodes.exists(_.exists(_.contains(x, y))) => '#'
  //     case (c, _) => c
  //   }.mkString
  // }.mkString("\n"))

  println(antinodes.flatMap(_.flatMap(_.lift(1))).toSet.size)
  println(antinodes.flatMap(_.flatten).toSet.size)
}
