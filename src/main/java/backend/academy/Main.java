package backend.academy;

import backend.academy.maze.Maze;
import backend.academy.maze.MazeSelector;
import backend.academy.maze.MazeUnicodeRenderer;
import backend.academy.maze.Node;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MazeSelector mazeSelector = new MazeSelector(System.in, System.out);
        mazeSelector.algorithmSelection();
        mazeSelector.sizeSelection();
        Maze maze = mazeSelector.mazeGeneration();
        MazeUnicodeRenderer renderer = new MazeUnicodeRenderer();
        renderer.render(maze, System.out);
        mazeSelector.getPathCoordinates();
        mazeSelector.getMazeSolver();
        List<Node> answer = mazeSelector.applySolver();
        renderer.render(maze, answer, System.out);
    }
}
