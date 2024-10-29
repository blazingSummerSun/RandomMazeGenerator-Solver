package backend.academy.maze;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AStarPathTest {

    @Test
    void solve() {
        Cell[][] grid = new Cell[7][9];
        Coordinate start = new Coordinate(5, 1);
        Coordinate end = new Coordinate(1, 7);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 0 || j == 0 || i == 6 || j == 8) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }
        grid[4][1] = new Cell(4, 1, Cell.Type.LAKE);
        grid[2][2] = new Cell(2, 2, Cell.Type.LAKE);
        grid[2][3] = new Cell(4, 3, Cell.Type.LAKE);
        grid[3][3] = new Cell(3, 3, Cell.Type.LAKE);
        grid[4][3] = new Cell(4, 3, Cell.Type.LAKE);
        grid[5][3] = new Cell(5, 3, Cell.Type.LAKE);
        grid[1][5] = new Cell(1, 5, Cell.Type.LAKE);
        grid[2][5] = new Cell(2, 5, Cell.Type.LAKE);
        grid[3][5] = new Cell(3, 5, Cell.Type.LAKE);
        grid[4][5] = new Cell(4, 5, Cell.Type.LAKE);
        grid[1][6] = new Cell(1, 6, Cell.Type.LAKE);
        grid[3][7] = new Cell(3, 7, Cell.Type.LAKE);
        grid[4][7] = new Cell(4, 7, Cell.Type.LAKE);
        grid[5][7] = new Cell(5, 7, Cell.Type.LAKE);
        // WWWWWWWWW
        // WPPPPLLPW
        // WPLLPLPPW
        // WPPLPLPLW
        // WLPLPLPLW
        // WPPLPPPLW
        // WWWWWWWWW
        Maze maze = new Maze(7, 9, grid);
        AStarPath path = new AStarPath(maze, start, end);
        List<Node> generatedPath = path.solve();
        List<Node> shortestPath = new ArrayList<>();
        shortestPath.add(new Node(5, 1, 0, 0, null));
        shortestPath.add(new Node(5, 2, 1, 0, shortestPath.getFirst()));
        shortestPath.add(new Node(4, 2, 2, 0, shortestPath.get(1)));
        shortestPath.add(new Node(3, 2, 3, 0, shortestPath.get(2)));
        shortestPath.add(new Node(3, 1, 4, 0, shortestPath.get(3)));
        shortestPath.add(new Node(2, 1, 5, 0, shortestPath.get(4)));
        shortestPath.add(new Node(1, 1, 6, 0, shortestPath.get(5)));
        shortestPath.add(new Node(1, 2, 7, 0, shortestPath.get(6)));
        shortestPath.add(new Node(1, 3, 8, 0, shortestPath.get(7)));
        shortestPath.add(new Node(1, 4, 9, 0, shortestPath.get(8)));
        shortestPath.add(new Node(2, 4, 10, 0, shortestPath.get(9)));
        shortestPath.add(new Node(3, 4, 11, 0, shortestPath.get(10)));
        shortestPath.add(new Node(4, 4, 12, 0, shortestPath.get(11)));
        shortestPath.add(new Node(5, 4, 13, 0, shortestPath.get(12)));
        shortestPath.add(new Node(5, 5, 14, 0, shortestPath.get(13)));
        shortestPath.add(new Node(5, 6, 15, 0, shortestPath.get(14)));
        shortestPath.add(new Node(4, 6, 16, 0, shortestPath.get(15)));
        shortestPath.add(new Node(3, 6, 17, 0, shortestPath.get(16)));
        shortestPath.add(new Node(2, 6, 18, 0, shortestPath.get(17)));
        shortestPath.add(new Node(2, 7, 19, 0, shortestPath.get(18)));
        shortestPath.add(new Node(1, 7, 20, 0, shortestPath.get(19)));
        assertEquals(generatedPath.size(), shortestPath.size());
        for (int i = 0; i < shortestPath.size(); i++) {
            assertEquals(shortestPath.get(i).x, generatedPath.get(i).x);
            assertEquals(shortestPath.get(i).y, generatedPath.get(i).y);
        }
    }
}
