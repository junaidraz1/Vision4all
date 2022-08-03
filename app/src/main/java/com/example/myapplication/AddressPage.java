package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddressPage extends AppCompatActivity {
    Button save;
    ListView listView;
    ArrayList<String> list;
    EditText editText;
    FirebaseFirestore f_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_page);


        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        save = findViewById(R.id.btnsaveAddress);
        listView= findViewById(R.id.listview);
        editText = findViewById(R.id.et_address);
        f_db = FirebaseFirestore.getInstance();

        // creating new list to store address in them
        list = new ArrayList<String>();
        //array adapter used to push data into the list view
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,list);




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to add address in listview
                String address = editText.getText().toString();
                if(!TextUtils.isEmpty(address))
                {
                list.add(address);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                editText.setText("");
                }
                else
                {
                    editText.setError("Please Enter a Valid Adress");
                }




                // for storing user data in firebase firestore
                Map<String, String> details = new HashMap<>();
                details.put("Address", address);
                f_db.collection("Addresses").add(details).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        if (!TextUtils.isEmpty(address)) {
                            Toast.makeText(getApplicationContext(), "Address Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        // to delete address from listview
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int item = position;

                new AlertDialog.Builder(AddressPage.this)
                        .setTitle("Delete Address")
                        .setMessage("Do you want to delete this address?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(item);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null).show();
                        return true;
            }
        });



        // setting action when address selected
        bnv.setSelectedItemId(R.id.address);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.location:
                        startActivity(new Intent(getApplicationContext(),LocationBottomNavigation.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.address:
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfilePage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(),LogoutPage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}