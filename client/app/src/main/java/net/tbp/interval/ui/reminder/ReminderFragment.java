package net.tbp.interval.ui.reminder;

import android.os.Bundle;
import android.view.LayoutInflater;
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

        // textView
        final TextView saveStatus = root.findViewById(R.id.saveStatus);
        reminderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                saveStatus.setText(s);
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









        // task dashboard init
        final Button taskSaveButton = root.findViewById(R.id.saveButton);
        taskSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText taskNameET = (EditText) root.findViewById(R.id.taskName);
                String taskName = taskNameET.getText().toString();

//                RequestQueue queue = Volley.newRequestQueue(getActivity());
//                String url ="http://192.168.0.42:8080/test/add";
//                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError e) {
//                        e.printStackTrace();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("name", taskName);
//                        return params;
//                    }
//                };
//
//                System.out.println(request.toString());
//                queue.add(request);
//                taskNameET.getText().clear();
            }
        }) ;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}