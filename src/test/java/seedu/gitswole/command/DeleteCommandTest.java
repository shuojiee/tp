package seedu.gitswole.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.gitswole.assets.Exercise;
import seedu.gitswole.assets.Workout;
import seedu.gitswole.assets.WorkoutList;
import seedu.gitswole.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DeleteCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private WorkoutList workouts;
    private Ui ui;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ui = new Ui();
        workouts = new WorkoutList();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void execute_validWorkoutDelete_runsWithoutException() {
        DeleteCommand command = new DeleteCommand("delete w/Legs");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }

    @Test
    public void execute_validExerciseDelete_runsWithoutException() {
        DeleteCommand command = new DeleteCommand("delete e/Squats w/Legs");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }

    @Test
    public void execute_missingWorkoutName_handledGracefully() {
        DeleteCommand command = new DeleteCommand("delete w/");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }

    @Test
    public void execute_invalidFormat_handledGracefully() {
        DeleteCommand command = new DeleteCommand("delete random string");

        assertDoesNotThrow(() -> command.execute(workouts, ui));
    }

    @Test
    @DisplayName("delete w/INDEX — deletes workout at valid index")
    public void deleteWorkout_byValidIndex_succeeds() {
        workouts.addWorkout(new Workout("push"));
        workouts.addWorkout(new Workout("pull"));
        new DeleteCommand("delete w/1").execute(workouts, ui);
        assertEquals(1, workouts.numOfWorkouts());
        assertNull(workouts.getWorkoutByName("push"));
    }

    @Test
    @DisplayName("delete w/INDEX — out of bounds index shows error message")
    public void deleteWorkout_byOutOfBoundsIndex_showsError() {
        workouts.addWorkout(new Workout("push"));
        new DeleteCommand("delete w/99").execute(workouts, ui);
        assertEquals(1, workouts.numOfWorkouts()); // workout not removed
    }

    @Test
    @DisplayName("delete w/INDEX — index 0 is invalid, shows error")
    public void deleteWorkout_byZeroIndex_showsError() {
        workouts.addWorkout(new Workout("push"));
        new DeleteCommand("delete w/0").execute(workouts, ui);
        assertEquals(1, workouts.numOfWorkouts());
    }

    @Test
    @DisplayName("delete e/INDEX w/WORKOUT — deletes exercise at valid index")
    public void deleteExercise_byValidIndex_succeeds() {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench", 0, 0, 0));
        push.addExercise(new Exercise("dips", 0, 0, 0));
        workouts.addWorkout(push);
        new DeleteCommand("delete e/1 w/push").execute(workouts, ui);
        assertEquals(1, workouts.getWorkoutByName("push").getNumOfExercises());
        assertEquals("dips", workouts.getWorkoutByName("push").getExerciseList().get(0).getExerciseName());
    }

    @Test
    @DisplayName("delete e/INDEX w/WORKOUT — out of bounds exercise index shows error")
    public void deleteExercise_byOutOfBoundsIndex_showsError() {
        Workout push = new Workout("push");
        push.addExercise(new Exercise("bench", 0, 0, 0));
        workouts.addWorkout(push);
        new DeleteCommand("delete e/99 w/push").execute(workouts, ui);
        assertEquals(1, workouts.getWorkoutByName("push").getNumOfExercises());
    }

    @Test
    @DisplayName("delete w/NAME — name-based deletion still works after index support added")
    public void deleteWorkout_byName_stillWorks() {
        workouts.addWorkout(new Workout("push"));
        new DeleteCommand("delete w/push").execute(workouts, ui);
        assertEquals(0, workouts.numOfWorkouts());
    }
}
