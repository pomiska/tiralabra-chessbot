/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.model;

import java.util.ArrayList;
import java.util.Arrays;
import chess.engine.Engine;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import org.json.JSONObject;

public class GameState {
    public String id;
    
    public String playingBlack;
    public String playingWhite;
    
    public ArrayList<String> moves;
    
    public Engine engine = new Engine();
    
    public GameState() {
        
    }
    
    /**
     * Parses a full game state from a gameFull JSON object
     * <b>Note:</b> Use only to gain the initial game state,
     * game state should be updated via updateFromJson()
     * @param json
     * @return A full, initial game state
     */
    public static GameState parseFromJson(String json) {
        GameState gameState = new GameState();
        
        JSONObject jsonGameState = new JSONObject(json);
        
        if (jsonGameState.getString("type").equals("gameFull")) {
            gameState.id = jsonGameState.getString("id");
            
            gameState.playingWhite = jsonGameState
                    .getJSONObject("white").getString("id");
            gameState.playingBlack = jsonGameState
                    .getJSONObject("black").getString("id");
            
            String[] moves = jsonGameState
                    .getJSONObject("state").getString("moves").split(" ");
            
            gameState.moves = new ArrayList<>(Arrays.asList(moves));
        }
        
        return gameState;
    }
    
    /**
     * Update a GameState object from JSON
     * @param json 
     */
    public void updateFromJson(String json) {
        JSONObject jsonGameState = new JSONObject(json);
        
        if (jsonGameState.getString("type").equals("gameFull")) {
            this.id = jsonGameState.getString("id");
            
            this.playingWhite = jsonGameState
                    .getJSONObject("white").getString("id");
            this.playingBlack = jsonGameState
                    .getJSONObject("black").getString("id");
            
            String[] moves = jsonGameState
                    .getJSONObject("state").getString("moves").split(" ");
            
            this.moves = new ArrayList<>(Arrays.asList(moves));
        } else if (jsonGameState.getString("type").equals("gameState")) {
            String[] moves = jsonGameState.getString("moves").split(" ");
            
            this.moves = new ArrayList<>(Arrays.asList(moves));
        } else {
            // This would only have chat stuff, we probably don't need it.
        }
        parseLatestMove();
    }
    
    private void parseLatestMove(){
        if(!this.moves.isEmpty()){
           String startingString = this.moves.get(this.moves.size() - 1).substring(0, 2).toUpperCase();
           String endingString = this.moves.get(this.moves.size() - 1).substring(2, 4).toUpperCase();
           Move latestMove = new Move(
                 Square.fromValue(startingString),
                   Square.valueOf(endingString));
         this.engine.getBoard().doMove(latestMove);
        }
    }
}
