package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exceptions.ModifyFrozenStateException;

/**
 * Unfreezes the address book.
 */
public class UnfreezeCommand extends Command {

    public static final List<String> COMMAND_WORDS = List.of(new String[]{"unfreeze", "unf"});
    public static final String MESSAGE_SUCCESS = "Address book has been unfrozen!";
    public static final String MESSAGE_FAILURE = "Address book is not frozen!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.unfreezeFilteredPersonList();
        } catch (ModifyFrozenStateException ex) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS, true, true);
    }
}