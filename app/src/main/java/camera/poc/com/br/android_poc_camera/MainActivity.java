package camera.poc.com.br.android_poc_camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import static camera.poc.com.br.android_poc_camera.Camera.requestCameraPermission;

public class MainActivity extends AppCompatActivity {


    private static String TAG_LOG = "CAMERA";

    private ImageView imgFoto;
    private Button btnFoto;
    private File file;
    private static String APP_FOLDER = "MY_APP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicia os componentes da tela
        initComponents();

        Log.i(TAG_LOG, "componente init");

        Context ctx = getBaseContext();

        // check camera
        if (Camera.checkCameraHardware(ctx)) {

            Log.i(TAG_LOG, "dispositivo contem camera");

            if (!Camera.checkCameraPermission(ctx)) {

                btnFoto.setEnabled(false);

                //solicita permissao
                requestCameraPermission(ctx);

                Log.i(TAG_LOG, "dado permissao para usar a camera");

            } else {
                Log.i(TAG_LOG, "ja existe permissao para usar a camera");
            }
        } else {
            Log.e(TAG_LOG, "dispositivo nao contem camera");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        //verifica se recebeu a autorizacao para habilitar o botao de foto
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                btnFoto.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                final Uri uriForFile = FileProvider.getUriForFile(getBaseContext(),
                        getBaseContext().getApplicationContext().getPackageName() + ".provider", file);

                imgFoto.setImageURI(uriForFile);

                Camera.addImagemGaleria(file, getBaseContext());

                Log.i(TAG_LOG, "img file: " + uriForFile.toString());
            }
        }
    }

    /**
     * Inicia os componentes da tela
     */
    private void initComponents() {

        imgFoto = (ImageView) findViewById(R.id.img_Foto);
        btnFoto = (Button) findViewById(R.id.btn_foto);
    }


    /**
     * Tira a foto
     * @param view
     */
    public void tirarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {

            file = getOutputMediaFile(getBaseContext());

        } catch (IOException e) {
            Log.e(TAG_LOG, e.getMessage());
        }

        if (file == null) {

            Log.e(TAG_LOG, "Nao foi possivel criar um diretorio!");

        } else {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(view.getContext(),
                    view.getContext().getApplicationContext().getPackageName() + ".provider", file)
            );

            startActivityForResult(intent, 100);
        }
    }


    /**
     * Recuperao diretorio para gravar a foto
     *
     * @param context
     * @return
     * @throws IOException
     */
    private static File getOutputMediaFile(Context context) throws IOException {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), Camera.getCameraDir(APP_FOLDER));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        return  File.createTempFile(Camera.getImageNewName(), Camera.IMAGE_TYPE, mediaStorageDir);
    }

}
