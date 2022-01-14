package wiffleballScorekeeper.menu;

import wiffleballScorekeeper.game.Game;

public class GameMenu
{
    private Game game;
    private boolean quit = false;
    private PitchMenu pitchMenu = new PitchMenu();
    private HitMenu hitMenu = new HitMenu();
    private OutMenu outMenu = new OutMenu();
    private Menu currentMenu = pitchMenu;

    public GameMenu(Game game)
    {
        this.game = game;
    }

    private class PitchMenu extends Menu
    {
        public PitchMenu()
        {
            super("B - Ball", "S - Strike", "H - Hit", "O - Out", "Q - Quit");
        }
        
        protected void processInput(char input)
        {
            switch (input)
            {
                case 'B':
                    game.callBall();
                    break;
                case 'S':
                    game.callStrike();
                    break;
                case 'H':
                    currentMenu = hitMenu;
                    break;   
                case 'O':
                    currentMenu = outMenu;
                    break;
                case 'Q':
                    quit = askYesNo("Are you sure you want to quit?");
                    break;
            }
        }
    }

    private class HitMenu extends Menu
    {
        public HitMenu()
        {
            super("1 - Single", "2 - Double", "3 - Triple", "4 - Homerun", "C - Cancel");
        }

        protected void processInput(char input)
        {
            switch (input)
            {
                case '1':
                    game.advanceRunnersHit(1);
                    break;
                case '2':
                    game.advanceRunnersHit(2);
                    break;
                case '3':
                    game.advanceRunnersHit(3);
                    break;
                case '4':
                    game.advanceRunnersHit(4);    
                    break;
            }
            currentMenu = pitchMenu;
        }
    }

    private class OutMenu extends Menu
    {
        public OutMenu()
        {
            super("F - Flyout", "G - Groundout", "C - Cancel");
        }

        protected void processInput(char input)
        {
            switch (input)
            {
                case 'F':
                    game.flyOut();
                    break;
                case 'G':
                    game.groundOut();
                    break;
                case 'C':
                    break;
            }
            currentMenu = pitchMenu;
        }
    }
    
    public void playGame()
    {
        while (!quit && !game.isGameOver)
        {
            Menu.clearConsole();
            game.display();
            currentMenu.nextAction();
        }
        if (game.isGameOver)
        {
            Menu.clearConsole();
            game.display();
            System.out.println("");
            game.displayFinal();
            System.out.println("Game Over");
            Menu.waitForEnter();
        }
    }
}