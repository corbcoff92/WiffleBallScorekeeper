package wiffleballScorekeeper;

import wiffleballScorekeeper.game.Game;
import wiffleballScorekeeper.menu.MainMenu;
import wiffleballScorekeeper.menu.GameMenus;

public class WiffleballScorekeeper
{
    
    private static String promptTeamName(String defaultName)
    {
        String prompt = String.format("%s please enter a name that is 10 characters or less, or press enter to keep the default.", defaultName);
        String name = wiffleballScorekeeper.menu.Menu.getString(prompt, 10);
        if (name.strip().length() == 0) name = defaultName;
        return name.toUpperCase();
    }

    public void newGame()
    {
        String awayTeamName = promptTeamName("AWAY");
        String homeTeamName = promptTeamName("HOME");
        int numInnings = wiffleballScorekeeper.menu.Menu.getInt("How many innings should the game be?", 1, 9);
        Game game = new Game(numInnings, homeTeamName, awayTeamName);
        GameMenus gameMenus = new GameMenus(game);
        gameMenus.playGame();
    }

    private void begin()
    {
        MainMenu mainMenu = new MainMenu(this);
        mainMenu.run();
    }

    public static void main(String[] args) 
    {
        WiffleballScorekeeper wiffleballScorekeeper = new WiffleballScorekeeper();
        wiffleballScorekeeper.begin();
    }
}