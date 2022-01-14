package wiffleballScorekeeper.menu;

import wiffleballScorekeeper.WiffleBallScorekeeper;

public class MainMenu extends Menu
{
    private WiffleBallScorekeeper scorekeeperApp;
    
    public MainMenu(WiffleBallScorekeeper scorekeeperApp)
    {
        super("N - New Game", "Q - Quit");
        this.scorekeeperApp = scorekeeperApp;
    }

    protected void display()
    {
        System.out.println("Main Menu");
        super.display();
    }

    protected void processInput(char input)
    {
        switch (input)
        {
            case 'N':
                scorekeeperApp.newGame();
                break;
            case 'Q':
                scorekeeperApp.exit = true;
                break;
        }
    }    
}