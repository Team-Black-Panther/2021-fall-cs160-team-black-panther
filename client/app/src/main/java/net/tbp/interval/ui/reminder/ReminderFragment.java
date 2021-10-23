package net.tbp.interval.ui.reminder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.interval.R;
import com.example.interval.databinding.FragmentReminderBinding;


import java.util.ArrayList;
import java.util.List;

// this class will use to render the reminder page
public class ReminderFragment extends Fragment  {
    private List<Reminder> reminderList = new ArrayList<>();        // list that will store reinder
    private ReminderViewModel reminderViewModel;
    private FragmentReminderBinding binding;
    private RecyclerView recyclerView;                              // recyclerview that create cell to store each reminder
    private RecyclerView.Adapter myAdapter;                         // will use to render reclerview
    private RecyclerView.LayoutManager layoutManager;               // layout of reclerview
    private RecyclerView completedRecyclerView;                     // recyclerview that create cell to store each reminder
    private RecyclerView.Adapter completedMyAdapter;                // will use to render reclerview
    private RecyclerView.LayoutManager completedLayoutManager;
    private FragmentActivity myContext;

    // func to set view when user load page
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        binding = FragmentReminderBinding.inflate(inflater, container, false);
        // to get root of the binding in this case it will mean fragment_reminder
        View root = binding.getRoot();

        //  set the page to have the option menu to have the add button
        setHasOptionsMenu(true);

        // textView
        reminderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        // recyclerView
        recyclerView = (RecyclerView) root.findViewById(R.id.reminderRecyclerView);
        // Use this setting to improve performance when knowing that change
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // Use linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // completeRecyclerView
        completedRecyclerView = (RecyclerView) root.findViewById(R.id.completedReminderRecyclerView);
        // Use this setting to improve performance when knowing that change
        // in content do not change the layout size of the RecyclerView
        completedRecyclerView.setHasFixedSize(true);
        // Use linear layout manager
        completedLayoutManager = new LinearLayoutManager(getContext());
        completedRecyclerView.setLayoutManager(completedLayoutManager);

        String task1 = "Write Rust as parser";
        Reminder reminder = new Reminder(1,"HW CS152", task1, false);
        reminderList.add(reminder);

        String task2 = "Write essay 3-5 pages";
        reminder = new Reminder(2,"Essay NUF163", task2, false );
        reminderList.add(reminder);

        String task3 = "Work on the reminder. Need to finish it!!";
        reminder = new Reminder(3,"Proj CS160", task3, true);
        reminderList.add(reminder);

        String task4 = "Tell the work progress";
        reminder = new Reminder(4,"Update proj CS160", task4, true);
        reminderList.add(reminder);

        String task5 = "Buy milk for morning coffee :)";
        reminder = new Reminder( 5,"Buy milk",task5, false);
        reminderList.add(reminder);

        myAdapter = new ReminderRecyclerViewAdapter(getContext(), reminderList);
        recyclerView.setAdapter(myAdapter);

        completedMyAdapter = new CompletedReminderRecyclerViewAdapter(getContext(), reminderList);
        completedRecyclerView.setAdapter(completedMyAdapter);
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
    }

    // add button in the action bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.reminder_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;
        if (context instanceof Activity){
            a = (Activity) context;
        }
    }

}