package net.tbp.interval.ui.reminder;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interval.R;
import com.example.interval.databinding.FragmentReminderBinding;

import java.util.List;

// this class will use to set each component that will show in the reminder recycler cell that have not finish
public class CompletedReminderRecyclerViewAdapter extends RecyclerView.Adapter<CompletedReminderRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ReminderRecyclerView";        // use to debug
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
        public CheckBox checkBox;

        // Construction of ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            title = (TextView) itemView.findViewById(R.id.completed_reminderTitle);
            checkBox = (CheckBox) itemView.findViewById(R.id.completed_reminderCheckBox);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CompletedReminderRecyclerViewAdapter(Context context, List<Reminder> reminderList) {
        this.reminderList = reminderList;
        this.context = context;
    }

    // Create new View (invoke by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.completed_reminder_row_layout, parent, false);
        // Set the view's size, margin, padding and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // this func wil call when user select the row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from a database at this position
        // replace the contents of view with that element
        int count = -1;
        for(int i = 0; i < reminderList.size(); i++){
            if(reminderList.get(i).getStatus()){
                count++;
                if(count == position){
                    Reminder row = reminderList.get(position);
                    Log.d(TAG, "complete: " + position + " : "+i);
                    holder.title.setText(reminderList.get(i).getTitle());
                    holder.checkBox.setChecked(reminderList.get(i).getStatus());

                    int finalCount = i;
                    holder.checkBox.setOnClickListener(new View.OnClickListener() {
                        // user select a row
                        @Override
                        public void onClick(View view) {
                            if(!holder.checkBox.isChecked()){
                                reminderList.get(finalCount).setStatus(false);
                                Log.d(TAG, "check reminder to show: " + (finalCount) + " ");

                                RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.reminderRecyclerView);

                                notifyDataSetChanged();
                                view.invalidate();
                            }
                        }
                    });

                    holder.layout.setOnClickListener(new View.OnClickListener() {
                        // user select a row
                        @Override
                        public void onClick(View view) {
                            if(!holder.checkBox.isChecked()){
                                Log.d(TAG, "check reminder to show: " + (position) + " " + (row.getTitle()));
                            }
                            Log.d(TAG, "select reminder to show: " + (position) + " " + (row.getTitle()));
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(reminderList.size() >= 1){
            for(int i = 0; i < reminderList.size(); i++){
                if(reminderList.get(i).getStatus()){
                    count++;
                }
            }
        }
        return count;
    }
}