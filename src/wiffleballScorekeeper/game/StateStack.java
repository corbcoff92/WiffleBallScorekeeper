package wiffleballScorekeeper.game;

import java.util.LinkedList;

class StateStack 
{
    private LinkedList<GameState> gameStateStack = new LinkedList<GameState>();
    private Game game;
    final int STACK_SIZE = 10;

    public StateStack(Game game)
    {
        this.game = game;
    }

    private class GameState
    {
        boolean isGameOver;
        boolean isTopOfInning;
        String message;
        int inning;
        Count count;
        Team homeTeam;
        Team awayTeam;
        Team battingTeam;
        Team pitchingTeam;
        int outs;
        LinkedList <Integer> runners;
        
        private GameState(final Game game)
        {
            this.isGameOver = game.isGameOver;
            this.isTopOfInning = game.isTopOfInning;
            this.message = game.message;
            this.inning = game.inning;
            this.count = new Count(game.count);
            this.battingTeam = new Team(game.battingTeam);
            this.pitchingTeam = new Team(game.pitchingTeam);
            if (game.homeTeam == game.battingTeam)
            {
                this.homeTeam = this.battingTeam;
                this.awayTeam = this.pitchingTeam;
            }
            else
            {
                this.homeTeam = this.pitchingTeam;
                this.awayTeam = this.battingTeam;
            }
            this.outs = game.outs;
            this.runners= new LinkedList<Integer>(game.runners);
        }
    }
    
    public void logState()
    {
        if (gameStateStack.size() >= STACK_SIZE)
        {
            gameStateStack.removeLast();
        }
        gameStateStack.push(new GameState(game));
    }

    public void loadLastState()
    {
        if (!gameStateStack.isEmpty())
        {
            GameState state = gameStateStack.pop();
            game.isGameOver = state.isGameOver;        
            game.isTopOfInning = state.isTopOfInning;
            game.message = "UNDO: " + state.message;
            game.inning = state.inning;
            game.count = state.count;
            game.awayTeam = state.awayTeam;
            game.homeTeam = state.homeTeam;
            game.battingTeam = state.battingTeam;
            game.pitchingTeam = state.pitchingTeam;
            game.outs = state.outs;
            game.runners= state.runners;
        }
        else
        {
            game.message = "Cannot undo further...";
        }
    }
}