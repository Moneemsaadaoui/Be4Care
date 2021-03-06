package rrdl.be4care.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.telecom.Call;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rrdl.be4care.Models.Doctor;
import rrdl.be4care.Views.Activities.DocInfoActivity;

public class FirebaseUpload {
    private Bitmap bitmap;
    private StorageReference mReference;
    private StorageReference mStorageRef;
    private Context mContext;
    private ImageView imageView;
    private ProgressDialog mProgressDialog;
    private JsonObject link = new JsonObject();
    private Activity mActivity;
    ProgressDialog dialogan;
    public FirebaseUpload(Activity act,Context context, StorageReference ref, Bitmap bitmap, ImageView imageView, ProgressDialog dial) {
        mReference = ref;
        mProgressDialog = dial;
        this.bitmap = bitmap;
        mContext = context;
        mActivity=act;
        this.imageView = imageView;
    }

    public void upload() {
        final SharedPreferences prefs = mContext.getSharedPreferences("GLOBAL", Context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] compressedimage = baos.toByteArray();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://peaceful-forest-76384.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiservice = retrofit.create(ApiService.class);
        StorageReference path = mReference.child("images/" + UUID.randomUUID().toString());

        path.putBytes(compressedimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.i("TAG", downloadUrl.toString());
                link.addProperty("url", downloadUrl.toString());
                mProgressDialog.dismiss();

                final ProgressDialog dialog = ProgressDialog.show(mContext, "",
                        "Analyse en cours...", true);
                retrofit2.Call<JsonObject> analyse = apiservice.analyse(prefs.getString("AUTH", ""), link);
                analyse.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {

                            Intent intent = new Intent(mContext, DocInfoActivity.class);
                            intent.putExtra("ocr", response.body().toString());
                            intent.putExtra("url", downloadUrl.toString());
                            mActivity.startActivity(intent);
                            Toasty.success(mContext,"document ajouté avec succes").show();
                        } else {
                            Toasty.error(mContext,"Erreur de l'analyse automatique").show();


                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                        Intent infointent=new Intent(mContext,DocInfoActivity.class);
                        infointent.putExtra("url",downloadUrl.toString());
                        mActivity.startActivity(infointent);
                        Toasty.error(mContext,"Erreur de l'analyse automatique").show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toasty.error(mContext, "Erreur", Toast.LENGTH_SHORT).show();
                // ...

                mProgressDialog.dismiss();


            }
        });
        imageView.setImageBitmap(bitmap);

    }

}