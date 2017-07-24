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

import java.io.File

import org.scalatest.{BeforeAndAfterAll, FunSpec}
import org.scalatest.Matchers._

class LocationsFileWriterSpec extends FunSpec with BeforeAndAfterAll {
  val fileName = "./src/test/resources/temp.txt"
  val fileWritten = new File(fileName)

  override def afterAll(): Unit = {
    fileWritten.delete()
  }

  describe("The locations file writer") {

    it("should read a list of locations info and coordinates and write them to a file") {
      val coord1x = "678,89"
      val coord1y = "458,89"
      val coord2x = "123,45"
      val coord2y = "111,99"
      val coordinates1 = Coordinates(coord1x, coord1y)
      val coordinates2 = Coordinates(coord2x, coord2y)
      val trafficInfo1 = TrafficInfo("1", "2013-07-12 07:15:00", "1065", "9", "48", "M", "73", "N", "4", "5")
      val trafficInfo2 = TrafficInfo("2", "2013-07-12 07:15:00", "912", "7", "18", "M", "58", "N", "5", "3")
      val trafficInfoPlusCoordinates1 = TrafficInfoPlusCoordinates(trafficInfo1, coordinates1)
      val trafficInfoPlusCoordinates2 = TrafficInfoPlusCoordinates(trafficInfo2, coordinates2)

      val pointsAndCoordinatesList = Iterator(trafficInfoPlusCoordinates1, trafficInfoPlusCoordinates2)
      LocationsFileWriter.writeToFile(fileName, pointsAndCoordinatesList)
      val src = io.Source.fromFile(fileWritten)
      val lines = src.getLines.toList
      lines.size should be(2)

      lines should contain theSameElementsAs (Seq(
        s"""{"idelem":"1","fecha":"2013-07-12 07:15:00", "identif": "1065", "tipoElem": "9", "intensidad": "48", "ocupacion": "M", "carga": "73", "vmed": "N", "error": "4", "periodoIntegracion": "5", "longitude": "${coord1x}", "latitude": "${coord1y}"}""",
        s"""{"idelem":"2","fecha":"2013-07-12 07:15:00", "identif": "912", "tipoElem": "7", "intensidad": "18", "ocupacion": "M", "carga": "58", "vmed": "N", "error": "5", "periodoIntegracion": "3", "longitude": "${coord2x}", "latitude": "${coord2y}"}"""))
    }
  }
}