package camera.poc.com.br.android_poc_camera;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by rksc on 20/12/16.
 */

public class Camera {

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


}
