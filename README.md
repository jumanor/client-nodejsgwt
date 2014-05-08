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
