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

import com.typesafe.scalalogging.slf4j.LazyLogging

import scala.io.BufferedSource


object LocationsFileReader extends LazyLogging {

  def findPointsForFiles(directory: String): Array[(String, Iterator[TrafficInfo])] = {
    logger.debug("Let's find points for files")
    val filesWithinDirectory = new File(directory).listFiles
    logger.debug("Number of files to be scan: "+filesWithinDirectory.size)
    for (fileWithinDirectory <- filesWithinDirectory;
         file: BufferedSource = io.Source.fromFile(fileWithinDirectory)
    ) yield (fileWithinDirectory.getName, lines(file.getLines))
  }

  private def lines(strings: Iterator[String]) = {
    for (line <- strings;
         elements = line.replace("\"","").split(';')
    ) yield (
      TrafficInfo(
        elements(0),
        elements(1),
        elements(2),
        elements(3),
        elements(4),
        elements(5),
        elements(6),
        elements(7),
        elements(8)))
  }

  def findCoordinates(coordinatesFile: String): Map[String, Coordinates] = {
    logger.debug("Let's find the coordinates....")
    val coordinateFile = io.Source.fromFile(coordinatesFile)
    val lines = coordinateFile.getLines
    val coordinates = for (line <- lines;
                           elements = line.split(';')) yield (elements(0), Coordinates(elements(1), elements(2)))
    val coordinatesMap = coordinates.toMap
    logger.debug("Number of coordinates found: "+coordinatesMap.size)
    coordinatesMap
  }
}
