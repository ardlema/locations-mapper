Locations mapper
===================

Madrid City Council trafic data mapper. This will transform the Madrid City Council locations ids into geographical coordinates.

Data
-------------
Madrid City Council has published data about traffic density over the last years. The files containing the measurements do not have the coordinates of the measurement points, they have the ID of the measurement point instead. This project aims to map this measurement point with the file which contains the coordinates in order to visualize this data using [Stratio Viewer](https://stratio.atlassian.net/wiki/display/PLATFORM/STRATIO+VIEWER)

Data files and measurement points location can be found in the Madrid City Council web page:

[Madrid City Council data](http://datos.madrid.es/portal/site/egob/menuitem.c05c1f754a33a9fbe4b2e4b284f1a5a0/?vgnextoid=33cb30c367e78410VgnVCM1000000b205a0aRCRD&vgnextchannel=374512b9ace9f310VgnVCM100000171f5a0aRCRD)

Running the project
------------------------

To run the project and transform the data to be visualize by [Stratio Viewer](https://stratio.atlassian.net/wiki/display/PLATFORM/STRATIO+VIEWER) just clone the project and execute the following command:

```
sbt "run {measurement-points-coordinates-location} {data-density-files-directory} {data-density-directory-output}"
```

> **Where:**

> - measurement-points-coordinates-location is the absolute path of the file containing the coordinates (UTM) of the measurement points (see below an example)
> - data-density-files-directory is the directory containing the files to be mapped (see below an example of such files)
> - data-density-files-directory-output is the output directory where you want the transformed files will be generated (see below and example of such files)

#### <i class="icon-file"></i> Measurement points location  file example

"cod_cent";"nombre";"tipo_elem";"st_x";"st_y"
"03FL08PM01";"03FL08PM01";"PUNTOS MEDIDA M-30";439474.372756695;4474094.8493885
"03FL08PM02";"03FL08PM02";"PUNTOS MEDIDA M-30";438850.92988411;4474529.21911231

#### <i class="icon-file"></i> Data density file example

"idelem";"fecha";"identif";"tipo_elem";"intensidad";"ocupacion";"carga";"vmed";"error";"periodo_integracion"
1001;"2017-06-01 00:00:00";"05FT10PM01";"PUNTOS MEDIDA M-30";576;4;0;73;"N";5
1002;"2017-06-01 00:00:00";"05FT37PM01";"PUNTOS MEDIDA M-30";888;4;0;70;"N";5

#### <i class="icon-file"></i> Data density output file example

{"identif":"PM20152","fecha":{$date: "2013-07-12T07:15:00Z"}, "intensidad": 1065, "ocupacion": 9, "carga": 48, "tipo": "M", "vmed": 73, "error": "N", "longitude": -3.67139582, "latitude": 40.47111280}
{"identif":"PM22901","fecha":{$date: "2013-07-12T07:15:00Z"}, "intensidad": 912, "ocupacion": 7, "carga": 18, "tipo": "M", "vmed": 58, "error": "N", "longitude": -3.71034342, "latitude": 40.48089332}
{"identif":"PM22971","fecha":{$date: "2013-07-12T07:15:00Z"}, "intensidad": 1008, "ocupacion": 6, "carga": 19, "tipo": "M", "vmed": 64, "error": "N", "longitude": -3.70247055, "latitude": 40.48286285}
