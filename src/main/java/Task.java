public abstract class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markItem() {
        this.isDone = true;
    }

    public void unmarkItem() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "]" + " " + this.description;
    }

    public String serializeTask() {
        return (this.isDone ? "1" : "0") + " | " + this.description;
    }
}
