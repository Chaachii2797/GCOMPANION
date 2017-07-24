package cabiso.daphny.com.gcompanion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Lenovo on 7/23/2017.
 */

public class CaptureDIY extends AppCompatActivity {

    static final int CAPTURE_IMAGE_CODE = 2;

    private ImageView imgViewPhoto;
    private Button btnSave;
    private EditText price;
    private EditText diyName;
    private EditText category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_diy);


        imgViewPhoto = (ImageView) findViewById(R.id.photoSaver);
        btnSave = (Button) findViewById(R.id.btnSave);
        price = (EditText) findViewById(R.id.etPrice);
        diyName = (EditText) findViewById(R.id.etName);
        category = (EditText) findViewById(R.id.etCategory);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaptureDIY.this,MainActivity.class);
                startActivity(intent);
            }
        });
        dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_CODE) {
            if (resultCode == MainActivity.RESULT_OK){
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                imgViewPhoto.setImageBitmap(bitmap);

                //fragment
            }
        }
    }


}
