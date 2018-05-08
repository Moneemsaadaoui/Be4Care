package rrdl.be4care.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class FirebaseUpload {
    private Bitmap bitmap;
    private StorageReference mReference;
    private StorageReference mStorageRef;
    private Context mContext;
    private ImageView imageView;
    private ProgressDialog mProgressDialog;
    public FirebaseUpload(Context context, StorageReference ref, Bitmap bitmap, ImageView imageView,ProgressDialog dial) {
        mReference = ref;
        mProgressDialog=dial;
        this.bitmap = bitmap;
        mContext = context;
        this.imageView = imageView;
    }

    public void upload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] compressedimage = baos.toByteArray();

        StorageReference path = mReference.child("images/" + UUID.randomUUID().toString());

        path.putBytes(compressedimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(mContext, "Upload successful", Toast.LENGTH_SHORT).show();
                Log.i("TAG", downloadUrl.toString());
                Toast.makeText(mContext, downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
                Toast.makeText(mContext, "ERROR", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();

            }
        });
        imageView.setImageBitmap(bitmap);

    }

}