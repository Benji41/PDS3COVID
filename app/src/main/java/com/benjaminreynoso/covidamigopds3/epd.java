package com.benjaminreynoso.covidamigopds3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.benjaminreynoso.covidamigopds3.Interface.API;
import com.benjaminreynoso.covidamigopds3.Model.Event;
import com.benjaminreynoso.covidamigopds3.Model.Medida;
import com.benjaminreynoso.covidamigopds3.Model.User;
import com.benjaminreynoso.covidamigopds3.adapter.AdapterAvEvents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class epd extends AppCompatActivity implements AdapterAvEvents.OnEventListener {

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://covid-amigo-pds3.herokuapp.com/").addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit = builder.build();
    API getEventosAPI = retrofit.create(API.class);
    RecyclerView mRecyclerView;
    List<Event> eventos;
    List<Medida> medidas;
    String nombre, apellidos;
    TextView tvHost, tvMeasures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epd);
        mRecyclerView = findViewById(R.id.rec_edp);
        eventos = new ArrayList<>();
        medidas = new ArrayList<>();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        MyFirebaseMessagingService t;
                        t = new MyFirebaseMessagingService();
                        t.onNewToken(token);
                        // Log and toast
                        Log.d(TAG, token);
                        Toast.makeText(epd.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        TextView tv = (TextView) findViewById(R.id.textView2);
        getEvents("noereynoso.business@gmail.com");
    }

    public void sig() {
        Intent i = new Intent(this, cuestionario.class);
        startActivity(i);
    }

    private void getEvents(String id_usuario) {

        API getEventosAPI = retrofit.create(API.class);
        Call<List<Event>> Call = getEventosAPI.getEventos(id_usuario);
        Call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Event>> call, Response<List<Event>> response) {

                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                }
                List<Event> eventosList = response.body();
                    for(Event evento: eventosList) {
                        System.out.println(evento.toString());
                        eventos.add(evento);
                    }
                    putDataIntoRecycler();
                }
            @Override
            public void onFailure(retrofit2.Call<List<Event>> call, Throwable t) {
                Toast.makeText(epd.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getMedidasEvento(int id_evento){
        Call<List<Medida>> Call = getEventosAPI.getMedidasEventos(id_evento);
        Call.enqueue(new Callback<List<Medida>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Medida>> call, Response<List<Medida>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                }
                List<Medida> medidasList = response.body();
                for(int i=0;i<medidasList.size();i++) {
                    if (!(i<medidasList.size() -1)){
                        tvMeasures.setText(tvMeasures.getText()+String.valueOf(i+1)+". "+medidasList.get(i).getDescripcion());
                    }else{
                        tvMeasures.setText(tvMeasures.getText()+String.valueOf(i+1)+". "+medidasList.get(i).getDescripcion()+"\n"+"\n");
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Medida>> call, Throwable t) {
                Toast.makeText(epd.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
    @NonNull
    private void getHostEvento(int id_evento){

        Call<User> Call = getEventosAPI.getHostEventos(id_evento);
        Call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                nombre = response.body().getNombre();
                apellidos = response.body().getApellidos();
                tvHost.setText(
                        tvHost.getText()+" " + nombre+" "+apellidos
                );
                System.out.println(nombre+apellidos);
            }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {

            }
        });

    }



    private void putDataIntoRecycler(){
        AdapterAvEvents adapter = new AdapterAvEvents(this,eventos,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onEventClick(int position) {

        Event evento = eventos.get(position);
        getHostEvento(evento.getId_evento());
       // Intent intent = new Intent(this,)
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.event_dialog);
        Button btnTest = dialog.findViewById(R.id.btnForm);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvHost = dialog.findViewById(R.id.tvHost);
        TextView tvDate = dialog.findViewById(R.id.tvDate);
        TextView tvLocation = dialog.findViewById(R.id.tvLocation);
        TextView tvEnviro = dialog.findViewById(R.id.tvEnviroment);
        TextView tvMinAge = dialog.findViewById(R.id.tvMinAge);
        TextView tvConfirmed = dialog.findViewById(R.id.tvConfirmedInvites);
        TextView tvCap = dialog.findViewById(R.id.tvAforo);
        TextView tvDesc = dialog.findViewById(R.id.tvDesc);
        tvDesc.setMovementMethod(new ScrollingMovementMethod());
        tvMeasures = dialog.findViewById(R.id.tvMedidas);
        tvMeasures.setMovementMethod(new ScrollingMovementMethod());
        //consultas a API
        getMedidasEvento(evento.getId_evento());

        //checamos
        tvTitle.setText(evento.getNombre());
        tvLocation.setText(tvLocation.getText()+" "+evento.getDireccion());
        tvDate.setText(tvDate.getText()+" "+evento.getFecha());
        tvEnviro.setText(tvEnviro.getText()+" "+evento.getEntorno());
        tvMinAge.setText(tvMinAge.getText()+" "+evento.getEdad_min());
        tvConfirmed.setText(tvConfirmed.getText()+" "+evento.getInvitadosConfirmados());
        tvCap.setText(tvCap.getText()+" "+evento.getAforo());
        tvDesc.setText(evento.getDescripcion());
        //falta llenar la descripcion y las medidas

        //ENFASIS
        //PARA LLENAR LAS MEDIDAS HAY QUE TRABAJAR DESDE EL METODO DE GET MEDIDAS Y NO AQUI PORQUE SI ES POR AQUI CARGA LOS VALORES INICIALES Y NO ESPERA AL RETROFIT


        dialog.show();
        btnTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
    }
}