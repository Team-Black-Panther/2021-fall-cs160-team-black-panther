package net.tbp.interval.ui.reminder;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interval.R;

import java.util.List;

// this class will use to set each component that will show in the reminder recycler cell that have not finish
public class ReminderRecyclerViewAdapter extends RecyclerView.Adapter<ReminderRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ReminderRecyclerView";        // use to debug
    private List<Reminder> reminderList;                             // list that will store the reminder
    private Context context;
    String TAGSQL = "ReminderSQL";



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public View layout;
        // since it is uncompleted reminder then set ischeck to false
        public boolean isChecked = false;
        public CheckBox checkBox;

        // Construction of ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            title = (TextView) itemView.findViewById(R.id.reminderTitle);
            checkBox = (CheckBox) itemView.findViewById(R.id.reminderCheckBox);
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
        // set count to -1 case no uncompleted reminder
        int count = -1;
        for (int i = 0; i < reminderList.size(); i++) {
            // check status reminder if reminder status is false will show in the recyclerView
            if (!reminderList.get(i).getStatus()) {
                // add count
                count++;
                // to make the recyclerView show the first reminder that has staus false
                if (count == position) {
                    Reminder row = reminderList.get(position);
                    // set recyclerView title
                    holder.title.setText(reminderList.get(i).getTitle());
                    // set recyclerView checkbox
                    holder.checkBox.setChecked(reminderList.get(i).getStatus());

                    // will use to check that user mark it as complete it
                    int finalCount = i;
                    // add listener to checkbox
                    holder.checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // uer check reminder as completed
                            if (holder.checkBox.isChecked()) {
                                // change status of reminder to true
                                reminderList.get(finalCount).setStatus(true);

                                // update data to sql
                                ContentValues values = new ContentValues();
                                // title that user will add to the database
                                values.put(ReminderProvider.STATUS, true);
                                context.getContentResolver().update(ReminderProvider.CONTENT_URI, values, ReminderProvider._ID + "="
                                        + reminderList.get(finalCount).getReminderId(), null);
                                // update data
                                notifyDataSetChanged();
                            }
                        }
                    });

                    // this function will add new intent to pop up to show description
                    holder.layout.setOnClickListener(new View.OnClickListener() {
                        // user select a row
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "select reminder to show: " + (position) + " " + (row.getTitle()));
                        }
                    });
                }
            }
        }
    }

    // function to count the total number of recyclerView that has incompleted status
    @Override
    public int getItemCount() {
        int count = 0;
        if (reminderList.size() >= 1) {
            for (int i = 0; i < reminderList.size(); i++) {
                if (!reminderList.get(i).getStatus()) {
                    count++;
                }
            }
        }
        return count;
    }
}
