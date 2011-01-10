// code-examples/Scala28/specialized/myvector-v28.scala
package pso;

class MyVector[@specialized T](val len: Int)(implicit manifest: Manifest[T]){
  val array = new Array[T](len)
  def apply(i: Int): T = array(i)
  def update(i:Int, data:T) = array(i) = data
}
