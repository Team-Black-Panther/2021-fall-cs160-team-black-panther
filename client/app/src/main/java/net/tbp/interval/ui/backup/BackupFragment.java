package net.tbp.interval.ui.backup;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.interval.databinding.FragmentBackupBinding;

import net.tbp.interval.ui.reminder.AddNewReminder;
import net.tbp.interval.ui.reminder.Reminder;
import net.tbp.interval.ui.reminder.ReminderProvider;

import java.util.Date;

public class BackupFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    String TAG = "BackupFragment";
    String TAGSQL = "UserSQL";
    private BackupViewModel backupViewModel;
    private FragmentBackupBinding binding;
    int userCount = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        backupViewModel =
                new ViewModelProvider(this).get(BackupViewModel.class);

        binding = binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // load user database
        LoaderManager.getInstance(this).initLoader(0,null, (LoaderManager.LoaderCallbacks<Cursor>)this);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Uri to the content provider LocationContentProvider
        Uri uri = UserProvider.CONTENT_URI;
        Log.d(TAGSQL, "onCreateLoader");
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished");
        // variable that will use to store data from sql
        userCount = 0;
        userCount = cursor.getCount();

        if(userCount < 1){
            // console that user click add new reminder btn
            Log.d(TAGSQL, "user click add new reminder");
            // initial intent that will use to call AddNewReminder class to render screen to add new reminder
            Intent informationIntent = new Intent(getContext(), SigninSignUp.class);
            startActivity(informationIntent);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}