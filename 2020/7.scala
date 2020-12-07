import scala.io.Source

val line_re = raw"(\w+) (\w+) bags contain (?:((?:\d+ \w+ \w+ bags?(?:, )?)+)|no other bags)\.".r
val contains_re = raw"(\d+) (\w+) (\w+) bags?".r

implicit val rules: Map[(String, String), Map[(String, String), Int]] = Source.fromFile("7.txt").getLines().map {
  case line_re(adj, col, null) => (adj, col) -> Map[(String, String), Int]()
  case line_re(adj, col, contains) => (adj, col) -> contains.split(", ").map {
    case contains_re(n, adj, col) => (adj, col) -> n.toInt
  }.toMap
}.toMap

def can_hold(bag: (String, String))(implicit rules: Map[(String, String), Map[(String, String), Int]]): Seq[(String, String)] = {
  val bags = rules.toSeq.collect {
    case (k, v) if v.contains(bag) => k
  }
  (bags ++ bags.flatMap(can_hold)).distinct
}

def contains(bag: (String, String))(implicit rules: Map[(String, String), Map[(String, String), Int]]): Int = {
  rules(bag).map{case (b, n) => n + contains(b)*n}.sum
}

println(can_hold(("shiny", "gold")).length)
println(contains(("shiny", "gold")))
