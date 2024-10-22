package backend.academy.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStarPath implements Solver {
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };
    private final Maze maze;
    private final Coordinate start;
    private final Coordinate end;
    private final Node[][] destinations;
    List<Node> shortestPath;

    public AStarPath(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        destinations = new Node[maze.height()][maze.width()];
        shortestPath = new ArrayList<>();
    }

    @Override
    public List<Node> solve() {
        int xStep = 0;
        int yStep = 1;
        if (!isValidInitPos()) {
            return shortestPath;
        }
        PriorityQueue<Node> futureSteps = new PriorityQueue<>();
        futureSteps.add(new Node(start.row(), start.col(), 0, heuristic(0, 0), null));
        Node current;
        while (!futureSteps.isEmpty()) {
            current = futureSteps.poll();
            if (current.x == end.row() && current.y == end.col()) {
                while (current != null) {
                    shortestPath.add(current);
                    current = current.parent;
                }
                return shortestPath;
            }
            for (int[] nextStep : DIRECTIONS) {
                int newX = current.x + nextStep[xStep];
                int newY = current.y + nextStep[yStep];
                if (destinations[newX][newY] == null && isValid(newX, newY)) {
                    Node neighborNode = new Node(newX, newY, current.g + 1, heuristic(newX, newY), current);
                    destinations[newX][newY] = neighborNode;
                    futureSteps.add(neighborNode);
                }
            }
        }
        return shortestPath;
    }

    private boolean isValidInitPos() {
        return start.row() == 0 && start.col() == 1
            || start.row() == maze.height() - 2 && start.col() == maze.width() - 1
            || start.row() > 0 && start.row() < maze.height() - 1 && start.col() > 0 && start.col() < maze.height() - 1
            && (end.row() == 0 && end.col() == 1
            || end.row() == maze.height() - 2 && end.col() == maze.width() - 1
            || end.row() > 0 && end.row() < maze.height() - 1 && end.col() > 0 && end.col() < maze.height() - 1);

    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.height() && y >= 0 && y < maze.width()
            && maze.grid()[x][y].type() == Cell.Type.PASSAGE;
    }

    private int heuristic(int x, int y) {
        return Math.abs(x - end.row()) + Math.abs(y - end.col());
    }
}
