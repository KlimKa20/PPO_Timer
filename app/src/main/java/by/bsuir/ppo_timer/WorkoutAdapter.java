package by.bsuir.ppo_timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.bsuir.ppo_timer.Model.Workout;

public class WorkoutAdapter extends ArrayAdapter<Workout> {

    private LayoutInflater inflater;
    private int layout;
    private List<Workout> workouts;

    public WorkoutAdapter(Context context, int resource, List<Workout> workouts) {
        super(context, resource, workouts);
        this.workouts = workouts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        Workout workout = workouts.get(position);
        view.setBackgroundColor(workout.getColor());
        ((TextView) view.findViewById(R.id.Title)).setText(workout.getName());
        ((TextView) view.findViewById(R.id.TimeOfPreparation)).setText(formatString(getContext().getResources().getString(R.string.Preparation), String.valueOf(workout.getTimeOfPreparation())));
        ((TextView) view.findViewById(R.id.TimeOfWork)).setText(formatString(getContext().getResources().getString(R.string.Work), String.valueOf(workout.getTimeOfWork())));
        ((TextView) view.findViewById(R.id.TimeOfRest)).setText(formatString(getContext().getResources().getString(R.string.Rest), String.valueOf(workout.getTimeOfRest())));
        ((TextView) view.findViewById(R.id.CountOfCycles)).setText(formatString(getContext().getResources().getString(R.string.Cycle), String.valueOf(workout.getCountOfCycles())));
        ((TextView) view.findViewById(R.id.TotalTime)).setText(formatString(getContext().getResources().getString(R.string.Totaltime), String.valueOf(getTotalTime(workout))));
        view.findViewById(R.id.buttonStart).setTag(workout.getId());
        view.findViewById(R.id.buttonEdit).setTag(workout.getId());
        view.findViewById(R.id.buttonDelete).setTag(workout.getId());
        return view;
    }

    private int getTotalTime(Workout workout) {
        int totalTime = workout.getTimeOfPreparation();
        for (int i = workout.getCountOfSets(); i > 0; i--) {
            for (int j = workout.getCountOfCycles(); j > 0; j--) {
                totalTime += workout.getTimeOfWork();
                totalTime += workout.getTimeOfRest();
            }
            if (i != 1) {
                totalTime += workout.getTimeOfRestBetweenSet();
            }
        }
        totalTime += workout.getTimeOfFinalRest();
        return totalTime;
    }


    private String formatString(String name, String time) {
        return name + " : " + time;
    }
}
