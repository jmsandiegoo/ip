public class EventCommand extends Command {
    public static final String COMMAND_WORD = "event";
    private final Event eventItem;

    public EventCommand(Event eventItem) {
        this.eventItem = eventItem;
    }

    @Override
    public CommandResult execute() {
        super.taskList.addItem(this.eventItem);
        return new CommandResult(
                String.format(Messages.MESSAGE_ADD_TASK, this.eventItem, super.taskList.getListSize())
        );
    }
}
