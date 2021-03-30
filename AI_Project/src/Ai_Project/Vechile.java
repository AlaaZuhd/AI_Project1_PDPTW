package Ai_Project;

public class Vechile {
    int capacity;
    int currentLoad;

    public Vechile(int capacity) {
        super();
        this.capacity = capacity;
        this.currentLoad=0;
    }
    public Vechile() {
        super();
        this.currentLoad=0;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    @Override
    public String toString() {
        return "Vechile [capacity=" + capacity + ", currentLoad=" + currentLoad + "]";
    }


}
