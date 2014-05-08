CLIENTE NODEJSGWT 
=
* Solamente probado con el Navegador CHROME.
* Testeado solo para conexiones "websocket".
* En caso la conexion "websocket" falle se usa "xhr-polling", esto ultimo no testeado.
* Es servidor Node.js se encuentra alojado en http://prueba-jquerymobile.rhcloud.com.
* El codigo fuente del servidor http://github.com/jumanor/server-nodejsgwt

**UN MICRO TUTORIAL**

http://www.youtube.com/watch?v=s2cfzGVIwHU

**ERRORES**
* Aparentemente FF falla la conexion "websocket" y toma la conexion "xhr-polling" en OPENSHIFT RED HAT
* En una conexion "xhr-polling" se lanza el evento "disconnect" con un retardo de 20 segundos.  

**PROBANDO**

http://nodejs2014.appspot.com/

**ACTUALIZACION**
* Se creo un Chat (8/5/2014)

**INSTALACION**

* Instale el plugin de MAVEN para Eclipse
* Para configura el proyecto MAVEN en Eclipse ver el siguiente [video](http://www.dailymotion.com/video/x1ru116_instalacion-matamarciano-gwt-html5_tech)
