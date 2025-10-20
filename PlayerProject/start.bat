@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

:: Check if an argument is provided
IF "%1"=="same" (
    echo Starting players in the same process...
    mvn clean install
    java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.Main same
) ELSE IF "%1"=="separate" (
    echo Starting players in separate processes...
    mvn clean install
    java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.Main separate
) ELSE (
    echo Invalid mode. Use 'same' or 'separate'.
)

ENDLOCAL
