package juanolek.game;

import juanolek.exceptions.InvalidMoveException;
import juanolek.exceptions.TrashDataException;
import java.util.ArrayList;
import java.util.List;

class GameLogic{

    private final int size;
    private GamePawnType makingMove;
    private final GameTile[][] tiles;
    private int lastWhiteX = -1;
    private int lastWhiteY = -1;
    private int lastBlackX = -1;
    private int lastBlackY = -1;
    private int whitePoints = 0;
    private int blackPoints = 0;
    private boolean lastMovePassed = false;
    private final IEndGameHandler endGameHandler;

    GameLogic(IEndGameHandler endGameHandler, int size){
        this.endGameHandler = endGameHandler;
        this.size = size;
        this.tiles = new GameTile[size][size];
        for(int x = 0; x < size; x++){
            for(int y= 0; y < size; y++){
                tiles[x][y] = new GameTile();
            }
        }
        this.makingMove = GamePawnType.White;
    }

    public int getWhitePoints() {
        return whitePoints;
    }

    public int getBlackPoints() {
        return blackPoints;
    }

    private int getBreaths(int x, int y, GamePawnType type){
        return getBreaths(x, y, type, new ArrayList<>());
    }
    private int getBreaths(int x, int y, GamePawnType chainType, List<GameTile> visited){
        if(x < 0 || x >= size || y < 0 || y >= size)
            return 0;
        if(visited.contains(tiles[x][y]))
            return 0;
        visited.add(tiles[x][y]);

        GamePawnType type = tiles[x][y].type;
        if(type == GamePawnType.Empty){
            return 1;
        }
        else if(type == chainType){
            return getBreaths(x+1, y, chainType, visited) +
                    getBreaths(x-1, y, chainType, visited) +
                    getBreaths(x, y + 1, chainType, visited) +
                    getBreaths(x, y -1, chainType, visited);
        }
        else{
            return 0;
        }
    }

    synchronized ArrayList<GameBoardChange> setPawn(int x, int y, GamePawnType type) throws InvalidMoveException, TrashDataException {
        ArrayList<GameBoardChange> changes = new ArrayList<>();

        if(type == GamePawnType.Empty){
            //trash data warning
            throw new TrashDataException("Type must be either Black or White");
        }
        if(type != makingMove){
            throw new InvalidMoveException("The opponent must move first");
        }
        if(x < 0 || x >= size || y < 0 || y >= size){
            throw new InvalidMoveException("Invalid coordinates");
        }
        if(tiles[x][y].type != GamePawnType.Empty){
            throw new InvalidMoveException("Can't place pawn on a non-empty tile");
        }

        tiles[x][y].type = type;
        int breaths = getBreaths(x, y, type);
        boolean killsOpponent = false;
        for(int xIndex = 0; xIndex < size; xIndex++)
            for(int yIndex = 0; yIndex < size; yIndex++){
                if(tiles[xIndex][yIndex].type == GamePawnType.other(type) && getBreaths(xIndex, yIndex, tiles[xIndex][yIndex].type) <= 0){
                    killsOpponent = true;
                    break;
                }
            }
        tiles[x][y].type = GamePawnType.Empty;
        if(breaths <= 0 && !killsOpponent) {
            throw new InvalidMoveException("Can't place pawn on a tile with no breaths");
        }
        if(type == GamePawnType.White && x == lastWhiteX && y == lastWhiteY ||
                type == GamePawnType.Black && x == lastBlackX && y == lastBlackY){
            throw new InvalidMoveException("Can't place pawn here because of ko rule");
        }

        tiles[x][y].type = type;
        if(type == GamePawnType.White){
            lastWhiteX = x;
            lastWhiteY = y;
        }
        else{
            lastBlackX = x;
            lastBlackY = y;
        }

        changes.add(GameBoardChange.add(x, y, type));
        makingMove = GamePawnType.other(makingMove);
        lastMovePassed = false;

        for(int xIndex = 0; xIndex < size; xIndex++)
            for(int yIndex = 0; yIndex < size; yIndex++){
                if(tiles[xIndex][yIndex].type == GamePawnType.other(type) && getBreaths(xIndex, yIndex, tiles[xIndex][yIndex].type) <= 0){
                    tiles[xIndex][yIndex].isAlive = false;
                    //dodanie punktów za zbicie pionka
                    if(type == GamePawnType.White) whitePoints++;
                    else blackPoints++;
                    changes.add(GameBoardChange.delete(xIndex, yIndex));
                }
            }

        for(int xIndex = 0; xIndex < size; xIndex++)
            for(int yIndex = 0; yIndex < size; yIndex++){
                if(!tiles[xIndex][yIndex].isAlive){
                    tiles[xIndex][yIndex].type = GamePawnType.Empty;
                    tiles[xIndex][yIndex].isAlive = true;
                }
            }
        return changes;
    }

    //active when a player attempts to pass
    public boolean pass(GamePawnType type) throws InvalidMoveException{
        if(type == makingMove){
            if(lastMovePassed){
                endGame();
                return false;
            }
            else{
                makingMove = GamePawnType.other(makingMove);
                lastMovePassed = true;
                return true;
            }
        }
        else{
            throw new InvalidMoveException("You can't pass when it's not you turn");
        }
    }

    //check if a tile counts area of "type"
    private boolean checkFinalTileType(int x, int y, ArrayList<GameTile> visited, GamePawnType type){
        if(x < 0 || x >= size || y < 0 || y >= size)
            return true;

        if(visited.contains(tiles[x][y])){
            return true;
        }
        visited.add(tiles[x][y]);
        if(tiles[x][y].type == GamePawnType.other(type)){
            return false;
        }
        else if(tiles[x][y].type == type){
            return true;
        }
        else{
            return checkFinalTileType(x+1, y, visited, type) &&
                    checkFinalTileType(x-1, y, visited, type) &&
                    checkFinalTileType(x, y+1, visited, type) &&
                    checkFinalTileType(x, y-1, visited, type);
        }
    }

    //checks if a tile in the endgame counts as a white, black or no-mans area
    private GamePawnType assignFinalTileType(int x, int y){
        if(tiles[x][y].type != GamePawnType.Empty){
            return GamePawnType.Empty;
        }
        if(checkFinalTileType(x, y, new ArrayList<>(), GamePawnType.White)){
            return GamePawnType.White;
        }
        if(checkFinalTileType(x, y, new ArrayList<>(), GamePawnType.Black)){
            return GamePawnType.Black;
        }
        return GamePawnType.Empty;
    }

    //called on game end
    private void endGame(){
        System.out.println("Game has ended");

        ArrayList<GameBoardChange> boardChanges = new ArrayList<>();
        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++) {
                if(tiles[x][y].type != GamePawnType.Empty && getBreaths(x, y, GamePawnType.White) < 2 && getBreaths(x, y, GamePawnType.Black) < 2){
                    boardChanges.add(GameBoardChange.delete(x, y));
                    tiles[x][y].isAlive = false;
                }
            }

        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++) {
                if (!tiles[x][y].isAlive) {
                    tiles[x][y].type = GamePawnType.Empty;
                }
            }

        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++){

                GamePawnType finalType = assignFinalTileType(x, y);
                if(finalType == GamePawnType.White){
                    whitePoints++;
                }
                else if(finalType == GamePawnType.Black){
                    blackPoints++;
                }
            }

        endGameHandler.handleEndGame(boardChanges);
    }
}