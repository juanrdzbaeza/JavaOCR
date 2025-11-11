# JavaOCR

Una aplicación Java sencilla que usa Tesseract (a través de Tess4J) para realizar OCR sobre imágenes.

Resumen
-------
JavaOCR carga una imagen seleccionada por el usuario y extrae su texto usando Tesseract. Este repositorio incluye un directorio `tessdata/` con el archivo `spa.traineddata` (español) para facilitar pruebas locales.

Características principales
- Interfaz Swing minimalista para seleccionar imágenes y ver el texto reconocido.
- Usa Tess4J (wrapper Java para Tesseract).
- Viene preparado para usar los datos de lenguaje local (`tessdata/`).

Requisitos
----------
- Java JDK 8 o posterior (se recomienda JDK 17+).
- Tesseract OCR (instalación nativa) si deseas usar OCR completo. Para pruebas locales el proyecto incluye `tessdata/spa.traineddata`.
- Maven para compilar (opcional si usas IntelliJ). Si no tienes Maven en PATH, puedes usar el Maven Wrapper (`mvnw`) — ver sección "Maven Wrapper".

Quick start (rápido)
--------------------
Desde CMD (ventana de comandos) en Windows:

```cmd
cd /d C:\Users\juan\Documents\IdeaProjects\JavaOCR
:: Si tienes Maven instalado globalmente
mvn -DskipTests package

:: Si no tienes Maven, usa IntelliJ (Maven -> Lifecycle -> package) o genera el wrapper (ver sección "Maven Wrapper")

:: Configura tessdata (temporal para la sesión):
set TESSDATA_PREFIX=C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata
:: O define de forma persistente:
setx TESSDATA_PREFIX "C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata"

:: Ejecuta el JAR empacado (o usa el script run_javaocr.bat):
java -jar target\JavaOCR-0.0.1-SNAPSHOT.jar
```

Maven Wrapper (opcional pero recomendado)
-----------------------------------------
El Maven Wrapper (`mvnw`, `mvnw.cmd` y `.mvn/wrapper/`) permite a cualquier usuario construir el proyecto sin instalar Maven globalmente.

Para generarlo (necesitas Maven al menos una vez) ejecuta en la raíz del proyecto:

```cmd
mvn -N io.takari:maven:wrapper
```

Esto crea los archivos `mvnw`, `mvnw.cmd` y la carpeta `.mvn/wrapper/`. Después podrás compilar con:

```cmd
mvnw.cmd -DskipTests package
```

Nota: no incluí los binarios del wrapper en el repositorio para evitar añadir artefactos binarios aquí; si quieres que los añada, puedo generarlos e incluirlos (necesito ejecutar Maven localmente o que los descargues).

Instalación rápida
------------------
1. Clona o descarga el repositorio.
2. Compila (ver Quick start).
3. El JAR generado estará en `target/JavaOCR-*.jar`.

Configuración (tessdata)
------------------------
Tesseract necesita encontrar los archivos `.traineddata`. El código intenta usar la variable de entorno `TESSDATA_PREFIX` si está definida; si no lo está, usa `./tessdata` relativo al directorio de trabajo.

Cómo establecer `TESSDATA_PREFIX` en Windows (CMD):

```cmd
:: Para la sesión actual (temporal)
set TESSDATA_PREFIX=C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata

:: Para la sesión y de forma persistente (setx)
setx TESSDATA_PREFIX "C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata"
```

En IntelliJ (Run Configuration):
- Run -> Edit Configurations -> selecciona tu aplicación -> Environment variables -> añade `TESSDATA_PREFIX`.

Verificar instalación de Tesseract y JVM
---------------------------------------
Comandos útiles para diagnosticar en Windows (CMD):

```cmd
:: Versión de Java y arquitectura (importante para compatibilidad con DLLs nativas)
java -version
echo %PROCESSOR_ARCHITECTURE%

:: Localiza tesseract si está instalado
where tesseract

:: Comprueba que spa.traineddata existe en el tessdata usado
dir C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata\spa.traineddata
```

Ejecución
---------
1) Desde IntelliJ: Ejecuta la clase `org.juanrdzbaeza.JavaOCR` (Run Configuration).
2) Desde la línea de comandos (si ya generaste el JAR):

```cmd
java -jar target\JavaOCR-0.0.1-SNAPSHOT.jar
```

También hay dos scripts en la raíz:
- `setup_tessdata_win.bat` — configura `TESSDATA_PREFIX` (temporal y persistente) y comprueba `tesseract --version`.
- `run_javaocr.bat` — establece `TESSDATA_PREFIX` para la sesión si no está definido y ejecuta el JAR empaquetado si existe en `target/`.

Uso (interfaz)
--------------
- Pulsa "Examinar" para seleccionar una imagen.
- La aplicación mostrará "Procesando..." y luego el texto reconocido.

Ejemplo de salida OCR
---------------------
A modo de ejemplo, si ejecutas OCR sobre `src/main/resources/images-tests/2024-10-17_02-02.png`, podrías obtener una salida similar a:

```
Factura Nº: 2024-1001
Fecha: 2024-10-17
Importe: 123,45 EUR
Cliente: Empresa Ejemplo S.A.
```

Los resultados varían según la calidad de la imagen y el idioma.

Resolución de problemas ampliada
--------------------------------
1) Error: `Error opening data file ./spa.traineddata` o mensajes sobre `TESSDATA_PREFIX`:
   - Asegúrate de que `tessdata/spa.traineddata` existe en la ruta que use `TESSDATA_PREFIX`.
   - Ejecuta:

```cmd
echo %TESSDATA_PREFIX%
dir "%TESSDATA_PREFIX%\spa.traineddata"
```

   - Si no existe, apunta `TESSDATA_PREFIX` al directorio correcto o copia `spa.traineddata` en el directorio `tessdata` del proyecto.

2) Error nativo `Invalid memory access` (JNA / DLLs) o excepciones nativas:
   - Causa típica: las DLLs nativas de Tesseract/Leptonica no están en `PATH`, o la arquitectura (x86 vs x64) no coincide con la JVM.
   - Pasos:
     1. Verifica `where tesseract` para confirmar la instalación nativa.
     2. Verifica `java -version` y `%PROCESSOR_ARCHITECTURE%` para comparar arquitecturas.
     3. Si no tienes Tesseract instalado, instala una build para Windows (ej.: UB-Mannheim) y añade su `bin` al `PATH`.
     4. Reinicia el terminal/IDE después de modificar `PATH`.

3) Advertencia SLF4J (`No SLF4J providers were found`):
   - Ya añadimos `slf4j-simple` en `pom.xml` para evitar la advertencia en ejecución.

4) Si un archivo ya estaba trackeado por Git y ahora lo quieres ignorar (ej.: `.idea/`), debes eliminarlo del índice primero:

```cmd
git rm -r --cached .idea
git commit -m "Remove .idea from repo and ignore globally"
```

Nota sobre librerías nativas y distribución portable
--------------------------------------------------
- El JAR resultante empaqueta dependencias Java, pero las DLLs nativas de Tesseract NO quedan dentro del JAR. Para una distribución portable en Windows puedes crear un ZIP que incluya:
  - `JavaOCR-<versión>.jar`
  - carpeta `tessdata/` completa
  - la carpeta `bin/` con los DLLs nativos de Tesseract (desde una instalación Windows)
  - un script `run_javaocr.bat` que establezca `TESSDATA_PREFIX` y añada temporalmente la carpeta `bin` al `PATH` antes de ejecutar el JAR.

Ejemplo sencillo de `run_portable.bat` (esquema):

```bat
@echo off
set SCRIPT_DIR=%~dp0
set PATH=%SCRIPT_DIR%bin;%PATH%
set TESSDATA_PREFIX=%SCRIPT_DIR%tessdata
java -jar %SCRIPT_DIR%JavaOCR-0.0.1-SNAPSHOT.jar
```

Build reproducible / CI (opcional)
----------------------------------
Si quieres compilar automáticamente en GitHub Actions, puedo añadir un workflow mínimo que ejecute `mvn -DskipTests package` y almacene el artifact. Dime si lo quieres y lo añado.

Contribuir
----------
Contribuciones bienvenidas. Abre issues o pull requests en GitHub: https://github.com/juanrdzbaeza/JavaOCR

Licencia
--------
MIT. Ver el archivo `LICENSE`.

Contacto
--------
Si necesitas ayuda adicional, comenta en un issue del repo o deja un mensaje en la página del proyecto en GitHub.
