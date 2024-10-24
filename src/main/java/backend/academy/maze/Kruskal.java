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
        // List to store all existing sets of points
        List<List<int[]>> sets = new ArrayList<>();
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Create passage on odd coordinates
                boolean add = !(i % 2 != 0 && j % 2 != 0);
                maze[i][j] = add ? new Cell(i, j, Cell.Type.WALL) : new Cell(i, j, Cell.Type.PASSAGE);

                // If add is false (cell is a passage),
                // a new list containing the cell's coordinates is created and added to the sets list.
                // In other words, create the new set with the current passage cell's coordinates.
                if (!add) {
                    List<int[]> newArrayList = new ArrayList<>();
                    newArrayList.add(new int[] {i, j});
                    sets.add(newArrayList);
                }

                // If the cell is a passage and not on the bottom or right boundary
                // (excluding the second-to-last row and column),
                // adds vertical and horizontal edges to the edges list connecting the cell to its adjacent cells.
                if (i != height - 2 && !add) {
                    edges.add(new int[] {i + 1, j});
                }

                if (j != width - 2 && !add) {
                    edges.add(new int[] {i, j + 1});
                }
            }
        }
        // Create the entrance to the maze at (0, 1) cell
        maze[zeroIdx][oneIdx] = new Cell(0, 1, Cell.Type.PASSAGE);

        while (!edges.isEmpty()) {
            int index = random.nextInt(edges.size());
            int[] removed = edges.remove(index);

            // Decide the orientation (vertical or horizontal) of the removed edge
            // based on its coordinates (1 is horizontal, != 1 is vertical)
            int orientation = removed[zeroIdx] % 2;

            // Arrays to store the coordinates of the neighbor cells
            int[] cell1;
            int[] cell2;

            if (orientation == 1) {
                cell1 = new int[] {removed[zeroIdx], removed[oneIdx] - 1};
                cell2 = new int[] {removed[zeroIdx], removed[oneIdx] + 1};
            } else {
                cell1 = new int[] {removed[zeroIdx] - 1, removed[oneIdx]};
                cell2 = new int[] {removed[zeroIdx] + 1, removed[oneIdx]};
            }

            // Find the indices of the sets containing cell1 and cell2
            int index1 = indexOfSet(sets, cell1);
            int index2 = indexOfSet(sets, cell2);

            // If sets are different, merge these sets
            if (index1 != index2) {
                // Remove the set with index 'index2'
                List<int[]> add = sets.remove(index2);
                // Adjust 'index1' if 'index2' < ''index1
                if (index2 < index1) {
                    index1--;
                }
                // Add all elements from second set to the first set
                sets.get(index1).addAll(add);
                maze[removed[zeroIdx]][removed[oneIdx]] =
                    new Cell(removed[zeroIdx], removed[oneIdx], Cell.Type.PASSAGE);
            }
        }

        // Add the exit from the maze at (height-1, width-2) coordinate
        maze[height - 1][width - 2] = new Cell(height - 1, width - 1, Cell.Type.PASSAGE);
        return new Maze(height, width, maze);
    }

    private static int indexOfSet(List<List<int[]>> sets, int[] coordinates) {
        for (int i = 0; i < sets.size(); i++) {
            if (contains(sets.get(i), coordinates)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean contains(List<int[]> listOfCoordinates, int[] coordinates) {
        int zeroIdx = 0;
        int oneIdx = 1;
        for (int[] cell : listOfCoordinates) {
            if (cell[zeroIdx] == coordinates[zeroIdx] && cell[oneIdx] == coordinates[oneIdx]) {
                return true;
            }
        }
        return false;
    }
}
