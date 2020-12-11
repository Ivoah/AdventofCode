import scala.io.Source

val adapters = Source.fromFile("10.txt").getLines().map(_.toInt).toSeq
val differences = (0 +: adapters).sorted.sliding(2).map(a => a.last - a.head).toSeq :+ 3

println(differences.count(_ == 1) * differences.count(_ == 3))
