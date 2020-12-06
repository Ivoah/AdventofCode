import scala.io.Source

val answers = Source.fromFile("6.txt").mkString.split("\n\n").map(_.split("\\s").map(_.toSet))
println(answers.map(_.reduce((s1, s2) => s1 union s2).size).sum)
println(answers.map(_.reduce((s1, s2) => s1 intersect s2).size).sum)