package cabiso.daphny.com.gcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import cabiso.daphny.com.gcompanion.Fragments.DIYCommunity;

public class UploadImage extends AppCompatActivity implements View.OnClickListener{

    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView imgView;
    private Button button;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    Uri ImagePathAndName;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imgView = (ImageView) findViewById(R.id.imgUpload);
        button = (Button) findViewById(R.id.buttonSave);
        button.setOnClickListener(this);

        Fragment fragment = new DIYCommunity();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap", bitmap);
        fragment.setArguments(bundle);
        fetchImage();

    }


    @Override
    public void onClick(View v) {
        if (v == button) {
            Toast.makeText(UploadImage.this, "YEEEEEEEEs!", Toast.LENGTH_SHORT).show();
//            uploadImage(ImagePathAndName);
        }
    }
//
//    private void uploadImage(Uri ImagePathAndName) {
//        storage = FirebaseStorage.getInstance();
//        storageRef = storage.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(ImagePathAndName);
//    }

    private void fetchImage(){
        Intent ImageIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //implicit intent
        UploadImage.this.startActivityForResult(ImageIntent,RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            ImagePathAndName = data.getData();
            imgView.setImageURI(ImagePathAndName);
        }
    }
}
