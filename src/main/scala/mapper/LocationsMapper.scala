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

case class TrafficInfo(idelem: String,
  fecha: String,
  identif: String,
  tipoElem: String,
  intensidad: String,
  ocupacion: String,
  carga: String,
  vmed: String,
  error: String,
  periodoIntegracion: String) {

  override def toString(): String = {
    s"$idelem;$fecha;$identif;$tipoElem,$intensidad;$ocupacion;$carga;$vmed;$error;$periodoIntegracion"
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
    s"""{"idelem":"${trafficInfo.idelem}","fecha":"${trafficInfo.fecha}", "identif": "${trafficInfo.identif}", "tipoElem": "${trafficInfo.tipoElem}", "intensidad": "${trafficInfo.intensidad}", "ocupacion": "${trafficInfo.ocupacion}", "carga": "${trafficInfo.carga}", "vmed": "${trafficInfo.vmed}", "error": "${trafficInfo.error}", "periodoIntegracion": "${trafficInfo.periodoIntegracion}", "longitude": "${coordinates.xCoord}", "latitude": "${coordinates.yCoord}"}"""
  }

}

object LocationsMapper extends LazyLogging {

  def findCoordinates(trafficInfos: Iterator[TrafficInfo],
                      coordinatesMap: Map[String, Coordinates]): Iterator[TrafficInfoPlusCoordinates] = {

    for  { trafficInfo <- trafficInfos
                       coordinate = coordinatesMap.get(trafficInfo.identif)
                       if (coordinate.isDefined) } yield TrafficInfoPlusCoordinates(trafficInfo, coordinate.get)
  }
}
