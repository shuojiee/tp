package seedu.gitswole.command;
import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.ui.Ui;
import seedu.gitswole.parser.Parser;
import java.util.logging.Level;
/**
 * Represents a command that deletes a workout or an exercise from the workout list.
 * <p>
 * Supported formats:
 * <ul>
 * <li>{@code delete w/WORKOUT} — removes the specified workout</li>
 * <li>{@code delete e/EXERCISE w/WORKOUT} — removes the specified exercise from a workout</li>
 * </ul>
 */
public class DeleteCommand extends Command {
    private String arguments;

    /**
     * Constructs a DeleteCommand with the raw user input string.
     *
     * @param arguments The full command string entered by the user.
     */
    public DeleteCommand(String arguments) {
        assert arguments != null : "Arguments passed to DeleteCommand cannot be null";
        this.arguments = arguments;
    }

    /**
     * Executes the delete command by determining whether to remove a workout or an exercise,
     * based on the flags present in the input.
     *
     * @param workouts The current list of workouts.
     * @param ui       The user interface for displaying results or error messages.
     */
    @Override
    public void execute(WorkoutList workouts, Ui ui) {
        // Assert that the essential dependencies are initialized before proceeding
        assert workouts != null : "WorkoutList must be initialized before execution";
        assert ui != null : "Ui must be initialized before execution";

        // Check if the user is trying to delete an exercise (contains "e/")
        if (Parser.parseValue(arguments, "e/") != null) {
            deleteExercise(workouts, ui);
        } else if (Parser.parseValue(arguments, "w/") != null) {
            deleteWorkout(workouts, ui);
        } else { // Handle invalid formats
            LOGGER.log(Level.WARNING, "Invalid delete format received: {0}", arguments);
            ui.showMessage("Invalid delete format!");
            ui.showMessage("Use: delete w/WORKOUT  OR  delete e/EXERCISE w/WORKOUT");
        }
    }

    /**
     * Parses the input and deletes the specified workout from the workout list.
     *
     * @param workouts The current list of workouts.
     */
    private void deleteWorkout(WorkoutList workouts, Ui ui) {
        String workoutName = Parser.parseValue(arguments, "w/");

        if (workoutName == null || workoutName.isEmpty()) {
            LOGGER.log(Level.WARNING, "DeleteWorkout failed: Workout name is empty.");
            ui.showMessage("Please specify the workout name or index. Usage: delete w/WORKOUT or delete w/INDEX");
            ui.showLine();
            return;
        }

        boolean isDeleted = false;
        String deletedName = workoutName;

        // Try index-based deletion first
        try {
            int index = Integer.parseInt(workoutName.trim());
            seedu.gitswole.assets.Workout target = workouts.getWorkoutByIndex(index);
            if (target != null) {
                deletedName = target.getWorkoutName();
            }
            isDeleted = workouts.removeWorkoutByIndex(index);
        } catch (NumberFormatException e) {
            // Not an index — fall through to name-based deletion
            isDeleted = workouts.removeWorkout(workoutName);
        }

        if (isDeleted) {
            String formattedName = deletedName.substring(0, 1).toUpperCase() + deletedName.substring(1);
            ui.showMessage("Successfully deleted the " + formattedName + " session!");
        } else {
            ui.showMessage("'" + workoutName + "' not found. Please check your spelling or index.");
        }
        ui.showLine();
    }

    /**
     * Parses the input and deletes the specified exercise from the target workout.
     *
     * @param workouts The current list of workouts.
     */
    private void deleteExercise(WorkoutList workouts, Ui ui) {
        String exerciseValue = Parser.parseValue(arguments, "e/");
        String workoutName = Parser.parseValue(arguments, "w/");

        if (exerciseValue == null || workoutName == null
                || exerciseValue.isEmpty() || workoutName.isEmpty()) {
            LOGGER.log(Level.WARNING, "DeleteExercise failed: Missing e/ or w/ flags.");
            ui.showMessage("Invalid format! Please use: delete e/EXERCISE w/WORKOUT"
                    + " or delete e/INDEX w/WORKOUT");
            ui.showLine();
            return;
        }

        // Resolve workout by name (index not supported for w/ in exercise deletion
        // since w/ always refers to the parent workout by name)
        seedu.gitswole.assets.Workout targetWorkout = workouts.getWorkoutByName(workoutName);
        if (targetWorkout == null) {
            ui.showMessage("Workout '" + workoutName + "' not found. Please check your spelling.");
            ui.showLine();
            return;
        }

        boolean isDeleted = false;
        String deletedName = exerciseValue;

        // Try index-based deletion for exercise
        try {
            int index = Integer.parseInt(exerciseValue.trim());
            java.util.ArrayList<seedu.gitswole.assets.Exercise> exercises = targetWorkout.getExerciseList();
            if (index >= 1 && index <= exercises.size()) {
                deletedName = exercises.get(index - 1).getExerciseName();
                exercises.remove(index - 1);
                isDeleted = true;
            }
        } catch (NumberFormatException e) {
            // Not an index — fall through to name-based deletion
            isDeleted = workouts.removeExercise(workoutName, exerciseValue);
        }

        if (isDeleted) {
            ui.showMessage("Successfully deleted '" + deletedName + "' from '" + workoutName + "'!");
        } else {
            ui.showMessage("'" + exerciseValue + "' not found in '" + workoutName
                    + "'. Please check your spelling or index.");
        }
        ui.showLine();
    }
}
