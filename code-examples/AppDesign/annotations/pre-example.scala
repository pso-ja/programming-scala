// code-examples/AppDesign/annotations/pre-example.scala

import org.contract4j5.contract._

class Person(
  @Pre( "name != null && name.length() > 0" ) 
  val name: String,
  @Pre( value = "age > 0", message = "You're too young!" )
  val age: Int,
  @Pre( "ssn != null" )
  val ssn: SSN)
    
class SSN(
  @Pre( value = "valid(ssn)", message = "Format must be NNN-NN-NNNN." )
  val ssn: String) {

  private def valid(value: String) = 
    value.matches("""^\s*\d{3}-\d{2}-\d{4}\s*$""")
}
