import scala.io.Source

val report = Source.fromFile("1.in").getLines().map(_.toInt).toSeq

(2 to 3).foreach(c => println(report.combinations(c).filter(_.sum == 2020).next().product))

