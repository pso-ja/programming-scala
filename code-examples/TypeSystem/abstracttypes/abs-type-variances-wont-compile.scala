// code-examples/TypeSystem/abstracttypes/abs-type-variances-wont-compile.scala
// WON'T COMPILE

trait T1 { val name1: String }
trait T2 extends T1 { val name2: String }
class C(val name1: String, val name2: String) extends T2

trait T {
  type t: +T1   // ERROR, no +/- type variance annotations
  val v
}
