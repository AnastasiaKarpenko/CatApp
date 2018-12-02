package ws.tilda.anastasia.catapp.ui.favoritecats;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.catapp.R;
import ws.tilda.anastasia.catapp.data.model.FavoriteCat;

public class FavoriteCatsAdapter extends RecyclerView.Adapter<FavoriteCatsAdapter.FavoriteCatsViewHolder> {


    @NonNull
    private final List<FavoriteCat> mFavoriteCats = new ArrayList<>();
    private final OnItemClickListener mOnItemClickListener;

    public FavoriteCatsAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FavoriteCatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_item_cat_layout, viewGroup, false);
        return new FavoriteCatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteCatsViewHolder favoriteCatsViewHolder, int i) {
        FavoriteCat favoriteCat = mFavoriteCats.get(i);
        favoriteCatsViewHolder.bind(favoriteCat, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mFavoriteCats.size();
    }


    void addData(List<FavoriteCat> data, boolean isRefreshed) {
        if (isRefreshed) {
            mFavoriteCats.clear();
        }

        mFavoriteCats.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(String catId);
    }


    class FavoriteCatsViewHolder extends RecyclerView.ViewHolder {
        ImageView mCatPhoto;

        FavoriteCatsViewHolder(@NonNull View itemView) {
            super(itemView);
            mCatPhoto = itemView.findViewById(R.id.cat_photo_iv);
        }

        void bind(FavoriteCat favoriteCat, OnItemClickListener listener) {
            String url = favoriteCat.getImage().getUrl();

//        Tried to implement the logic of loading the images, but it does not work. Need to review later
//        Bitmap photo = getBitmap(url);
//        mCatPhoto.setImageBitmap(photo);


            //Decided to use Glide for now
            Glide.with(mCatPhoto.getContext()).load(url)
                    .centerCrop()
                    .into(mCatPhoto);

            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(
                        favoriteCat.getImageId()
                ));
            }
        }

        private Bitmap getBitmap(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;

            } catch (Exception e) {
                return null;
            }
        }

    }
}
