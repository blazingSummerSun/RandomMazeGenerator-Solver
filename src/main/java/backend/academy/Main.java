package backend.academy;

import backend.academy.maze.Maze;
import backend.academy.maze.MazeAsSymbolsRenderer;
import backend.academy.maze.MazeSelector;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MazeSelector mazeSelector = new MazeSelector(System.in, System.out);
        mazeSelector.algorithmSelection();
        mazeSelector.sizeSelection();
        Maze maze = mazeSelector.mazeGeneration();
        MazeAsSymbolsRenderer renderer = new MazeAsSymbolsRenderer();
        renderer.render(maze, System.out);
        mazeSelector.getPathCoordinates();
        mazeSelector.getMazeSolver();
        renderer.render(maze, mazeSelector.applySolver(), System.out);
    }
}
