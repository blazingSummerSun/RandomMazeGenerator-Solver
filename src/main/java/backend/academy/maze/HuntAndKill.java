package backend.academy.maze;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class HuntAndKill implements Generator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int NEIGHBOURS = 4;
    private static final int STEP_BACK = -2;

    @Override
    public Maze generate(int height, int width) {
        // Fill the whole grid with walls
        Cell[][] maze = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }

        int zeroCoord = 0;
        int oneCoord = 1;
        // (0, 1) and (1, 1) are entry coordinates
        maze[zeroCoord][oneCoord] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze[oneCoord][oneCoord] = new Cell(1, 1, Cell.Type.PASSAGE);
        int[] currentCell = {1, 1};

        while (!complete(maze)) {
            // Collecting all unvisited neighbors of the current cell
            List<int[]> currentNeighbors = neighbors(maze, currentCell[zeroCoord], currentCell[oneCoord], true);
            // If there are no unvisited neighbors,
            // it hunts for the next starting point by finding a cell with unvisited neighbors
            // and marks it as the new starting point.
            if (currentNeighbors.isEmpty()) {
                int[][] temp = findCoord(maze);
                if (temp != null && temp[0] != null) {
                    currentCell = temp[0];

                    // Mark current cell as a passage
                    maze[currentCell[zeroCoord]][currentCell[oneCoord]] = new Cell(
                        currentCell[zeroCoord], currentCell[oneCoord], Cell.Type.PASSAGE);
                    // Calculate the midpoint (avgZero, avgOne) between currentCell and its new neighbor (temp[1]).
                    int avgZero = currentCell[zeroCoord] + (temp[oneCoord][zeroCoord] - currentCell[zeroCoord]) / 2;
                    int avgOne = currentCell[oneCoord] + (temp[oneCoord][oneCoord] - currentCell[oneCoord]) / 2;
                    // Marks the midpoint as a passage.
                    maze[avgZero][avgOne] = new Cell(avgZero, avgOne, Cell.Type.PASSAGE);
                }
            } else {
                // If there are unvisited neighbors,
                // it chooses one randomly and connects it to the current cell, marking both as passages.
                int i = RANDOM.nextInt(currentNeighbors.size());
                // Pick random neighbor and mark it as a passage.
                int[] randomNeighbor = currentNeighbors.get(i);
                maze[randomNeighbor[zeroCoord]][randomNeighbor[oneCoord]] = new Cell(
                    randomNeighbor[zeroCoord], randomNeighbor[oneCoord], Cell.Type.PASSAGE);

                // Calculate the midpoint (avgZero, avgOne) between currentCell and its new neighbor (temp[1]).
                int avgZero = randomNeighbor[zeroCoord] + (currentCell[zeroCoord] - randomNeighbor[zeroCoord]) / 2;
                int avgOne = randomNeighbor[oneCoord] + (currentCell[oneCoord] - randomNeighbor[oneCoord]) / 2;
                // Marks the midpoint as a passage.
                maze[avgZero][avgOne] = new Cell(avgZero, avgOne, Cell.Type.PASSAGE);
                // Go to the random neighbor
                currentCell = randomNeighbor.clone();
            }
        }

        // (height - 2, width - 1) is the exit from the maze
        maze[height - 2][width - 1] = new Cell(height - 2, width - 1, Cell.Type.PASSAGE);
        EnvironmentGeneration generation = new EnvironmentGeneration(new Maze(height, width, maze));
        // Generation additional environment
        generation.randomGeneration();
        generation.randomGeneration();
        return generation.maze();
    }

    private static List<int[]> neighbors(Cell[][] maze, int iCell, int jCell, boolean checkWall) {
        int zeroIdx = 0;
        int oneIdx = 1;
        List<int[]> finalList = new ArrayList<>();
        for (int i = 0; i < NEIGHBOURS; i++) {
            int[] n = {iCell, jCell};
            // Iterates through four neighbors
            // [i][j - 2]
            // [i][j + 2]
            // [i - 2][j]
            // [i + 2][j]
            n[i % 2] += ((i / 2 * 2) != 0) ? i / 2 * 2 : STEP_BACK;
            if (n[zeroIdx] < maze.length && n[oneIdx] < maze[zeroIdx].length && n[zeroIdx] > 0 && n[oneIdx] > 0) {
                if (checkWall || maze[n[zeroIdx]][n[oneIdx]].type() == Cell.Type.WALL) {
                    finalList.add(n);
                }
            }
        }
        return finalList;
    }

    private static boolean complete(Cell[][] maze) {
        // Iterating through the maze and checking if all cells are marked as visited
        for (int i = 1; i < maze.length; i += 2) {
            for (int j = 1; j < maze[i].length; j += 2) {
                if (maze[i][j].type() != Cell.Type.PASSAGE) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[][] findCoord(Cell[][] maze) {
        // Searching for a cell in the maze that is a wall and has at least one neighboring cell that is a passage.
        for (int i = 1; i < maze.length; i += 2) {
            for (int j = 1; j < maze[i].length; j += 2) {
                if (maze[i][j].type() == Cell.Type.WALL) {
                    List<int[]> n = neighbors(maze, i, j, false);
                    for (int[] coord : n) {
                        int firstCoord = 1;
                        int secondCoord = 2;
                        if (maze[coord[firstCoord]][coord[secondCoord]].type() == Cell.Type.PASSAGE) {
                            return new int[][] {{i, j}, coord};
                        }
                    }
                }
            }
        }
        return null;
    }
}
