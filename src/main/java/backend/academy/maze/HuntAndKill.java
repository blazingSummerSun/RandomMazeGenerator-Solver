package backend.academy.maze;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class HuntAndKill implements Generator {
    private final static SecureRandom RANDOM = new SecureRandom();
    private final static int NEIGHBOURS = 4;
    private final static int STEP_BACK = -2;

    @Override
    public Maze generate(int height, int width) {
        // Make dimensions odd
        int oddWidth = width - width % 2;
        oddWidth++;
        int oddHeight = height - height % 2;
        oddHeight++;

        // Fill maze with 1's (walls)
        Cell[][] maze = new Cell[oddHeight][oddWidth];
        for (int i = 0; i < oddHeight; i++) {
            for (int j = 0; j < oddWidth; j++) {
                maze[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }

        // Opening at top - start of maze
        int zeroCoord = 0;
        int oneCoord = 1;
        maze[zeroCoord][oneCoord] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze[oneCoord][oneCoord] = new Cell(1, 1, Cell.Type.PASSAGE);

        int[] on = {1, 1};

        while (!complete(maze)) {
            List<int[]> n = neighbors(maze, on[zeroCoord], on[oneCoord], true);
            if (n.isEmpty()) {
                int[][] t = findCoord(maze);
                if (t != null && t[0] != null) {  // Add null checks
                    on = t[0];

                    maze[on[zeroCoord]][on[oneCoord]] = new Cell(on[zeroCoord], on[oneCoord], Cell.Type.PASSAGE);
                    int avgZero = on[zeroCoord] + (t[oneCoord][zeroCoord] - on[zeroCoord]) / 2;
                    int avgOne = on[oneCoord] + (t[oneCoord][oneCoord] - on[oneCoord]) / 2;
                    maze[avgZero][avgOne] = new Cell(avgZero, avgOne, Cell.Type.PASSAGE);
                }
            } else {
                int i = RANDOM.nextInt(n.size());
                int[] nb = n.get(i);
                maze[nb[zeroCoord]][nb[oneCoord]] = new Cell(nb[zeroCoord], nb[oneCoord], Cell.Type.PASSAGE);
                int avgZero = nb[zeroCoord] + (on[zeroCoord] - nb[zeroCoord]) / 2;
                int avgOne = nb[oneCoord] + (on[oneCoord] - nb[oneCoord]) / 2;
                maze[avgZero][avgOne] = new Cell(avgZero, avgOne, Cell.Type.PASSAGE);
                on = nb.clone();
            }
        }

        maze[oddHeight - 2][oddWidth - 1] = new Cell(oddHeight - 2, oddWidth - 1, Cell.Type.PASSAGE);
        return new Maze(oddHeight, oddWidth, maze);
    }

    private static List<int[]> neighbors(Cell[][] maze, int ic, int jc, boolean checkWall) {
        int zeroIdx = 0;
        int oneIdx = 1;
        List<int[]> finalList = new ArrayList<>();
        for (int i = 0; i < NEIGHBOURS; i++) {
            int[] n = {ic, jc};
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
