package juanolek.game;

import juanolek.Message;
import juanolek.exceptions.InvalidMoveException;
import juanolek.exceptions.TrashDataException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class GameLogic{

    int size;
    GamePawnType makingMove;
    GameTile[][] tiles;


    GameLogic(int size){
        this.size = size;
        this.tiles = new GameTile[size][size];
        for(int x = 0; x < size; x++){
            for(int y= 0; y < size; y++){
                tiles[x][y] = new GameTile();
            }
        }
        this.makingMove = GamePawnType.White;
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
            throw new InvalidMoveException("Cell not empty");
        }

        tiles[x][y].type = type;
        int breaths = getBreaths(x, y, type);
        System.out.println("New pawn has " + breaths + " breaths");
        if(breaths > 0){
            changes.add(GameBoardChange.add(x, y, type));
            makingMove = GamePawnType.other(makingMove);

            for(int xIndex = 0; xIndex < size; xIndex++)
                for(int yIndex = 0; yIndex < size; yIndex++){
                    if(tiles[xIndex][yIndex].type == GamePawnType.other(type) && getBreaths(xIndex, yIndex, tiles[xIndex][yIndex].type) <= 0){
                        tiles[xIndex][yIndex].isAlive = false;
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
        }
        else{
            tiles[x][y].type = GamePawnType.Empty;
            throw new InvalidMoveException("Brak oddechow dla pionka");
        }


        return changes;
    }


}