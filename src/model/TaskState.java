package model;

public enum TaskState {
    NEW("Новый"),
    IN_PROGRESS("В процессе"),
    DONE("Завершен");

    String stateName;

    TaskState(String stateName) {
        this.stateName = stateName;
    }

    String getName() {
        return stateName;
    }
}
