package tugoapps.com.sharing.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import tugoapps.com.sharing.R;

public class DetailsPhotosActivity extends AppCompatActivity {

  SharedPreferences pref;
  String photoID;
  String photoUrl;
  String photoTitle;
  TextView detailTitle;
  ImageView detailImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details_photos);
    pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

    photoID = pref.getString("prefPhotosId", "");
    photoUrl = pref.getString("prefPhotosUrl", "");
    photoTitle = pref.getString("prefPhotosTitle", "");


    detailImage = findViewById(R.id.details_imageView);
    detailTitle = findViewById(R.id.txt_detailsTitle);


    fetchData();
  }


  private void fetchData() {
    RequestOptions requestOptions = new RequestOptions()
      .placeholder(R.drawable.mylogo);
    Glide.with(DetailsPhotosActivity.this)
      .load(photoUrl)
      .apply(requestOptions)
      .into(detailImage);

    detailTitle.setText(photoTitle);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right );
  }
}
