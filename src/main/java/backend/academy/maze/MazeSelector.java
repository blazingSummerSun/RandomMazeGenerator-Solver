package backend.academy.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MazeSelector {
    private static final int KRUSKAL = 1;
    private static final int HUNT_AND_KILL = 2;
    private static final int DFS = 1;
    private static final int BFS = 2;
    private static final int A_STAR = 3;
    private static final String NOT_A_NUMBER_ERROR = "Your input is not a number! Try again!";
    private final static String IO_ERROR = "Something wrong with your input! Try again!";
    private final static String INPUT_HINT = "Enter any positive integer!";

    private int generatorIndex = -1;
    private int solverIndex;
    private int height = -1;
    private int width = -1;
    private final BufferedReader reader;
    private final PrintStream output;
    private Coordinate endCoordinate;
    private Coordinate startCoordinate;
    private Maze generatedMaze;

    public MazeSelector(InputStream input, PrintStream output) {
        reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        this.output = output;
    }

    public void algorithmSelection() {
        output.println("Welcome to the maze!");
        while (generatorIndex < KRUSKAL || generatorIndex > HUNT_AND_KILL) {
            output.print("""
                Choose desired algorithm for maze generation (enter the number)!
                1. Kruskal's algorithm generation (Kinda simple maze without loops inside)
                2. Hunt and Kill algorithm generation (More complicated maze with loops inside)
                """);
            try {
                generatorIndex = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                output.println(NOT_A_NUMBER_ERROR);
            } catch (IOException e) {
                output.println(IO_ERROR);
            }
        }
    }

    public void sizeSelection() {
        output.println("Well! Time to choose the height for the maze!");
        while (height < 0) {
            try {
                output.println(INPUT_HINT);
                height = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                output.println(NOT_A_NUMBER_ERROR);
            } catch (IOException e) {
                output.println(IO_ERROR);
            }
        }
        output.println("Choose the width for the maze");
        while (width < 0) {
            try {
                output.println(INPUT_HINT);
                width = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                output.println(NOT_A_NUMBER_ERROR);
            } catch (IOException e) {
                output.println(IO_ERROR);
            }
        }
    }

    public Maze mazeGeneration() {
        int oddWidth = width - width % 2;
        oddWidth++;
        int oddHeight = height - height % 2;
        oddHeight++;
        if (oddHeight == 1 && oddWidth == 1) {
            output.println("You entered too small height and width! It will decreased by a few blocks");
            oddHeight += 2;
            oddWidth += 2;
        }
        if (generatorIndex == 1) {
            generatedMaze = new Kruskal().generate(oddHeight, oddWidth);
            return generatedMaze;
        }
        generatedMaze = new HuntAndKill().generate(oddHeight, oddWidth);
        return generatedMaze;
    }

    private int getXCoordinate() {
        int xCoordinate = -1;
        while (xCoordinate < 0 || xCoordinate > generatedMaze.height()) {
            try {
                output.println("""
                    Enter the x-coordinate within the maze (starting from 0-index)!
                    Note that x-coordinate is a row number counting from the left top corner!""");
                xCoordinate = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                output.println(NOT_A_NUMBER_ERROR);
            } catch (IOException e) {
                output.println(IO_ERROR);
            }
        }
        return xCoordinate;
    }

    private int getYCoordinate() {
        int yCoordinate = -1;
        while (yCoordinate < 0 || yCoordinate > generatedMaze.width()) {
            try {
                output.print("""
                    Enter the y-coordinate! (starting from 0-index)!
                    Note that y-coordinate is a row number counting from the left top corner!
                    """);
                yCoordinate = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                output.println(NOT_A_NUMBER_ERROR);
            } catch (IOException e) {
                output.println(IO_ERROR);
            }
        }
        return yCoordinate;
    }

    public void getPathCoordinates() {
        output.println("Choose coordinates for the start!");
        int xStart = getXCoordinate();
        int yStart = getYCoordinate();
        startCoordinate = new Coordinate(xStart, yStart);

        output.println("Choose coordinates for the end!");
        int xEnd = getXCoordinate();
        int yEnd = getYCoordinate();
        endCoordinate = new Coordinate(xEnd, yEnd);
    }

    public void getMazeSolver() {
        output.printf("Choose the method you want to apply to find the shortest path from (%d, %d) to (%d, %d) %n",
            startCoordinate.row(), startCoordinate.col(), endCoordinate.row(), endCoordinate.col());
        while (solverIndex < DFS || solverIndex > A_STAR) {
            output.print("""
                1. DFS
                2. BFS
                3. A* algorithm
                """);
            try {
                solverIndex = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                output.println(NOT_A_NUMBER_ERROR);
            } catch (IOException e) {
                output.println(IO_ERROR);
            }
        }
    }

    public List<Node> applySolver() {
        return switch (solverIndex) {
            case DFS -> new DFSPath(generatedMaze, startCoordinate, endCoordinate).solve();
            case BFS -> new BFSPath(generatedMaze, startCoordinate, endCoordinate).solve();
            default -> new AStarPath(generatedMaze, startCoordinate, endCoordinate).solve();
        };
    }
}
