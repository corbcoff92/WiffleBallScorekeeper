package wiffleballScorekeeper.menu;

import java.util.Scanner;
import java.util.LinkedHashMap;


/**
 * Abstract base class used for all menus in the app. 
 * This base class includes the {@code Scanner} for input, 
 * and main methods to get input from the user, including a 
 * couple useful static methods.
 */
public abstract class Menu
{
    private static Scanner inputScanner = new Scanner(System.in);
    private LinkedHashMap<Character, String> options = new LinkedHashMap<Character, String>();

    /**
     * Constructor used for generating the base menu. Each option in {@code optionStrings} will be directly 
     * printed to the screen as this menu's display. The first character of each option will be used to 
     * determine the user's choice, and should therefore be unique. 
     * @param optionStrings Any number of strings that should be used for both displaying the user's options and determine their selection based on the first character.
     */
    public Menu(String ... optionStrings)
    {
        // Creates map with first letter of option being the key, 
        // and the string itself being the user's option
        char key;
        for (String str : optionStrings)
        {
            key = str.toUpperCase().charAt(0);
            options.put(key, str);
        }
    }
    
    /**
     * Displays the list of options to the screen for the user to see. 
     * This method may be overidden by any child classes.
     */
    protected void display()
    {
        // Print each option
        for (String option : options.values())
        {
            System.out.println(option);
        }
    }
    
    /**
     * Prompts for, gets & processes the next valid user input, 
     * using the overidden {@link processInput} method in the child class. 
     * This method continues prompting the user until a valid option is selected.
     * @see processInput
     */
    public void nextAction()
    {
        display();
        char input = getInput();
        processInput(input);
    }
    
    /**
     * Used to get determine the users next valid choice. 
     * This method continues prompting the user until a valid option is selected.
     * @return Character representing the key for the users choice.
     */
    private char getInput()
    {
        // First character of the input will be used as the key to select their chosen option
        char input;
        boolean isValidInput;
        System.out.print("Please enter your selection...\n> ");
        do
        {
            // In case the user enters a blank line for which no first character can be indexed  
            try 
            {
                input = inputScanner.nextLine().toUpperCase().charAt(0);
            } 
            catch (Exception e) 
            {
                input = ' ';
            }
            isValidInput = (options.containsKey(input) ? true : false);
            if (!isValidInput)
            {
                // Erase line above and display message if input is invalid   
                System.out.print("\033[2F\033[0J");
                System.out.print("Invalid entry, please try again...\n>");
            }
        } while (!isValidInput);  // Continue asking until valid option is selected
        return input;
    }
    
    /**
     * This method must be overridden by child classes, and should complete the users selected option.
     * This method is used to as a controller to complete the users selected option. The selected 
     * option is determined by the input character.
     * @param input Character indicating the key for which option the user has selected. This option should always be valid.
     */
    abstract protected void processInput(char input);
    
    /**
     * Static method used for waiting until the user presses enter to continue the execution of the program.
     */
    public static void waitForEnter()
    {
        System.out.print("Press enter to continue...");
        inputScanner.nextLine();
    }
    
    /**
     * Static method used for clearing the console. When used before each display, it simulates the 
     * effect of the display being fized, rather than scrolling.
     */
    public static void clearConsole()
    {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Static method used for asking the user a yes or no question. The given {@code question} is displayed, 
     * and the user is prompted to enter either yes or no. The method keeps asking until a valid answer is 
     * recieved. Returns {@code true} if the users response began with a 'y' and {@code false} if it began 
     * with an 'n'.
     * @param question  Question String that should be displayed as the prompt to the user.
     * @return  Boolean indicating whether the user answered 'yes' to the given question.
     */
    public static boolean askYesNo(String question)
    {
        // First string of response is used to determin if yes or no was selected
        char input;
        boolean isValid = true;
        do
        {
            System.out.print(question + " (Y/N): ");
            // In case the user enters a blank line for which no first character can be indexed  
            try 
            {
                input = inputScanner.nextLine().toUpperCase().charAt(0);                
            } 
            catch (Exception e) 
            {
                input = ' ';
            }
            isValid = (input == 'Y' || input == 'N');
            if (!isValid)
            {
                // Clear input before asking again
                System.out.print("\033[1F\033[0J");
            }
        } while (!isValid); // Continure asking until yes or no is entered
        return input == 'Y';
    }

    /**
     * Static method used to get a string no longer than the provided maximum number of characters. 
     * The method will continue asking until the user inputs a String containing a valid number of 
     * characters.
     * @param prompt    Prompt that should be displayed to the user.
     * @param maxChars  Maximum number of characters that the input string should contain.
     * @return          Validated String retrieved from the user.
     */
    public static String getString(String prompt, int maxChars)
    {
        String str;        
        System.out.println(prompt);
        do
        {
            System.out.print("> ");
            str = inputScanner.nextLine();
            if (str.length() > maxChars) System.out.println(String.format("\033[1F\033[0JThat is too long, please shorten it to %d characters or less...", maxChars));
        } while (str.length() > maxChars);
        return str;
    }
    
    /**
     * Static mehtod used to get a validated integer between the minimum and maximum numbers. 
     * The method will continue asking until the user inputs a number between the minumum and maximum values.
     * @param prompt    Prompt that should be displayed to the user.
     * @param max       Minimum value that should be accepted from the user.
     * @param min       Maximum value that should be accepted from the user.
     * @return          Validated integer between minumum and maximum that was retrieved from the user.
     */
    public static int getInt(String prompt, int min, int max)
    {
        int num;        
        System.out.println(prompt);
        do
        {
            System.out.print("> ");
            // Check if user has entered an integer
            if (inputScanner.hasNextInt())
            {
                num = inputScanner.nextInt();
            }
            else
            {
                // If not, retrieve invalid input and set to ask again
                inputScanner.nextLine();
                num = min - 1;
            }
            if (num < min || num > max)
            {
                // Print message if valid input is not entered
                System.out.println(String.format("Invalid entry, number must be between %d and %d...", min, max));
                num = min - 1;
            }
        } while (num < min || num > max);  // Continue asking until input is valid
        // Accept trailing newline character
        inputScanner.nextLine();
        return num;
    }
}