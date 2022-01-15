package wiffleballScorekeeper;

import wiffleballScorekeeper.game.Game;
import wiffleballScorekeeper.menu.MainMenu;
import wiffleballScorekeeper.menu.GameMenus;

/**
 * Main class for the Wiffleball Scorekeeper app. It is used to create a new instance 
 * of the {@link Game} class, and also to create the menus that will be used to control it.
 * This class contains the {@link main} method used to start the program. This class is controlled
 * by an instance of the {@link MainMenu} class.
 */
public class WiffleballScorekeeper
{
    /**
     * Prompt's the user for the name of a wiffleball team, and returns its capitalized version. The name can only be a maximum of 10 characters long. 
     * The user must input a valid value before the program will continue. If the user enters a blank name, then the capitalized version of the given 
     * default name will be returned.
     * @param defaultName   Name to be used if the user enters a blank name.
     * @return              Capitalized version of the valid answer that the user provided.
     */
    private static String promptTeamName(String defaultName)
    {
        String prompt = String.format("%s please enter a name that is 10 characters or less, or press enter to keep the default.", defaultName);
        String name = wiffleballScorekeeper.menu.Menu.getString(prompt, 10);
        if (name.strip().length() == 0) name = defaultName;
        return name.toUpperCase();
    }

    /**
     * Implementation of a new game of wiffleball being started. The user is prompted for two team names, 
     * as well as the desired length of the game. A new instance of {@link Game} is created and control is 
     * handed to the game menus for the game to be played.
     */
    public void newGame()
    {
        String awayTeamName = promptTeamName("AWAY");
        String homeTeamName = promptTeamName("HOME");
        int numInnings = wiffleballScorekeeper.menu.Menu.getInt("How many innings should the game be?", 1, 9);
        Game game = new Game(numInnings, homeTeamName, awayTeamName);
        GameMenus gameMenus = new GameMenus(game);
        gameMenus.playGame();
    }
    
    /**
     * Implementation of the Wiffleball Scorekeeper app being started. A new instance of {@code WiffleballScorekeeper} 
     * is created and control is handed to the {@link MainMenu}.
     * @see MainMenu.run
     */
    static private void begin()
    {
        WiffleballScorekeeper wiffleballScorekeeper = new WiffleballScorekeeper();
        MainMenu mainMenu = new MainMenu(wiffleballScorekeeper);
        mainMenu.run();
    }

    /**
     * Entry point of the Wiffleball Scorekeeper app. This method is called as soon as the program is run, 
     * and the Wiffleball Scorekeeper app is begun.
     * @param args  Command line arguments provided by the user. 
     *              There are currently no command line options that are processed at this time.
     */
    public static void main(String[] args) 
    {
        WiffleballScorekeeper.begin();
    }
}