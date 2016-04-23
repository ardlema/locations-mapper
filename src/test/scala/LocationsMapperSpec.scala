import org.scalatest.FunSpec
import org.scalatest.Matchers._

class LocationsMapperSpec extends FunSpec {

  describe("The location mapper") {

    it("should say hello") {
      val greeting = LocationsMapper.sayHello()
      greeting shouldBe "hello"
    }
  }
}

object LocationsMapper {

  def sayHello(): String = "hello"
}