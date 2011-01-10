// code-examples/DSLs/payroll/dsl/payroll-dsl-script.scala

import payroll._
import payroll.dsl._
import payroll.dsl.rules._

val payrollCalculator = rules { employee =>
  employee salary_for 2.weeks minus_deductions_for { gross =>
    federalIncomeTax            is  (25.  percent_of gross)
    stateIncomeTax              is  (5.   percent_of gross)
    insurancePremiums           are (500. in gross.currency)
    retirementFundContributions are (10.  percent_of gross)
  }
}

val buck = Employee(Name("Buck", "Trends"), Money(80000))
val jane = Employee(Name("Jane", "Doe"), Money(90000))

List(buck, jane).foreach { employee =>
  val check = payrollCalculator(employee)
  format("%s %s: %s\n", employee.name.first, employee.name.last, check)
}
