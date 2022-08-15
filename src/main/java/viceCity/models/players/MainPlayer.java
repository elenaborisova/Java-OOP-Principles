package viceCity.models.players;

public class MainPlayer extends BasePlayer{
    public static final int INITIAL_POINTS = 1000;
    private static final String NAME = "Tommy Vercetti";

    public MainPlayer() {
        super(NAME, INITIAL_POINTS);
    }
}
