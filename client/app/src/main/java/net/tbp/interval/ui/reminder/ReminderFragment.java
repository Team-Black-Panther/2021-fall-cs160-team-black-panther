package net.tbp.interval.ui.reminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interval.R;
import com.example.interval.databinding.FragmentReminderBinding;


import java.util.ArrayList;
import java.util.List;

public class ReminderFragment extends Fragment  {
    private List<Reminder> reminderList = new ArrayList<>();
    private ReminderViewModel reminderViewModel;
    private FragmentReminderBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private  RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);

        binding = FragmentReminderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        // giraffe
        String giraffeDescription = "Write Rust as parser";
        Reminder reminder = new Reminder(1,"HW CS152", giraffeDescription, false);
        reminderList.add(reminder);

        // rhino
        String rhinoDescription = "Write essay 3-5 pages";
        reminder = new Reminder(2,"Essay NUF163", rhinoDescription, false );
        reminderList.add(reminder);

        // sloth
        String slothDescription = "Work on the reminder. Need to finish it!!";
        reminder = new Reminder(3,"Proj CS160", slothDescription, false);
        reminderList.add(reminder);

        // wolf
        String wolfDescription = "Tell the work progress";
        reminder = new Reminder(4,"Update proj CS160", wolfDescription, false);
        reminderList.add(reminder);

        // zebra
        String zebraDescription = "Buy milk for morning coffee :)";
        reminder = new Reminder( 5,"Buy milk",zebraDescription, false);
        reminderList.add(reminder);

        myAdapter = new ReminderRecyclerViewAdapter(getContext(), reminderList);
        recyclerView.setAdapter(myAdapter);











        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // add button in the action bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.reminder_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}