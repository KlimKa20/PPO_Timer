package by.bsuir.ppo_timer.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.bsuir.ppo_timer.Model.FieldType;
import by.bsuir.ppo_timer.R;

public class CreateWorkoutViewModel extends AndroidViewModel {
    private final MutableLiveData<String> Name = new MutableLiveData<>("");
    private final MutableLiveData<Integer> TimeOfPreparation = new MutableLiveData<>(getApplication().getResources().getInteger(R.integer.PreparationTextView));
    private final MutableLiveData<Integer> TimeOfWork = new MutableLiveData<>(getApplication().getResources().getInteger(R.integer.WorkTextView));
    private final MutableLiveData<Integer> TimeOfRest = new MutableLiveData<>(getApplication().getResources().getInteger(R.integer.RestTextView));
    private final MutableLiveData<Integer> CountOfCycles = new MutableLiveData<>(getApplication().getResources().getInteger(R.integer.CycleTextView));
    private final MutableLiveData<Integer> CountOfSets = new MutableLiveData<>(getApplication().getResources().getInteger(R.integer.SetTextView));
    private final MutableLiveData<Integer> TimeOfRestBetweenSet = new MutableLiveData<>(getApplication().getResources().getInteger(R.integer.TimeOfRestBetweenSetTextView));
    private final MutableLiveData<Integer> TimeOfFinalRest = new MutableLiveData<>(getApplication().getResources().getInteger(R.integer.TimeOfFinalRestTextView));
    private final MutableLiveData<Integer> Color = new MutableLiveData<>(-16777216);

    public CreateWorkoutViewModel(@NonNull Application application) {
        super(application);
    }

    public void setName(String name) {
        Name.setValue(name);
    }

    public LiveData<String> getName() {
        return Name;
    }


    public void increment(FieldType field) {
        switch (field) {
            case TIMEOFPREPARATION:
                TimeOfPreparation.setValue(TimeOfPreparation.getValue() + 1);
                break;
            case TIMEOFWORK:
                TimeOfWork.setValue(TimeOfWork.getValue() + 1);
                break;
            case TIMEOFREST:
                TimeOfRest.setValue(TimeOfRest.getValue() + 1);
                break;
            case COUNTOFCYCLE:
                CountOfCycles.setValue(CountOfCycles.getValue() + 1);
                break;
            case COUNTOFSETS:
                CountOfSets.setValue(CountOfSets.getValue() + 1);
                break;
            case TIMEOFRESTBETWEENSET:
                TimeOfRestBetweenSet.setValue(TimeOfRestBetweenSet.getValue() + 1);
                break;
            case TIMEOFFINALREST:
                TimeOfFinalRest.setValue(TimeOfFinalRest.getValue() + 1);
                break;
        }
    }

    public void Initialize(String Name ,int TimeOfPreparation,int TimeOfWork,int TimeOfRest,int CountOfCycles,int CountOfSets,int TimeOfRestBetweenSet,
                           int TimeOfFinalRest,int Color) {
        this.Name.setValue(Name);
        this.TimeOfPreparation.setValue(TimeOfPreparation);
        this.TimeOfWork.setValue(TimeOfWork);
        this.TimeOfRest.setValue(TimeOfRest);
        this.CountOfCycles.setValue(CountOfCycles);
        this.CountOfSets.setValue(CountOfSets);
        this.TimeOfRestBetweenSet.setValue(TimeOfRestBetweenSet);
        this.TimeOfFinalRest.setValue(TimeOfFinalRest);
        this.Color.setValue(Color);
    }

    public void decrement(FieldType field) {
        switch (field) {
            case TIMEOFPREPARATION:
                if (TimeOfPreparation.getValue() != 0)
                    TimeOfPreparation.setValue(TimeOfPreparation.getValue() - 1);
                break;
            case TIMEOFWORK:
                if (TimeOfWork.getValue() != 1)
                    TimeOfWork.setValue(TimeOfWork.getValue() - 1);
                break;
            case TIMEOFREST:
                if (TimeOfRest.getValue() != 1)
                    TimeOfRest.setValue(TimeOfRest.getValue() - 1);
                break;
            case COUNTOFCYCLE:
                if (CountOfCycles.getValue() != 1)
                    CountOfCycles.setValue(CountOfCycles.getValue() - 1);
                break;
            case COUNTOFSETS:
                if (CountOfSets.getValue() != 1)
                    CountOfSets.setValue(CountOfSets.getValue() - 1);
                break;
            case TIMEOFRESTBETWEENSET:
                if (TimeOfRestBetweenSet.getValue() != 0)
                    TimeOfRestBetweenSet.setValue(TimeOfRestBetweenSet.getValue() - 1);
                break;
            case TIMEOFFINALREST:
                if (TimeOfFinalRest.getValue() != 0)
                    TimeOfFinalRest.setValue(TimeOfFinalRest.getValue() - 1);
                break;
        }
    }

    public void setColor(int color) {
        Color.setValue(color);
    }

    public LiveData<Integer> getColor() {
        return Color;
    }

    public LiveData<Integer> GetValue(FieldType field) {
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
}
