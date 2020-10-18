package by.bsuir.ppo_timer.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bsuir.ppo_timer.Model.FieldType;
import by.bsuir.ppo_timer.Model.Workout;

public class CreateWorkoutViewModel extends AndroidViewModel {
    private final MutableLiveData<String> Name = new MutableLiveData<>("");
    private final MutableLiveData<String> TimeOfPreparation = new MutableLiveData<>("10");
    private final MutableLiveData<String> TimeOfWork = new MutableLiveData<>("20");
    private final MutableLiveData<String> TimeOfRest = new MutableLiveData<>("20");
    private final MutableLiveData<String> CountOfCycles = new MutableLiveData<>("1");
    private final MutableLiveData<String> CountOfSets = new MutableLiveData<>("1");
    private final MutableLiveData<String> TimeOfRestBetweenSet = new MutableLiveData<>("0");
    private final MutableLiveData<String> TimeOfFinalRest = new MutableLiveData<>("0");
    private final MutableLiveData<Integer> Color = new MutableLiveData<>(-16777216);

    public CreateWorkoutViewModel(@NonNull Application application) {
        super(application);
    }

    public Boolean setName(String name) {
        Name.setValue(name);
        return true;
    }

    public void increment(FieldType field) {
        switch (field) {
            case TIMEOFPREPARATION:
                TimeOfPreparation.setValue(Integer.toString((Integer.parseInt(TimeOfPreparation.getValue())) + 1));
                break;
            case TIMEOFWORK:
                TimeOfWork.setValue(Integer.toString((Integer.parseInt(TimeOfWork.getValue())) + 1));
                break;
            case TIMEOFREST:
                TimeOfRest.setValue(Integer.toString((Integer.parseInt(TimeOfRest.getValue())) + 1));
                break;
            case COUNTOFCYCLE:
                CountOfCycles.setValue(Integer.toString((Integer.parseInt(CountOfCycles.getValue())) + 1));
                break;
            case COUNTOFSETS:
                CountOfSets.setValue(Integer.toString((Integer.parseInt(CountOfSets.getValue())) + 1));
                break;
            case TIMEOFRESTBETWEENSET:
                TimeOfRestBetweenSet.setValue(Integer.toString((Integer.parseInt(TimeOfRestBetweenSet.getValue())) + 1));
                break;
            case TIMEOFFINALREST:
                TimeOfFinalRest.setValue(Integer.toString((Integer.parseInt(TimeOfFinalRest.getValue())) + 1));
                break;
        }
    }

    public void Initialize(Workout workout){
        TimeOfPreparation.setValue(workout.getTimeOfPreparation());
        TimeOfWork.setValue(workout.getTimeOfWork());
        TimeOfRest.setValue(workout.getTimeOfRest());
        CountOfCycles.setValue(workout.getCountOfCycles());
        CountOfSets.setValue(workout.getCountOfSets());
        TimeOfRestBetweenSet.setValue(workout.getTimeOfRestBetweenSet());
        TimeOfFinalRest.setValue(workout.getTimeOfFinalRest());
        Color.setValue(workout.getColor());
    }
    public void decrement(FieldType field) {
        switch (field) {
            case TIMEOFPREPARATION:
                TimeOfPreparation.setValue(Integer.toString((Integer.parseInt(TimeOfPreparation.getValue())) - 1));
                break;
            case TIMEOFWORK:
                TimeOfWork.setValue(Integer.toString((Integer.parseInt(TimeOfWork.getValue())) - 1));
                break;
            case TIMEOFREST:
                TimeOfRest.setValue(Integer.toString((Integer.parseInt(TimeOfRest.getValue())) - 1));
                break;
            case COUNTOFCYCLE:
                CountOfCycles.setValue(Integer.toString((Integer.parseInt(CountOfCycles.getValue())) - 1));
                break;
            case COUNTOFSETS:
                CountOfSets.setValue(Integer.toString((Integer.parseInt(CountOfSets.getValue())) - 1));
                break;
            case TIMEOFRESTBETWEENSET:
                TimeOfRestBetweenSet.setValue(Integer.toString((Integer.parseInt(TimeOfRestBetweenSet.getValue())) - 1));
                break;
            case TIMEOFFINALREST:
                TimeOfFinalRest.setValue(Integer.toString((Integer.parseInt(TimeOfFinalRest.getValue())) - 1));
                break;
        }
    }

    public void setColor(int color){
        Color.postValue(color);
    }

    public LiveData<Integer> getColor(){
        return Color;
    }

    public LiveData<String> GetValue(FieldType field) {
        switch (field) {
            case TIMEOFPREPARATION:
                return TimeOfPreparation;
            case TIMEOFWORK:
                return TimeOfWork;
            case TIMEOFREST:
                return TimeOfRest;
            case COUNTOFCYCLE:
                return CountOfCycles;
            case COUNTOFSETS:
                return CountOfSets;
            case TIMEOFRESTBETWEENSET:
                return TimeOfRestBetweenSet;
            case TIMEOFFINALREST:
                return TimeOfFinalRest;
            default:
                return new MutableLiveData<>();
        }
    }

    public Workout getObject(int id) {
        return new Workout(id,Name.getValue(), TimeOfPreparation.getValue(), TimeOfWork.getValue(), TimeOfRest.getValue(), CountOfCycles.getValue(), CountOfSets.getValue(), TimeOfRestBetweenSet.getValue(), TimeOfFinalRest.getValue(),Color.getValue());
    }
}
