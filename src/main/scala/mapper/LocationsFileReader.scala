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
import common.UtmCoordinates

import scala.io.BufferedSource


object LocationsFileReader extends LazyLogging {

  def findPointsForFiles(directory: String): List[(String, Iterator[TrafficInfo])] = {
    logger.debug("Let's find points for files")
    val filesWithinDirectory = new File(directory).listFiles
    logger.debug("Number of files to be scan: "+filesWithinDirectory.size)
    (for (fileWithinDirectory <- filesWithinDirectory;
          file: BufferedSource = io.Source.fromFile(fileWithinDirectory)
    ) yield (fileWithinDirectory.getName, lines(getLinesWithoutHeader(file)))).toList
  }

  private def lines(strings: Iterator[String]) = {
    for (line <- strings;
         elements = replaceQuotesAndSplitByColon(line)
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
        elements(8),
        elements(9)))
  }

  def findCoordinates(coordinatesFile: String): Map[String, UtmCoordinates] = {
    logger.debug("Let's find the coordinates....")
    val coordinateFile = io.Source.fromFile(coordinatesFile)
    val coordinates = for (line <- getLinesWithoutHeader(coordinateFile);
                           elements = replaceQuotesAndSplitByColon(line)) yield (elements(0),
      UtmCoordinates(elements(3).toDouble, elements(4).toDouble))
    val coordinatesMap = coordinates.toMap
    logger.debug("Number of coordinates found: "+coordinatesMap.size)
    coordinatesMap
  }

  private def getLinesWithoutHeader(source: BufferedSource) = source.getLines().drop(1)

  private def replaceQuotesAndSplitByColon(lineWithQuotes: String) = lineWithQuotes.replace("\"","").split(';')
}
