package net.tbp.interval.ui.tracker;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.interval.R;
import com.example.interval.databinding.FragmentTrackerBinding;

import java.util.HashMap;
import java.util.Map;


public class TrackerFragment extends Fragment {

    private TrackerViewModel trackerViewModel;
    private FragmentTrackerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trackerViewModel =
                new ViewModelProvider(this).get(TrackerViewModel.class);

        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // textView
        final TextView saveStatus = root.findViewById(R.id.saveTracker);
        final TextView textView = binding.textNotifications;
        trackerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // task dashboard init
//        final Button taskSaveButton = root.findViewById(R.id.saveButtonTracker);
//        taskSaveButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                EditText taskNameET = (EditText) root.findViewById(R.id.trackerName);
//                String taskName = taskNameET.getText().toString();
//
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
//            }
//        }) ;





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}