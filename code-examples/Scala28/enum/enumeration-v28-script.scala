// code-examples/Scala28/enum/enumeration-v28-script.scala
object WeekDay extends Enumeration {
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}
import WeekDay._
WeekDay.values foreach{ println(_) }
println(WeekDay.values)
WeekDay.withName("Mon")
