// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License"),
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package mapper

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class LocationsFileReaderSpec extends FunSpec {

  describe("The locations file reader") {

    it("should read a list of files within a directory and get the list of elements") {
      val directoryName = "./src/test/resources/dir1"
      val fileName1 = "points1.csv"
      val fileName2 = "points2.csv"
      val trafficInfo1 = TrafficInfo("PM20152","2013-07-12 07:15:00","1065","9","48","M","73","N","4")
      val trafficInfo2 = TrafficInfo("PM22901","2013-07-12 07:15:00","912","7","18","M","58","N","5")
      val trafficInfo3 = TrafficInfo("PM22971","2013-07-12 07:15:00","1008","6","19","M","64","N","5")
      val trafficInfo4 = TrafficInfo("90009","2013-07-12 07:15:00","16","6","6","C","0","N","5")
      val expectedFirstElement = (fileName1, List(trafficInfo1, trafficInfo2))
      val expectedSecondElement = (fileName2, List(trafficInfo3, trafficInfo4))
      val pointsList = LocationsFileReader.findPointsForFiles(directoryName)

      expectedFirstElement._1 should equal(fileName1)
      expectedFirstElement._2 should contain theSameElementsAs(pointsList(1)._2.toList)
      expectedSecondElement._1 should equal(fileName2)
      expectedSecondElement._2 should contain theSameElementsAs(pointsList(0)._2.toList)
    }
  }

  it("should read the file with locations ids and get the map") {
    val coordinatesFile = "./src/test/resources/pointsidandcoordinates.txt"
    val id1 = "id1"
    val id2 = "id2"
    val xCoord1 = "1234,78"
    val yCoord1 = "3456,90"
    val xCoord2 = "5678,90"
    val yCoord2 = "4567,23"
    val coordinates1 = Coordinates(xCoord1, yCoord1)
    val coordinates2 = Coordinates(xCoord2, yCoord2)
    val expectedMapCoordinates = Map((id1, coordinates1), (id2, coordinates2))
    val mapCoordinates = LocationsFileReader.findCoordinates(coordinatesFile)

    mapCoordinates should contain theSameElementsAs(expectedMapCoordinates)
  }
}