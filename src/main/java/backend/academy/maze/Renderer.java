package backend.academy.maze;

import java.io.PrintStream;
import java.util.List;

public interface Renderer {
    void render(Maze maze, PrintStream output);

    void render(Maze maze, List<Node> path, PrintStream output);
}
