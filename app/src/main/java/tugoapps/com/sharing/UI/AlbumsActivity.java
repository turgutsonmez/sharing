package tugoapps.com.sharing.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tugoapps.com.sharing.R;
import tugoapps.com.sharing.Retrofit.IMyAPI;
import tugoapps.com.sharing.Retrofit.RetrofitClient;
import tugoapps.com.sharing.adapter.AlbumAdapter;
import tugoapps.com.sharing.model.Albums;
import tugoapps.com.sharing.model.ItemClickListener;

public class AlbumsActivity extends AppCompatActivity {

  SharedPreferences pref;
  List<Albums> albumsList = new ArrayList<>();
  RecyclerView recyclerView_albums;
  AlbumAdapter albumAdapter;
  IMyAPI myAPI;
  CompositeDisposable compositeDisposable = new CompositeDisposable();
  String userid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_albums);
    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

    albumAdapter = new AlbumAdapter(this, albumsList, new ItemClickListener() {
      @Override
      public void onClick(View view, int position) {
        SharedPreferences.Editor editor2 = pref.edit();
        editor2.putString("prefAlbumId", String.valueOf(albumsList.get(position).id));
        editor2.apply();
        Intent intent = new Intent(AlbumsActivity.this, PhotosActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
      }
    });

    recyclerView_albums = findViewById(R.id.recyclerview_albums);
    recyclerView_albums.setHasFixedSize(true);
    recyclerView_albums.setLayoutManager(new LinearLayoutManager(this));
    recyclerView_albums.setAdapter(albumAdapter);

    userid = pref.getString("prefUserId", "");


    //init api
    Retrofit retrofit = RetrofitClient.getInstance();
    myAPI = retrofit.create(IMyAPI.class);

    fetchData();
  }

  private void fetchData() {
    compositeDisposable.add(myAPI.getAlbums(Integer.valueOf(userid))
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Consumer<List<Albums>>() {
        @Override
        public void accept(List<Albums> albums) throws Exception {
          albumsList.clear();
          albumsList.addAll(albums);
          albumAdapter.notifyDataSetChanged();
          //recyclerView_posts.setAdapter(postAdapter);
          //displayData(posts);
        }

      }));
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
  }
}
