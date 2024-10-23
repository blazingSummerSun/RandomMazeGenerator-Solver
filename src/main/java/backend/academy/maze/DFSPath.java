package backend.academy.maze;

import java.util.ArrayList;
import java.util.List;

public class DFSPath implements Solver {
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };
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
        findPath(start, end, visited, 0, new ArrayList<>());
        return new ArrayList<>(shortestPath); // Return a copy to avoid exposing internal representation
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
                Coordinate next = new Coordinate(start.row() + direction[xStep], start.col() + direction[yStep]);
                findPath(next, end, visited, length + 1, currentPath);
            }
        }

        currentPath.removeLast(); // Backtrack
        visited[start.row()][start.col()] = false; // Unmark this cell
    }
}
