@main
def main() = {
  val stacks = io.Source.fromFile("5.txt").getLines().takeWhile(_.nonEmpty).toSeq.dropRight(1).map(_.grouped(4).map(_(1)).toSeq).transpose.map(_.filter(_ != ' '))
    
  val move_re = raw"move (\d+) from (\d+) to (\d+)".r
  val moves = io.Source.fromFile("5.txt").getLines().collect {
    case move_re(num, from, to) => (num.toInt, from.toInt - 1, to.toInt - 1)
  }.toSeq

  val final_stacks = moves.foldLeft(stacks) { case (current_stacks, (num, from, to)) =>
    current_stacks.zipWithIndex.map { case (stack, i) =>
      if (i == from) stack.drop(num)
      else if (i == to) current_stacks(from).take(num).reverse ++ stack
      else stack
    }
  }

  println(final_stacks.map(_.head).mkString)

  val final_stacks_part_2 = moves.foldLeft(stacks) { case (current_stacks, (num, from, to)) =>
    current_stacks.zipWithIndex.map { case (stack, i) =>
      if (i == from) stack.drop(num)
      else if (i == to) current_stacks(from).take(num) ++ stack
      else stack
    }
  }

  println(final_stacks_part_2.map(_.head).mkString)
}
