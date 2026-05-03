@echo off
echo Limpiando carpeta bin...
if exist bin rmdir /s /q bin
mkdir bin

echo Compilando el proyecto...
javac --module-path "C:\javafx-sdk-17.0.19\lib" --add-modules javafx.controls,javafx.fxml -d bin -sourcepath src src\main\java\com\emergencias\main\Main.java src\main\java\com\emergencias\controller\*.java src\main\java\com\emergencias\modelo\*.java src\main\java\com\emergencias\alerta\*.java src\main\java\com\emergencias\detector\*.java

echo Ejecutando la aplicacion...
java --module-path "C:\javafx-sdk-17.0.19\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics -Dprism.order=sw -cp bin com.emergencias.main.Main

pause