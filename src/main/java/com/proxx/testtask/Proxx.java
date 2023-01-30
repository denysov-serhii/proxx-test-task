package com.proxx.testtask;

import java.util.Random;
import java.util.function.Predicate;

public class Proxx {
  public static final int BOARD_SIZE = 10;

  public static void main(String[] args) {
    final GameBoard gameBoard = new GameBoard(BOARD_SIZE, 9);
    final Random random = new Random();
    final ConsoleDisplay consoleDisplay = new ConsoleDisplay();

    random
        .ints(0, gameBoard.getSize())
        .mapToObj(el -> new BoardCoords(el, random.nextInt(gameBoard.getSize())))
        .takeWhile((__) -> !gameBoard.isGameOver())
        .distinct()
        .filter(Predicate.not(gameBoard::isOpened))
        .peek(coords -> consoleDisplay.displayClicking(coords, gameBoard))
        .peek(gameBoard::open)
        .forEach((coords) -> consoleDisplay.displayUpdatedBorder(coords, gameBoard));
  }
}
