package miercoles.dsl.resproductorsonidos;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SonidosService extends Service {
    public static final String PARAR_SONIDO = "parar";
    public static final String INICIAR_SONIDO = "iniciar";
    public static final String PARAR_SONIDO_Y_MANDAR_A_MAIN_ACTIVITY = "parar_mandar";

    private MediaPlayer mediaPlayer;
    private NotificationCompat.Builder builder;

    public SonidosService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        builder = new NotificationCompat.Builder(this, "michannnel")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Sonando")
                .setContentText("Click para detener");

        Intent intentPararSonido = new Intent(this, SonidosService.class);
        intentPararSonido.setAction(PARAR_SONIDO_Y_MANDAR_A_MAIN_ACTIVITY);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intentPararSonido, 0);

        builder.setContentIntent(pendingIntent);



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
                }else if(accion.equals(PARAR_SONIDO_Y_MANDAR_A_MAIN_ACTIVITY)){
                    mediaPlayer.stop();
                    mediaPlayer.release();

                    stopSelf();

                    Intent intentMainActivity = new Intent(this, MainActivity.class);
                    intentMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentMainActivity);
                }
            } else {
                if (accion.equals(INICIAR_SONIDO)) {
                    mediaPlayer.start();
                    startForeground(488, builder.build());
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
