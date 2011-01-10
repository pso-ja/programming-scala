// code-examples/AdvOOP/objects/person-factory.scala

package objects

object PersonFactory {
  def make(name: String, age: Int) = new Person(name, age)
}