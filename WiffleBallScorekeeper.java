
import game.Game;
import menu.GameMenu;
import menu.Menu;

final class WiffleBallScoreKeeper
{
    private class MainMenu extends Menu
    {
        public MainMenu()
        {
            super("N - New Game", "Q - Quit");
        }

        protected void processInput(char input)
        {
            switch (input)
            {
                case 'N':
                    newGame();
                    break;
                case 'Q':
                    exit =true;
                    break;
            }
        }
    }
    private Game game;
    private MainMenu mainMenu = new MainMenu();
    private boolean exit = false;
        
    private void newGame()
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
        WiffleBallScoreKeeper wiffleBallScorekeeper = new WiffleBallScoreKeeper();
        wiffleBallScorekeeper.run();
    }
}