package wiffleballScorekeeper.game;

import java.util.LinkedList;


public class Game
{   
    public boolean isGameOver = false;
    private boolean isTopOfInning;
    private String message;
    private int NUM_INNINGS; 
    private int inning;
    private Count count;
    private Team homeTeam;
    private Team awayTeam;
    private Team battingTeam;
    private Team pitchingTeam;
    private int outs;
    private LinkedList <Integer> runners;
    private int namePadding;
    
    public Game(int numInnings, String homeTeamName, String awayTeamName)
    {
        NUM_INNINGS = numInnings;
        inning = 1;
        isTopOfInning = true;
        count = new Count();
        outs = 0;
        awayTeam = new Team(awayTeamName);
        homeTeam = new Team(homeTeamName);
        battingTeam = awayTeam;
        pitchingTeam = homeTeam;
        message = "Play Ball!";
        runners = new LinkedList<Integer>();
        namePadding = Math.max(6,Math.max(awayTeam.name.length(), homeTeam.name.length()));
        battingTeam.newInning();
    }
    
    public void callBall()
    {
        count.balls++;
        message = "Ball";      
        if (count.checkWalk())
        {
            message = "Walk";
            advanceRunnersWalk();
        }
    }
    
    public void callStrike()
    {
        count.strikes++;
        message = "Strike";      
        if (count.checkStrikeout())
        {
            message = "Struckout";
            outMade();
        }
    }
    
    public void advanceRunnersHit(int numBases)
    {
        switch (numBases)
        {
            case 1:
            message = "Single";
                break;
                case 2:
                message = "Double";
                break;
                case 3:
                message = "Triple";
                break;
                case 4:
                message = (runners.size() != 3 ? "Homerun" : "Grand Slam");
                break;
            }
            battingTeam.hits++;
        for (int i = 0; i < runners.size(); i++)
        {
            runners.set(i, runners.get(i) +numBases);
        }
        runners.addFirst(numBases);
        checkRunnersScored();
        count.reset();
    }
    
    private void advanceRunnersWalk()
    {
        int i = 0;
        while (i < runners.size() && runners.get(i) == i+1)
        {
            runners.set(i, i+2);
            i++;
        }
        runners.addFirst(1);
        pitchingTeam.walks++;
        checkRunnersScored();
        count.reset();
    }
    
    private void checkRunnersScored()
    {
        int runsScored = 0;
        while (!runners.isEmpty() && runners.getLast() >= 4)
        {
            runsScored++;
            battingTeam.scoreRun();
            runners.removeLast();
        }
        if (runsScored > 0)
        {
            message += (", " + runsScored + (runsScored != 1 ? " runs" : " run") + " scored!");
        }
        checkGameOver();
    }
    
    public void flyOut()
    {
        message = "Flyout";
        outMade();
    }
    
    public void groundOut()
    {
        message = "Groundout";
        outMade();
    }
    
    private void outMade()
    {
        outs++;
        count.reset();
        checkGameOver();
        if (outs >=3 && !isGameOver)
        {
            nextHalfInning();
        }
    }
    
    private void nextHalfInning()
    {
        outs = 0;
        if (isTopOfInning)
        {
            isTopOfInning = false;
            battingTeam = homeTeam;
            pitchingTeam = awayTeam;
            message = "Switch sides";
        }
        else
        {
            isTopOfInning = true;
            inning++;
            battingTeam = awayTeam;
            pitchingTeam = homeTeam;
            message = (inning != NUM_INNINGS + 1 ? "Next inning" : "Extra Innings!");
        }
        runners.clear();
        battingTeam.newInning();
    }
    
    private void checkGameOver()
    {
        if (inning >= NUM_INNINGS)
        {
            if (isTopOfInning)
            {
                if (outs >= 3 && homeTeam.getRuns() > awayTeam.getRuns())
                {
                    isGameOver = true;
                    message = "Home Team has won!";
                }
            }
            else
            {
                if (homeTeam.getRuns() > awayTeam.getRuns())
                {
                    isGameOver = true;
                    message = "Walkoff " + message.split(",")[0] + ", Home Team has won!"; 
                }
                else
                {
                    if (outs >= 3 && homeTeam.getRuns() < awayTeam.getRuns())
                    {
                        isGameOver = true;
                        message = "Away Team has won!"; 
                    }
                }
            }
        } 
    }
    
    public void display()
    {
        char first = (runners.contains(1) ? 'X' : 'O');
        char second = (runners.contains(2) ? 'X' : 'O');
        char third = (runners.contains(3) ? 'X' : 'O');
        
        System.out.println(String.format("%d inning game", NUM_INNINGS));
        System.out.println(String.format("%-" + namePadding + "s   R  H  W  |  %c  |", (isTopOfInning ? "TOP " : "BOT ") + inning, second));
        System.out.println(String.format("%-" +  namePadding + "s   %-2d %-2d %-2d |%c   %c|", awayTeam.name, awayTeam.getRuns(), awayTeam.hits, awayTeam.walks, third, first));
        System.out.println(String.format("%-" +  namePadding + "s   %-2d %-2d %-2d |  O  |", homeTeam.name, homeTeam.getRuns(), homeTeam.hits, homeTeam.walks, third, first));
        System.out.println(count.getDisplayString() + ", " + outs + (outs != 1 ? " outs" : " out"));
        System.out.println(message);
    } 

    public void displayFinal()
    {
        String headingText = (inning <= NUM_INNINGS ? "FINAL" : "FIN/" + inning);
        headingText = String.format("%-" + namePadding + "s  | ", headingText);
        for (int i = 1; i <= awayTeam.getRunserPerInning().size(); i++)
        {
            headingText += (String.format("%-2d ",i));
        }
        headingText += "|R  H  W";
        
        String spacingString = "";
        for (int i = 0; i < headingText.length(); i++) 
        {
            spacingString += '-';
        }
        
        String awayString = String.format("%-" + namePadding + "s  | ", awayTeam.name);
        String homeString = String.format("%-" + namePadding + "s  | ", homeTeam.name);
        for (int i=0; i < awayTeam.getRunserPerInning().size(); i++)
        {
            awayString += String.format("%-2d ",awayTeam.getRunserPerInning().get(i));
            if (i < homeTeam.getRunserPerInning().size())
            {
                homeString += String.format("%-2d ",homeTeam.getRunserPerInning().get(i));
            }
            else
            {
                homeString += "-  ";
            }
        }
        awayString += String.format("|%-2d %-2d %-2d", awayTeam.getRuns(), awayTeam.hits, awayTeam.walks);
        homeString += String.format("|%-2d %-2d %-2d", homeTeam.getRuns(), homeTeam.hits, homeTeam.walks);
        
        System.out.println(headingText);
        System.out.println(spacingString);
        System.out.println(awayString);
        System.out.println(homeString);
        System.out.println(spacingString);
        System.out.println("");
    }
}