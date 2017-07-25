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
import common.{DegreeCoordinates, UtmCoordinates}
import converter.UTMToLatitudeLongitudeConverter

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

/*case class Coordinates(xCoord: String, yCoord: String) {

  override def toString(): String = {
    s"$xCoord;$yCoord"
  }
}*/

case class TrafficInfoPlusCoordinates(trafficInfo: TrafficInfo, coordinates: DegreeCoordinates) {



  override def toString(): String = {
    s"""${trafficInfo.idelem};"${trafficInfo.fecha}";"${trafficInfo.identif}";"${trafficInfo.tipoElem}";${trafficInfo.intensidad};${trafficInfo.ocupacion};${trafficInfo.carga};${trafficInfo.vmed};"${trafficInfo.error}";${trafficInfo.periodoIntegracion};${coordinates.longitude};${coordinates.latitude}"""
      .stripMargin
  }
}

object TrafficInfoPlusCoordinates {

  //TODO: Find out an "elegant" way to do this (type-classes, inference??)
  def printHeader(): String = {
    s""""idelem";"fecha";"identif";"tipo_elem";"intensidad";"ocupacion";"carga";"vmed";"error";"periodo_integracion";"longitude";"latitude""""
  }
}

object LocationsMapper extends LazyLogging {

  def getLongitudeAndLatitudeCoordinates(trafficInfos: Iterator[TrafficInfo],
                                         coordinatesMap: Map[String, UtmCoordinates]): Iterator[TrafficInfoPlusCoordinates] = {

    for  { trafficInfo <- trafficInfos
                       coordinate = coordinatesMap.get(trafficInfo.identif)
                       if (coordinate.isDefined) } yield TrafficInfoPlusCoordinates(trafficInfo,
                                              UTMToLatitudeLongitudeConverter.utm2LatitudeLongitude(coordinate.get))
  }
}
