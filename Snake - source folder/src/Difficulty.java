public enum Difficulty {
    EASY(100, 25),
    MEDIUM(75, 25),
    HARD(50, 20);

    public final int delay;
    public final int unitSize;

    Difficulty(int delay, int unitSize) {
        this.delay = delay;
        this.unitSize = unitSize;
    }
}
