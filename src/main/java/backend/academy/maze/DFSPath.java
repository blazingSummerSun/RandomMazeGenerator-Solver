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
    private int shortestLength;
    private final Node[][] pathToEachCell;

    public DFSPath(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        this.shortestPath = new ArrayList<>();
        this.shortestLength = Integer.MAX_VALUE;
        pathToEachCell = new Node[maze.height()][maze.width()];
        pathToEachCell[start.row()][start.col()] = new Node(start.row(), start.col(), 0, 0, null);
    }

    @Override
    public List<Node> solve() {
        return findShortestPath();
    }

    public List<Node> findShortestPath() {
        findPath(start, end, 0, new ArrayList<>());
        return new ArrayList<>(shortestPath);
    }

    private void findPath(
        Coordinate start,
        Coordinate end,
        int length,
        List<Node> currentPath
    ) {
        if (!isValid(start.row(), start.col())) {
            return;
        }

        currentPath.add(new Node(start.row(), start.col(), length, 0, null));

        if (start.row() == end.row() && start.col() == end.col()) {
            if (length < shortestLength) {
                shortestPath = new ArrayList<>(currentPath);
                shortestLength = length;
            }
        } else {
            int xStep = 0;
            int yStep = 1;
            for (int[] direction : DIRECTIONS) {
                int nextXStep = start.row() + direction[xStep];
                int nextYStep = start.col() + direction[yStep];
                if (isValid(nextXStep, nextYStep) && pathToEachCell[nextXStep][nextYStep] == null) {
                    addNeighbor(start.row(), start.col(), nextXStep, nextYStep);
                    Coordinate next = new Coordinate(nextXStep, nextYStep);
                    findPath(next, end, length + maze.grid()[nextXStep][nextYStep].cellCost(), currentPath);
                } else if (isValid(nextXStep, nextYStep) && pathToEachCell[nextXStep][nextYStep] != null
                    && pathToEachCell[nextXStep][nextYStep].g > length + maze.grid()[nextXStep][nextYStep].cellCost()) {
                    addNeighbor(start.row(), start.col(), nextXStep, nextYStep);
                    Coordinate next = new Coordinate(nextXStep, nextYStep);
                    findPath(next, end, length + maze.grid()[nextXStep][nextYStep].cellCost(), currentPath);
                }
            }
        }
        currentPath.removeLast();
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.height() && y >= 0 && y < maze.width() && !isWall(x, y);
    }

    private void addNeighbor(int startX, int startY, int newX, int newY) {
        Node currentNode = pathToEachCell[startX][startY];
        Node neighbor = new Node(newX, newY, currentNode.g + maze.grid()[newX][newY].cellCost(),
            0, currentNode);
        pathToEachCell[newX][newY] = neighbor;
    }

    private boolean isWall(int x, int y) {
        return maze.grid()[x][y].type() == Cell.Type.WALL;
    }
}
