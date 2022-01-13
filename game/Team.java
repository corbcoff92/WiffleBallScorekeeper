package game;

import java.util.LinkedList;

class Team 
{
    public String name;
    private int runs;
    private int hits;
    private int walks;
    public LinkedList<Integer> inningsRunsScored;
    
    public Team(String defaultName)
    {
        name = defaultName;
        runs = 0;
        hits = 0;
        walks = 0;
        inningsRunsScored = new LinkedList<Integer>();
    }

    public void scoreRun()
    {
        runs++;
        int inningsRuns = inningsRunsScored.removeLast();
        inningsRuns++;
        inningsRunsScored.add(inningsRuns);
    }

    public int getRuns()
    {
        return runs;
    }

    public void hit()
    {
        hits++;
    }

    public int getHits()
    {
        return hits;
    }

    public void walkBatter()
    {
        walks++;        
    }

    public int getWalks()
    {
        return walks;
    }
}
