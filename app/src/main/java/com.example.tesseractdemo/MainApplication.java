package com.example.tesseractdemo;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainApplication extends Application {

    public static MainApplication instance =null;

    @Override
    public void  onCreate() {
        super.onCreate();
        //start copy file here, copy eng.traineddata from assets to external storage...tessdata/eng.traineddata
        // the data path, must contain sub folder call "tessdata", if not the lib will not work.
        instance =this;
        copyTessDataForTextRecognizer();
    }

    private String tessDataPath()
    {
        return MainApplication.instance.getExternalFilesDir(null)+"/tessdata/";
    }

    private String getTessDataParentDirectory()
    {
        return MainApplication.instance.getExternalFilesDir(null).getAbsolutePath();
    }

    private void copyTessDataForTextRecognizer()
    {
        Runnable run = new Runnable(){
            @Override
            public void run() {
                AssetManager assetManager = MainApplication.instance.getAssets();
                OutputStream out=null;
                try {
                    //Log.d("MainApplication", "copyTessDataForTextRecognizer");

                    InputStream in = assetManager.open("eng.traineddata");
                    String tesspath = instance.tessDataPath();
                    File tessFolder = new File(tesspath);
                    if(!tessFolder.exists())
                        tessFolder.mkdir();
                    String tessData = tesspath+"/"+"eng.traineddata";
                    File tessFile = new File(tessData);
                    if(!tessFile.exists())
                    {
                        out = new FileOutputStream(tessData);
                        byte[] buffer = new byte[1024];
                        int read = in.read(buffer);
                        while (read != -1) {
                            out.write(buffer, 0, read);
                            read = in.read(buffer);

                        }
                        Log.d("MainApplication", "Did finish copy tess file ");

                        }
                        else
                            Log.d("MainApplication", "tess file exist ");

                    } catch (Exception e) {
                    Log.d("MainApplication", "couldn't copy with the following error: " +
                            e.toString());
                    } finally {
                        try {
                            if(out!=null)
                                out.close();
                        }catch (Exception exx)
                        {}
                        }
                    }
            };
        new Thread(run).start();
        }

    }
