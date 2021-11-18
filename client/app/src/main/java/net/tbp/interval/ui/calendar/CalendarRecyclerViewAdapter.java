package net.tbp.interval.ui.calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

import net.tbp.interval.ui.calendar.EditCalendar;
import net.tbp.interval.ui.calendar.Calendar;
import net.tbp.interval.ui.calendar.CalendarProvider;
import net.tbp.interval.ui.calendar.CalendarRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CalendarRecyclerView";        // use to debug
    private List<Calendar> calendarList;                             // list that will store the reminder
    private Context context;
    String TAGSQL = "CalenderSQL";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public View layout;
        // since it is uncompleted reminder then set ischeck to false
        public boolean isChecked = false;
//        public CheckBox checkBox;
        public TextView duedate;

        // Construction of ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            name = (TextView) itemView.findViewById(R.id.calendarTitle);
//          checkBox = (CheckBox) itemView.findViewById(R.id.reminderCheckBox);
            duedate = (TextView) itemView.findViewById(R.id.calendarDuedate);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CalendarRecyclerViewAdapter(Context context, List<Calendar> calendarList) {
        this.calendarList = calendarList;
        this.context = context;
    }

    // Create new View (invoke by the layout manager)
    @NonNull
    @Override
    public CalendarRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_row_layout, parent, false);
        // Set the view's size, margin, padding and layout parameters
        CalendarRecyclerViewAdapter.ViewHolder viewHolder = new CalendarRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    // this func wil call when use select the row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from a database at this position
        // replace the contents of view with that element
        // set count to -1 case no uncompleted reminder
        int count = -1;
        for (int i = 0; i < calendarList.size(); i++) {
            // check status reminder if reminder status is false will show in the recyclerView
            if (!calendarList.get(i).getStatus()) {
                // add count
                count++;
                // to make the recyclerView show the first reminder that has staus false
                if (count == position) {
                    Calendar row = calendarList.get(position);
                    // set recyclerView name
                    holder.name.setText(calendarList.get(i).getName());
                    // set recyclerView checkbox
//                    holder.checkBox.setChecked(calendarList.get(i).getStatus());
                    Date date = calendarList.get(i).getDuedate();
                    SimpleDateFormat dateFormat = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        dateFormat = new SimpleDateFormat("MMM-dd, YYYY");
                    }
                    String dateString = dateFormat.format(date);
                    holder.duedate.setText(dateString);

                    // will use to check that user mark it as complete it
                    int finalCount = i;
                    // add listener to checkbox
//                    holder.checkBox.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // uer check reminder as completed
//                            if (holder.checkBox.isChecked()) {
//                                // change status of reminder to true
//                                calendarList.get(finalCount).setStatus(true);
//
//                                // update data to sql
//                                ContentValues values = new ContentValues();
//                                // name that user will add to the database
//                                values.put(CalendarProvider.STATUS, true);
//                                context.getContentResolver().update(ReminderProvider.CONTENT_URI, values, CalendarProvider._ID + "="
//                                        + calendarList.get(finalCount).getReminderId(), null);
//                                // update data
//                                notifyDataSetChanged();
//                            }
//                        }
//                    });

                    // this function will add new intent to pop up to show edit reminder
                    holder.layout.setOnClickListener(new View.OnClickListener() {
                        // user select a row
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "select calendar to show: " + (position) + " " + (row.getName()));
                            // initial intent that will use to call EditReminder class to render screen to add new reminder
                            Intent editCalendarIntent = new Intent(context, EditCalendar.class);
                            // data that will use to sendData to EditReminder.class
                            editCalendarIntent.putExtra("id", calendarList.get(finalCount).getCalendarId());
                            editCalendarIntent.putExtra("name", calendarList.get(finalCount).getName());
//                            editCalendarIntent.putExtra("description", calendarList.get(finalCount).getDescription());
//                            editCalendarIntent.putExtra("priority", calendarList.get(finalCount).getPriority());
                            editCalendarIntent.putExtra("dueDate", calendarList.get(finalCount).getDuedate().getTime());
                            context.startActivity(editCalendarIntent);
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
        if (calendarList.size() >= 1) {
            for (int i = 0; i < calendarList.size(); i++) {
                if (!calendarList.get(i).getStatus()) {
                    count++;
                }
            }
        }
        Log.d(TAG, String.valueOf(count));
        return count;
    }
}




