package backend.academy.maze;

import java.io.PrintStream;
import java.util.List;

public class MazeAsSymbolsRenderer implements Renderer {
    @Override
    public void render(Maze maze, List<Node> shortestPath, PrintStream output) {
        if (shortestPath.isEmpty()) {
            output.println("Such a path doesn't exist!");
        } else {
            output.println("""
                Here is your maze! Note that '1's are walls, '0's are passages, '#'s are the shortest path!
                """);
            for (Node node : shortestPath) {
                maze.grid()[node.x][node.y] =
                    new Cell(node.x, node.y, Cell.Type.ANSWER);
            }

            for (int i = 0; i < maze.height(); i++) {
                for (int j = 0; j < maze.width(); j++) {
                    if (maze.grid()[i][j].type() == Cell.Type.PASSAGE) {
                        output.print(0 + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.WALL) {
                        output.print(1 + " ");
                    } else {
                        output.print("# ");
                    }
                }
                output.println();
            }
        }
    }

    @Override
    public void render(Maze maze, PrintStream output) {
        output.println("""
            Here is your maze! Note that '1's are walls, '0's are passages!
            """);
        for (int i = 0; i < maze.height(); i++) {
            for (int j = 0; j < maze.width(); j++) {
                if (maze.grid()[i][j].type() == Cell.Type.PASSAGE) {
                    output.print(0 + " ");
                } else {
                    output.print(1 + " ");
                }
            }
            output.println();
        }
        output.println();
    }
}
