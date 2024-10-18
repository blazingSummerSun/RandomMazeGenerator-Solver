package backend.academy.maze;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Kruskal implements Generator {
    private final static int ZERO = 0;
    private final static int ONE = 1;

    @Override
    public Maze generate(int height, int width) {
        // Make dimensions odd
        int oddWidth = width - width % 2;
        oddWidth++;
        int oddHeight = height - height % 2;
        oddHeight++;

        // Initialize maze: each square is its own set
        Cell[][] maze = new Cell[oddHeight][oddWidth];
        List<List<int[]>> sets = new ArrayList<>();
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < oddHeight; i++) {
            for (int j = 0; j < oddWidth; j++) {
                boolean add = !(i % 2 == 1 && j % 2 == 1);
                maze[i][j] = add ? new Cell(i, j, Cell.Type.WALL) : new Cell(i, j, Cell.Type.PASSAGE);
                if (!add) {
                    List<int[]> newSet = new ArrayList<>();
                    newSet.add(new int[] {i, j});
                    sets.add(newSet);
                }

                if (i != oddHeight - 2 && !add) {
                    edges.add(new int[] {i + 1, j});
                }

                if (j != oddWidth - 2 && !add) {
                    edges.add(new int[] {i, j + 1});
                }
            }
        }

        maze[0][1] = new Cell(0, 1, Cell.Type.PASSAGE);

        SecureRandom random = new SecureRandom();

        while (!edges.isEmpty()) {
            int index = random.nextInt(edges.size());
            int[] removed = edges.remove(index);

            int choice = removed[0] % 2;

            int[] cell1;
            int[] cell2;

            if (choice == 1) {
                cell1 = new int[] {removed[0], removed[1] - 1};
                cell2 = new int[] {removed[0], removed[1] + 1};
            } else {
                cell1 = new int[] {removed[0] - 1, removed[1]};
                cell2 = new int[] {removed[0] + 1, removed[1]};
            }

            int i1 = indexOfSet(sets, cell1);
            int i2 = indexOfSet(sets, cell2);

            if (i1 != i2) {
                List<int[]> add = sets.remove(i2);
                if (i2 < i1) {
                    i1--;
                }
                sets.get(i1).addAll(add);
                maze[removed[0]][removed[1]] = new Cell(removed[0], removed[1], Cell.Type.PASSAGE);
            }
        }

        maze[oddHeight - 1][oddWidth - 2] = new Cell(oddHeight - 1, oddWidth - 1, Cell.Type.PASSAGE);
        return new Maze(oddHeight, oddWidth, maze);
    }

    private static int indexOfSet(List<List<int[]>> sets, int[] c) {
        for (int i = 0; i < sets.size(); i++) {
            if (contains(sets.get(i), c)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean contains(List<int[]> s, int[] c) {
        for (int[] cell : s) {
            if (cell[ZERO] == c[ZERO] && cell[ONE] == c[ONE]) {
                return true;
            }
        }
        return false;
    }
}
