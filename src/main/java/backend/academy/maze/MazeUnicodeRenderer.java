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
                        output.print(END + " ");
                        continue;
                    } else if (i == shortestPath.getFirst().x && j == shortestPath.getFirst().y) {
                        output.print(START + " ");
                        continue;
                    }
                    switch (maze.grid()[i][j].type()) {
                        case SWAMP:
                            output.print(SWAMP + " ");
                            break;
                        case WALL:
                            output.print(WALL + " ");
                            break;
                        case ANSWER:
                            output.print(ANSWER + " ");
                            break;
                        case LAKE:
                            output.print(LAKE + " ");
                            break;
                        case COIN:
                            output.print(COIN + " ");
                            break;
                        default:
                            output.print(PASSAGE + " ");
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
                switch (maze.grid()[i][j].type()) {
                    case COIN:
                        output.print(COIN + " ");
                        break;
                    case SWAMP:
                        output.print(SWAMP + " ");
                        break;
                    case WALL:
                        output.print(WALL + " ");
                        break;
                    case LAKE:
                        output.print(LAKE + " ");
                        break;
                    default:
                        output.print(PASSAGE + " ");
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
        output.println(SWAMP + " is a swamp (cost: 5000)");
        output.println(PASSAGE + " is a passage (cost: 50)");
        output.println(ANSWER + " is the cheapest path");
        output.println(WALL + " is a wall");
        output.println(LAKE + " is a lake (cost: 1000)");
        output.println(COIN + " is a coin (cost: 0)");
    }
}
