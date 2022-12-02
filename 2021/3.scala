object MyImplicits {
    implicit class Common[T](seq: Seq[T]) {
        val groups = seq.groupBy(identity).groupBy(_._2.length).view.mapValues(_.map(_._1)).toMap

        def mostCommon: T = mostCommon(None)
        def mostCommon(default: T): T = mostCommon(Some(default))
        def mostCommon(default: Option[T]): T = {
            val max = groups.maxBy(_._1)._2
            default match {
                case Some(default) if max.size > 1 => default
                case _ => max.head
            }
        }

        def leastCommon: T = leastCommon(None)
        def leastCommon(default: T): T = leastCommon(Some(default))
        def leastCommon(default: Option[T]): T = {
            val min = groups.minBy(_._1)._2
            default match {
                case Some(default) if min.size > 1 => default
                case _ => min.head
            }
        }
    }
}
import MyImplicits._

val report = scala.io.Source.fromFile("3.txt").getLines().toSeq

val gamma = (0 until report.head.length).map(i => report.map(_(i)).mostCommon).map(_.asDigit)
val epsilon = gamma.map(Map(0 -> 1, 1 -> 0))

println(gamma.foldLeft(0)(_*2+_)*epsilon.foldLeft(0)(_*2+_))

def filter(values: Seq[String], bitCriteria: (Seq[String], Int) => Int, bitPosition: Int = 0): Seq[String] = {
    values.filter(_(bitPosition) == bitCriteria(values, bitPosition)) match {
        case Seq(single) => Seq(single)
        case multiple => filter(multiple, bitCriteria, bitPosition + 1)
    }
}

val o2gr = filter(report, (values: Seq[String], bitPosition) => values.map(_(bitPosition)).mostCommon('1')).head.map(_.asDigit)
val co2sr = filter(report, (values: Seq[String], bitPosition) => values.map(_(bitPosition)).leastCommon('0')).head.map(_.asDigit)

println(o2gr.foldLeft(0)(_*2+_)*co2sr.foldLeft(0)(_*2+_))
