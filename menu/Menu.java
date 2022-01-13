package menu;

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

    private void display()
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
        do
        {
            System.out.print("> ");
            input = inputScanner.nextLine().toUpperCase().charAt(0);
            isValidInput = (options.containsKey(input) ? true : false);
            if (!isValidInput) System.out.println("Invalid entry, please try again...");
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
    
    abstract protected void processInput(char input);
}
