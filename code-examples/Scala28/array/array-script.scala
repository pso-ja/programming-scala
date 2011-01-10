// code-examples/Scala28/array/array-script.scala
class ArrayV27[T](val length: Int){
  private var ary = new Array[T](length)
  def apply(i:Int) = ary(i)
  def update(i: Int, value:T) = ary(i) = value
  override def toString(): String = "" + ary.getClass +":" + ary.mkString("Array(", ", ", ")")
}

var a = new ArrayV27[Int](10)
a(0).toString
