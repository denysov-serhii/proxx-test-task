package com.proxx.testtask;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardCoords {
  private final int top;
  private final int left;

  public BoardCoords(int top, int left) {
    this.top = top;
    this.left = left;
  }

  public int top() {
    return top;
  }

  public int left() {
    return left;
  }

  public List<BoardCoords> neighborhood(int upperBound) {
    return Stream.of(
            new BoardCoords(top - 1, left - 1),
            new BoardCoords(top, left - 1),
            new BoardCoords(top + 1, left - 1),
            new BoardCoords(top - 1, left),
            new BoardCoords(top + 1, left),
            new BoardCoords(top - 1, left + 1),
            new BoardCoords(top, left + 1),
            new BoardCoords(top + 1, left + 1))
        .filter(coords -> coords.top >= 0 && coords.left >= 0)
        .filter(coords -> coords.top < upperBound && coords.left < upperBound)
        .collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BoardCoords coords = (BoardCoords) o;
    return top == coords.top && left == coords.left;
  }

  @Override
  public int hashCode() {
    return Objects.hash(top, left);
  }
}
