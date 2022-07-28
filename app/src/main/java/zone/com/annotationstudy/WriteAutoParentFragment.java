package zone.com.annotationstudy;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zone.AutoBundle;

import java.util.List;

public class WriteAutoParentFragment extends Fragment {

    @AutoBundle
    String keyParentString;
    @AutoBundle
    public boolean keyBooleanParent;
    private final WriteAutoParentFragmentAutoBundleInjector writeAutoParentFragmentAutoBundleInjector = new WriteAutoParentFragmentAutoBundleInjector(this);

    public WriteAutoParentFragmentAutoBundleInjector getWriteAutoParentFragmentAutoBundleInjector() {
        return writeAutoParentFragmentAutoBundleInjector;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("WriteAutoFragment","onAttach");
        writeAutoParentFragmentAutoBundleInjector.decode(getArguments())
//                .keyDefaults()
                .resolve();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("WriteAutoFragment","onSaveInstanceState");
        writeAutoParentFragmentAutoBundleInjector.autoEncode(outState)
                .save();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("WriteAutoFragment","onViewStateRestored");
        writeAutoParentFragmentAutoBundleInjector.decode(savedInstanceState)
                .resolve();
    }
}
