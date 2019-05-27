package tugoapps.com.sharing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.OnPostClickCallBack;


public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
  TextView txt_title, txt_author, txt_content;
  OnPostClickCallBack onPostClickCallBack;

  public PostViewHolder(@NonNull View itemView) {
    super(itemView);
    txt_title = itemView.findViewById(R.id.txt_title);
    txt_content = itemView.findViewById(R.id.txt_content);
    txt_author = itemView.findViewById(R.id.txt_author);
    itemView.setOnClickListener(this);
    itemView.setOnLongClickListener(this);
  }

  public void setOnPostClickCallBack(OnPostClickCallBack listener) {
    this.onPostClickCallBack = listener;
  }

  public void setOnPostLongClick(OnPostClickCallBack listener) {
    this.onPostClickCallBack = listener;
  }

  @Override
  public void onClick(View v) {
    onPostClickCallBack.onClick(v, getAdapterPosition());
  }

  @Override
  public boolean onLongClick(View v) {
    onPostClickCallBack.onLongClick(v, getAdapterPosition());
    return false;
  }
}
