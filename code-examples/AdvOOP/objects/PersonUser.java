// code-examples/AdvOOP/objects/PersonUser.java

package objects;

public class PersonUser {
  public static void main(String[] args) {
    // The following line won't compile.
    // Person buck = Person.apply("Buck Trends", 100);
    Person buck = PersonFactory.make("Buck Trends", 100);
    System.out.println(buck);
  }
}