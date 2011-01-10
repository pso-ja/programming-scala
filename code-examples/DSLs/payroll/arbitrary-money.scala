// code-examples/DSLs/payroll/arbitrary-money.scala
package payroll
import org.scalacheck._
import org.scalacheck.Prop._

trait ArbitraryMoney {
    def toD(i: Int) = i * 1000.
    
    implicit def arbitraryMoney: Arbitrary[Money] = Arbitrary {
        Gen.sized {s => 
            for { 
                whole   <- Gen.choose(-s, s) 
                decimal <- Gen.choose(-s, s) 
            } yield Money(toD(whole) + decimal * .11)
        }
    }
}