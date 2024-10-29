package backend.academy.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraPath implements Solver {
    private static final int[][] DIRECTIONS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };
    private final Maze maze;
    private final Coordinate start;
    private final Coordinate end;
    private final Node[][] destinations;
    List<Node> shortestPath;

    public DijkstraPath(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        destinations = new Node[maze.height()][maze.width()];
        shortestPath = new ArrayList<>();
    }

    @Override
    public List<Node> solve() {
        int xCoordinate = 0;
        int yCoordinate = 1;
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Node(start.row(), start.col(), 0, 0, null));
        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            if (current.x == end.row() && current.y == end.col()) {
                while (current != null) {
                    shortestPath.add(current);
                    current = current.parent;
                }
                return shortestPath.reversed();
            }
            for (int[] nextMove : DIRECTIONS) {
                int newX = current.x + nextMove[xCoordinate];
                int newY = current.y + nextMove[yCoordinate];
                if (isValid(newX, newY)) {
                    int newCost = current.g + maze.grid()[newX][newY].cellCost();
                    Node neighborNode = new Node(newX, newY, newCost, 0, current);
                    destinations[newX][newY] = neighborNode;
                    priorityQueue.add(neighborNode);
                }
            }
        }
        return shortestPath;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.height() && y >= 0 && y < maze.width()
            && maze.grid()[x][y].type() != Cell.Type.WALL && destinations[x][y] == null;
    }
}
