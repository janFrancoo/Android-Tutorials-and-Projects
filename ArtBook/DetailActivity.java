package com.janfranco.artbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    Bitmap selectedImage;
    SQLiteDatabase database;
    EditText editText, editText2, editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        imageView = findViewById(R.id.imageView);
        database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,
                null);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        if(info.matches("new")) {
            editText.setEnabled(true);
            editText2.setEnabled(true);
            editText3.setEnabled(true);
            editText.setText("");
            editText2.setText("");
            editText3.setText("");
            button.setVisibility(View.VISIBLE);
            Bitmap selectImage = BitmapFactory.decodeResource(
                    getApplicationContext().getResources(), R.drawable.selectimage);
            imageView.setImageBitmap(selectImage);
        } else {
            int artID = intent.getIntExtra("artID", 1);
            button.setVisibility(View.INVISIBLE);
            editText.setEnabled(false);
            editText2.setEnabled(false);
            editText3.setEnabled(false);

            try {
                Cursor cursor = database.rawQuery("SELECT * FROM arts WHERE id = ?",
                        new String[] {String.valueOf(artID)});

                int artIndex = cursor.getColumnIndex("art");
                int painterIndex = cursor.getColumnIndex("painter");
                int yearIndex = cursor.getColumnIndex("year");
                int imageIndex = cursor.getColumnIndex("image");

                while(cursor.moveToNext()){
                    editText.setText(cursor.getString(artIndex));
                    editText2.setText(cursor.getString(painterIndex));
                    editText3.setText(cursor.getString(yearIndex));
                    byte[] bytes = cursor.getBlob(imageIndex);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                }

                cursor.close();

            } catch (Exception e){

            }
        }
    }

    public void selectImg(View view){

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGal = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGal, 2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGal = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGal, 2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            Uri imageData = data.getData();
            try{
                if(Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(
                            this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                }
                else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                            imageData);
                    imageView.setImageBitmap(selectedImage);
                }
            } catch (IOException e){

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void save(View view){

        String artName = editText.getText().toString();
        String painterName = editText2.getText().toString();
        String year = editText3.getText().toString();
        Bitmap smallImg = decreaseSize(selectedImage, 300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImg.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
        byte[] byteArr = outputStream.toByteArray();

        try{
            database.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY, " +
                    "art VARCHAR, painter VARCHAR, year VARCHAR, image, BLOB)");
            String query = "INSERT INTO arts (art, painter, year, image) VALUES (?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(query);
            sqLiteStatement.bindString(1, artName);
            sqLiteStatement.bindString(2, painterName);
            sqLiteStatement.bindString(3, year);
            sqLiteStatement.bindBlob(4, byteArr);
            sqLiteStatement.execute();
        } catch (Exception e){

        }

        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public Bitmap decreaseSize(Bitmap image, int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();
        float ratio = (float) width / (float) height;

        if(ratio > 1){
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
