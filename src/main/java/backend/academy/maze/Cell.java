package backend.academy.maze;

public record Cell(int row, int col, Type type) {
    private final static int SWAMP_COST = 5;
    private final static int PASSAGE_COST = 5;
    private final static int LAKE_COST = 5;

    public enum Type {
        WALL, PASSAGE, ANSWER, SWAMP, LAKE
    }

    public int cellCost() {
        return switch (type) {
            case SWAMP -> SWAMP_COST;
            case LAKE -> LAKE_COST;
            default -> PASSAGE_COST;
        };
    }
}
