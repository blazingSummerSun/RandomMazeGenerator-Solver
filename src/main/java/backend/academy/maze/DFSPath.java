package backend.academy.maze;

import java.util.ArrayList;
import java.util.List;

public class DFSPath implements Solver {
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };
    private static final int SWAMP = 5;
    private static final int PASSAGE = 1;
    private final Maze maze;
    private final Coordinate start;
    private final Coordinate end;
    private List<Node> shortestPath;
    private int currentLength;

    public DFSPath(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        this.shortestPath = new ArrayList<>();
        this.currentLength = Integer.MAX_VALUE;
    }

    @Override
    public List<Node> solve() {
        return findShortestPath();
    }

    public List<Node> findShortestPath() {
        boolean[][] visited = new boolean[maze.height()][maze.width()];
        findPath(start, end, visited, cellCost(maze.grid()[start.row()][start.col()]), new ArrayList<>());
        return new ArrayList<>(shortestPath);
    }

    private void findPath(
        Coordinate start,
        Coordinate end,
        boolean[][] visited,
        int length,
        List<Node> currentPath
    ) {
        if (start.row() < 0 || start.col() < 0 || start.row() >= maze.height() || start.col() >= maze.width()
            || maze.grid()[start.row()][start.col()].type() == Cell.Type.WALL || visited[start.row()][start.col()]) {
            return;
        }

        currentPath.add(new Node(start.row(), start.col(), length, 0, null));
        visited[start.row()][start.col()] = true;

        if (start.row() == end.row() && start.col() == end.col()) {
            if (length < currentLength) {
                shortestPath = new ArrayList<>(currentPath);
                currentLength = length;
            }
        } else {
            int xStep = 0;
            int yStep = 1;
            for (int[] direction : DIRECTIONS) {
                int nextXStep = start.row() + direction[xStep];
                int nextYStep = start.col() + direction[yStep];
                if (nextXStep >= 0 && nextXStep < maze.height() && nextYStep >= 0 && nextYStep < maze.width()) {
                    Coordinate next = new Coordinate(nextXStep, nextYStep);
                    findPath(next, end, visited, length + cellCost(maze.grid()[nextXStep][nextYStep]), currentPath);
                }
            }
        }

        currentPath.removeLast(); // Backtrack
        visited[start.row()][start.col()] = false; // Unmark this cell
    }

    private int cellCost(Cell cell) {
        if (maze.grid()[cell.row()][cell.col()].type() == Cell.Type.SWAMP) {
            return SWAMP;
        } else if (maze.grid()[cell.row()][cell.col()].type() == Cell.Type.PASSAGE) {
            return PASSAGE;
        }
        return PASSAGE;
    }
}
