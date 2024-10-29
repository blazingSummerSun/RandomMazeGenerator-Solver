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
        if (!isValid(start.row(), start.col())) {
            return shortestPath;
        }
        PriorityQueue<Node> priorityTraverse = new PriorityQueue<>();
        priorityTraverse.add(new Node(start.row(), start.col(), 0, heuristic(0, 0), null));
        Node current;
        while (!priorityTraverse.isEmpty()) {
            current = priorityTraverse.poll();
            if (current.x == end.row() && current.y == end.col()) {
                while (current != null) {
                    shortestPath.add(current);
                    current = current.parent;
                }
                return shortestPath.reversed();
            }
            for (int[] nextStep : DIRECTIONS) {
                int newX = current.x + nextStep[xStep];
                int newY = current.y + nextStep[yStep];
                Node neighborNode;
                if (isValid(newX, newY)) {
                    neighborNode = new Node(newX, newY,
                        current.g + maze.grid()[newX][newY].cellCost(), heuristic(newX, newY), current);
                    destinations[newX][newY] = neighborNode;
                    priorityTraverse.add(neighborNode);
                }
            }
        }
        return shortestPath;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.height() && y >= 0 && y < maze.width()
            && maze.grid()[x][y].type() != Cell.Type.WALL && destinations[x][y] == null;
    }

    private int heuristic(int x, int y) {
        return Math.abs(x - end.row()) + Math.abs(y - end.col());
    }
}
