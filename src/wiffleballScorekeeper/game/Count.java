package wiffleballScorekeeper.game;

/**
 * The Count class is used to represent a count in wiffleball. 
 * Mainly used in the main {@link Game} class.
 */
public class Count
{
    /**
     * The number of balls of this count.
     */
    public int balls;
    /**
     * The number of strikes of this count.
     */
    public int strikes;
    
    /**
     * Initializes a blank count.
     */
    public Count()
    {
        reset();
    }

    /**
     * Resets the balls and strikes of this count to zero.
     */
    public void reset()
    {
        balls = 0;
        strikes = 0;
    }

    /**
     * Produces a formatted string representing this count, that can then be printed to the screen.
     * @return The formatted display string representing this count, which can be printed to the screen.
     */
    public String getDisplayString()
    {
        return balls + "-" + strikes;
    }

    /**
     * Determines whether or not this count should result in a walk. 
     * The count does not reset itself when a walk is determined, thus 
     * the controlling class should call the {@link reset} method when 
     * the count should be reset.
     * @return  Boolean indicating whether this count should result in a walk.
     * @see     reset
     */
    public boolean checkWalk()
    {
        return balls >= 4;
    }
    
    /**
     * Determines whether or not this count should result in a strikeout. 
     * The count does not reset itself when a strikeout is determined, thus 
     * the controlling class should call the {@link reset} method when 
     * the count should be reset.
     * @return  Boolean indicating whether this count should result in a strikeout.
     * @see     reset
     */
    public boolean checkStrikeout()
    {
        return strikes >= 3;
    }
}