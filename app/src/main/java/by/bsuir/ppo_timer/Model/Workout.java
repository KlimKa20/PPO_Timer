package by.bsuir.ppo_timer.Model;

public class Workout {
    private int Id;
    private String Name;
    private String TimeOfPreparation;
    private String TimeOfWork;
    private String TimeOfRest;
    private String CountOfCycles;
    private String CountOfSets;
    private String TimeOfRestBetweenSet;
    private String TimeOfFinalRest;

    public Workout(int id, String name, String timeOfPreparation, String timeOfWork, String timeOfRest, String countOfCycles, String countOfSets, String timeOfRestBetweenSet, String timeOfFinalRest) {
        Id = id;
        Name = name;
        TimeOfPreparation = timeOfPreparation;
        TimeOfWork = timeOfWork;
        TimeOfRest = timeOfRest;
        CountOfCycles = countOfCycles;
        CountOfSets = countOfSets;
        TimeOfRestBetweenSet = timeOfRestBetweenSet;
        TimeOfFinalRest = timeOfFinalRest;
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

    public String getTimeOfPreparation() {
        return TimeOfPreparation;
    }

    public void setTimeOfPreparation(String timeOfPreparation) {
        TimeOfPreparation = timeOfPreparation;
    }

    public String getTimeOfWork() {
        return TimeOfWork;
    }

    public void setTimeOfWork(String timeOfWork) {
        TimeOfWork = timeOfWork;
    }

    public String getTimeOfRest() {
        return TimeOfRest;
    }

    public void setTimeOfRest(String timeOfRest) {
        TimeOfRest = timeOfRest;
    }

    public String getCountOfCycles() {
        return CountOfCycles;
    }

    public void setCountOfCycles(String countOfCycles) {
        CountOfCycles = countOfCycles;
    }

    public String getCountOfSets() {
        return CountOfSets;
    }

    public void setCountOfSets(String countOfSets) {
        CountOfSets = countOfSets;
    }

    public String getTimeOfRestBetweenSet() {
        return TimeOfRestBetweenSet;
    }

    public void setTimeOfRestBetweenSet(String timeOfRestBetweenSet) {
        TimeOfRestBetweenSet = timeOfRestBetweenSet;
    }

    public String getTimeOfFinalRest() {
        return TimeOfFinalRest;
    }

    public void setTimeOfFinalRest(String timeOfFinalRest) {
        TimeOfFinalRest = timeOfFinalRest;
    }
}
