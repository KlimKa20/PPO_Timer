package by.bsuir.ppo_timer.Model;

public class Workout {
    private final int Id;
    private String Name;
    private final int TimeOfPreparation;
    private final int TimeOfWork;
    private final int TimeOfRest;
    private final int CountOfCycles;
    private final int CountOfSets;
    private final int TimeOfRestBetweenSet;
    private final int TimeOfFinalRest;
    private int color;

    public Workout(int id, String name, int timeOfPreparation, int timeOfWork, int timeOfRest, int countOfCycles, int countOfSets, int timeOfRestBetweenSet, int timeOfFinalRest, int color) {
        Id = id;
        Name = name;
        TimeOfPreparation = timeOfPreparation;
        TimeOfWork = timeOfWork;
        TimeOfRest = timeOfRest;
        CountOfCycles = countOfCycles;
        CountOfSets = countOfSets;
        TimeOfRestBetweenSet = timeOfRestBetweenSet;
        TimeOfFinalRest = timeOfFinalRest;
        this.color = color;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getTimeOfPreparation() {
        return TimeOfPreparation;
    }

    public int getTimeOfWork() {
        return TimeOfWork;
    }

    public int getTimeOfRest() {
        return TimeOfRest;
    }

    public int getCountOfCycles() {
        return CountOfCycles;
    }

    public int getCountOfSets() {
        return CountOfSets;
    }

    public int getTimeOfRestBetweenSet() {
        return TimeOfRestBetweenSet;
    }

    public int getTimeOfFinalRest() {
        return TimeOfFinalRest;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
