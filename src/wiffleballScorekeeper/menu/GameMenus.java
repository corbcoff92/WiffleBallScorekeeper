package wiffleballScorekeeper.menu;

import wiffleballScorekeeper.game.Game;

/**
 * This {@code GameMenu} class is a collection of {@link Menu}'s used to control an instance of the {@link Game} class. 
 * This class is not a menu itself, rather it contains sub-menus that are used to get and process the different types of 
 * actions that can occur during a game of wiffleball.
 */
public class GameMenus
{
    private Game game;
    private boolean quit = false;
    private PitchMenu pitchMenu = new PitchMenu();
    private HitMenu hitMenu = new HitMenu();
    private OutMenu outMenu = new OutMenu();
    private GameOverMenu gameOverMenu = new GameOverMenu();
    private Menu currentMenu = pitchMenu;

    /**
     * Creates an instance of the this {@code GameMenu} class that will be used to control 
     * the provided game based on the user's input. This class is not a menu itself, but 
     * each of its sub-menus are. These sub-menus are used to get and process the different
     * types of actions that can occur during a game of wiffleball.
     * @param game  Specific instance of {@link Game} that is to be controlled by this collection of menus.
     */
    public GameMenus(Game game)
    {
        this.game = game;
    }

    /**
     * Main loop used to get input from the user for this instance's {@link Game}. 
     * The loop is continued until either the game ends, or the user selects to quit.
     * If the game was played in full, then the final game display is shown, and the 
     * user is required to press enter so that the results can be seen before handing 
     * control back to the main app menu.
     */
    public void playGame()
    {
        // Continue until game ends or user selects to quit
        while (!quit || !game.isGameOver)
        {
            Menu.clearConsole();
            game.display();
            currentMenu.nextAction();
            
            // If the game was played in its entirety, 
            // display the final results
            if (game.isGameOver)
            {
                Menu.clearConsole();
                game.display();
                System.out.println("");
                game.displayFinal();
                gameOverMenu.nextAction();
            }
        }
        
    }

    /**
     * This menu is used to control the possible results that can occur during each pitch of a game of wiffleball.
     * The input is either processed, or a different sub-menu is displayed depending on the option that the user selects.
     * This class directly extends the abstract base {@link Menu} class.
     * @see Menu
     */
    private class PitchMenu extends Menu
    {
        /**
         * Initializes a {@link Menu} with the options that can occur during 
         * each pitch of a game of wiffleball. 
         * <ul>The options include:</ul>
         * <li>B - Ball</li> 
         * <li>S - Strike</li> 
         * <li>H - Hit</li> 
         * <li>O - Out</li> 
         * <li>Q - Quit</li> 
         */
        public PitchMenu()
        {
            super("B - Ball", "S - Strike", "H - Hit", "O - Out", "U - Undo", "Q - Quit");
        }
        
        /** 
         * Implements the abstract {@link Menu.processInput} function which is used to control this 
         * collection of {@code GameMenus}'s {@link Game} instance based on the user's choices. If 
         * the user selects to quit, they are asked if they are sure they really want to quit. This 
         * helps to prevent the accidental loss of data if quit is selected by accident.
         */
        @Override
        protected void processInput(char input)
        {
            switch (input)
            {
                case 'B':
                {
                    game.callBall();
                    break;
                }
                case 'S':
                {
                    game.callStrike();
                    break;
                }
                case 'H':
                {
                    currentMenu = hitMenu;
                    break;   
                }
                case 'O':
                {
                    currentMenu = outMenu;
                    break;
                }
                case 'U':
                {
                    game.loadLastState();
                    break;
                }
                case 'Q':
                {
                    quit = askYesNo("Are you sure you want to quit?");
                    break;
                }
            }
        }
    }
    
    /**
     * This menu is used to control the possible results that can occur when the play results 
     * in a hit during a game of wiffleball. The input is processed based on the option that the user selects.
     * This class directly extends the abstract base {@link Menu} class.
     * @see Menu
     */
    private class HitMenu extends Menu
    {
        /**
         * Initializes a {@link Menu} with the options that can occur 
         * when the result of a play is a hit during a game of wiffleball. 
         * <ul>The options include:</ul>
         * <li>1 - Single</li> 
         * <li>2 - Double</li> 
         * <li>3 - Triple</li> 
         * <li>4 - Homerun</li> 
         * <li>C - Cancel</li>
         * The cancel option allows the user to return to the main pitch menu without changing 
         * the state of the game. 
         */
        public HitMenu()
        {
            super("1 - Single", "2 - Double", "3 - Triple", "4 - Homerun", "C - Cancel");
        }
        
        /** 
         * Implements the abstract {@link Menu.processInput} function which is used to control this 
         * collection of {@code GameMenus}'s {@link Game} instance based on the user's choices. If 
         * the user selects to cancel, they are returned to the main pitch menu with the state of 
         * the game remaining unaltered. This helps to if the user selected the "Hit" option by accident.
         * When the input is processed, the main pitch menu is automatically activated.
         */
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
    
    /**
     * This menu is used to control the possible results that can occur when the play results 
     * in an out during a game of wiffleball. The input is processed based on the option that 
     * the user selects. This class directly extends the abstract base {@link Menu} class.
     * @see Menu
     */
    private class OutMenu extends Menu
    {
        /**
         * Initializes a {@link Menu} with the options that can occur when 
         * the result of a play is an out during a game of wiffleball. 
         * <ul>The options include:</ul>
         * <li>F - Flyout</li> 
         * <li>G - Groundout</li> 
         * <li>C - Cancel</li>
         * The cancel option allows the user to return to the main pitch menu without changing 
         * the state of the game. 
         */
        public OutMenu()
        {
            super("F - Flyout", "G - Groundout", "C - Cancel");
        }
        
        /** 
         * Implements the abstract {@link Menu.processInput} function which is used to control this 
         * collection of {@code GameMenus}'s {@link Game} instance based on the user's choices. If 
         * the user selects to cancel, they are returned to the main pitch menu with the state of 
         * the game remaining unaltered. This helps to if the user selected the "Out" option by accident.
         * When the input is processed, the main pitch menu is automatically activated.
         */
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
    
    /**
     * This menu is used at the end of a game to determine if the user would like to undo the last action, 
     * or continue to the main menu.The input is processed based on the option that the user selects. This 
     * class directly extends the abstract base {@link Menu} class.
     * @see Menu
     */
    private class GameOverMenu extends Menu
    {
        /**
         * Initializes a {@link Menu} with the options to undo the last action, 
         * or continue to the main menu. 
         */
        public GameOverMenu()
        {
            super("U - Undo last action", "C - Continue to main menu");
        }
        
        /** 
         * Implements the abstract {@link Menu.processInput} function which is used to control this 
         * collection of {@code GameMenus}'s {@link Game} instance based on the user's choices. If 
         * the user selects "Undo", the last game action is undone and they are returned to the 
         * main pitch menu to continue the game from the previous state. This helps to if the user 
         * made a mistake selecting the last action of the game.
         */
        protected void processInput(char input)
        {
            switch (input)
            {
                case 'U':
                    game.loadLastState();
                    currentMenu = pitchMenu;
                    break;
                case 'C':
                    quit = true;
                    break;
            }
        }
    }
}