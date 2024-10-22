package backend.academy.maze;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Kruskal implements Generator {
    private final SecureRandom random = new SecureRandom();

    @Override
    public Maze generate(int height, int width) {
        int zeroIdx = 0;
        int oneIdx = 1;

        Cell[][] maze = new Cell[height][width];
        List<List<int[]>> sets = new ArrayList<>();
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean add = !(i % 2 != 0 && j % 2 != 0);
                maze[i][j] = add ? new Cell(i, j, Cell.Type.WALL) : new Cell(i, j, Cell.Type.PASSAGE);
                if (!add) {
                    List<int[]> newArrayList = new ArrayList<>();
                    newArrayList.add(new int[] {i, j});
                    sets.add(newArrayList);
                }

                if (i != height - 2 && !add) {
                    edges.add(new int[] {i + 1, j});
                }

                if (j != width - 2 && !add) {
                    edges.add(new int[] {i, j + 1});
                }
            }
        }

        maze[zeroIdx][oneIdx] = new Cell(0, 1, Cell.Type.PASSAGE);

        while (!edges.isEmpty()) {
            int index = random.nextInt(edges.size());
            int[] removed = edges.remove(index);

            int choice = removed[zeroIdx] % 2;

            int[] cell1;
            int[] cell2;

            if (choice == 1) {
                cell1 = new int[] {removed[zeroIdx], removed[oneIdx] - 1};
                cell2 = new int[] {removed[zeroIdx], removed[oneIdx] + 1};
            } else {
                cell1 = new int[] {removed[zeroIdx] - 1, removed[oneIdx]};
                cell2 = new int[] {removed[zeroIdx] + 1, removed[oneIdx]};
            }

            int i1 = indexOfSet(sets, cell1);
            int i2 = indexOfSet(sets, cell2);

            if (i1 != i2) {
                List<int[]> add = sets.remove(i2);
                if (i2 < i1) {
                    i1--;
                }
                sets.get(i1).addAll(add);
                maze[removed[zeroIdx]][removed[oneIdx]] =
                    new Cell(removed[zeroIdx], removed[oneIdx], Cell.Type.PASSAGE);
            }
        }

        maze[height - 1][width - 2] = new Cell(height - 1, width - 1, Cell.Type.PASSAGE);
        return new Maze(height, width, maze);
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
        int zeroIdx = 0;
        int oneIdx = 1;
        for (int[] cell : s) {
            if (cell[zeroIdx] == c[zeroIdx] && cell[oneIdx] == c[oneIdx]) {
                return true;
            }
        }
        return false;
    }
}
