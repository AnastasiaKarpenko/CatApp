package ws.tilda.anastasia.catapp.ui.cat;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CatFragment extends Fragment {
    public static final String CAT_ID_KEY = "CAT_ID_KEY";

    public static CatFragment newInstance(Bundle bundleExtra) {
        return new CatFragment();
    }
}
