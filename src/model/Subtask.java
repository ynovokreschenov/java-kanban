package model;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String title, String description, TaskState taskState) {
        this.setTitle(title);
        this.setDescription(description);
        this.setState(taskState);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "model.Subtask{" +
                "epicId=" + epic.getId() + ", " +
                "id=" + super.getId() +
                ", state=" + super.getState() +
                ", title='" + super.getTitle() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                '}';
    }
}
