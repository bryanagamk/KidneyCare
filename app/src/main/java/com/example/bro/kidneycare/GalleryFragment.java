package com.example.bro.kidneycare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bro.kidneycare.Utils.*;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {

    View view;
    ImageView img_gallery;
    Button btn_check, btn_regallery;
    LinearLayout btn_gallery;
    public Bitmap bmp_captured;
    int flagGalatauCam = 0;

    public static final int RequestPermissionCode = 1;
    private static int RESULT_LOAD_IMG = 1;
    private final int RESULT_CROP = 400;

    String imgDecodableString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        btn_gallery = view.findViewById(R.id.btn_gallery);
        img_gallery = view.findViewById(R.id.iv_picture);
        btn_regallery = view.findViewById(R.id.btn_gallery2);
        btn_check = view.findViewById(R.id.btn_check);

        btn_regallery.setVisibility(View.INVISIBLE);
        btn_check.setVisibility(View.INVISIBLE);

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMG);
            }
        });

        btn_regallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMG);
            }
        });

        return view;
    }

    public void loadImagefromGallery(View view) {
        // buat intentnya
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // saat gambar diambil
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                Uri photoUri = data.getData();
                if(photoUri != null) {
                    try {
                        Bitmap img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                        ImageView imgView = view.findViewById(R.id.iv_picture);
                        // Set the Image in ImageView after decoding the String
                        imgView.setImageBitmap(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception ignored) {

        }

    }
}