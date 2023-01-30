package com.proxx.testtask;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameBoard {
  private static final Random random = new Random();
  private static final String BH = "H";
  private static final String EMPTY = " ";

  private final Set<BoardCoords> holesCoordinates;
  private final boolean[][] visibility;
  private final String[][] cells;
  private boolean gameOver = false;
  private final int size;

  public GameBoard(final int size, final int blackHolesNumber) {
    this(size, generateBlackHoles(size, blackHolesNumber));
  }

  GameBoard(int size, final Set<BoardCoords> holesCoordinates) {
    this.size = size;
    this.cells = new String[size][size];
    this.visibility = new boolean[size][size];
    this.holesCoordinates = holesCoordinates;

    buildBoard();
  }

  public void open(BoardCoords coords) {
    if (visibility[coords.top()][coords.left()]) {
      return;
    }

    visibility[coords.top()][coords.left()] = true;

    if (cells[coords.top()][coords.left()].equals(BH)) {
      gameOver = true;
      holesCoordinates.forEach(
          coordinate -> visibility[coordinate.top()][coordinate.left()] = true);
    } else if (cells[coords.top()][coords.left()].equals(EMPTY)) {
      coords.neighborhood(cells.length).forEach(this::open);
    }
  }

  public int getSize() {
    return size;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public String[][] getState() {
    String[][] state = new String[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        state[i][j] = visibility[i][j] ? cells[i][j] : "*";
      }
    }

    return state;
  }

  public boolean isOpened(BoardCoords coords) {
    return visibility[coords.top()][coords.left()];
  }

  private void buildBoard() {
    final Map<BoardCoords, Long> adjacentBlackHoleCells =
        calcCountAdjacentCoordinates(size, holesCoordinates);
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        BoardCoords coords = new BoardCoords(i, j);
        if (isBlackHole(holesCoordinates, coords)) {
          cells[i][j] = BH;
        } else if (adjacentBlackHoleCells.containsKey(coords)) {
          cells[i][j] = adjacentBlackHoleCells.get(coords).toString();
        } else {
          cells[i][j] = EMPTY;
        }
      }
    }
  }

  private boolean isBlackHole(Set<BoardCoords> holesCoordinates, BoardCoords coords) {
    return holesCoordinates.contains(coords);
  }

  private static Map<BoardCoords, Long> calcCountAdjacentCoordinates(
      int size, Set<BoardCoords> holesCoordinates) {
    final Map<BoardCoords, Long> adjacentBlackHoleCells =
        holesCoordinates.stream()
            .flatMap(coords -> coords.neighborhood(size).stream())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return adjacentBlackHoleCells;
  }

  private static Set<BoardCoords> generateBlackHoles(int size, int blackHolesNumber) {
    final Set<BoardCoords> holesCoordinates = new HashSet<>();

    while (holesCoordinates.size() <= blackHolesNumber) {
      final int top = random.nextInt(size);
      final int left = random.nextInt(size);
      holesCoordinates.add(new BoardCoords(top, left));
    }
    return holesCoordinates;
  }

//  private
}
