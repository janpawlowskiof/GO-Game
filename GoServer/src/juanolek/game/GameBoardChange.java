package juanolek.game;

public class GameBoardChange {

    private final ChangeType changeType;
    private final GamePawnType pawnType;
    private final int x;
    private final int y;

    public GamePawnType getPawnType() {
        return pawnType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ChangeType getChangeType(){
        return changeType;
    }

    public static GameBoardChange delete(int x, int y){
        System.out.println("Deleting change " + x + " " + y);
        return new GameBoardChange(x, y);
    }

    public static GameBoardChange add(int x, int y, GamePawnType pawnType){
        return new GameBoardChange(x, y, pawnType);
    }

    private GameBoardChange(int x, int y){
        this.x = x;
        this.y = y;
        this.changeType = ChangeType.Delete;
        this.pawnType = null;
    }

    private GameBoardChange(int x, int y, GamePawnType pawnType){
        this.x = x;
        this.y = y;
        this.changeType = ChangeType.Add;
        this.pawnType = pawnType;
    }

    public enum ChangeType{
        Add,
        Delete
    }
}
