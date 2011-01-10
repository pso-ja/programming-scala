// code-examples/DSLs/payroll/dsl/payroll-dsl-spec.scala

package payroll.dsl
import org.specs._
import org.specs.matcher.Monoid
import payroll._
import payroll.dsl.rules._
import payroll.Type2Money._

// Not very complete...
object PayrollSpec extends Specification("Payroll") {

  val payrollCalculator = rules { employee =>
    employee salary_for 2.weeks minus_deductions_for { gross =>
      federalIncomeTax            is  (25.  percent_of gross)
      stateIncomeTax              is  (5.   percent_of gross)
      insurancePremiums           are (500. in gross.currency)
      retirementFundContributions are (10.  percent_of gross)
    }
  }

  implicit def money2double(m: Money) = m.amount.doubleValue

  implicit def moneyToMonoid(v: Money) = new Monoid[Money] {
    def +(t: Money) = v + t
    def -(t: Money) = v - t
  }

  implicit def moneyToOrderd(v:Money) = new Ordered[Money] {
    def compare(that: Money):Int =
      if (v < that) -1
      else if (v > that) 1
      else  0
  }

  "Payroll calculation" should {
    "calculate the gross, net, and deductions for the pay period" in {
      for (m <- 3 to 10) {
        val salary = Money(m * 10000.1)
        val buck = Employee(Name("Buck", "Trends"), salary)
        val expectedGross = salary / 26.
        val expectedDeductions = (expectedGross * .4) + Money(500)
        val expectedNet = expectedGross - expectedDeductions
        // For some reason, actual types as ScalaObject, unless cast.
        val actual = payrollCalculator(buck).asInstanceOf[Paycheck]
        actual.gross must beCloseTo(expectedGross, Money(.001))
        actual.net must beCloseTo(expectedNet, Money(.001))
        actual.deductions must beCloseTo(expectedDeductions, Money(.001))
      }
    }
  }
}
