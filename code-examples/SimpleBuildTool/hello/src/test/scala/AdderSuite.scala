import org.scalatest.FunSuite
class AdderSuite extends FunSuite {
  test("""Adderのテスト""") {
    assert(4 === Adder.add(1,3))
  }
}