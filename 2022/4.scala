@main
def main() = {
  val regex = raw"(\d+)-(\d+),(\d+)-(\d+)".r
  val assignmentPairs = io.Source.fromFile("4.txt").getLines().map {
    case regex(a, b, c, d) => (a.toInt to b.toInt, c.toInt to d.toInt)
  }.toSeq

  println(assignmentPairs.count { pair =>
    (pair._1.start >= pair._2.start && pair._1.end <= pair._2.end)
    || (pair._2.start >= pair._1.start && pair._2.end <= pair._1.end)
  })

  println(assignmentPairs.count { pair =>
    (pair._1.toSet & pair._2.toSet).nonEmpty
  })
}
