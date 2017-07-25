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

import common.{DegreeCoordinates, UtmCoordinates}
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



