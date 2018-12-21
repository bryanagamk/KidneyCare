package com.example.bro.kidneycare;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    ImageView img_camera;
    Button btn_check, btn_recamera;
    LinearLayout btn_camera;
    public static final int RequestPermissionCode = 1;

    ImageView imageView;
    Toolbar toolbar;
    File file;
    Uri uri;
    Intent CamIntent,GalIntent,CropIntent;
    DisplayMetrics displayMetrics;
    int width,height;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        btn_camera = view.findViewById(R.id.btn_camera);
        img_camera = view.findViewById(R.id.iv_picture);
        btn_recamera = view.findViewById(R.id.btn_camera2);
        btn_check = view.findViewById(R.id.btn_check);

        btn_recamera.setVisibility(View.INVISIBLE);
        btn_check.setVisibility(View.INVISIBLE);

        EnableRuntimePermission();

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraOpen();
            }
        });

        btn_recamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraOpen();
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_DENIED)
            RequestRuntimePermission();

        return view;
    }

    private void RequestRuntimePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA))
            Toast.makeText(getActivity(),"CAMERA permission allows us to access CAMERA app",Toast.LENGTH_SHORT).show();
        else
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},RequestPermissionCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

            CropImage();
        }
        else if (requestCode == 1)
        {
            if(data != null)
            {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                img_camera.setImageBitmap(bitmap);
                btn_camera.setVisibility(View.INVISIBLE);
                btn_recamera.setVisibility(View.VISIBLE);
                btn_check.setVisibility(View.VISIBLE);
            }
        }
    }

    private void CropImage() {

        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri,"image/*");

            CropIntent.putExtra("crop","true");
            CropIntent.putExtra("outputX",180);
            CropIntent.putExtra("outputY",180);
            CropIntent.putExtra("aspectX",3);
            CropIntent.putExtra("aspectY",4);
            CropIntent.putExtra("scaleUpIfNeeded",true);
            CropIntent.putExtra("return-data",true);

            startActivityForResult(CropIntent,1);
        }
        catch (ActivityNotFoundException ex)
        {

        }

    }

    private void CameraOpen() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(),
                "file"+String.valueOf(System.currentTimeMillis())+".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        CamIntent.putExtra("return-data",true);
        startActivityForResult(CamIntent,0);
    }

    private void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {

            Toast.makeText(getContext(), "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getActivity(), "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getActivity(), "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}