    package net.tbp.interval.ui.calendar;

    import android.app.Activity;
    import android.content.Intent;
    import android.database.Cursor;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Handler;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.CalendarView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProvider;
    import androidx.loader.app.LoaderManager;
    import androidx.loader.content.CursorLoader;
    import androidx.loader.content.Loader;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.interval.R;
    import com.example.interval.databinding.FragmentCalendarBinding;

    import net.tbp.interval.ui.reminder.AddNewReminder;
    import net.tbp.interval.ui.reminder.Reminder;
    import net.tbp.interval.ui.reminder.ReminderProvider;
    import net.tbp.interval.ui.reminder.ReminderRecyclerViewAdapter;

    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;

    public class CalendarFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
        String TAG = "CalendarFragment";
        private CalendarViewModel calendarViewModel;
        private FragmentCalendarBinding binding;
        private String TAGSQL = "useraddnewcalendar";
        private Date dueDate = new Date();
        private Long dueDateLong;
        private RecyclerView recyclerView;                              // recyclerview that create cell to store each reminder
        private RecyclerView.Adapter myAdapter;                         // will use to render reclerview
        private RecyclerView.LayoutManager layoutManager;
        private List<CalendarInterval> calendarList = new ArrayList<>();

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            calendarViewModel =
                    new ViewModelProvider(this).get(CalendarViewModel.class);

            binding = FragmentCalendarBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            // set up recyclerView to show incomplete reminder
            recyclerView = (RecyclerView) root.findViewById(R.id.calendarRecyclerView);
            // Use this setting to improve performance when knowing that change
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);
            // Use linear layout manager
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);


            //  set the page to have the option menu to have the add button
            setHasOptionsMenu(true);

            // prepare and recieve priority from calendar
            CalendarView calendarView = root.findViewById(R.id.calendar_1);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month,
                                                int dayOfMonth) {
                    // Create calender object with which will have system date time.
                    java.util.Calendar calendar = Calendar.getInstance();
                    // Set attributes in calender object as per selected date.
                    calendar.set(year, month, dayOfMonth);
                    dueDate.setTime(calendar.getTimeInMillis());
                    dueDateLong = dueDate.getTime();
                    Log.e("duedate",dueDate.toString());


                }
            });
            // load reminder database
            LoaderManager.getInstance(this).initLoader(0,null, (LoaderManager.LoaderCallbacks<Cursor>)this);



            return root;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

        @Override
        public void onResume() {
            super.onResume();
            // console that reminderfragment onResume start
            Log.d(TAG, "onResume start");
            // use lock thred
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "onResume start");
                    // add adapter that will use to show incomplete reminder
                    myAdapter = new CalendarRecyclerViewAdapter(getContext(), calendarList);
                    recyclerView.setAdapter(myAdapter);
                }
            }, 10);
            // console that reminderfragment onResume end
            Log.d(TAG, "onResume end");
        }

        // add button in the action bar
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu items for use in the action bar
            inflater.inflate(R.menu.calendar_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                // this will use to add new reminder
                case R.id.addCalendarBtn:
                    // console that user click add new reminder btn
                    Log.d(TAGSQL, "user click add new reminder");
                    // initial intent that will use to call AddNewReminder class to render screen to add new reminder
                    Intent informationIntent = new Intent(getContext(), AddEventsToCalendar.class);
                    startActivity(informationIntent);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        // helper func to load sql
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            // Uri to the content provider LocationContentProvider
            Uri uri = CalendarProvider.CONTENT_URI;
            Log.d(TAGSQL, "onCreateLoader");
            return new CursorLoader(getContext(), uri, null, null, null, null);
        }


        // *****************
        @Override
        public void onLoadFinished(@NonNull Loader loader, Cursor cursor) {
            Log.d(TAG, "onLoadFinished");
            // variable that will use to store data from sql
            int reminderCount = 0;
            int id;
            String name = "name";
            String description;
            Boolean status;
            int priority;
            Date duedate;

            reminderCount = cursor.getCount();
            calendarList.clear();

            cursor.moveToFirst();
            for(int i = 0; i < reminderCount; i++) {
                // get id from sql
                id = cursor.getInt(cursor.getColumnIndex(CalendarProvider._ID));
                // get name from sql
                name = cursor.getString(cursor.getColumnIndex(CalendarProvider.NAME));

                Long dateFromSql = cursor.getLong(cursor.getColumnIndex(CalendarProvider.DUEDATE));
                duedate = new Date();
                duedate.setTime(dateFromSql);
                Log.d(TAG, "SQL");


                // add data to reminder class
                CalendarInterval calendar = new CalendarInterval(id, name, duedate);
                // add reminder to arraylist
                calendarList.add(calendar);
                // move cursor to the next one
                cursor.moveToNext();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        }


    }