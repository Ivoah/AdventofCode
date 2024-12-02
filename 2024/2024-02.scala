import scala.io.Source

def dropped(report: Seq[Int]): Seq[Seq[Int]] = {
  report.indices.map(i => report.patch(i, Seq(), 1))
}

def increasing(report: Seq[Int]): Boolean = {
  report.init
    .zip(report.tail)
    .forall { case (a, b) => b - a >= 1 && b - a <= 3 }
}

def decreasing(report: Seq[Int]): Boolean = {
  report.init
    .zip(report.tail)
    .forall { case (a, b) => a - b >= 1 && a - b <= 3 }
}

@main
def main202402() = {
  val reports = Source.fromFile("2.txt")
    .getLines
    .toSeq
    .map(_.split(" ").map(_.toInt).toSeq)

  println(reports.count(r => increasing(r) || decreasing(r)))
  println(reports.count(dropped(_).exists(r => increasing(r) || decreasing(r))))
}
