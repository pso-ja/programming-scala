// code-examples/DSLs/payroll/paycheck.scala

package payroll

/** We're ignoring invalid (?) cases like a negative net 
 *  when deductions exceed the gross.
 */
case class Paycheck(gross: Money, net: Money, deductions: Money) {

  def plusGross (m: Money)      = Paycheck(gross + m, net + m, deductions)
  def plusDeductions (m: Money) = Paycheck(gross,     net - m, deductions + m)
}

