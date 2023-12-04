import scala.io.Source

@main
def main() = {
    println(Source.fromFile("1.txt").getLines.map { line =>
        val digits = line.filter(_.isDigit).map(_.asDigit)
        s"${digits.head}${digits.last}".toInt
    }.sum)

    val numbers = Map(
        "zero" -> '0', "0" -> '0',
        "one" -> '1', "1" -> '1',
        "two" -> '2', "2" -> '2',
        "three" -> '3', "3" -> '3',
        "four" -> '4', "4" -> '4',
        "five" -> '5', "5" -> '5',
        "six" -> '6', "6" -> '6',
        "seven" -> '7', "7" -> '7',
        "eight" -> '8', "8" -> '8',
        "nine" -> '9', "9" -> '9',
    )
    println(Source.fromFile("1.txt").getLines.map { line =>
        s"${
            numbers.keys.map(n => (line.indexOfSlice(n), numbers(n))).filter(_._1 >= 0).minBy(_._1)._2
        }${
            numbers.keys.map(n => (line.lastIndexOfSlice(n), numbers(n))).maxBy(_._1)._2
        }".toInt
    }.sum)
}
