// code-examples/ToolsLibs/complex-javabean.scala

case class ComplexBean(
  @scala.reflect.BeanProperty real: Double, 
  @scala.reflect.BeanProperty imaginary: Double) {

  def +(that: ComplexBean) = 
    new ComplexBean(real + that.real, imaginary + that.imaginary)
  def -(that: ComplexBean) = 
    new ComplexBean(real - that.real, imaginary - that.imaginary)
}
