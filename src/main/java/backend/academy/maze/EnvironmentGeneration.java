package backend.academy.maze;

import java.security.SecureRandom;

public record EnvironmentGeneration(Maze maze) {
    private final static SecureRandom RANDOM = new SecureRandom();
    private final static int ENVIRONMENT_TYPES = 2;
    private final static int SWAMP_ID = 0;
    private final static int LAKE_ID = 1;
    private final static int FREQUENCY = 3;

    public void randomGeneration() {
        for (int i = 1; i < maze.height(); i++) {
            for (int j = 0; j < maze.width() / FREQUENCY; j++) {
                int type = RANDOM.nextInt(ENVIRONMENT_TYPES);
                int randomIndex = RANDOM.nextInt(maze.width());
                if (type == SWAMP_ID) {
                    if (randomIndex > 0 && randomIndex < maze.width() - 1
                        && maze.grid()[i][randomIndex].type() == Cell.Type.PASSAGE) {
                        maze.grid()[i][randomIndex] = new Cell(i, randomIndex, Cell.Type.SWAMP);
                    }
                } else {
                    if (randomIndex > 0 && randomIndex < maze.width() - 1
                        && maze.grid()[i][randomIndex].type() == Cell.Type.PASSAGE) {
                        maze.grid()[i][randomIndex] = new Cell(i, randomIndex, Cell.Type.LAKE);
                    }
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
}
