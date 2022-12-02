extension [T](seq: Seq[T]) {
  def split(p: T => Boolean): Seq[Seq[T]] = {
    seq.splitAt(seq.indexWhere(p)) match {
      case (a, b) => if (a.nonEmpty) {
        a +: b.tail.split(p)
      } else {
        Seq(b)
      }
    }
  }
}

@main
def main() = {
  val calories = scala.io.Source.fromFile("1.txt").getLines().map(_.toIntOption).toSeq.split(_.isEmpty).map(_.map(_.get))
  println(calories.map(_.sum).max)
  println(calories.map(_.sum).sorted.reverse.take(3).sum)
}
