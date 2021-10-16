package net.tbp.interval.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewaccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewaccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}