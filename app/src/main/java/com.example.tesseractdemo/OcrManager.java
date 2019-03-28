package com.example.tesseractdemo;

import android.graphics.Bitmap;

import com.example.tesseractdemo.MainApplication;
import com.googlecode.tesseract.android.TessBaseAPI;

public class OcrManager {

    //TessBaseAPI baseAPI = null;
    public void initAPI()
    {
        TessBase baseAPI = new TessBaseAPI(); //baseAPI = new TessBaseAPI();
        //after copy,my path to trained data is getExternalFilesDir(null)+"/tessdata/"+"eng.traineddata";
        //but init() function just need parent folder path of "tessdata", so it is getExternalFilesDir(null)
        String dataPath = MainApplication.instance.getTessDataParentDirectory();
        baseAPI.init(dataPath, "eng");
        //language code is name of trained data file, except extension part
        //"eng.traineddata" => language code is "eng"

        //first param is datapath which is part of your trained data, second is language code
        //now, your trained data stored in assets folder, we need to copy it to another external storage folder
        //It is better to do this work when application starts the first time
    }

    public String startRecogniza(Bitmap bitmap)
    {
        if(baseAPI == null)
            initAPI();
        baseAPI.setImage(bitmap);
        return baseAPI.getUTF8Text();
    }
}
