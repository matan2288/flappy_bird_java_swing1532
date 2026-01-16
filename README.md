## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Building and Running


### Compile the project & Run the application:
```bash
javac -d bin -sourcepath src src/**/*.java src/*.java && java -cp bin FlappyBird
```

Diagram:
https://app.diagrams.net/#G1iUJjz6zgnlljq6DMpPmRuQ2_jVpHGwbI#%7B%22pageId%22%3A%22tlDLi45S8lB7pMdPApnj%22%7D
