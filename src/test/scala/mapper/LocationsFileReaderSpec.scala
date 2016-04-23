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

import scala.io.{BufferedSource, Source}
import org.scalatest.FunSpec
import org.scalatest.Matchers._

class LocationsFileReaderSpec extends FunSpec {

  describe("The locations file reader") {

    it("should read a list of files within a directory and get the list of elements") {
      val directoryName = "./src/test/resources/dir1"
      val fileName1 = "points1.txt"
      val fileName2 = "points2.txt"
      val point1Line1Info1 = "point1Line1Info1"
      val point1Line1Info2 = "point1Line1Info2"
      val point1Line2Info1 = "point1Line2Info1"
      val point1Line2Info2 = "point1Line2Info2"
      val point2Line1Info1 = "point2Line1Info1"
      val point2Line1Info2 = "point2Line1Info2"
      val point2Line2Info1 = "point2Line2Info1"
      val point2Line2Info2 = "point2Line2Info2"
      val expectedPointList = List(
        (fileName1, List((point1Line1Info1, point1Line1Info2), (point1Line2Info1, point1Line2Info2))),
        (fileName2, List((point2Line1Info1, point2Line1Info2), (point2Line2Info1, point2Line2Info2))))
      val pointsList = LocationsFileReader.findPointsForFiles(directoryName)

      pointsList should contain theSameElementsAs(expectedPointList)
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
    val expectedMapCoordinates = Map((id1, (xCoord1, yCoord1)), (id2, (xCoord2, yCoord2)))
    val mapCoordinates = LocationsFileReader.findCoordinates(coordinatesFile)

    mapCoordinates should contain theSameElementsAs(expectedMapCoordinates)
  }
}

object LocationsFileReader {

  def findPointsForFiles(directory: String): Array[(String, List[(String, String)])] = {
    val filesWithinDirectory = new File(directory).listFiles
    for (fileWithinDirectory <- filesWithinDirectory;
      file: BufferedSource = io.Source.fromFile(fileWithinDirectory)
    ) yield (fileWithinDirectory.getName, lines(file.getLines).toList)
  }

  private def lines(strings: Iterator[String]) = {
    for (line <- strings;
      elements = line.split(',')
    ) yield (elements(0), elements(1))
  }

  def findCoordinates(coordinatesFile: String): Map[String, (String, String)] = {
    val coordinateFile = io.Source.fromFile(coordinatesFile)
    val lines = coordinateFile.getLines
    val coordinates = for (line <- lines;
        elements = line.split('-')) yield (elements(0), (elements(1), elements(2)))
    coordinates.toMap
  }
}