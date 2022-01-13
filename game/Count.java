package game;

class Count
{
    public int balls;
    public int strikes;
    public Count()
    {
        reset();
    }
    public void reset()
    {
        balls = 0;
        strikes = 0;
    }

    public String getDisplayString()
    {
        return balls + "-" + strikes;
    }

    public boolean checkWalk()
    {
        return balls >= 4;
    }

    public boolean checkStrikeout()
    {
        return strikes >= 3;
    }
}