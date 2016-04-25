// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
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

class LocationsMapperSpec extends FunSpec {

  describe("The locations mapper") {

    it("should transform the date to the mongo format") {
      val date = "2013-07-12 07:15:00"
      val id1 = "id1"
      val trafficInfo1 = TrafficInfo(id1,date,"1065","9","48","M","73","N","4")
      val expectedDate = "2013-07-12T07:15:00Z"
      val transformedDate = trafficInfo1.mongoDate()

      transformedDate should equal(expectedDate)
    }

    it("should find out the coordinates for existing ids") {
      val id1 = "PM20152"
      val id2 = "PM20153"
      val xCoord1 = "1234,45"
      val xCoord2 = "6874,89"
      val yCoord1 = "6789,45"
      val yCoord2 = "4542,01"
      val coordinates1 = Coordinates(xCoord1, yCoord1)
      val coordinates2 = Coordinates(xCoord2, yCoord2)
      val coordinatesMap = Map((id1, coordinates1), (id2, coordinates2))
      val trafficInfo1 = TrafficInfo(id1,"2013-07-12 07:15:00","1065","9","48","M","73","N","4")
      val trafficInfo2 = trafficInfo1.copy(identif = id2)
      val pointsInfo = Iterator(trafficInfo1, trafficInfo2)
      val trafficInfoPlusCoordinates1 = TrafficInfoPlusCoordinates(trafficInfo1, coordinates1)
      val trafficInfoPlusCoordinates2 = TrafficInfoPlusCoordinates(trafficInfo2, coordinates2)
      val expectedOutput = List(trafficInfoPlusCoordinates1, trafficInfoPlusCoordinates2)
      val coordinates = LocationsMapper.findCoordinates(pointsInfo, coordinatesMap).toList

      coordinates should contain theSameElementsAs(expectedOutput)
    }

    it("should not fail when there is no coordinates for an id") {
      val id1 = "id1"
      val id2 = "id2"
      val nonExistingId = "nonExistingId"
      val xCoord1 = "1234,45"
      val xCoord2 = "6874,89"
      val yCoord1 = "6789,45"
      val yCoord2 = "4542,01"
      val coordinates1 = Coordinates(xCoord1, yCoord1)
      val coordinates2 = Coordinates(xCoord2, yCoord2)
      val coordinatesMap: Map[String, Coordinates] = Map((id1, coordinates1), (id2, coordinates2))
      val trafficInfo1 = TrafficInfo(id1,"2013-07-12 07:15:00","1065","9","48","M","73","N","4")
      val trafficInfo2 = trafficInfo1.copy(identif = nonExistingId)
      val pointsInfo = Iterator(trafficInfo1, trafficInfo2)
      val trafficInfoPlusCoordinates1 = TrafficInfoPlusCoordinates(trafficInfo1, coordinates1)
      val expectedCoordinates = List(trafficInfoPlusCoordinates1)
      val coordinates = LocationsMapper.findCoordinates(pointsInfo, coordinatesMap).toList

      coordinates.size should be(1)
      coordinates should contain theSameElementsAs(expectedCoordinates)
    }
  }
}