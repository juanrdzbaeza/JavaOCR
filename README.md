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
- Tesseract OCR instalado en el sistema (recomendado: versión 4.1.1+ o la última estable).
- `tessdata` con los archivos `.traineddata` del/los idioma(s) que necesites. Este repositorio ya contiene `tessdata/spa.traineddata`.

Instalación rápida
------------------
1. Clona o descarga el repositorio.
2. Si usas Maven, compila:

```bash
mvn clean package
```

3. El JAR generado estará en `target/JavaOCR-*.jar`.

Configuración (tessdata)
------------------------
Tesseract necesita encontrar los archivos `.traineddata`. Hay dos opciones:

1) Usar el `tessdata` del proyecto (recomendado para pruebas internas)
   - El proyecto ya incluye `tessdata/spa.traineddata` en la raíz del repo.
   - El código de la aplicación intenta usar `TESSDATA_PREFIX` si está definido; si no, usa el directorio del proyecto `./tessdata`.

2) Instalar Tesseract en el sistema y apuntar `TESSDATA_PREFIX` a su carpeta `tessdata`.

Cómo establecer `TESSDATA_PREFIX` en Windows (CMD):

```cmd
:: Establece permanentemente la variable de entorno para el usuario actual
setx TESSDATA_PREFIX "C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata"

:: Para la sesión actual (temporal)
set TESSDATA_PREFIX=C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata
```

Si usas PowerShell (temporal):

```powershell
$env:TESSDATA_PREFIX = 'C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata'
```

Configurar variable de entorno en IntelliJ (Run Configuration):
- Run -> Edit Configurations -> selecciona tu aplicación -> Environment variables -> añade `TESSDATA_PREFIX` con el valor `C:\Users\juan\Documents\IdeaProjects\JavaOCR\tessdata`.

Verificar instalación de Tesseract
---------------------------------
Desde una terminal, ejecuta:

```cmd
tesseract --version
```

Si se muestra versión, Tesseract está en el PATH. Si no, instala Tesseract (por ejemplo: https://github.com/UB-Mannheim/tesseract/wiki) y añade su `bin` al `PATH`.

Ejecución
---------
1) Desde IntelliJ: Ejecuta la clase `org.juanrdzbaeza.JavaOCR` (Run Configuration).
2) Desde la línea de comandos (si ya generaste el JAR):

```cmd
java -jar target\JavaOCR-0.0.1-SNAPSHOT.jar
```

Uso
---
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

Scripts útiles
--------------
En la raíz del proyecto incluimos un script para Windows que configura `TESSDATA_PREFIX` apuntando al `tessdata/` del proyecto y comprueba si `tesseract` está en el `PATH`.

Uso del script `setup_tessdata_win.bat`:

```cmd
:: Ejecuta desde la carpeta raíz del proyecto
setup_tessdata_win.bat
```

El script hará dos cosas: ajustará la variable de entorno para la sesión actual y colocará `TESSDATA_PREFIX` de forma persistente para el usuario (usando `setx`). Si el script imprime `tesseract not found in PATH`, instala Tesseract y añade su `bin` al `PATH`.

Resolución de problemas comunes
--------------------------------
- Error: `Error opening data file ./spa.traineddata` o mensajes sobre `TESSDATA_PREFIX`:
  - Asegúrate de que `tessdata/spa.traineddata` existe y que `TESSDATA_PREFIX` apunta al directorio que contiene `spa.traineddata`.

- Error nativo `Invalid memory access` (JNA / DLLs):
  - Esto indica un problema con las librerías nativas de Tesseract/Leptonica (DLLs) o una incompatibilidad de arquitectura (x86 vs x64).
  - Solución:
    1. Instala Tesseract para Windows (versión apropiada x64 si tu JVM es x64).
    2. Añade la carpeta `bin` de la instalación de Tesseract al `PATH` de Windows.
    3. Reinicia el IDE para que recoja las nuevas variables de entorno.
    4. Verifica con `tesseract --version`.

- Advertencia SLF4J (`No SLF4J providers were found`):
  - Es solo una advertencia; para eliminarla añade un proveedor SLF4J (por ejemplo `slf4j-simple` o `slf4j-log4j12`) en `pom.xml`.

Consejos de depuración
----------------------
- Habilita logs en la aplicación o revisa la salida de la consola de IntelliJ para ver mensajes completos.
- Asegúrate de usar una JVM cuya arquitectura (32/64 bits) coincida con las DLLs nativas de Tesseract.

Contribuir
----------
Contribuciones bienvenidas. Abre issues o pull requests en GitHub: https://github.com/juanrdzbaeza/JavaOCR

Licencia
--------
MIT. Ver el archivo `LICENSE`.

Contacto
--------
Si necesitas ayuda adicional, comenta en un issue del repo o deja un mensaje en la página del proyecto en GitHub.
