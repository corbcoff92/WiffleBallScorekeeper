package wiffleballScorekeeper.menu;

import java.util.Scanner;
import java.util.LinkedHashMap;

public abstract class Menu
{
    private static Scanner inputScanner = new Scanner(System.in);
    
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

    protected void display()
    {
        for (String option : options.values())
        {
            System.out.println(option);
        }
    }
    
    public void nextAction()
    {
        display();
        char input = getInput();
        processInput(input);
    }

    private char getInput()
    {
        char input;
        boolean isValidInput;
        System.out.print("Please enter your selection...\n> ");
        do
        {
            try 
            {
                input = inputScanner.nextLine().toUpperCase().charAt(0);
            } catch (Exception e) 
            {
                input = ' ';
            }
            isValidInput = (options.containsKey(input) ? true : false);
            if (!isValidInput)
            {   
                System.out.print("\033[2F\033[0J");
                System.out.print("Invalid entry, please try again...\n>");
            }
        } while (!isValidInput);
        return input;
    }
    
    public static void waitForEnter()
    {
        System.out.print("Press enter to continue...");
        inputScanner.nextLine();
    }
    
    public static void clearConsole()
    {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
    
    public static boolean askYesNo(String question)
    {
        char input;
        boolean isValid = true;
        do
        {
            System.out.print(question + " (Y/N): ");
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
                System.out.print("\033[1F\033[0J");
            }
        } while (!isValid);
        return input == 'Y';
    }

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
    
    public static int getInt(String prompt, int min, int max)
    {
        int num;        
        System.out.println(prompt);
        do
        {
            System.out.print("> ");
            if (inputScanner.hasNextInt())
            {
                num = inputScanner.nextInt();
            }
            else
            {
                inputScanner.nextLine();
                num = min - 1;
            }
            if (num < min || num > max)
            {
                System.out.println(String.format("Invalid entry, number must be between %d and %d...", min, max));
                num = min - 1;
            }
        } while (num < min || num > max);
        inputScanner.nextLine();
        return num;
    }

    abstract protected void processInput(char input);
}