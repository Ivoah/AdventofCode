import scala.io.Source

val map = Source.fromFile("3.txt").getLines().toSeq

def trees(slope: (Int, Int)): Long = (map.indices by slope._2).zipWithIndex.count{case (y, i) => map(y)(i*slope._1%map(y).length) == '#'}
println(trees((3, 1)))
println(Seq((1, 1), (3, 1), (5, 1), (7, 1), (1, 2)).map(trees).product)
