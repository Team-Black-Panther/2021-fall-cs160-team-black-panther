package net.tbp.interval.ui.calendar;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interval.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CalendarRecyclerView";        // use to debug
    private List<CalendarInterval> calendarList;                             // list that will store the reminder
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
    public CalendarRecyclerViewAdapter(Context context, List<CalendarInterval> calendarList) {
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

                // add count
                count++;
                // to make the recyclerView show the first reminder that has staus false
                if (count == position) {
                    CalendarInterval row = calendarList.get(position);
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



                }

        }
    }

    // ***************
    // function to count the total number of recyclerView that has incompleted status
    @Override
    public int getItemCount() {
        int count = 0;
        if (calendarList.size() >= 1) {
            for (int i = 0; i < calendarList.size(); i++) {

                    count++;

            }
        }
        Log.d(TAG, String.valueOf(count));
        return count;
    }
}




