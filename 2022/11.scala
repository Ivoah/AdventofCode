import scala.collection.mutable

object Expression {
  private val expression_re = raw"old ([+*]) (\w+)".r
  private val ops: Map[String, (BigInt, BigInt) => BigInt] = Map(
    "+" -> ((a, b) => a + b),
    "*" -> ((a, b) => a * b)
  )
  def parse(str: String): BigInt => BigInt = {
    str match {
      case expression_re(op, "old") =>
        val fn = ops(op)
        old => fn(old, old)
      case expression_re(op, arg) =>
        val fn = ops(op)
        val argInt = arg.toInt
        old => fn(old, argInt)
    }
  }
}

case class Monkey(items: mutable.Queue[BigInt], operation: BigInt => BigInt, test: BigInt => Int, test_n: Int) {
  var inspections: BigInt = 0
  def inspect(reduce: Boolean = true, lcm: Option[BigInt] = None) = {
    inspections += 1
    val item = lcm match {
      case Some(lcm) => operation(items.dequeue())/(if (reduce) 3 else 1) % lcm
      case None => operation(items.dequeue())/(if (reduce) 3 else 1)
    }
    (item, test(item))
  }
}

object Monkey {
  private val monkey_re = raw"Monkey \d+:\n  Starting items: (.+)\n  Operation: new = (.+)\n  Test: divisible by (\d+)\n    If true: throw to monkey (\d+)\n    If false: throw to monkey (\d+)".r
  def parse(strs: Seq[String]): Monkey = {
    strs.filter(_.nonEmpty).mkString("\n") match {
      case monkey_re(items, operation, divisible, if_true, if_false) =>
        Monkey(
          items.split(", ").map(BigInt.apply).to(mutable.Queue),
          Expression.parse(operation),
          item => if (item%divisible.toInt == 0) if_true.toInt else if_false.toInt,
          divisible.toInt
        )
    }
  }
}

def lcm(list: Seq[BigInt]): BigInt = list.foldLeft(1:BigInt) {
  (a, b) => b * a /
  Stream.iterate((a,b)){case (x,y) => (y, x%y)}.dropWhile(_._2 != 0).head._1.abs
}

@main
def main() = {
  val monkeys = io.Source.fromFile("11.txt").getLines().grouped(7).map(Monkey.parse).toSeq

  for (round <- 0 until 20) {
    for (monkey <- monkeys) {
      while (monkey.items.nonEmpty) {
        val (item, to) = monkey.inspect()
        monkeys(to).items.append(item)
      }
    }
  }

  println(monkeys.map(_.inspections).sorted.reverse.take(2).reduce(_ * _))


  val monkeys_2 = io.Source.fromFile("11.txt").getLines().grouped(7).map(Monkey.parse).toSeq

  for (round <- 0 until 10000) {
    val _lcm = lcm(monkeys_2.map(_.test_n))
    for (monkey <- monkeys_2) {
      while (monkey.items.nonEmpty) {
        val (item, to) = monkey.inspect(false, Some(_lcm))
        monkeys_2(to).items.append(item)
      }
    }
  }

  println(monkeys_2.map(_.inspections).sorted.reverse.take(2).reduce(_ * _))
}
