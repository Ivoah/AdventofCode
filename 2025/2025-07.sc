import scala.io.Source

val splitters = Source.fromFile("7.txt").getLines.toSeq

val start = splitters.head.indexOf('S')
println(splitters.tail.foldLeft((0, Set(start))) { case ((splits, beams), line) =>
  (
    splits + beams.count(line(_) == '^'),
    beams.flatMap(c => if (line(c) == '^') Set(c - 1, c + 1) else Set(c))
  )
}._1)

println(splitters.tail.foldLeft(Seq((start, 1L))) { case (beams, line) =>
  beams
    .flatMap{case (c, n) => if (line(c) == '^') Seq((c - 1, n), (c + 1, n)) else Seq((c, n))}
    .groupBy(_._1)
    .toSeq
    .map{case (c, bs) => (c, bs.map(_._2).sum)}
}.map(_._2).sum)
