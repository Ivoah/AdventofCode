@main
def main() = {
  val heights = io.Source.fromFile("8.txt").getLines().map(_.map(_ - '0')).toSeq
  // println(heights)

  val visibility = heights.zipWithIndex.flatMap { case (row, y) =>
    row.zipWithIndex.map { case (tree, x) =>
      // println((x, y, tree))
      val (west, east) = heights(y).splitAt(x)
      val (north, south) = heights.map(_(x)).splitAt(y)
      west.forall(_ < tree)
      || east.tail.forall(_ < tree)
      || north.forall(_ < tree)
      || south.tail.forall(_ < tree)
    }
  }

  println(visibility.count(identity))
  
  val scores = heights.zipWithIndex.drop(1).dropRight(1).flatMap { case (row, y) =>
    row.zipWithIndex.drop(1).dropRight(1).map { case (tree, x) =>
      // println((x, y, tree))
      val (west, east) = heights(y).splitAt(x)
      val (north, south) = heights.map(_(x)).splitAt(y)
      (west.reverse.dropRight(1).takeWhile(_ < tree).length + 1)
      * (east.tail.dropRight(1).takeWhile(_ < tree).length + 1)
      * (north.reverse.dropRight(1).takeWhile(_ < tree).length + 1)
      * (south.tail.dropRight(1).takeWhile(_ < tree).length + 1)
    }
  }

  println(scores.max)
}
