import scala.io.Source

val banks = Source.fromFile("3.txt")
  .getLines
  .map(_.split("").map(_.toInt).toSeq)
  .toSeq

def joltage(n: Int, bank: Seq[Int]): Long = {
  if (n == 0) 0
  else {
    val (max, i) = bank.dropRight(n - 1).zipWithIndex.maxBy(_._1)
    max*math.pow(10, n - 1).toLong + joltage(n - 1, bank.drop(i + 1))
  }
}

println(banks.map(joltage(2, _)).sum)
println(banks.map(joltage(12, _)).sum)
