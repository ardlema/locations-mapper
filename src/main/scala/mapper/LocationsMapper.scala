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

case class TrafficInfo(
  identif: String,
  fecha: String,
  intensidad: String,
  ocupacion: String,
  carga: String,
  tipo: String,
  vmed: String,
  error: String,
  periodoIntegracion: String) {

  override def toString(): String = {
    s"$identif;$fecha;$intensidad;$ocupacion;$carga;$tipo;$vmed;$error;$periodoIntegracion"
  }
}

case class Coordinates(xCoord: String, yCoord: String) {

  override def toString(): String = {
    s"$xCoord;$yCoord"
  }
}

case class TraffinInfoPlusCoordinates(trafficInfo: TrafficInfo, coordinates: Coordinates) {
  override def toString(): String = {
    s"${trafficInfo.toString};${coordinates.toString}"
  }
}

object LocationsMapper extends LazyLogging {

  def findCoordinates(
                       pointsInfo: List[TrafficInfo],
                       coordinatesMap: Map[String, Coordinates]): List[TraffinInfoPlusCoordinates] = {
    for (trafficInfo <- pointsInfo;
         coordinates = coordinatesMap.get(trafficInfo.identif);
         if coordinates.isDefined
    ) yield (TraffinInfoPlusCoordinates(trafficInfo, coordinates.get))
  }
}
