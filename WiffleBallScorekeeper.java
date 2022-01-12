import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

class WiffleBallScoreKeeper
{
    class Menu
    {
        private Scanner inputScanner = new Scanner(System.in);
        interface MenuedApplication 
        {
            public char getInput();
            public void processInput(char input);
        }

        LinkedHashMap<Character, String> options = new LinkedHashMap<Character, String>();
        public Menu(String ... optionStrings)
        {
            char key;
            for (String str : optionStrings)
            {
                key = str.toUpperCase().charAt(0);
                options.put(key, str);
            }
        }

        private void display()
        {
            for (String option : options.values())
            {
                System.out.println(option);
            }
        }
        
        public char getInput()
        {
            display();
            char input;
            boolean isValidInput;
            do
            {
                System.out.print("> ");
                input = inputScanner.nextLine().toUpperCase().charAt(0);
                isValidInput = (options.containsKey(input) ? true : false);
                if (!isValidInput) System.out.println("Invalid entry, please try again...");
            } while (!isValidInput);
            return input;
        }
    }

    public class Game
    {
        private class Count
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

        final int NUM_INNINGS = 2;
        private int inning;
        boolean isTopOfInning;
        private Count count;
        private int homeScore;
        private int awayScore;
        private int outs;
        private LinkedList<Integer> runners;
        boolean possibleWalkoff;
        String message;
        private boolean isGameOver = false;
        private Menu pitchMenu = new Menu("B - Ball", "S - Strike", "H - Hit", "O - Out", "Q - Quit");
        private Menu hitMenu = new Menu("1 - Single", "2 - Double", "3 - Triple", "4 - Homerun", "C - Cancel");
        private Menu outMenu = new Menu("F - Flyout", "G - Groundout", "C - Cancel");
        private Menu currentMenu = pitchMenu;
        
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
            if (count.checkStrikeout())
            {
                count.reset();
                message = "Struckout";
                outMade();
            }
        }

        private void advanceRunnersHit(int numBases)
        {
            currentMenu = pitchMenu;
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

        private void outMade()
        {
            outs++;
            currentMenu = pitchMenu;
            count.reset();
            if (outs >=3)
            {
                nextHalfInning();
            }
        }
        
        private void nextHalfInning()
        {
            if (isTopOfInning)
            {
                isTopOfInning = false;
                outs = 0;
                checkGameOver();
                message = "Switch sides";
                if (inning == NUM_INNINGS)
                {
                    if (homeScore <= awayScore) possibleWalkoff = true;
                }
            }
            else
            {
                checkGameOver();
                inning++;
                isTopOfInning = true;
                outs = 0;
                message = (inning != NUM_INNINGS + 1 ? "Next inning" : "Extra Innings!");
            }
            runners.clear();
        }
        
        private void checkGameOver()
        {
            if (inning >= NUM_INNINGS)
            {
                if (!isTopOfInning)
                {
                    if (homeScore > awayScore)
                    {
                        isGameOver = true;
                        if (possibleWalkoff)
                        {
                            message = "Walkoff " + message.split(",")[0] + ", Home Team has won!"; 
                        }
                        else
                        {
                            message = "Home Team has won!";
                        }
                    }
                    else if (homeScore <= awayScore)
                    {                        
                        if (outs >= 3 && homeScore < awayScore)
                        {
                            isGameOver = true;
                            message = "Away Team has won!"; 
                        }
                    } 
                }
                if (isGameOver) display();
            }
        }

        private void processInput(char input)
        {
            switch (input)
            {
                case 'B':
                    message = "Ball";
                    callBall();
                    break;
                case 'S':
                    message = "Strike";
                    callStrike();
                    break;
                case 'H':
                    currentMenu = hitMenu;
                    break;
                case '1':
                    message = "Single";
                    advanceRunnersHit(1);
                    break;
                case '2':
                    message = "Double";
                    advanceRunnersHit(2);
                    break;
                case '3':
                    message = "Triple";
                    advanceRunnersHit(3);
                    break;
                case '4':
                    message = (runners.size() != 3 ? "Homerun" : "Grand Slam");
                    advanceRunnersHit(4);    
                    break;
                case 'O':
                    currentMenu = outMenu;
                    break;
                case 'F':
                    message = "Flyout";
                    outMade();
                    break;
                case 'G':
                    message = "Groundout";
                    outMade();
                    break;
                case 'C':
                    currentMenu = pitchMenu;
                    break;
                case 'Q':
                    isGameOver = true;
                    break;
                default:
                    System.out.println("Invalid input, please try again...");
            }
        }
        
        public void play()
        {   
            char input;
            while (!isGameOver)
            {
                display();
                input = currentMenu.getInput();
                processInput(input);
            }
        }
                
        public void display()
        {
            System.out.println("\033[H\033[2J");
            System.out.flush();
            char first = (runners.contains(1) ? 'X' : 'O');
            char second = (runners.contains(2) ? 'X' : 'O');
            char third = (runners.contains(3) ? 'X' : 'O');
            String inningText = (isTopOfInning ? "TOP " : "BOT ") + inning;
            System.out.println((!isGameOver ? inningText : "FINAL") + "   R  " + "|  " + second + "  |");
            System.out.println("AWAY\t" + awayScore + "  " + "|" + third + "   " + first + "|");
            System.out.println("HOME\t" + homeScore + "  |  O  |");
            System.out.println(count.getDisplayString() + ", " + outs + (outs != 1 ? " outs" : " out"));
            System.out.println((currentMenu == pitchMenu ? message : ""));
        }
    }
    public static void main(String[] args) 
    {
        WiffleBallScoreKeeper scorekeeper = new WiffleBallScoreKeeper();
        Game game = scorekeeper.new Game();
        game.play();
    }
}