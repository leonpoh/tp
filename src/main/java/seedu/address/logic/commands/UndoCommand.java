package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.StateHistory;

/**
 * Undoes a number of the most recent prior {@code Command}s.
 */
public class UndoCommand extends Command {

    public static List<String> COMMAND_WORDS = new ArrayList<String>(Arrays.asList("undo", "u"));

    public static final String MESSAGE_USAGE = COMMAND_WORDS + ": Undoes the previous command, or a number of most "
            + "recent commands. Ignores Undo, Redo, and Export commands; affects all other valid commands.\n"
            + "Parameters: [NUMBER_OF_COMMANDS]...\n"
            + "Example: " + COMMAND_WORDS + " 5";

    public static final String MESSAGE_SUCCESS = "Undone %1$d / %2$d commands";

    private final int numCommands;
    private StateHistory history = null;

    /**
     * Creates an UndoCommand to undo a given number of previous Commands.
     *
     * @param numCommands Number of commands to undo
     */
    public UndoCommand(int numCommands) {
        this.numCommands = numCommands;
    }

    @Override
    public void setHistory(StateHistory history) {
        this.history = history;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        requireNonNull(history);
        int undoneCommands = history.undo(numCommands);
        Model undoneModel = history.presentModel();
        model.setAddressBook(undoneModel.getAddressBook());
        model.updateFilteredPersonList(undoneModel.getPredicate());
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, undoneCommands, numCommands), false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && numCommands == ((UndoCommand) other).numCommands); // state check
    }
}
