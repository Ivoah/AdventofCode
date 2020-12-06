import scala.io.Source

val answers = Source.fromFile("6.txt").mkString.split("\n\n").map(_.split("\\s").map(_.toSet))
println(answers.map(_.reduce(_ union _).size).sum)
println(answers.map(_.reduce(_ intersect _).size).sum)