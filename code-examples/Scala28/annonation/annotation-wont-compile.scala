// code-examples/Scala28/annonation/annotaion-wont-compile.scala
import scala.StaticAnnotation

class Ann3(values:Array[String]) extends StaticAnnotation

@Ann3(Array("foo", "bar"))
class Test1{}
