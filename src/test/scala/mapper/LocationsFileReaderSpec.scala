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
      val trafficInfo1 = TrafficInfo("1001", "2017-06-01 00:00:00", "05FT10PM01", "PUNTOS MEDIDA M-30", "576", "4", "0", "73", "N", "5")
      val trafficInfo2 = TrafficInfo("1002", "2017-06-01 00:00:00", "05FT37PM01", "PUNTOS MEDIDA M-30", "888", "4", "0", "70", "N", "5")
      val trafficInfo3 = TrafficInfo("1003", "2017-06-01 00:00:00", "05FT66PM01", "PUNTOS MEDIDA M-30", "1008", "4", "0", "75", "N", "5")
      val trafficInfo4 = TrafficInfo("1006", "2017-06-01 00:00:00", "04FT74PM01", "PUNTOS MEDIDA M-30", "888", "3", "0", "67", "N", "5")
      val pointsList = LocationsFileReader.findPointsForFiles(directoryName)
      val expectedFileNameList = Seq(fileName1, fileName2)
      val expectedTrafficInfoList = Seq(trafficInfo1, trafficInfo2, trafficInfo3, trafficInfo4)
      val pointsListFileNames = pointsList.map(_._1)
      val pointsListTrafficInfoAsList = pointsList.flatMap(_._2.toList)

      pointsListFileNames should contain theSameElementsAs(expectedFileNameList)
      pointsListTrafficInfoAsList should contain theSameElementsAs(expectedTrafficInfoList)
    }
  }

  it("should read the file with locations ids and get the map") {
    val coordinatesFile = "./src/test/resources/pointsidandcoordinates.txt"
    val id1 = "03FL08PM01"
    val id2 = "03FL08PM02"
    val xCoord1 = "439474.372756695"
    val yCoord1 = "4474094.8493885"
    val xCoord2 = "438850.92988411"
    val yCoord2 = "4474529.21911231"
    val coordinates1 = Coordinates(xCoord1, yCoord1)
    val coordinates2 = Coordinates(xCoord2, yCoord2)
    val expectedMapCoordinates = Map((id1, coordinates1), (id2, coordinates2))
    val mapCoordinates = LocationsFileReader.findCoordinates(coordinatesFile)

    mapCoordinates should contain theSameElementsAs(expectedMapCoordinates)
  }
}