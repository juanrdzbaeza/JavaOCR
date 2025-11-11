@REM @echo off
REM setup_tessdata_win.bat - configura TESSDATA_PREFIX para este proyecto y verifica tesseract
SETLOCAL

REM Ruta relativa a la carpeta del script (raíz del proyecto)
SET PROJECT_DIR=%~dp0
nSET TESSDATA_DIR=%PROJECT_DIR%tessdata

echo Proyecto: %PROJECT_DIR%
echo Comprobando carpeta tessdata: %TESSDATA_DIR%
if not exist "%TESSDATA_DIR%" (
  echo ERROR: No se encontró el directorio tessdata en %TESSDATA_DIR%
  echo Asegurate de ejecutar este script desde la raiz del proyecto donde existe la carpeta tessdata\
  goto :EOF
)

echo Estableciendo variable TESSDATA_PREFIX para la sesión actual...
set TESSDATA_PREFIX=%TESSDATA_DIR%
echo TESSDATA_PREFIX=%TESSDATA_PREFIX%

echo Estableciendo variable TESSDATA_PREFIX de forma persistente para el usuario (setx)...
setx TESSDATA_PREFIX "%TESSDATA_DIR%" >nul
if %ERRORLEVEL% equ 0 (
  echo Variable persistente creada/actualizada.
) else (
  echo Advertencia: no se pudo escribir la variable persistente. Ejecuta el script como administrador si es necesario.
)

echo Comprobando si tesseract está en el PATH...
where tesseract >nul 2>&1
if %ERRORLEVEL% equ 0 (
  echo tesseract encontrado en el PATH.
  echo Versión:
  tesseract --version
) else (
  echo tesseract not found in PATH.
  echo Si no tienes tesseract instalado, descarga e instala desde:
  echo https://github.com/UB-Mannheim/tesseract/wiki
  echo Luego añade la carpeta bin (por ejemplo C:\Program Files\Tesseract-OCR\bin) a tu PATH y reinicia el terminal/IDE.
)

echo Hecho.
ENDLOCAL

