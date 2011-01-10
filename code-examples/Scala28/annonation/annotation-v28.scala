// code-examples/Scala28/annonation/annotation-v28.scala
import scala.StaticAnnotation

class Ann1(id:Int=1, name:List[String]=List("bar")) extends StaticAnnotation

@Ann1(id=1,name=List("foo"))
class Test1 {}

@Ann1()
class Test2 {}

class Ann2(name:String, list:List[Ann1]) extends StaticAnnotation

@Ann2(name="foo", list=List(new Ann1(1), new Ann1(2)))
class Test3 {}
