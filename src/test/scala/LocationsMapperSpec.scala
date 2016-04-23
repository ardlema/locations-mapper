import org.scalatest.FunSpec
import org.scalatest.Matchers._

class LocationsMapperSpec extends FunSpec {

  describe("The locations mapper") {

    it("should find out the coordinates for an existing id") {
      val id1 = "id1"
      val id2 = "id2"
      val xCoord1 = "1234,45"
      val xCoord2 = "6874,89"
      val yCoord1 = "6789,45"
      val yCoord2 = "4542,01"
      val coordinatesMap: Map[String, (String, String)] = Map((id1, (xCoord1, yCoord1)), (id2, (xCoord2, yCoord2)))
      val element1 = "78"
      val element2 = "30"
      val pointsInfo = List((id1, element1), (id2, element2))
      val expectedCoordinates = List(
        (id1, element1, xCoord1, yCoord1),
        (id2, element2, xCoord2, yCoord2))
      val coordinates = LocationsMapper.findCoordinates(pointsInfo, coordinatesMap)

      coordinates should contain theSameElementsAs(expectedCoordinates)
    }
  }
}

object LocationsMapper {

  def findCoordinates(
     pointsInfo: List[(String, String)],
     coordinatesMap: Map[String, (String, String)]): List[(String, String, String, String)] = {
    for (pointInfo <- pointsInfo) yield (
      pointInfo._1,
      pointInfo._2,
      coordinatesMap.get(pointInfo._1).get._1,
      coordinatesMap.get(pointInfo._1).get._2)
  }
}