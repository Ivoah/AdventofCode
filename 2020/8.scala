import scala.annotation.tailrec
import scala.io.Source

val re = raw"(\w+) ([+-]\d+)".r
implicit val instructions: Seq[(String, Int)] = Source.fromFile("8.txt").getLines().map{case re(instr, arg) => (instr, arg.toInt)}.toSeq

@tailrec
def run(hist: Seq[Int] = Seq(0), acc: Int = 0)(implicit instructions: Seq[(String, Int)]): Either[Int, Int] = {
  val pc = hist.last
  val (next_acc, next_pc) = instructions(pc) match {
    case ("acc", n) => (acc + n, pc + 1)
    case ("jmp", n) => (acc, pc + n)
    case ("nop", _) => (acc, pc + 1)
  }
  if (hist.contains(next_pc)) Left(next_acc)
  else if (next_pc >= instructions.length) Right(next_acc)
  else run(hist :+ next_pc, next_acc)
}

println(run() match {case Left(ans) => ans})

val modified_instructions = instructions.indices.collect {
  case i if instructions(i)._1 == "jmp" => instructions.zipWithIndex.map {
    case ((instr, n), j) => if (j == i) ("nop", n) else (instr, n)
  }
  case i if instructions(i)._1 == "nop" => instructions.zipWithIndex.map {
    case ((instr, n), j) => if (j == i) ("jmp", n) else (instr, n)
  }
}
val ans = modified_instructions.collect {
  run()(_) match {
    case Right(ans) => ans
  }
}.head
println(ans)
