val readings = scala.io.Source.fromFile("1.txt").getLines().map(_.toInt).toSeq

println(readings.sliding(2, 1).count(p => p(1) > p(0)))
println(readings.sliding(3, 1).map(_.sum).sliding(2, 1).count(p => p(1) > p(0)))
