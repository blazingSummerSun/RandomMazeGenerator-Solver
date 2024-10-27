package backend.academy.maze;

import java.io.PrintStream;
import java.util.List;

public class MazeAsSymbolsRenderer implements Renderer {
    private final static int SWAMP = 5;
    private final static int PASSAGE = 0;
    private final static char ANSWER = '#';
    private final static int WALL = 1;

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
                        output.print(PASSAGE + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.WALL) {
                        output.print(WALL + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.ANSWER) {
                        output.print(ANSWER + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.SWAMP) {
                        output.print(SWAMP + " ");
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
                    output.print(PASSAGE + " ");
                } else if (maze.grid()[i][j].type() == Cell.Type.SWAMP) {
                    output.print(SWAMP + " ");
                } else if (maze.grid()[i][j].type() == Cell.Type.WALL) {
                    output.print(WALL + " ");
                }
            }
            output.println();
        }
        output.println();
    }
}
