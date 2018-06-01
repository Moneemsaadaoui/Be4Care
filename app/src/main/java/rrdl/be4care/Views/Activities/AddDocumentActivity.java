package rrdl.be4care.Views.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mindorks.paracamera.Camera;

import java.io.File;
import java.io.IOException;

import rrdl.be4care.R;
import rrdl.be4care.Utils.CameraService;
import rrdl.be4care.Utils.CameraUtility;
import rrdl.be4care.Utils.FirebaseUpload;
import rrdl.be4care.Views.Fragments.AddDoc.InfoFragment;
import rrdl.be4care.Views.Fragments.AddDoc.PickPictureFragment;

public class AddDocumentActivity extends AppCompatActivity implements InfoFragment.OnFragmentInteractionListener, PickPictureFragment.OnFragmentInteractionListener {

    private String userChoosenTask;
    private int REQUEST_CAMERA;
    private int SELECT_FILE;
    private StorageReference mStorageRef;
    private Uri file;
    private int PICK_IMAGE_REQUEST;
    private ImageView imageView;
    private Uri filePath;
    private ImageButton uploadbtn;
    private Camera camera = null;
    private int GALLERY_REQUEST=19;
    private CameraService mCameraService;
    private FirebaseUpload mFirebaseUpload;
    private  ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        imageView = findViewById(R.id.testiv);
        Uri file = Uri.fromFile(new File("drawable/first.png"));
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(70)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        uploadbtn = findViewById(R.id.uploadbtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    camera.takePicture();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
              /*  final CharSequence[] items = {"Take Photo", "Choose from Library",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddDocumentActivity.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        boolean result = CameraUtility.checkPermission(AddDocumentActivity.this);
                        if (items[item].equals("Take Photo")) {
                            userChoosenTask = "Take Photo";
                            if (result)
                                                  } else if (items[item].equals("Choose from Library")) {
                            userChoosenTask = "Choose from Library";
                            if (result) {
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                            }
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                            finish();
                        }
                    }
                });
                builder.show();
            }*/
        });

      /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);}*/
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("imag/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     /*   if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = camera.getCameraBitmap();
            if (bitmap != null) {
                final ProgressDialog dialog = ProgressDialog.show(this, "",
                        "Chargement...", true);
                mFirebaseUpload=new FirebaseUpload(AddDocumentActivity.this,getApplicationContext(),mStorageRef,bitmap,imageView,dialog);
                mFirebaseUpload.upload();

            }
            else{
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    imageView.setImageBitmap(bitmap);
                    mFirebaseUpload=new FirebaseUpload(AddDocumentActivity.this,getApplicationContext(),mStorageRef,bitmap,imageView,dialog);
                    mFirebaseUpload.upload();
                } catch (IOException e) {
                    Log.i("TAG", "Some exception " + e);
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.deleteImage();
    }
}
