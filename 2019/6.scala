import scala.io.Source

val re = raw"(\w+)\)(\w+)".r
implicit val orbits: Map[String, String] = Source.fromFile("6.txt").getLines().map{case re(b1, b2) => (b2, b1)}.toMap

def path(body: String)(implicit orbits: Map[String, String]): Seq[String] = {
  if (body == "COM") Seq(body)
  else path(orbits(body)) :+ body
}

println(orbits.keys.toSeq.map(path(_).length - 1).sum)

def common[A](path1: Seq[A], path2: Seq[A]) = {
  path1.zip(path2).takeWhile(Function.tupled(_ == _)).unzip._1
}

val you_path = path("YOU")
val santa_path = path("SAN")
println(you_path.length - 1 + santa_path.length - 1 - common(you_path, santa_path).length*2)
