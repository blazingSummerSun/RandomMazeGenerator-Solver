package backend.academy.maze;

import lombok.Getter;

@Getter public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width, Cell[][] grid) {
        this.width = (width - width % 2) + 1;
        this.height = (height - height % 2) + 1;
        this.grid = grid;
    }

}
