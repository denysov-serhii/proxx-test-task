package com.proxx.testtask;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GameBoardTest {
  final int sizeOfBoard = 5;
  final Set<BoardCoords> holesCords =
      new HashSet<>(
          List.of(
              new BoardCoords(0, 1),
              new BoardCoords(2, 2),
              new BoardCoords(3, 1),
              new BoardCoords(3, 4)));
  final String[][] expectedWholeState =
      new String[][] {
        {"1", "H", "1", " ", " "},
        {"1", "2", "2", "1", " "},
        {"1", "2", "H", "2", "1"},
        {"1", "H", "2", "2", "H"},
        {"1", "1", "1", "1", "1"},
      };

  @Test
  public void testOpeningEmptyCell() {

    final GameBoard gameBoard = new GameBoard(sizeOfBoard, holesCords);

    gameBoard.open(new BoardCoords(0, 4));

    final String[][] expectedState =
        new String[][] {
          {"*", "*", "1", " ", " "},
          {"*", "*", "2", "1", " "},
          {"*", "*", "*", "2", "1"},
          {"*", "*", "*", "*", "*"},
          {"*", "*", "*", "*", "*"},
        };

    assertTrue(Arrays.deepEquals(expectedState, gameBoard.getState()));
  }

  @Test
  public void testGameOverFlag() {

    final GameBoard gameBoard = new GameBoard(sizeOfBoard, holesCords);

    // just open empty cell
    gameBoard.open(new BoardCoords(0, 4));
    // the game is not over yet
    assertFalse(gameBoard.isGameOver());
    // open black hole
    gameBoard.open(new BoardCoords(0, 1));
    assertTrue(gameBoard.isGameOver());
  }

  @Test
  public void testStateCorrectness() {
    final GameBoard gameBoard = new GameBoard(sizeOfBoard, holesCords);
    // open all not black holes cells
    for (int left = 0; left < sizeOfBoard; left++) {
      for (int top = 0; top < sizeOfBoard; top++) {
        final BoardCoords coords = new BoardCoords(top, left);
        if (holesCords.contains(coords)) {
          continue;
        }

        gameBoard.open(coords);
      }
    }

    // open any black hole to reveal all of them
    holesCords.stream().findAny().ifPresent(gameBoard::open);

    assertTrue(gameBoard.isGameOver());

    assertTrue(Arrays.deepEquals(expectedWholeState, gameBoard.getState()));
  }
}
