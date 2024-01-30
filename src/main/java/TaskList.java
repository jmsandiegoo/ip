import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    private ArrayList<Task> items;
    private static final StorageHelper storageHelper = new StorageHelper();
    private static class StorageHelper {
        private static final String directoryPath = "data";
        private static final String fileName = "task_list.txt";

        public File loadFile() throws IOException {
            // check if the directory exists if not create it
            File directory = new File(directoryPath);
            directory.mkdir();

            // check if the file exists if not create it
            File file = new File(directoryPath + "/" + fileName);
            file.createNewFile();

            return file;
        }

        public void saveFile(String content) throws IOException {
            // check if the directory exists if not create it
            File directory = new File(directoryPath);
            directory.mkdir();

            // check if the file exists if not create it
            File file = new File(directoryPath + "/" + fileName);
            file.createNewFile();

            // write into the file
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        }

    }

    public TaskList() {
        this.items = new ArrayList<>();
    }

    public int getListSize() {
        return this.items.size();
    }

    public Task getItem(int index) {
        return this.items.get(index);
    }

    public void loadTaskListFromFile() throws StorageHelperException {
        try {
            File file = storageHelper.loadFile();

            // read the file and parse into the array
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] strArr = sc.nextLine().split(" \\| ");
                Task t;
                switch (strArr[0]) {
                case "T":
                    t = new ToDo(strArr[2]);
                    break;
                case "D":
                    t = new Deadline(strArr[2], new DateTime(strArr[3]));
                    break;
                case "E":
                    String[] periodArr = strArr[3].split(" - ");
                    t = new Event(strArr[2], new DateTime(periodArr[0]), new DateTime(periodArr[1]));
                    break;
                default:
                    throw new IOException("Invalid text file format.");
                }

                if (strArr[1].equals("1")) {
                    t.markItem();
                }

                this.items.add(t);
            }
        } catch (IOException | DateTimeParseException e) {
            System.out.println(e.getMessage());
            this.items = new ArrayList<>();
            throw new StorageHelperException("Error loading the local task list file.");
        }
    }

    public void saveTaskListToFile() throws StorageHelperException {
        StringBuilder content = new StringBuilder();
        for (Task item : this.items) {
            content.append(item.serializeTask()).append("\n");
        }

        try {
            storageHelper.saveFile(content.toString());
        } catch (IOException | SecurityException e) {
            throw new StorageHelperException("Failed to save the list changes locally. My bad...");
        }
    }

    public void addItem(Task item) {
        this.items.add(item);
    }

    public void markItemDone(int index) {
        if (index < 0 || index >= this.getListSize()) return;
        Task currItem = this.items.get(index);
        currItem.markItem();

    }

    public void unmarkItemDone(int index) {
        if (index < 0 || index >= this.getListSize()) return;
        Task currItem = this.items.get(index);
        currItem.unmarkItem();
    }

    public Task deleteItem(int index) {
        if (index < 0 || index >= this.getListSize()) return null;
        return this.items.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < items.size(); ++i) {
            output.append(i + 1).append(".  ").append(items.get(i).toString());
            if (i < items.size() - 1) output.append("\n");
        }
        return output.toString();
    }
}
