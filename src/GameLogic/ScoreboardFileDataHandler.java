package GameLogic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.LinkedHashMap;

public class ScoreboardFileDataHandler {
  private Path path = Path.of("assets/score_data.csv");

  public void fileExistenceChecker() {
    if (!Files.exists(path)) {
      try {
        Files.createFile(path);
        // Write the header line when creating the file
        String header = "Username:Score" + System.lineSeparator();
        Files.write(path, header.getBytes(), java.nio.file.StandardOpenOption.APPEND);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String readFile() {
    try {
      if (Files.exists(path)) {
        String content = Files.readString(path);
        System.out.println(content);
        return content;
      } else {
        System.out.println("File does not exist: " + path.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  public void writeFile(String username, int score) {
    try {
      String line = username + "," + score + System.lineSeparator();
      Files.write(path, line.getBytes(), java.nio.file.StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public LinkedHashMap<String, String> turnFileOutputToHashmap(String fileContent) {
    // Read all lines from the file and print them out
    LinkedHashMap<String, String> scoreboard = new LinkedHashMap<String, String>();

    // Split the input string into lines
    String[] lines = fileContent.split("\\R");
    for (String line : lines) {
      // Skip empty lines
      if (line.trim().isEmpty()) {
        continue;
      }
      // Skip header if present
      if (line.trim().toLowerCase().startsWith("username")) {
        continue;
      }
      String regex = "[:,]";
      String[] myArray = line.split(regex);
      // Check if line is valid and has both username and score
      if (myArray.length >= 2) {
        System.out.println(myArray[0]);
        System.out.println(myArray[1]);
        scoreboard.put(myArray[0], myArray[1]);
      }
    }

    System.out.println(scoreboard);
    return scoreboard;
  }

  void main(String[] args) {
    // fileExistenceChecker();
    // writeFile("matan", 321321);
    // writeFile("bob", 231);
    // turnFileOutputToHashmap(readFile());
  }
}