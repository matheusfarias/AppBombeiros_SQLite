package sos.bombeiro.appbombeiros;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class Tela2 extends ActionBarActivity {
    private RadioButton emclinica, actransito, incendio;
    private EditText edLatitude, edLongitude;
    private String cpf, nome, mae, nsc, s;
    private Button sos;

    public Map<String, String> conectar(){
        //HashMap
        Map<String, String> mapa = new HashMap<>();
        mapa.put("cpf", cpf);
        mapa.put("nome", nome);
        mapa.put("nomeMae", mae);
        mapa.put("dataNascimento", nsc);

        return mapa;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);
        setupElements();
    }
    // Método usado para importar os elementos da classe R
    public void setupElements() {

        sos = (Button) findViewById(R.id.sos);
        sos.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //startGPS();

                new Thread(){

                    @Override
                    public void run(){
                        HttpHelper enviaDados = new HttpHelper();

                        try{
                            s = enviaDados.doPost("http://10.0.2.2:8080/ExemploWebService/rest/ocorrencia/todas", conectar(), "UTF-8");
                        }catch(IOException e){

                        }
                    }
                }.start();
                Toast.makeText(getApplicationContext(), "Ocorrencia enviada.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS() {
        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                updateView(locat);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
    }

    //  Método que faz a atualização da tela para o usuário.
    public void updateView(Location locat) {
        Double latitude = locat.getLatitude();
        Double longitude = locat.getLongitude();

        edLatitude.setText(latitude.toString());
        edLongitude.setText(longitude.toString());
    }

}