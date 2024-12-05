import scala.io.Source

given [A, B, C]: Conversion[((A, B)) => C, (A, B) => C] = (f: ((A, B)) => C) => (a: A, b: B) => f((a, b))

@main
def main202405() = {
  val (rules, orders) = Source.fromFile("5.txt")
    .getLines
    .foldLeft((Seq[(Int, Int)](), Seq[Seq[Int]]())) { (lists, next) =>
      next match {
        case s"$p1|$p2" => (lists._1 :+ (p1.toInt, p2.toInt), lists._2)
        case "" => lists
        case ordering => (lists._1, lists._2 :+ ordering.split(",").map(_.toInt).toSeq)
      }
    }
  
  val (correct, incorrect) = orders.partition { o =>
    rules.forall { case (p1, p2) =>
      o.indexOf(p1) == -1
      || o.indexOf(p2) == -1
      || o.indexOf(p1) < o.indexOf(p2)
    }
  }
  
  println(correct.map(o => o(o.length/2)).sum)
  println(incorrect
    .map(_.sortWith(rules.contains))
    .map(o => o(o.length/2)).sum
  )
}
