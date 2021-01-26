package com.example.firebaseintegracion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextTitulo;
    EditText mEditTextContenido;
    EditText mEditTextEdad;
    Button mButtonCrearDatos;

    TextView mTextViewDatos;

    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirestore = FirebaseFirestore.getInstance();

        mEditTextTitulo = findViewById(R.id.editTextTitulo);
        mEditTextContenido = findViewById(R.id.editTextContenido);
        mEditTextEdad = findViewById(R.id.editTextEdad);
        mButtonCrearDatos = findViewById(R.id.btnCrearDatos);

        mTextViewDatos = findViewById(R.id.textViewDatos);

        mButtonCrearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crearDatos();


            }
        });

        obtenerDatos();


    }

// No es en tiempo real

 /*   private void obtenerDatos(){
        mFirestore.collection("Articulos").document("1").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String titulo = documentSnapshot.getString("titulo");
                    String contenido = documentSnapshot.getString("contenido");
 //                 Long fecha = documentSnapshot.getLong("fecha");
                    String edad = documentSnapshot.getString("edad");

                    mTextViewDatos.setText("Titulo: " + titulo + "\n" + "Contenido: " + contenido + "\n" + "edad: " + edad );
                }
            }
        });

    }

  */

// En tiempo real

    private void obtenerDatos() {
        mFirestore.collection("Articulos").document("1").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {

                    String titulo = documentSnapshot.getString("titulo");
                    String contenido = documentSnapshot.getString("contenido");
                    //                 Long fecha = documentSnapshot.getLong("fecha");
                    String edad = documentSnapshot.getString("edad");

                    mTextViewDatos.setText("Titulo: " + titulo + "\n" + "Contenido: " + contenido + "\n" + "edad: " + edad);
                }


            }
        });
    }



    private void crearDatos() {

        String titulo = mEditTextTitulo.getText().toString();
        String contenido = mEditTextContenido.getText().toString();
        String edad = mEditTextEdad.getText().toString();

        Map<String, Object> map = new HashMap<>();

        map.put("contenido",contenido);
        map.put("fecha", new Date().getTime());
        map.put("titulo",titulo);
        map.put("edad",edad);


//        mFirestore.collection("Articulos").document().set(map);
          mFirestore.collection("Articulos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
              @Override
              public void onSuccess(DocumentReference documentReference) {


                  Toast.makeText(MainActivity.this, "Se enviaron los datos CORRECTAMENTE", Toast.LENGTH_SHORT).show();

              }
          }).addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {


                  Toast.makeText(MainActivity.this, "No se pudieron crear los datos", Toast.LENGTH_SHORT).show();


              }
          });



    }

}











