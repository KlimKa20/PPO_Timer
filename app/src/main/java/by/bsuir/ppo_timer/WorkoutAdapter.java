package by.bsuir.ppo_timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        ((TextView) view.findViewById(R.id.Title)).setText(workout.getName());
        ((TextView) view.findViewById(R.id.TimeOfPreparation)).setText("Подготовка" + workout.getTimeOfPreparation());
        ((TextView) view.findViewById(R.id.TimeOfWork)).setText("Работа" + workout.getTimeOfWork());
        ((TextView) view.findViewById(R.id.TimeOfRest)).setText("Отдых" + workout.getTimeOfRest());
        ((TextView) view.findViewById(R.id.CountOfCycles)).setText("Циклы" + workout.getCountOfCycles());

        int time_cycle = Integer.parseInt(workout.getTimeOfPreparation());
        for (int i = Integer.parseInt(workout.getCountOfSets()); i > 0; i--) {
            for (int j = Integer.parseInt(workout.getCountOfCycles()); i > 0; i--) {
                time_cycle += Integer.parseInt(workout.getTimeOfWork());
                time_cycle += Integer.parseInt(workout.getTimeOfRest());
            }
            time_cycle += Integer.parseInt(workout.getTimeOfRestBetweenSet());
        }
        time_cycle += Integer.parseInt(workout.getTimeOfFinalRest());


        ((TextView) view.findViewById(R.id.TotalTime)).setText("Общее время" + String.valueOf(time_cycle));
        ((Button) view.findViewById(R.id.buttonStart)).setTag(workout.getId());
        ((Button) view.findViewById(R.id.buttonEdit)).setTag(workout.getId());
        return view;
    }
}
