package tugoapps.com.sharing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.ItemClickListener;
import tugoapps.com.sharing.model.Photos;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

  private List<Photos> photosList;
  private Context context;
  ItemClickListener itemClickListener;

  public PhotosAdapter(Context context, List<Photos> photosList, ItemClickListener listener) {
    this.photosList = photosList;
    this.context = context;
    this.itemClickListener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_photo, null);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    viewHolder.title.setText(String.valueOf(photosList.get(position).title));
    RequestOptions requestOptions = new RequestOptions()
      .placeholder(R.drawable.mylogo);

    Glide.with(context)
      .load(photosList.get(position).thumbnailUrl)
      .apply(requestOptions)
      .into(viewHolder.image);

    viewHolder.setItemClickListener(new ItemClickListener() {
      @Override
      public void onClick(View view, int position) {
        itemClickListener.onClick(view, position);
      }
    });
  }

  @Override
  public int getItemCount() {
    return photosList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title;
    ImageView image;
    ItemClickListener itemClickListener;

    public ViewHolder(View itemView) {
      super(itemView);
      this.title = itemView.findViewById(R.id.text_photosTitle);
      this.image = itemView.findViewById(R.id.image);
      itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener listener) {
      this.itemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
      itemClickListener.onClick(v, getAdapterPosition());
    }
  }
}
