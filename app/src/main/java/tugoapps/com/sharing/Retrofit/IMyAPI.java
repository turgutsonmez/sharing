package tugoapps.com.sharing.Retrofit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tugoapps.com.sharing.model.Albums;
import tugoapps.com.sharing.model.Photos;
import tugoapps.com.sharing.model.Post;
import tugoapps.com.sharing.model.Users;

public interface IMyAPI {
  @GET("users")
  Observable<List<Users>> getUsers();

  @GET("posts")
  Observable<List<Post>> getPosts(
    @Query("userId") int id
  );

  @POST("posts")
  @FormUrlEncoded
  Observable<Post> setPosts(
    @Field("userId") int id,
    @Field("title") String title,
    @Field("body") String body
  );

  @PUT("posts/{id}")
  @FormUrlEncoded
  Observable<Post> putPost(
    @Path("id") int id,
    @Field("title") String title,
    @Field("body") String body
  );

  @GET("albums")
  Observable<List<Albums>> getAlbums(
    @Query("userId") int id
  );

  @GET("photos")
  Observable<List<Photos>> getAlbumsPhotos(
    @Query("albumId") int id
  );

  @FormUrlEncoded
  @PUT("posts/{id}")
  Completable updatePost(
    @Path("id") int id,
    @Field("title") String title,
    @Field("body") String body
  );

  @DELETE("posts/{id}")
  Completable deletePost(
    @Path("id") int id
  );
}
