package mapper


object
LocationsMapper {

  def findCoordinates(
                       pointsInfo: List[(String, String)],
                       coordinatesMap: Map[String, (String, String)]): List[(String, String, String, String)] = {
    for (pointInfo <- pointsInfo;
         coordinates = coordinatesMap.get(pointInfo._1);
         if coordinates.isDefined
    ) yield (
      pointInfo._1,
      pointInfo._2,
      coordinates.get._1,
      coordinates.get._2)
  }
}
