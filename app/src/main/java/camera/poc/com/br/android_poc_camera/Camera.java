package camera.poc.com.br.android_poc_camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;

/**
 * Created by rksc on 20/12/16.
 */

public class Camera {

    private static final String IMAGE_NAME = "img";
    public static final String IMAGE_TYPE = ".jpg";
    private static final String IMAGE_DIR = "Camera/";



    /**
     * Verifica se o device possui uma camera
     * */
    public static boolean checkCameraHardware(Context context) {

        if (context != null) {

            if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                // this device has a camera
                return true;
            } else {
                // no camera on this device
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Verifica se tem permissao para usar a camera
     * @param context
     * @return
     */
    public static boolean checkCameraPermission(Context context) {

        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Solicita permissao
     * @param context
     */
    public static void requestCameraPermission(Context context) {

        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.CAMERA
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }


    /**
     * Scanner para adicionar a foto na galeria.
     * @param file
     * @param context
     */
    public static void addImagemGaleria(File file, Context context) {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /***
     * Cria um nome de imagem
     * @return
     */
    public static String getImageNewName() {

        //return IMAGE_NAME + new java.text.SimpleDateFormat("yyyyMMdd_hhmmss").format(new java.util.Date());
        return IMAGE_NAME;
    }


    /***
     * Diretorio para guardar a imagem
     * @param appFolder
     * @return
     */
    public static final String getCameraDir(final String appFolder) {
        return Camera.IMAGE_DIR + appFolder + "/";
    }
}
