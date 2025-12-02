import scala.io.Source

val ids = Source.fromFile("2.txt")
  .getLines
  .next
  .split(",")
  .flatMap {case s"$from-$to" => from.toLong to to.toLong}
  .toSeq

println(ids.filter(id => raw"(\d+)\1".r.matches(id.toString)).sum)
println(ids.filter(id => raw"(\d+)\1+".r.matches(id.toString)).sum)
