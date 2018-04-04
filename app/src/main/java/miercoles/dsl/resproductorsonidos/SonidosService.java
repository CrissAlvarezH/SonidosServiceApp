package miercoles.dsl.resproductorsonidos;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class SonidosService extends Service {
    public static final String PARAR_SONIDO = "parar";
    public static final String INICIAR_SONIDO = "iniciar";

    private MediaPlayer mediaPlayer;

    public SonidosService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.halloween);
        mediaPlayer.setLooping(true);// Se repite el audio ciclicamente
        mediaPlayer.setVolume(100, 100);// maximo en estereo
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null && intent.getAction() != null) {
            String accion = intent.getAction();

            if (mediaPlayer.isPlaying()) {
                if (accion.equals(PARAR_SONIDO)) {
                    mediaPlayer.stop();
                    mediaPlayer.release();

                    stopSelf();
                }
            } else {
                if (accion.equals(INICIAR_SONIDO)) {
                    mediaPlayer.start();
                }
            }

        }


        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mediaPlayer != null) {

            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }catch (IllegalStateException e){
                // Ya se aplico release al mediaPlayer (se liberó)
            }
            mediaPlayer = null;
        }
    }
}
