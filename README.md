**MUY IMPORTANTE !!!**
* El servidor(alojamiento FREE) http://prueba-jquerymobile.rhcloud.com a veces esta fuera de servicio.
* Refresque reiterademente (aprox 5 veces) el Navegador en http://prueba-jquerymobile.rhcloud.com cuando el servicio ya este disponible ingrese a http://nodejs2014.appspot.com/

CLIENTE NODEJSGWT 
=
* Solamente probado con el Navegador CHROME, FIREFOX, IE 10.
* Testeado solo para conexiones "websocket".
* En caso la conexion "websocket" falle se usa "xhr-polling", esto ultimo no testeado.
* Es servidor Node.js se encuentra alojado en http://prueba-jquerymobile.rhcloud.com.
* El codigo fuente del servidor http://github.com/jumanor/server-nodejsgwt

**UN MICRO TUTORIAL**

http://www.youtube.com/watch?v=s2cfzGVIwHU

**ERRORES**

* Aparentemente cuando se ejecuta detras de un PROXY la conexion "websocket" falla y utiliza la "xhr-polling"
* Si la conexion es "xhr-polling" el evento "disconnect" tarda 30 seg aprox.

**PROBANDO**

http://nodejs2014.appspot.com/

**ACTUALIZACION**
* Se creo un Chat (8/5/2014)

**INSTALACION**

* Instale el plugin de MAVEN para Eclipse
* Para configura el proyecto MAVEN en Eclipse ver el siguiente [video](http://www.dailymotion.com/video/x1ru116_instalacion-matamarciano-gwt-html5_tech)

![a link](http://googledrive.com/host/0B72oLqC-8YVbfkJKMFJrTWRuMmhWT19wcE83UFU1T2tHSHhHQllfVzJ2Z2tnUVltV2M3Qm8/nodegwt.png?raw=true)
