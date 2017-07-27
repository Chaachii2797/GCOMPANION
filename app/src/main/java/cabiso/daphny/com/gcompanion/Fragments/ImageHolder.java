package cabiso.daphny.com.gcompanion.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cabiso.daphny.com.gcompanion.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageHolder extends Fragment {


    public ImageHolder() {
        // Required empty public constructor
    }
    private ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_image_holder, container, false);
        return view;
    }

}
