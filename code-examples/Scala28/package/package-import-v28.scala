// code-examples/Scala28/package/package-import-v28.scala
import foo._   // 明示的に親のパッケージをインポートする必要がある。
import foo.bar._

class Baz {
  var bar = new Bar;
  var foo = new Foo; 
}
