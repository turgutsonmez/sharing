package tugoapps.com.sharing.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

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
import tugoapps.com.sharing.adapter.PhotosAdapter;
import tugoapps.com.sharing.model.ItemClickListener;
import tugoapps.com.sharing.model.Photos;

public class PhotosActivity extends AppCompatActivity {

  SharedPreferences pref;
  private List<Photos> photosList = new ArrayList<>();
  String albumId;
  RecyclerView recyclerView_photos;
  PhotosAdapter photosAdapter;
  IMyAPI myAPI;
  CompositeDisposable compositeDisposable = new CompositeDisposable();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photos);

    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    albumId = pref.getString("prefAlbumId", "");


    photosAdapter = new PhotosAdapter(this, photosList, new ItemClickListener() {
      @Override
      public void onClick(View view, int position) {
        SharedPreferences.Editor editor2 = pref.edit();
        editor2.putString("prefPhotosId", String.valueOf(photosList.get(position).id));
        editor2.putString("prefPhotosUrl", photosList.get(position).url);
        editor2.putString("prefPhotosTitle", photosList.get(position).title);
        editor2.apply();
        Intent intent=new Intent(PhotosActivity.this, DetailsPhotosActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
      }
    });
    recyclerView_photos = findViewById(R.id.recyclerview_photos);
    recyclerView_photos.setHasFixedSize(true);
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
    recyclerView_photos.setLayoutManager(staggeredGridLayoutManager);
    recyclerView_photos.setAdapter(photosAdapter);

    //init api
    Retrofit retrofit = RetrofitClient.getInstance();
    myAPI = retrofit.create(IMyAPI.class);

    fetchData();
  }

  private void fetchData() {
    compositeDisposable.add(myAPI.getAlbumsPhotos(Integer.valueOf(albumId))
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Consumer<List<Photos>>() {
        @Override
        public void accept(List<Photos> photos) throws Exception {
          photosList.clear();
          photosList.addAll(photos);
          photosAdapter.notifyDataSetChanged();
          //recyclerView_posts.setAdapter(postAdapter);
          //displayData(posts);
        }

      }));
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
  }
}
