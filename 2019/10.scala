import scala.io.Source

val map = Source.fromFile("10.txt").getLines().map(_.map(Map('#' -> true, '.' -> false))).toSeq
println(map.map(_.map(Map(true -> '#', false -> '.')).mkString).mkString("\n"))
