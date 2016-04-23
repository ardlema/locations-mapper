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

import java.io.PrintWriter
import java.io.File

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class LocationsFileWriterSpec extends FunSpec {

  describe("The locations file writer") {

    it("should read a list of locations info and coordinates and write them to a file") {
      val fileName = "./src/test/resources/temp.txt"
      val id1 = "id1"
      val id2 = "id2"
      val xCoord1 = "1234,45"
      val xCoord2 = "6874,89"
      val yCoord1 = "6789,45"
      val yCoord2 = "4542,01"
      val element1 = "78"
      val element2 = "30"
      val pointsAndCoordinatesList = List(
        (id1, element1, xCoord1, yCoord1),
        (id2, element2, xCoord2, yCoord2))

      LocationsFileWriter.writeToFile(fileName, pointsAndCoordinatesList)
      val fileWritten = new File(fileName)
      val src = io.Source.fromFile(fileWritten)
      val lines = src.getLines.toList
      lines.size should be(2)
      val firstLine = lines(0)
      val secondLine = lines(1)
      firstLine should be(s"${id1},${element1},${xCoord1},${yCoord1}")
      secondLine should be(s"${id2},${element2},${xCoord2},${yCoord2}")
      fileWritten.delete()
    }
  }
}

object LocationsFileWriter {

  def writeToFile(fileName: String, infoToBeWritten: List[(String, String, String, String)]): Unit = {
    val writer = new PrintWriter(new File(fileName))

    for (info <- infoToBeWritten) writer.write(s"${info._1},${info._2},${info._3},${info._4}\n")
    writer.close()
  }
}