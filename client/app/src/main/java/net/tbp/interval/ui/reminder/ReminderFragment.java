package net.tbp.interval.ui.reminder;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interval.R;
import com.example.interval.databinding.FragmentReminderBinding;


import net.tbp.interval.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// this class will use to render the reminder page
public class ReminderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    String TAG = "ReminderFragment";
    String TAGSQL = "ReminderSQL";
    private List<Reminder> reminderList = new ArrayList<>();        // list that will store reinder
    private ReminderViewModel reminderViewModel;
    private FragmentReminderBinding binding;
    private RecyclerView recyclerView;                              // recyclerview that create cell to store each reminder
    private RecyclerView.Adapter myAdapter;                         // will use to render reclerview
    private RecyclerView.LayoutManager layoutManager;               // layout of reclerview
    private FragmentActivity myContext;

    // func to set view when user load page
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        binding = FragmentReminderBinding.inflate(inflater, container, false);
        // to get root of the binding in this case it will mean fragment_reminder
        View root = binding.getRoot();
        //  set the page to have the option menu to have the add button
        setHasOptionsMenu(true);
        // console that reminderfragment oncreate start
        Log.d(TAG, "oncreate start");

        // textView
        reminderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        // load reminder database
        LoaderManager.getInstance(this).initLoader(0,null, (LoaderManager.LoaderCallbacks<Cursor>)this);

        // set up recyclerView to show incomplete reminder
        recyclerView = (RecyclerView) root.findViewById(R.id.reminderRecyclerView);
        // Use this setting to improve performance when knowing that change
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // Use linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // console that reminderfragment oncreate end
        Log.d(TAG, "oncreate end");
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
                myAdapter = new ReminderRecyclerViewAdapter(getContext(), reminderList);
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
        inflater.inflate(R.menu.reminder_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // this will later link to new intent
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        Activity a;
        if (context instanceof Activity){
            a = (Activity) context;
        }
    }

    // func that will call when user click button on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // this will use to add new reminder
            case R.id.addReminderBtn:
                // console that user click add new reminder btn
                Log.d(TAGSQL, "user click add new reminder");
                // initial intent that will use to call AddNewReminder class to render screen to add new reminder
                Intent informationIntent = new Intent(getContext(), AddNewReminder.class);
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
        Uri uri = ReminderProvider.CONTENT_URI;
        Log.d(TAGSQL, "onCreateLoader");
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }

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
        reminderList.clear();

        cursor.moveToFirst();
        for(int i = 0; i < reminderCount; i++) {
            // get id from sql
            id = cursor.getInt(cursor.getColumnIndex(ReminderProvider._ID));
            // get name from sql
            name = cursor.getString(cursor.getColumnIndex(ReminderProvider.NAME));
            // get description from sql
            description = cursor.getString(cursor.getColumnIndex(ReminderProvider.DESCRIPTION));
            // get status from sql
            status = cursor.getInt(cursor.getColumnIndex(ReminderProvider.STATUS)) > 0;
            priority = cursor.getInt(cursor.getColumnIndex(ReminderProvider.PRIORITY));
            Long dateFromSql = cursor.getLong(cursor.getColumnIndex(ReminderProvider.DUEDATE));
            duedate = new Date();
            duedate.setTime(dateFromSql);
            Log.d(TAGSQL, " id: " + id + " name: " + name + " description: " +
                    description + " status: " + status + " priority: " + priority
                    + " duedate: " + duedate);

            // add data to reminder class
            Reminder reminder = new Reminder( id, name, description, status, priority, duedate);
            // add reminder to arraylist
            reminderList.add(reminder);
            // move cursor to the next one
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }
}