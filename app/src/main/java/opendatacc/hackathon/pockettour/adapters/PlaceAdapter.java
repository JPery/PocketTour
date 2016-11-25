package opendatacc.hackathon.pockettour.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackathonopendatacc.pockettour.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import opendatacc.hackathon.pockettour.model.PlaceItem;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private final static String TAG = "PlaceAdapter";

    private List<PlaceItem> placeItemDataSet;

    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(PlaceItem placeItem);
    }

    public PlaceAdapter(List<PlaceItem> placeItemDataSet, OnItemClickListener onItemClickListener) {
        this.placeItemDataSet = placeItemDataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(placeItemDataSet.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return placeItemDataSet.size();
    }

    public void add(PlaceItem placeItem) {
        int position = getPosition(placeItem.getId());
        if (position == -1) {
            placeItemDataSet.add(placeItem);
            notifyDataSetChanged();
        }
    }

    public void remove(int placeItemId) {
        int position = getPosition(placeItemId);
        if (position != -1) {
            placeItemDataSet.remove(position);
            notifyDataSetChanged();
        }
    }

    private int getPosition(int placeItemId) {
        for (int i = 0; i < placeItemDataSet.size(); i++) {
            if (placeItemDataSet.get(i).getId() == placeItemId) {
                return i;
            }
        }
        return -1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_place_uri)
        TextView textViewUri;
        @BindView(R.id.item_place_image)
        ImageView imageViewPhoto;
        @BindView(R.id.item_place_name)
        TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final PlaceItem placeItem, final OnItemClickListener onItemClickListener) {

            ImageLoader imageLoader = ImageLoader.getInstance();

            textViewName.setText(placeItem.getName());
            textViewUri.setText(placeItem.getResourceURL().toString());


            imageLoader.displayImage(placeItem.getPhotoLocation(), imageViewPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(placeItem);
                }
            });
        }
    }
}