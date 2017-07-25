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

import common.{DegreeCoordinates, UtmCoordinates}
import org.scalatest.FunSpec
import org.scalatest.Matchers._

class LocationsMapperSpec extends FunSpec {

  describe("The locations mapper") {

    it("should find out the degree coordinates for existing ids") {
      val id1 = "PM20152"
      val id2 = "PM20153"
      val easting1 = 439474.372756695
      val northing1 = 4474094.8493885
      val easting2 = 439875.372756695
      val northing2 = 4474195.8493885
      val coordinates1 = UtmCoordinates(easting1, northing1)
      val coordinates2 = UtmCoordinates(easting2, northing2)
      val coordinatesMap = Map((id1, coordinates1), (id2, coordinates2))
      val trafficInfo1 = TrafficInfo("1","2013-07-12 07:15:00", id1, "9", "48", "M", "73", "N", "4", "5")
      val trafficInfo2 = trafficInfo1.copy(identif = id2)
      val pointsInfo = Iterator(trafficInfo1, trafficInfo2)
      val latitude1 = 40.41527701272302
      val longitude1 = -3.7134030732037786
      val latitude2 = 40.41621593316199
      val longitude2 = -3.708686459322763
      val degreeCoordinates1 = DegreeCoordinates(latitude1, longitude1)
      val degreeCoordinates2 = DegreeCoordinates(latitude2, longitude2)
      val trafficInfoPlusCoordinates1 = TrafficInfoPlusCoordinates(trafficInfo1, degreeCoordinates1)
      val trafficInfoPlusCoordinates2 = TrafficInfoPlusCoordinates(trafficInfo2, degreeCoordinates2)
      val expectedOutput = List(trafficInfoPlusCoordinates1, trafficInfoPlusCoordinates2)
      val coordinates = LocationsMapper.getLongitudeAndLatitudeCoordinates(pointsInfo, coordinatesMap).toList

      coordinates.size should be(2)
      coordinates should contain theSameElementsAs(expectedOutput)
    }

    it("should not fail when there is no coordinates for an id") {
      val id1 = "id1"
      val id2 = "id2"
      val nonExistingId = "nonExistingId"
      val easting1 = 439474.372756695
      val northing1 = 4474094.8493885
      val easting2 = 439875.372756695
      val northing2 = 4474195.8493885
      val coordinates1 = UtmCoordinates(easting1, northing1)
      val coordinates2 = UtmCoordinates(easting2, northing2)
      val coordinatesMap = Map((id1, coordinates1), (id2, coordinates2))
      val trafficInfo1 = TrafficInfo("1", "2013-07-12 07:15:00", id1, "9"," 48", "M", "73", "N", "4", "5")
      val trafficInfo2 = trafficInfo1.copy(identif = nonExistingId)
      val pointsInfo = Iterator(trafficInfo1, trafficInfo2)
      val latitude1 = 40.41527701272302
      val longitude1 = -3.7134030732037786
      val latitude2 = 40.415276
      val longitude2 = -3.713403079672105
      val degreeCoordinates1 = DegreeCoordinates(latitude1, longitude1)
      val degreeCoordinates2 = DegreeCoordinates(latitude2, longitude2)
      val trafficInfoPlusCoordinates1 = TrafficInfoPlusCoordinates(trafficInfo1, degreeCoordinates1)
      val expectedCoordinates = List(trafficInfoPlusCoordinates1)
      val coordinates = LocationsMapper.getLongitudeAndLatitudeCoordinates(pointsInfo, coordinatesMap).toList

      coordinates.size should be(1)
      coordinates should contain theSameElementsAs(expectedCoordinates)
    }
  }
}