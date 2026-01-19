
## Summary
A simple Flappy Bird clone implemented in Java for the sake of practice. This project features basic game mechanics, score tracking, and difficulty settings. Run and enjoy the game, or explore the code to learn more about Java game development.


## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.


## Building and Running

### Compile the project
```bash
javac -d bin -sourcepath src src/**/*.java src/*.java
```

### Compile the project & Run the application:
```bash
javac -d bin -sourcepath src src/**/*.java src/*.java && java -cp bin FlappyBird
```

### Create JAR with manifest
> This script build an executable file to run the game, build the app > run the commands below > click the generated JAR file to run the game .
```bash
echo "Main-Class: FlappyBird" > manifest.txt
jar cvfm FlappyBird.jar manifest.txt -C bin . -C assets .
```

## Screenshots

<img src="assets/screenshot1.png" alt="Screenshot 1" width="300"> <img src="assets/screenshot2.png" alt="Screenshot 2" width="300">

## Architecture

Here's the project structure:

![Flappy Bird Java Diagram](assets/FlappyBirdJavaDiagram.jpg)