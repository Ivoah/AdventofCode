import scala.io.Source

@main
def main202401() = {
  val (l1, l2) = Source.fromFile("1.txt")
    .getLines
    .toSeq
    .map(_.split(raw"\s+") match { case Array(a, b) => (a.toInt, b.toInt) })
    .unzip
  
  val distance = l1.sorted.zip(l2.sorted).map { case (a, b) => math.abs(b - a) }.sum
  println(distance)

  val similarity = l1.map(n => n * l2.count(_ == n)).sum
  println(similarity)
}
