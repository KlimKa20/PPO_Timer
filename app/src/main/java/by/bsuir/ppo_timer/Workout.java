package by.bsuir.ppo_timer;

public class Workout {
    private String Name;
    private int TimeOfPreparation;
    private int TimeOfWork;
    private int TimeOfRest;
    private int CountOfCycles;
    private int CountOfSets;
    private int TimeOfRestBetweenSet;
    private int TimeOfFinalRest;

    public Workout(String name, int timeOfPreparation, int timeOfWork, int timeOfRest, int countOfCycles, int countOfSets, int timeOfRestBetweenSet, int timeOfFinalRest) {
        Name = name;
        TimeOfPreparation = timeOfPreparation;
        TimeOfWork = timeOfWork;
        TimeOfRest = timeOfRest;
        CountOfCycles = countOfCycles;
        CountOfSets = countOfSets;
        TimeOfRestBetweenSet = timeOfRestBetweenSet;
        TimeOfFinalRest = timeOfFinalRest;
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

    public void setTimeOfPreparation(int timeOfPreparation) {
        TimeOfPreparation = timeOfPreparation;
    }

    public int getTimeOfWork() {
        return TimeOfWork;
    }

    public void setTimeOfWork(int timeOfWork) {
        TimeOfWork = timeOfWork;
    }

    public int getTimeOfRest() {
        return TimeOfRest;
    }

    public void setTimeOfRest(int timeOfRest) {
        TimeOfRest = timeOfRest;
    }

    public int getCountOfCycles() {
        return CountOfCycles;
    }

    public void setCountOfCycles(int countOfCycles) {
        CountOfCycles = countOfCycles;
    }

    public int getCountOfSets() {
        return CountOfSets;
    }

    public void setCountOfSets(int countOfSets) {
        CountOfSets = countOfSets;
    }

    public int getTimeOfRestBetweenSet() {
        return TimeOfRestBetweenSet;
    }

    public void setTimeOfRestBetweenSet(int timeOfRestBetweenSet) {
        TimeOfRestBetweenSet = timeOfRestBetweenSet;
    }

    public int getTimeOfFinalRest() {
        return TimeOfFinalRest;
    }

    public void setTimeOfFinalRest(int timeOfFinalRest) {
        TimeOfFinalRest = timeOfFinalRest;
    }
}
