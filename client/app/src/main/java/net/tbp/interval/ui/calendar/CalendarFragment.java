    package net.tbp.interval.ui.calendar;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
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

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.interval.R;
    import com.example.interval.databinding.FragmentCalendarBinding;

    import net.tbp.interval.ui.reminder.AddNewReminder;

    public class CalendarFragment extends Fragment {

        private CalendarViewModel calendarViewModel;
        private FragmentCalendarBinding binding;
        private String TAGSQL = "useraddnewcalendar";
        private CalendarView calendarView;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            calendarViewModel =
                    new ViewModelProvider(this).get(CalendarViewModel.class);

            binding = FragmentCalendarBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            calendarView = root.findViewById(R.id.calendar_1);

            //  set the page to have the option menu to have the add button
            setHasOptionsMenu(true);

//            final TextView textView = binding.calendar1.textView10.findViewById(R.id.editTextTextPersonName4);
//            textView.setText();
//            calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//                @Override
//                public void onChanged(@Nullable String s) {
//                    textView.setText(s);
 //               }
 //           });

//            Activity view = new Activity();
//            Button button = (Button) root.findViewById(R.id.getString);
//            button.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v) {
//                    // do something
//                    Log.d("myTag", "Fetching Task Data");
//                    final TextView stringTextView = (TextView) root.findViewById(R.id.addStringFromURL);
//                    // Instantiate the RequestQueue.
//                    RequestQueue queue = Volley.newRequestQueue(getActivity());
//                    //String url ="http://192.168.0.42:8080/test/all";
//                    String url ="https://www.google.com";
//
//                    // Request a string response from the provided URL.
//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    // Display the first 500 characters of the response string.
//                                    stringTextView.setText(response);
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            stringTextView.setText("That didn't work!");
//                        }
//                    });

                    // Add the request to the RequestQueue.
//                    queue.add(stringRequest);
//                }
//            });
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


    }