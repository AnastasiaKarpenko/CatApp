package ws.tilda.anastasia.catapp.ui.adapters;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
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

    @BindingAdapter({"bind:data", "bind:clickHandler"})
    public static void configureRecyclerView(RecyclerView recyclerView,
                                             List<MainCat> cats,
                                             CatsAdapter.OnItemClickListener onItemClickListener) {
        CatsAdapter catsAdapter = new CatsAdapter(cats, onItemClickListener);
        recyclerView.setAdapter(catsAdapter);

    }

    @BindingAdapter({"bind:refreshState", "bind:onRefresh"})
    public static void configureSwipeRefreshLayout(SwipeRefreshLayout layout, boolean isLoading,
                                                   SwipeRefreshLayout.OnRefreshListener listener) {

        layout.setOnRefreshListener(listener);
        layout.post(() -> layout.setRefreshing(isLoading));
    }
}
