// code-examples/ToolsLibs/aspectj/complex.scala

package example.aspectj

case class Complex(real: Double, imaginary: Double) {
  def +(that: Complex) = 
    new Complex(real + that.real, imaginary + that.imaginary)
  def -(that: Complex) = 
    new Complex(real - that.real, imaginary - that.imaginary)
}
