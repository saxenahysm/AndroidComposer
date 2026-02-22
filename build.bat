@echo off
REM Clear JAVA_HOME to let Gradle find Java
set JAVA_HOME=
cd /d D:\AndroidStudioProjects\KotlinPractice2024
call gradlew.bat assembleDebug
pause

