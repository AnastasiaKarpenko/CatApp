package ws.tilda.anastasia.catapp.ui.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomBindingAdapter {

    @BindingAdapter("bind:catPhotoUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl)
                .centerCrop()
                .into(imageView);
    }
}
