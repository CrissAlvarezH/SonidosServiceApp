package miercoles.dsl.resproductorsonidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickIniciarServicio(View btn){
        Intent intentIniciar = new Intent(this, SonidosService.class);
        intentIniciar.setAction(SonidosService.INICIAR_SONIDO);
        startService(intentIniciar);
    }

    public void clickDetenerServicio(View btn){
        Intent intentIniciar = new Intent(this, SonidosService.class);
        intentIniciar.setAction(SonidosService.PARAR_SONIDO);
        startService(intentIniciar);
    }
}
