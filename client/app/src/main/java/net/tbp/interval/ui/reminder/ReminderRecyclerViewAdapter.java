package net.tbp.interval.ui.reminder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interval.R;

import java.util.List;

public class ReminderRecyclerViewAdapter extends RecyclerView.Adapter<ReminderRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ReminderRecyclerViewAdapter";
    private List<Reminder> reminderList;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public View layout;
        public boolean isChecked = false;

        // Construction of ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            title = (TextView) itemView.findViewById(R.id.reminderTitle);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReminderRecyclerViewAdapter(Context context, List<Reminder> reminderList) {
        this.reminderList = reminderList;
        this.context = context;
    }

    // Create new View (invoke by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reminder_row_layout, parent, false);

        // Set the view's size, margin, padding and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // this func wil call when use select the row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from a database at this position
        // replace the contents of view with that element
        Reminder row = reminderList.get(position);
        holder.title.setText(row.getTitle());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            // user select a row
            @Override
            public void onClick(View view) {
                Log.d(TAG, "select reminder to show: " + (position) + " " + (row.getTitle()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }
}
