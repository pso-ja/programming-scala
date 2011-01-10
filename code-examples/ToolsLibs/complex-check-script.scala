// code-examples/ToolsLibs/complex-check-script.scala
// modified for the newer ScalaCheck (tested with ScalaCheck1.6)

import org.scalacheck._
import org.scalacheck.Prop._

def toD(i: Int) = i * .1

implicit def arbitraryComplex: Arbitrary[Complex] = Arbitrary {
  Gen.sized {s => 
    for {
      r <- Gen.choose(-toD(s), toD(s))
      i <- Gen.choose(-toD(s), toD(s))
    } yield Complex(r, i)
  }
}

object ComplexSpecification extends Properties("Complex") {
  def additionTest(a: Complex, b: Complex) = 
    (a + b).real.equals(a.real + b.real) &&
    (a + b).imaginary.equals(a.imaginary + b.imaginary)

  def subtractionTest(a: Complex, b: Complex) = 
    (a - b).real.equals(a.real - b.real) &&
    (a - b).imaginary.equals(a.imaginary - b.imaginary)

  val zero = Complex(0.0, 0.0)
  
  property("addition with (0,0)") = forAll { (a: Complex)  => additionTest(a, zero) }
  property("subtraction with (0,0)") = forAll { (a: Complex)  => subtractionTest(a, zero) }
  
  property("addition") = forAll { (a: Complex, b: Complex) => additionTest(a,b) }
  property("subtraction") = forAll { (a: Complex, b: Complex) => subtractionTest(a,b) }
}
ComplexSpecification.check