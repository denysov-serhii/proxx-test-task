package com.proxx.testtask;

import java.util.concurrent.TimeUnit;

public class ConsoleDisplay {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";

  public void displayClicking(BoardCoords coords, GameBoard gameBoard) {
    for (int i = 0; i < 10; i++) {
      final String emptyOrSymbol = i % 2 == 0 ? "×" : " ";
      clearConsole();
      final String[][] state = gameBoard.getState();
      state[coords.top()][coords.left()] = ANSI_RED + emptyOrSymbol + ANSI_RESET;

      System.out.printf("Clicking on: x = %s y = %s\n\r", coords.top(), coords.left());
      System.out.println(buildGameBoard(state, gameBoard.getSize()));
      sleep(200);
    }
  }

  public void displayUpdatedBorder(BoardCoords coords, GameBoard gameBoard) {
    clearConsole();
    if (gameBoard.isGameOver()) {
      System.out.println("Game Over!");
    } else {
      System.out.printf("Click on: x = %s y = %s\n\r", coords.top(), coords.left());
    }
    System.out.println(buildGameBoard(gameBoard.getState(), gameBoard.getSize()));
  }

  private static StringBuilder buildGameBoard(String[][] state, int size) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < size; i++) {
      if (i == 0) {
        addUpperBorder(size, output);
      }

      for (int j = 0; j < size; j++) {
        if (j == 0) {
          // add element of left order
          output.append("|");
        }

        // add main elemnt
        output.append(state[i][j]);

        if (j == size - 1) {
          // add element of right order
          output.append("|");
        } else {
          // add spaces between elements
          output.append("  ");
        }
      }
      output.append(System.lineSeparator());
    }
    addBottomBorder(size, output);
    return output;
  }

  private static void addBottomBorder(int size, StringBuilder output) {
    output.append("---".repeat(size));
  }

  private static void addUpperBorder(int size, StringBuilder output) {
    output.append("___".repeat(size)).append(System.lineSeparator());
  }

  public static void clearConsole() {
    try {
      String operatingSystem = System.getProperty("os.name");

      if (operatingSystem.contains("Windows")) {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
        Process startProcess = pb.start();
        startProcess.waitFor();
      } else {
        ProcessBuilder pb = new ProcessBuilder("clear");
        Process startProcess = pb.inheritIO().start();

        startProcess.waitFor();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private static void sleep(long milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
