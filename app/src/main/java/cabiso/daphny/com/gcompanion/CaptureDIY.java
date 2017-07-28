package cabiso.daphny.com.gcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cabiso.daphny.com.gcompanion.Fragments.HomePage;
import cabiso.daphny.com.gcompanion.Fragments.ImageHolder;

/**
 * Created by Lenovo on 7/23/2017.
 */

public class CaptureDIY extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgViewPhoto;
    private Button btnSave;
    private EditText price;
    private EditText diyName;
    private EditText category;

    private ProgressDialog mProgressDialog;

    private String userid;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private Uri ImagePathAndName;
    private Bitmap bitmap;

    private static final int CAMERA_REQUEST_CODE=1;

    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_diy);

        mStorageRef = FirebaseStorage.getInstance().getReference("Captured Images");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userid = user.getUid();

        mProgressDialog = new ProgressDialog(this);
        imgViewPhoto = (ImageView) findViewById(R.id.photoSaver);
        btnSave = (Button) findViewById(R.id.btnSave);
        price = (EditText) findViewById(R.id.etPrice);
        diyName = (EditText) findViewById(R.id.etName);
        category = (EditText) findViewById(R.id.etCategory);

        btnSave.setOnClickListener(this);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getApplication(),"savesavesavesave!",Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(CaptureDIY.this,MainActivity.class);
////                startActivity(intent);
//                startPosting();
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setType("image");
////                startActivityForResult(intent,GALLERY_INTENT);
//            }
//        });
        dispatchTakePictureIntent();
    }

    @Override
    public void onClick(View v) {
        if(v==btnSave){
            Toast.makeText(this,"Clicked!",Toast.LENGTH_SHORT).show();
            Toast.makeText(CaptureDIY.this, "YEEEEEEEEs!" + userid, Toast.LENGTH_SHORT).show();
//            uploadImage(ImagePathAndName);
//            StorageReference filePath = mStorageRef.child(userid).child(ImagePathAndName.getLastPathSegment());
            StorageReference filePath = mStorageRef.child(userid).child(bitmap.toString());
            showProgressDialog();
            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Toast.makeText(CaptureDIY.this,"Successfully uploaded image!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CaptureDIY.this,MainActivity.class);
                    startActivity(intent);
                    hideProgressDialog();
                }
            });
//            filePath.putFile(ImagePathAndName).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(CaptureDIY.this,"Successfully uploaded image!",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(CaptureDIY.this,MainActivity.class);
//                    startActivity(intent);
//                    hideProgressDialog();
//                }
//            });
        }else{Toast.makeText(CaptureDIY.this,"Failed to upload image!",Toast.LENGTH_SHORT).show();}
    }

    //request to capture image!
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode ==RESULT_OK){
//            if (resultCode == MainActivity.RESULT_OK){
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                // convert byte array to Bitmap
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                imgViewPhoto.setImageBitmap(bitmap);
                //fragment
//            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
