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

    it("should find out the coordinates for existing ids") {
      val id1 = "id1"
      val id2 = "id2"
      val xCoord1 = "1234,45"
      val xCoord2 = "6874,89"
      val yCoord1 = "6789,45"
      val yCoord2 = "4542,01"
      val coordinatesMap = Map((id1, (xCoord1, yCoord1)), (id2, (xCoord2, yCoord2)))
      val element1 = "78"
      val element2 = "30"
      val pointsInfo = List((id1, element1), (id2, element2))
      val expectedCoordinates = List(
        (id1, element1, xCoord1, yCoord1),
        (id2, element2, xCoord2, yCoord2))
      val coordinates = LocationsMapper.findCoordinates(pointsInfo, coordinatesMap)

      coordinates should contain theSameElementsAs(expectedCoordinates)
    }

    it("should not fail when there is no coordinates for an id") {
      val id1 = "id1"
      val id2 = "id2"
      val nonExistingId = "nonExistingId"
      val xCoord1 = "1234,45"
      val xCoord2 = "6874,89"
      val yCoord1 = "6789,45"
      val yCoord2 = "4542,01"
      val coordinatesMap = Map((id1, (xCoord1, yCoord1)), (id2, (xCoord2, yCoord2)))
      val element1 = "78"
      val element2 = "30"
      val pointsInfo = List((id1, element1), (nonExistingId, element2))
      val expectedCoordinates = List((id1, element1, xCoord1, yCoord1))
      val coordinates = LocationsMapper.findCoordinates(pointsInfo, coordinatesMap)

      coordinates should contain theSameElementsAs(expectedCoordinates)
    }
  }
}