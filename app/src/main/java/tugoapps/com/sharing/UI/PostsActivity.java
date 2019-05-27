package tugoapps.com.sharing.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tugoapps.com.sharing.R;
import tugoapps.com.sharing.Retrofit.IMyAPI;
import tugoapps.com.sharing.Retrofit.RetrofitClient;
import tugoapps.com.sharing.adapter.PostAdapter;
import tugoapps.com.sharing.model.OnPostClickCallBack;
import tugoapps.com.sharing.model.Post;

public class PostsActivity extends AppCompatActivity {

  SharedPreferences pref;
  String userid, title, body, updateTitle, updateBody;
  int id;
  IMyAPI myAPI;
  RecyclerView recyclerView_posts;
  CompositeDisposable compositeDisposable = new CompositeDisposable();
  FloatingActionButton fabButton;
  List<Post> postsList = new ArrayList<>();
  PostAdapter postAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_posts);
    fabButton = findViewById(R.id.fabButton);

    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    userid = pref.getString("prefUserId", "");
    title = pref.getString("prefPostTitle", "");
    body = pref.getString("prefPostBody", "");

    id = pref.getInt("prefEditPostId", 0);
    updateTitle = pref.getString("prefEditPostTitle", "");
    updateBody = pref.getString("prefEditPostBody", "");


    postAdapter = new PostAdapter(this, postsList, new OnPostClickCallBack() {
      @Override
      public void onClick(View view, int position) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("prefEditPostTitle", postsList.get(position).title);
        editor.putString("prefEditPostBody", postsList.get(position).body);
        editor.putInt("prefEditPostId", postsList.get(position).id);
        editor.putInt("prefEditPostPosition", position);
        editor.apply();
        Intent intent=new Intent(PostsActivity.this, EditPostActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
      }

      @Override
      public void onLongClick(View view, int position) {
        final int pos = position;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            switch (which) {
              case DialogInterface.BUTTON_POSITIVE:
                deletePost(id, pos);
                Toast.makeText(PostsActivity.this, "Silindi", Toast.LENGTH_LONG).show();
                break;
              case DialogInterface.BUTTON_NEGATIVE:
                break;
            }
          }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(PostsActivity.this);
        builder.setMessage("Silmek istiyormusun").setPositiveButton(("Evet"), onClickListener)
          .setNegativeButton("Hayır", onClickListener).show();

      }
    });

    //init api
    Retrofit retrofit = RetrofitClient.getInstance();
    myAPI = retrofit.create(IMyAPI.class);


    fabButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(PostsActivity.this, NewPostActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
      }

    });

    recyclerView_posts = findViewById(R.id.recyclerview_posts);
    recyclerView_posts.setHasFixedSize(true);
    recyclerView_posts.setLayoutManager(new LinearLayoutManager(this));
    recyclerView_posts.setAdapter(postAdapter);


    update(id, updateTitle, updateBody, pref.getInt("prefEditPostPosition", 0));
    sendNewPost(title, body);
    fetchData();

  }

  private void fetchData() {
    compositeDisposable.add(myAPI.getPosts(Integer.valueOf(userid))
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Consumer<List<Post>>() {
        @Override
        public void accept(List<Post> posts) throws Exception {
          postsList.clear();
          postsList.addAll(posts);
          postAdapter.notifyDataSetChanged();
          //recyclerView_posts.setAdapter(postAdapter);
          //displayData(posts);
        }
      }));
  }

  public void sendNewPost(final String title, final String body) {
    myAPI.setPosts(Integer.valueOf(userid), title, body)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Observer<Post>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Post post) {
          postsList.add(post);
          //postAdapter.notifyItemInserted(0);
          postAdapter.notifyDataSetChanged();

        }


        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
        }
      });
  }


  public void update(int id, final String title, final String body, final int position) {
    compositeDisposable.add(myAPI.updatePost(id, title, body)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(new DisposableCompletableObserver() {
        @Override
        public void onComplete() {
          Post post = postsList.get(position);
          post.setTitle(title);
          post.setBody(body);

          postsList.set(position, post);
          postAdapter.notifyItemChanged(position);
        }

        @Override
        public void onError(Throwable e) {

        }
      }));

  }

  public void deletePost(final int id, final int position) {
    compositeDisposable.add(myAPI.deletePost(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeWith(new DisposableCompletableObserver() {
        @Override
        public void onComplete() {
          postsList.remove(position);
          postAdapter.notifyItemRemoved(position);
        }

        @Override
        public void onError(Throwable e) {

        }
      })
    );
  }

  @Override
  protected void onStop() {
    compositeDisposable.clear();
    super.onStop();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
  }

}
