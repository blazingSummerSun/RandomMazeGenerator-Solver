package backend.academy.maze;

import java.security.SecureRandom;

public record EnvironmentGeneration(Maze maze) {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ENVIRONMENT_TYPES = 3;
    private static final int SWAMP_ID = 0;
    private static final int LAKE_ID = 1;
    private static final int COIN_ID = 2;
    private static final int FREQUENCY = 3;

    public void randomGeneration() {
        for (int i = 1; i < maze.height(); i++) {
            for (int j = 0; j < maze.width() / FREQUENCY; j++) {
                int type = RANDOM.nextInt(ENVIRONMENT_TYPES);
                int randomIndex = RANDOM.nextInt(maze.width());
                switch (type) {
                    case SWAMP_ID:
                        if (isValid(i, randomIndex)) {
                            maze.grid()[i][randomIndex] = new Cell(i, randomIndex, Cell.Type.SWAMP);
                        }
                        break;
                    case LAKE_ID:
                        if (isValid(i, randomIndex)) {
                            maze.grid()[i][randomIndex] = new Cell(i, randomIndex, Cell.Type.LAKE);
                        }
                        break;
                    default:
                        if (isValid(i, randomIndex) && randomIndex % 2 == 0) {
                            maze.grid()[i][randomIndex] = new Cell(i, randomIndex, Cell.Type.COIN);
                        }
                        break;
                }
            }
        }
    }

    public void removeRandomWalls() {
        for (int i = 1; i < maze.height() - 1; i++) {
            for (int j = 0; j < maze.width() / FREQUENCY; j++) {
                int randomIndex = RANDOM.nextInt(maze.width());
                if (randomIndex > 0 && randomIndex < maze.width() - 1
                    && maze.grid()[i][randomIndex].type() == Cell.Type.WALL) {
                    maze.grid()[i][randomIndex] = new Cell(i, randomIndex, Cell.Type.PASSAGE);
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return col > 0 && col < maze.width() - 1 && maze.grid()[row][col].type() == Cell.Type.PASSAGE;
    }
}
