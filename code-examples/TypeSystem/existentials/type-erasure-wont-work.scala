// code-examples/TypeSystem/existentials/type-erasure-wont-work.scala
// WARNINGS: Does not work as you might expect.

object ProcessList {
  def apply[B](list: List[B]) = list match {
    case lInt:    List[Int]    => // do something
    case lDouble: List[Double] => // do something
    case lString: List[String] => // do something
    case _                     => // default behavior
  }
}