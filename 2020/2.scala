import scala.io.Source

val patt = raw"(\d+)-(\d+) (\w): (\w+)".r

val lines = Source.fromFile("2.txt").getLines().toSeq
println(lines.count {
  case patt(min, max, c, pw) => (min.toInt to max.toInt).contains(pw.count(_ == c.head))
})

println(lines.count {
  case patt(p0, p1, c, pw) => Seq(pw(p0.toInt - 1), pw(p1.toInt - 1)).count(_ == c.head) == 1
})
