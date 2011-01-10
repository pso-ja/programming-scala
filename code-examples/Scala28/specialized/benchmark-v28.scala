// code-examples/Scala28/specialized/benchmark-v28.scala
package pso;
import scala.testing.Benchmark

object Main {
  def square(data:MyVector[Double]):MyVector[Double] = {
    var result = new MyVector[Double](data.len)
    for(i <- 0 until data.len) {
      result.update(i, data(i) * data(i))
    }
    result
  }

  def main(args:Array[String]) = {
    val vector = new MyVector[Double](1000000)
    for(i <- 0 until vector.len) {
      vector(i) = i
    }

    object BM extends Benchmark { def run = square(vector) }

    println ((BM runBenchmark(100) sum) /1000.0)
  }
}
