package aoc202409

import scala.io.Source

case class File(index: Int, size: Int)

@main
def main() = {
  // val (files, _) = Source.fromFile("9.example.txt")
  //   .getLines
  //   .next
  //   .map(_.asDigit)
  //   .grouped(2)
  //   .foldLeft((Seq[File](), 0)) { (acc, next) =>
  //     val (files, last) = acc
  //     (
  //       files :+ File(last, next(0)),
  //       last + next(0) + next.lift(1).getOrElse(0)
  //     )
  //   }
  // println(files)

  // def compress(files: Seq[File]): Seq[File] = {
  //   val last = files.last
  //   val space = files.init.sliding(2).zipWithIndex.find {
  //     case (Seq(f1, f2), i) => f2.index > f1.index + f1.size + last.size
  //   }.map(_._2)
  //   files.init.patch(space, Seq(last), 0)
  // }

  val map = Source.fromFile("9.txt")
    .getLines
    .next
    .map(_.asDigit)
    .grouped(2)
    .zipWithIndex
    .flatMap {
      case (Seq(file, free), i) => Seq.fill(file)(Some(i)) ++ Seq.fill(free)(None)
      case (Seq(file), i) => Seq.fill(file)(Some(i))
    }
    .toSeq

  def last1(map: Seq[Option[Int]]): (Seq[Option[Int]], Int) = {
    map.last match {
      case Some(i) => (map.init, i)
      case None => last1(map.init)
    }
  }

  def frag(map: Seq[Option[Int]], i: Int): Seq[Option[Int]] = {
    if (i == map.length) map
    else if (map(i).isDefined) frag(map, i + 1)
    else {
      val (init, f) = last1(map)
      frag(init.patch(i, Seq(Some(f)), 1), i + 1)
    }
  }

  // def last2(map: Seq[Option[Int]]): (Seq[Option[Int]], Seq[Int]) = {
  //   map.last match {
  //     case Some(i) => (map.init, i)
  //     case None => last2(map.init)
  //   }
  // }

  // def defrag(map: Seq[Option[Int]], i: Int): Seq[Option[Int]] = {
  //   if (i == map.length) map
  //   else if (map(i).isDefined) defrag(map, i + 1)
  //   else {
  //     val (init, f) = last2(map)

  //     defrag(init.patch(i, Seq(Some(f)), 1), i + 1)
  //   }
  // }

  val fragmented = frag(map, 0)
  // // println(defraged.map {
  //   //   case Some(i) => i
  //   //   case None => '.'
  //   // }.mkString)
  println(fragmented.flatten.zipWithIndex.map{case (f, i) => (f*i).toLong}.sum)
  // val defragged = defrag(map, 0)
  // println(defragged.flatten.zipWithIndex.map{case (f, i) => (f*i).toLong}.sum)
}
