// code-examples/AdvOOP/shapes/shapes-case-id.scala

package shapesid {  
  case class Point(x: Double, y: Double)

  abstract case class Shape(id: String) { 
    def draw(): Unit
  }
  
  case class Circle(override val id: String, center: Point, radius: Double)
        extends Shape(id) {
    def draw() = println("Circle.draw: " + this)
  }
  
  case class Rectangle(override val id: String, lowerLeft: Point, 
        height: Double, width: Double) extends Shape(id) {
    def draw() = println("Rectangle.draw: " + this)
  }
  
  case class Triangle(override val id: String, point1: Point, 
        point2: Point, point3: Point) extends Shape(id) {
    def draw() = println("Triangle.draw: " + this)
  }
}