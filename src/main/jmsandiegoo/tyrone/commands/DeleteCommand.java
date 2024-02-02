package jmsandiegoo.tyrone.commands;

import jmsandiegoo.tyrone.common.Messages;
import jmsandiegoo.tyrone.exceptions.CommandExecutionException;
import jmsandiegoo.tyrone.task.Task;

/**
 * Represents the deletion command of task from the list.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    private final int index;

    /**
     * @param int index the target list index of the item to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Returns CommandResult after deleting a task from the list.
     * If index is out of bounds it throws CommandExecutionException,
     *
     * @return CommandResult - the success result of the execution.
     */
    @Override
    public CommandResult execute() throws CommandExecutionException {
        try {
            Task delItem = super.taskList.deleteItem(this.index);
            return new CommandResult(
                    String.format(Messages.MESSAGE_DELETE, delItem, super.taskList.getListSize()));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandExecutionException(String.format(Messages.MESSAGE_INCORRECT_COMMAND_INDEX, "delete"));
        }
    }
}
