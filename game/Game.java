package game;

import java.util.LinkedList;

public class Game
{    
    final int NUM_INNINGS = 2;
    private int inning;
    boolean isTopOfInning;
    private Count count;
    private int homeScore;
    private int awayScore;
    private int outs;
    private LinkedList <Integer> runners;
    boolean possibleWalkoff;
    String message;
    public boolean isGameOver = false;
        
    public Game()
    {
        inning = 1;
        isTopOfInning = true;
        count = new Count();
        outs = 0;
        homeScore = 0;
        awayScore = 0;
        message = "Play Ball!";
        possibleWalkoff = false;
        runners = new LinkedList<Integer>();
    }
    
    public void callBall()
    {
        count.balls++;
        message = "Ball";      
        if (count.checkWalk())
        {
            message = "Walk";
            count.reset();
            advanceRunnersWalk();
        }
    }
    
    public void callStrike()
    {
        count.strikes++;
        message = "Strike";      
        if (count.checkStrikeout())
        {
            count.reset();
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
        checkRunnersScored();
    }
    
    private void checkRunnersScored()
    {
        int runsScored = 0;
        while (!runners.isEmpty() && runners.getLast() >= 4)
        {
            runsScored++;
            if (isTopOfInning) awayScore++; else homeScore++;
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
            message = "Switch sides";
            if (inning == NUM_INNINGS) possibleWalkoff = true;
        }
        else
        {
            inning++;
            isTopOfInning = true;
            message = (inning != NUM_INNINGS + 1 ? "Next inning" : "Extra Innings!");
        }
        runners.clear();
    }
    
    private void checkGameOver()
    {
        if (inning >= NUM_INNINGS)
        {
            if (isTopOfInning)
            {
                if (outs >= 3 && homeScore > awayScore)
                {
                    isGameOver = true;
                    message = "Home Team has won!";
                }
            }
            else
            {
                if (homeScore > awayScore)
                {
                    isGameOver = true;
                    if (possibleWalkoff)
                    {
                        message = "Walkoff " + message.split(",")[0] + ", Home Team has won!"; 
                    }
                }
                else
                {
                    if (!possibleWalkoff) possibleWalkoff = true;

                    if (outs >= 3 && homeScore < awayScore)
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
        System.out.println(((isTopOfInning ? "TOP " : "BOT ") + inning) + "   R  " + "|  " + second + "  |");
        System.out.println("AWAY\t" + awayScore + "  " + "|" + third + "   " + first + "|");
        System.out.println("HOME\t" + homeScore + "  |  O  |");
        System.out.println(count.getDisplayString() + ", " + outs + (outs != 1 ? " outs" : " out"));
        System.out.println(message);
    }

    public void displayFinal()
    {
        System.out.println(inning <= NUM_INNINGS ? "FINAL" : "FIN/" + inning);
        System.out.println("AWAY\t" + awayScore);
        System.out.println("HOME\t" + homeScore);
    }
}