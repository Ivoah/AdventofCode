extension (c: Char) {
  def priority: Int = c match {
    case c if c >= 'a' && c <= 'z' => c.toInt - 'a' + 1
    case c if c >= 'A' && c <= 'Z' => c.toInt - 'A' + 27
  }
}

@main
def main() = {
  val sacks = io.Source.fromFile("3.txt").getLines().map{ s =>
    s.splitAt(s.length/2) match {
      case (a, b) => (a.toSet, b.toSet)
    }
  }.toSeq

  val shared = sacks.map { case (a, b) => (a & b).head }
  println(shared.map(_.priority).sum)

  val badges = sacks.map(t => t._1 ++ t._2).grouped(3).map { s =>
    s.reduce(_ & _).head
  }
  println(badges.map(_.priority).sum)
}
