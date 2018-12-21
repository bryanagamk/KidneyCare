package com.example.bro.kidneycare;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class Utils {


    Intent performCrop (String picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        try {
            //Start Crop Activity


            // indicate image type and Uri
            File f = new File(picUri);

            Uri contentUri = Uri.fromFile(f);


            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 350);
            cropIntent.putExtra("outputY", 350);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
//            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
        }

        return cropIntent;
    }
}
