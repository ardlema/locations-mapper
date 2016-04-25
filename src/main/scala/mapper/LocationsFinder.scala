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

import com.typesafe.scalalogging.slf4j.LazyLogging

object LocationsFinder extends LazyLogging {

  def main(args: Array[String]): Unit = {
    logger.debug("Let's run this shit!!!")
    val idsAndCoordinatesMap = LocationsFileReader.findCoordinates(args(0))
    val pointsForFiles = LocationsFileReader.findPointsForFiles(args(1))
    logger.debug("Let's find the coordinates for each file...")
    for (pointForFile <- pointsForFiles) {
      logger.debug(s"Let's find the coordinates for the file: ${pointForFile._1}")
      val locationsInfo = LocationsMapper.findCoordinates(pointForFile._2, idsAndCoordinatesMap)
      LocationsFileWriter.writeToFile(args(2) + s"/${pointForFile._1}-output.csv", locationsInfo)
    }
  }
}
