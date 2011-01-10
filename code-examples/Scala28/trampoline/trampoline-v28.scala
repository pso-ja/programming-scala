// code-examples/Scala28/trampoline/trampoline-v28.scala
import scala.util.control.TailCalls._

def isEven(n: Int): TailRec[Boolean] = if (n == 0) done(true) else tailcall(isOdd(n - 1))
def isOdd(n: Int): TailRec[Boolean] = if (n == 0) done(false) else tailcall(isEven(n - 1))

println(isEven(9999).result)
