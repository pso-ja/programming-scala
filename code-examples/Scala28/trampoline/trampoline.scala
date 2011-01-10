// code-examples/Scala28/trampoline/trampoline-script.scala
def isEven(n: Int):Boolean = if (n == 0) true  else isOdd(n - 1)
def isOdd(n: Int):Boolean  = if (n == 0) false else isEven(n - 1)

println(isEven(9999))
