package by.bsuir.ppo_timer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

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

        View view=inflater.inflate(this.layout, parent, false);

        Workout state = workouts.get(position);

        ((TextView) view.findViewById(R.id.Title)).setText(state.getName());
        ((TextView) view.findViewById(R.id.TimeOfPreparation)).setText(Integer.toString(state.getTimeOfPreparation()));
        ((TextView) view.findViewById(R.id.TimeOfWork)).setText(Integer.toString(state.getTimeOfWork()));
        ((TextView) view.findViewById(R.id.CountOfCycles)).setText(Integer.toString(state.getCountOfCycles()));
        ((TextView) view.findViewById(R.id.TimeOfRest)).setText(Integer.toString(state.getTimeOfRest()));
        ((TextView) view.findViewById(R.id.TotalTime)).setText("40");

        return view;
    }
}
