@main
def main() = {
  val datastream = io.Source.fromFile("6.txt").getLines().take(1).toSeq.head
  println(datastream.sliding(4, 1).indexWhere(_.toSet.size == 4) + 4)
  println(datastream.sliding(14, 1).indexWhere(_.toSet.size == 14) + 14)
}
