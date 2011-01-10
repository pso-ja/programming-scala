// code-examples/Scala28/break/break-v28-script.scala
import scala.util.control.Breaks.{break, breakable}

breakable {
  for (i <- 1 until 10) {
    if (i > 5) break
    println(i)
  }
  println("not called")
}
println("end")
