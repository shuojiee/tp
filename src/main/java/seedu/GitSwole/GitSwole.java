package seedu.GitSwole;

import java.util.Scanner;

public class GitSwole {
    /**
     * Main entry-point for the GitSwole application.
     */
    public static void main(String[] args) {
        String logo =
                "   _____ _ _  _____               _      \n"
                        + "  / ____(_) |/ ____|             | |     \n"
                        + " | |  __ _| | (___  __      _____| | ___ \n"
                        + " | | |_ | | |\\___ \\ \\ \\ /\\ / / _ \\ |/ _ \\\n"
                        + " | |__| | | |____) | \\ V  V / (_) | |  __/\n"
                        + "  \\_____|_|_|_____/   \\_/\\_/ \\___/|_|\\___|\n";

        System.out.println("Hello from\n" + logo);
        System.out.println("Welcome to GitSwole! LET'S GET THEM GAINS");

        Scanner in = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.print("\n> ");
            String userInput = in.nextLine().trim();

            if (userInput.isEmpty()) {
                continue;
            }

            String[] parts = userInput.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arguments = parts.length > 1 ? parts[1] : "";

            switch (command) {
            case "help":
                System.out.println("Refer to the user guide: TO BE ADDED ON LATER");
                break;
            case "add":
            case "addworkout":
                // TODO: Pass 'arguments' to your AddWorkoutParser
                System.out.println("Successfully added a Workout Session! Remember to add your exercises :)");
                break;
            case "addexercise":
                // TODO: Pass 'arguments' to your AddExerciseParser
                System.out.println("Your exercise has been successfully added! Looking swole g");
                break;
            case "delete":
            case "deleteworkout":
            case "deleteexercise":
                // TODO: Pass 'arguments' to your DeleteExerciseParser
                Delete.execute(arguments);
                break;
            case "list":
                // TODO: Pass 'arguments' to check if it's 'all' or 'w/WORKOUT'
                System.out.println("Listing workouts/exercises...");
                break;
            case "findworkout":
                // TODO: Pass 'arguments' to find workout logic
                System.out.println("Finding workout...");
                break;
            case "findexercise":
                // TODO: Pass 'arguments' to find exercise logic
                System.out.println("Finding exercise...");
                break;
            case "exit":
                System.out.println("Bye! Keep getting swole!");
                isRunning = false;
                break;
            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
                break;
            }
        }
        in.close();
    }
}