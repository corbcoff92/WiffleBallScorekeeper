package wiffleballScorekeeper;

import wiffleballScorekeeper.game.Game;
import wiffleballScorekeeper.menu.GameMenu;
import wiffleballScorekeeper.menu.MainMenu;
import wiffleballScorekeeper.menu.Menu;

public class WiffleBallScorekeeper
{
    private Game game;
    private MainMenu mainMenu = new MainMenu(this);
    public boolean exit = false;
    
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
        int numInnings = Menu.getInt("How many innings should the game be?", 1, 9);
        game = new Game(numInnings, homeTeamName, awayTeamName);
        GameMenu gameMenu = new GameMenu(game);
        gameMenu.playGame();
    }

    public void run()
    {
        while (!exit)
        {
            Menu.clearConsole();   
            mainMenu.nextAction();
        }
    }

    public static void main(String[] args) 
    {
        WiffleBallScorekeeper wiffleBallScorekeeper = new WiffleBallScorekeeper();
        wiffleBallScorekeeper.run();
    }
}