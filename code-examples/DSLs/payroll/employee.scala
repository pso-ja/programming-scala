// code-examples/DSLs/payroll/employee.scala

package payroll

case class Name(first: String, last: String)

case class Employee(name: Name, annualGrossSalary: Money)

