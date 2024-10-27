package backend.academy.maze;

import java.io.PrintStream;
import java.util.List;

public class MazeUnicodeRenderer implements Renderer {
    private static final String START = "🛏";
    private static final String END = "📍";
    private static final String SWAMP = "🐸";
    private static final String PASSAGE = "🟠";
    private static final String ANSWER = "🟣";
    private static final String WALL = "🧱";
    private static final String LAKE = "🌊";
    private static final String COIN = "💷";

    @Override
    public void render(Maze maze, List<Node> shortestPath, PrintStream output) {
        if (shortestPath.isEmpty()) {
            output.println("Such a path doesn't exist!");
        } else {
            getInformation(output);
            for (Node node : shortestPath) {
                maze.grid()[node.x][node.y] =
                    new Cell(node.x, node.y, Cell.Type.ANSWER);
            }

            for (int i = 0; i < maze.height(); i++) {
                for (int j = 0; j < maze.width(); j++) {
                    if (i == shortestPath.getLast().x && j == shortestPath.getLast().y) {
                        output.print(START + " ");
                    } else if (i == shortestPath.getFirst().x && j == shortestPath.getFirst().y) {
                        output.print(END + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.PASSAGE) {
                        output.print(PASSAGE + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.WALL) {
                        output.print(WALL + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.ANSWER) {
                        output.print(ANSWER + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.SWAMP) {
                        output.print(SWAMP + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.LAKE) {
                        output.print(LAKE + " ");
                    } else if (maze.grid()[i][j].type() == Cell.Type.COIN) {
                        output.print(COIN + " ");
                    }
                }
                output.println();
            }
        }
    }

    @Override
    public void render(Maze maze, PrintStream output) {
        getInformation(output);
        for (int i = 0; i < maze.height(); i++) {
            for (int j = 0; j < maze.width(); j++) {
                if (maze.grid()[i][j].type() == Cell.Type.PASSAGE) {
                    output.print(PASSAGE + " ");
                } else if (maze.grid()[i][j].type() == Cell.Type.SWAMP) {
                    output.print(SWAMP + " ");
                } else if (maze.grid()[i][j].type() == Cell.Type.WALL) {
                    output.print(WALL + " ");
                } else if (maze.grid()[i][j].type() == Cell.Type.LAKE) {
                    output.print(LAKE + " ");
                } else if (maze.grid()[i][j].type() == Cell.Type.COIN) {
                    output.print(COIN + " ");
                }
            }
            output.println();
        }
        output.println();
    }

    private void getInformation(PrintStream output) {
        output.println("Here is your maze!");
        output.println(START + " is the start point");
        output.println(END + " is the end point");
        output.println(SWAMP + " is a swamp");
        output.println(PASSAGE + " is a passage");
        output.println(ANSWER + " is the cheapest (not the case for BFS) path");
        output.println(WALL + " is a wall");
        output.println(LAKE + " is a lake");
        output.println(COIN + " is a coin");
    }
}
