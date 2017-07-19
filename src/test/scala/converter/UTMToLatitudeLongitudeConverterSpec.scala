// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License"),
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
package converter

import org.scalatest.{FunSpec, Matchers}

class UTMToLatitudeLongitudeConverterSpec extends FunSpec with Matchers {

  describe("The UTM converter") {

    it("should convert UTM coordinates to latitude/longitude") {
      val utmCoordinates = UtmCoordinates(439474.372756695, 4474094.8493885)
      val expectedCoordinates = DegreeCoordinates(40.41527701272302, -3.7134030732037786)
      val coordinates = UTMToLatitudeLongitudeConverter.utm2LatitudeLongitude(utmCoordinates)

      coordinates should be(expectedCoordinates)
    }
  }
}

object UTMToLatitudeLongitudeConverter {

  private val centralMeridianFactor = 0.9996

  private val earthRadius = 6366197.724

  def utm2LatitudeLongitude(utmCoordinates: UtmCoordinates): DegreeCoordinates = {
    val zone = 30
    val letter = 'T'
    val easting = utmCoordinates.easting
    val northing = utmCoordinates.northing
    val hem = if (letter > 'M') 'N' else 'S'
    val north = if (hem == 'S') northing - 10000000 else northing
    val latitude = getLatitude(north, easting)
    val longitude = Math.atan((Math.exp((easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) * (1 - 0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) / 2 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) / 3)) - Math.exp(-(easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) * (1 - 0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) / 2 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) / 3))) / 2 / Math.cos((north - centralMeridianFactor * 6399593.625 * (north / earthRadius / centralMeridianFactor - 0.006739496742 * 3 / 4 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) + Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) + Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 4 + Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 3)) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) * (1 - 0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) / 2 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) + north / earthRadius / centralMeridianFactor)) * 180 / Math.PI + zone * 6 - 183
    DegreeCoordinates(latitude, longitude)
  }

  private def getLatitude(north: Double, easting: Double) = {
    (north / earthRadius / centralMeridianFactor + (1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) -
      0.006739496742 * Math.sin(north / earthRadius / centralMeridianFactor) * Math.cos(north / earthRadius / centralMeridianFactor) *
        (Math.atan(Math.cos(Math.atan((Math.exp((easting - 500000) / (centralMeridianFactor * 6399593.625 /
          Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) *
          (1 - 0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 +
            0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) / 2 *
            Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) / 3)) - Math.exp(-(easting - 500000) /
          (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 *
            Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) * (1 -
          0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 /
            Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) /
            2 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) / 3))) / 2 /
          Math.cos((north - centralMeridianFactor * 6399593.625 * (north / earthRadius / centralMeridianFactor - 0.006739496742 * 3 /
            4 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) +
            Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / earthRadius / centralMeridianFactor +
              Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) +
              Math.sin(2 * north / earthRadius / centralMeridianFactor) *
                Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 4 -
            Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north / earthRadius / centralMeridianFactor +
              Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) +
              Math.sin(2 * north / earthRadius / centralMeridianFactor) *
                Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 4 + Math.sin(2 *
              north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor),
              2) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 3)) / (centralMeridianFactor *
            6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north /
            earthRadius / centralMeridianFactor), 2)))) * (1 - 0.006739496742 * Math.pow((easting -
            500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 *
            Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) / 2 *
            Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) + north / earthRadius /
            centralMeridianFactor))) * Math.tan((north - centralMeridianFactor * 6399593.625 * (north / earthRadius /
          centralMeridianFactor - 0.006739496742 * 3 / 4 * (north / earthRadius / centralMeridianFactor +
          Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) +
          Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north /
            earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) /
            2) + Math.sin(2 * north / earthRadius / centralMeridianFactor) *
            Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) /
            4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 *
          (north / earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) +
          Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) /
          4 + Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) *
          Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 3)) / (centralMeridianFactor * 6399593.625 /
          Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) *
          (1 - 0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 /
            Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) /
            2 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) + north / earthRadius / centralMeridianFactor)) -
          north / earthRadius / centralMeridianFactor) * 3 / 2) * (Math.atan(Math.cos(Math.atan((Math.exp((easting - 500000) /
      (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) *
      (1 - 0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 *
        Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) / 2 * Math.pow(Math.cos(north / earthRadius /
        centralMeridianFactor), 2) / 3)) - Math.exp(-(easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 *
      Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) * (1 - 0.006739496742 * Math.pow((easting - 500000) /
      (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))),
      2) / 2 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) / 3))) / 2 / Math.cos((north - centralMeridianFactor * 6399593.625 *
      (north / earthRadius / centralMeridianFactor - 0.006739496742 * 3 / 4 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north /
        earthRadius / centralMeridianFactor) / 2) + Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / earthRadius / centralMeridianFactor +
        Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) + Math.sin(2 * north / earthRadius / centralMeridianFactor) *
        Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 *
        (5 * (3 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) +
          Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 4 +
          Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) *
            Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 3)) / (centralMeridianFactor * 6399593.625 /
      Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))) * (1 - 0.006739496742 *
      Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north /
        earthRadius / centralMeridianFactor), 2)))), 2) / 2 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) + north /
      earthRadius / centralMeridianFactor))) * Math.tan((north - centralMeridianFactor * 6399593.625 * (north / earthRadius / centralMeridianFactor -
      0.006739496742 * 3 / 4 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) +
      Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3 * (north / earthRadius / centralMeridianFactor + Math.sin(2 * north /
        earthRadius / centralMeridianFactor) / 2) + Math.sin(2 * north / earthRadius / centralMeridianFactor) * Math.pow(Math.cos(north /
        earthRadius / centralMeridianFactor), 2)) / 4 - Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3 * (north /
      earthRadius / centralMeridianFactor + Math.sin(2 * north / earthRadius / centralMeridianFactor) / 2) + Math.sin(2 * north / earthRadius /
      centralMeridianFactor) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) / 4 + Math.sin(2 * north / earthRadius / centralMeridianFactor) *
      Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2) * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) /
      3)) / (centralMeridianFactor * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius /
      centralMeridianFactor), 2)))) * (1 - 0.006739496742 * Math.pow((easting - 500000) / (centralMeridianFactor * 6399593.625 /
      Math.sqrt((1 + 0.006739496742 * Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)))), 2) / 2 *
      Math.pow(Math.cos(north / earthRadius / centralMeridianFactor), 2)) + north / earthRadius / centralMeridianFactor)) - north / earthRadius /
      centralMeridianFactor)) * 180 / Math.PI
  }
}

case class DegreeCoordinates(latitude: Double, longitude: Double)

case class UtmCoordinates(easting: Double, northing: Double)
