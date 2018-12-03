package ws.tilda.anastasia.catapp.ui.adapters;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import ws.tilda.anastasia.catapp.data.model.MainCat;

public class CustomBindingAdapter {

    @BindingAdapter({"catPhotoUrl"})

    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl)
                .centerCrop()
                .into(imageView);
    }

    public static void configureRecyclerView(RecyclerView recyclerView,
                                             List<MainCat> cats,
                                             CatsAdapter.OnItemClickListener onItemClickListener) {
        CatsAdapter catsAdapter = new CatsAdapter(cats, onItemClickListener);

    }
}
