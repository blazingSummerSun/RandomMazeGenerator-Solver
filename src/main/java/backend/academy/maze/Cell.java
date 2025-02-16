package backend.academy.maze;

public record Cell(int row, int col, Type type) {
    private static final int SWAMP_COST = 5000;
    private static final int PASSAGE_COST = 50;
    private static final int LAKE_COST = 1000;
    private static final int COIN_COST = 0;

    public enum Type {
        WALL, PASSAGE, ANSWER, SWAMP, LAKE, COIN
    }

    public int cellCost() {
        return switch (type) {
            case SWAMP -> SWAMP_COST;
            case LAKE -> LAKE_COST;
            case COIN -> COIN_COST;
            default -> PASSAGE_COST;
        };
    }
}
