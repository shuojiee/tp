package seedu.GitSwole;

public class Delete {

    public static void execute(String arguments) {
        // Check if the user is trying to delete an exercise (contains "e/")
        if (arguments.contains("e/")) {
            deleteExercise(arguments);
        }
        // Check if the user is trying to delete a workout (contains "w/")
        else if (arguments.contains("w/")) {
            deleteWorkout(arguments);
        }
        // Handle invalid formats
        else {
            System.out.println("Invalid delete format!");
            System.out.println("Use: delete w/WORKOUT  OR  delete e/EXERCISE w/WORKOUT");
        }
    }

    private static void deleteWorkout(String arguments) {
        // Extract the workout name by removing the "w/" prefix
        String workoutName = arguments.replace("w/", "").trim();

        if (workoutName.isEmpty()) {
            System.out.println("Please specify the workout name. Example: delete w/push");
            return;
        }

        String formattedName = workoutName.substring(0, 1).toUpperCase() + workoutName.substring(1);
        System.out.println("Successfully deleted a " + formattedName + " Session!");
    }

    private static void deleteExercise(String arguments) {
        int eIndex = arguments.indexOf("e/");
        int wIndex = arguments.indexOf("w/");

        // Ensure both prefixes exist and "e/" comes before "w/"
        if (eIndex == -1 || wIndex == -1 || eIndex > wIndex) {
            System.out.println("Invalid format! Please use: delete e/EXERCISE w/WORKOUT");
            return;
        }

        // Extract the exercise name between "e/" and "w/"
        String exerciseName = arguments.substring(eIndex + 2, wIndex).trim();

        // Extract the workout name after "w/"
        String remainingArgs = arguments.substring(wIndex + 2).trim();


        String workoutName = remainingArgs.split(" ")[0];

        if (exerciseName.isEmpty() || workoutName.isEmpty()) {
            System.out.println("Exercise or Workout name cannot be empty.");
            return;
        }

        System.out.println("Your exercise has been successfully deleted!");
    }
}
