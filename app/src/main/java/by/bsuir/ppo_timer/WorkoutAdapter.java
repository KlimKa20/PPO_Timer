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
        ((TextView) view.findViewById(R.id.TimeOfPreparation)).setText(getContext().getResources().getString(R.string.Preparation) + " : " + workout.getTimeOfPreparation());
        ((TextView) view.findViewById(R.id.TimeOfWork)).setText(getContext().getResources().getString(R.string.Work) + " : " + workout.getTimeOfWork());
        ((TextView) view.findViewById(R.id.TimeOfRest)).setText(getContext().getResources().getString(R.string.Rest) + " : " + workout.getTimeOfRest());
        ((TextView) view.findViewById(R.id.CountOfCycles)).setText(getContext().getResources().getString(R.string.Cycle) + " : " + workout.getCountOfCycles());

        int time_cycle = Integer.parseInt(workout.getTimeOfPreparation());
        for (int i = Integer.parseInt(workout.getCountOfSets()); i > 0; i--) {
            for (int j = Integer.parseInt(workout.getCountOfCycles()); j > 0; j--) {
                time_cycle += Integer.parseInt(workout.getTimeOfWork());
                time_cycle += Integer.parseInt(workout.getTimeOfRest());
            }
            if (i != 1) {
                time_cycle += Integer.parseInt(workout.getTimeOfRestBetweenSet());
            }
        }
        time_cycle += Integer.parseInt(workout.getTimeOfFinalRest());


        ((TextView) view.findViewById(R.id.TotalTime)).setText(getContext().getResources().getString(R.string.Totaltime) + " : " + String.valueOf(time_cycle));
        view.findViewById(R.id.buttonStart).setTag(workout.getId());
        view.findViewById(R.id.buttonEdit).setTag(workout.getId());
        view.findViewById(R.id.buttonDelete).setTag(workout.getId());
        return view;
    }
}
