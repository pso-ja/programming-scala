// code-examples/Scala28/array/array-v28-script.scala
class ArrayV28[T](val length:Int) (implicit m: ClassManifest[T]) {
  private var ary = new Array[T](length)
  def apply(i:Int) = ary(i)
  def update(i: Int, value:T) = ary(i) = value
  override def toString(): String = ary.mkString("Array(", ", ", ")")
}
var a = new ArrayV28[Int](10)
a.toString