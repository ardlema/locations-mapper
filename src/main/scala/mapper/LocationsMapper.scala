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

  def mongoDate(): String = fecha.replace(" ","T") + "Z"
}

case class Coordinates(xCoord: String, yCoord: String) {

  override def toString(): String = {
    s"$xCoord;$yCoord"
  }
}

case class TrafficInfoPlusCoordinates(trafficInfo: TrafficInfo, coordinates: Coordinates) {
  override def toString(): String = {
    s"""{"identif":"${trafficInfo.identif}","fecha":{$$date: "${trafficInfo.mongoDate}"}, "intensidad": ${trafficInfo.intensidad}, "ocupacion": ${trafficInfo.ocupacion}, "carga": ${trafficInfo.carga}, "tipo": "${trafficInfo.tipo}", "vmed": ${trafficInfo.vmed}, "error": "${trafficInfo.error}", "longitude": ${coordinates.xCoord}, "latitude": ${coordinates.yCoord}}"""
  }
}

object LocationsMapper extends LazyLogging {

  def findCoordinates(
                       pointsInfo: Iterator[TrafficInfo],
                       coordinatesMap: Map[String, Coordinates]): Iterator[TrafficInfoPlusCoordinates] = {
    pointsInfo.map(pointInfo => {
      if (!coordinatesMap.get(pointInfo.identif).isDefined) logger.error(s"There is no coordinates for ===> ${pointInfo.identif}")
      //TODO: Instead of using empty Coordinates it should be an Option
      TrafficInfoPlusCoordinates(pointInfo, coordinatesMap.get(pointInfo.identif).getOrElse(Coordinates("","")))
    }).filter(pointInfo => !pointInfo.coordinates.xCoord.isEmpty)
  }
}
