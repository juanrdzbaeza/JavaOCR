@echo off
REM run_javaocr.bat - ejecuta el JAR empaquetado de JavaOCR usando el tessdata del proyecto si no hay TESSDATA_PREFIX
SETLOCAL

SET SCRIPT_DIR=%~dp0
SET TESSDATA_DIR=%SCRIPT_DIR%tessdata

if "%TESSDATA_PREFIX%"=="" (
  echo TESSDATA_PREFIX no definido. Usando tessdata del proyecto: %TESSDATA_DIR%
  set TESSDATA_PREFIX=%TESSDATA_DIR%
) else (
  echo TESSDATA_PREFIX ya definido: %TESSDATA_PREFIX%
)

echo Buscando JAR en %SCRIPT_DIR%target\JavaOCR-*.jar
set JARNAME=
for %%f in ("%SCRIPT_DIR%target\JavaOCR-*.jar") do set JARNAME=%%~nxf
if "%JARNAME%"=="" (
  echo No se encontro ningun JAR compilado en target\ (ejecuta "mvn -DskipTests package" o usa IntelliJ para construir el artifact)
  goto :EOF
)

echo Ejecutando %JARNAME% ...
java -jar "%SCRIPT_DIR%target\%JARNAME%"
ENDLOCAL

