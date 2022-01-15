package wiffleballScorekeeper.menu;

import wiffleballScorekeeper.WiffleballScorekeeper;

/**
 * This {@code MainMenu} class is used to control an instance of the main {@link WiffleballScorekeeper} class.
 * It inherits from the abstract base {@link Menu} class, and is used to get & process input it from the user.
 * @see Menu  
 */
public class MainMenu extends Menu
{
    private WiffleballScorekeeper scorekeeperApp;
    private boolean exit = false;
    
    /**
     * Initializes an instance that will be used to control the given {@link WiffleballScorekeeper} instance.
     * The parent constructor from {@link Menu} is used to create the options to start a new game or quit.
     * @param scorekeeperApp
     */
    public MainMenu(WiffleballScorekeeper scorekeeperApp)
    {
        super("N - New Game", "Q - Quit");
        this.scorekeeperApp = scorekeeperApp;
    }

    /**
     * Overides the base {@link Menu} class {@link Menu.display} method to include the title of this menu.
     */
    @Override
    protected void display()
    {
        System.out.println("Main Menu");
        super.display();
    }
    
    /**
     * Implements the abstract {@link Menu.processInput} function which is used to control this 
     * {@code MainMenu}'s {@link WiffleballScorekeeper} instance based on the user's choices.
     */
    @Override
    protected void processInput(char input)
    {
        switch (input)
        {
            case 'N':
                scorekeeperApp.newGame();
                break;
            case 'Q':
                exit = true;
                break;
        }
    }

    /**
     * Main loop used to get input from the user. Displays the menu, and then gets & processes the user's 
     * input using the overriden {@link processInput} method. The loop is continued until the user selects quit.
     * This loop clears the console before each prompt, creating the effect that the menu is stationary and rather 
     * than scrolling.     
     */
    public void run()
    {
        // Continue until the user chooses to exit
        while (!exit)
        {
            clearConsole();
            nextAction();
        }
    }    
}