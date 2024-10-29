package backend.academy.maze;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class HuntAndKill implements Generator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int[][] DIRECTIONS = {
        {2, 0}, {-2, 0}, {0, 2}, {0, -2}
    };

    @Override
    public Maze generate(int height, int width) {
        Cell[][] maze = initializeMaze(height, width);
        // Entry creation in the maze
        setEntryPoints(maze);
        Cell currentCell = new Cell(1, 1, Cell.Type.PASSAGE);

        while (!complete(maze)) {
            // Searching for the neighbors of the current cell
            List<Cell> currentNeighbors = neighbors(maze, currentCell);

            // If there are no any neighbors, searching for the appropriate cell
            // Otherwise, pick random neighbor and build passage between these cells
            if (currentNeighbors.isEmpty()) {
                // Go for hunting
                Cell[] nextCells = findCoord(maze);
                int wallCell = 0;
                int adjacentCell = 1;
                if (nextCells != null) {
                    currentCell = nextCells[wallCell];
                    // Create passage in the currentCell and between its parent
                    markPassage(maze, currentCell);
                    markPassageBetweenCells(maze, currentCell, nextCells[adjacentCell]);
                }
            } else {
                Cell randomNeighbor = currentNeighbors.get(RANDOM.nextInt(currentNeighbors.size()));
                // Create passage in the currentCell and between its parent
                markPassage(maze, randomNeighbor);
                markPassageBetweenCells(maze, currentCell, randomNeighbor);
                currentCell = randomNeighbor;
            }
        }

        // Exit creation in the maze
        setExitPoint(maze, height, width);
        return generateEnvironment(new Maze(height, width, maze));
    }

    private Cell[][] initializeMaze(int height, int width) {
        Cell[][] maze = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }
        return maze;
    }

    private void setEntryPoints(Cell[][] maze) {
        int zeroIdx = 0;
        int firstIdx = 1;
        maze[zeroIdx][firstIdx] = new Cell(0, 1, Cell.Type.PASSAGE);
        maze[firstIdx][firstIdx] = new Cell(1, 1, Cell.Type.PASSAGE);
    }

    private void setExitPoint(Cell[][] maze, int height, int width) {
        maze[height - 2][width - 1] = new Cell(height - 2, width - 1, Cell.Type.PASSAGE);
    }

    private void markPassage(Cell[][] maze, Cell cell) {
        maze[cell.row()][cell.col()] = new Cell(cell.row(), cell.col(), Cell.Type.PASSAGE);
    }

    // Mark Passage between 2 cells
    private void markPassageBetweenCells(Cell[][] maze, Cell cell1, Cell cell2) {
        int midRow = cell1.row() + (cell2.row() - cell1.row()) / 2;
        int midCol = cell1.col() + (cell2.col() - cell1.col()) / 2;
        maze[midRow][midCol] = new Cell(midRow, midCol, Cell.Type.PASSAGE);
    }

    private Maze generateEnvironment(Maze maze) {
        EnvironmentGeneration generation = new EnvironmentGeneration(maze);
        generation.randomGeneration();
        generation.randomGeneration();
        return generation.maze();
    }

    private List<Cell> neighbors(Cell[][] maze, Cell cell) {
        int xDirection = 0;
        int yDirection = 1;
        List<Cell> neighbors = new ArrayList<>();
        for (int[] dir : DIRECTIONS) {
            int newRow = cell.row() + dir[xDirection];
            int newCol = cell.col() + dir[yDirection];
            if (isValid(newRow, newCol, maze)) {
                neighbors.add(new Cell(newRow, newCol, maze[newRow][newCol].type()));
            }
        }
        return neighbors;
    }

    private boolean isValid(int x, int y, Cell[][] maze) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length;
    }

    // Method checks if there are any walls on odd coordinates
    private boolean complete(Cell[][] maze) {
        for (int i = 1; i < maze.length; i += 2) {
            for (int j = 1; j < maze[i].length; j += 2) {
                if (maze[i][j].type() != Cell.Type.PASSAGE) {
                    return false;
                }
            }
        }
        return true;
    }

    // Method is responsible for the searching for the Wall with at least 1 Passage neighbor cell
    private Cell[] findCoord(Cell[][] maze) {
        for (int i = 1; i < maze.length; i += 2) {
            for (int j = 1; j < maze[i].length; j += 2) {
                if (maze[i][j].type() == Cell.Type.WALL) {
                    List<Cell> neighbors = neighbors(maze, new Cell(i, j, Cell.Type.WALL));
                    for (Cell neighbor : neighbors) {
                        if (maze[neighbor.row()][neighbor.col()].type() == Cell.Type.PASSAGE) {
                            return new Cell[] {new Cell(i, j, Cell.Type.WALL), neighbor};
                        }
                    }
                }
            }
        }
        return null;
    }
}
