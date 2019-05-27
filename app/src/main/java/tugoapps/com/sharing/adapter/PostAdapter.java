package tugoapps.com.sharing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tugoapps.com.sharing.R;
import tugoapps.com.sharing.model.OnPostClickCallBack;
import tugoapps.com.sharing.model.Post;


public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

  Context context;
  List<Post> postList;
  OnPostClickCallBack onPostClickCallBack;


  public PostAdapter(Context context, List<Post> postList, OnPostClickCallBack onPostClickCallBack) {
    this.onPostClickCallBack = onPostClickCallBack;
    this.context = context;
    this.postList = postList;
  }

  public PostAdapter(Context context, List<Post> postList) {
    this.context = context;
    this.postList = postList;
  }


  @NonNull
  @Override
  public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_post, viewGroup, false);
    return new PostViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {
    postViewHolder.txt_author.setText(String.valueOf(postList.get(position).id));
    postViewHolder.txt_title.setText(String.valueOf(postList.get(position).title));
    postViewHolder.txt_content.setText(new StringBuilder(postList.get(position).body));
    postViewHolder.setOnPostClickCallBack(new OnPostClickCallBack() {
      @Override
      public void onClick(View view, int position) {
        onPostClickCallBack.onClick(view, position);
      }

      @Override
      public void onLongClick(View view, int position) {
        onPostClickCallBack.onLongClick(view, position);
      }
    });

  }

  @Override
  public int getItemCount() {
    return postList.size();
  }

  public void newPost(Post post) {
    postList.add(post);
    notifyDataSetChanged();
  }
}
