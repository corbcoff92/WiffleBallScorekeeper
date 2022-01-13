package game;

class Team 
{
    public String name;
    private int runs;
    private int hits;
    private int walks;
    
    public Team(String defaultName)
    {
        name = defaultName;
        runs = 0;
        hits = 0;
        walks = 0;
    }

    public void scoreRun()
    {
        runs++;
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
