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
        
    public void newGame()
    {
        game = new Game();
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