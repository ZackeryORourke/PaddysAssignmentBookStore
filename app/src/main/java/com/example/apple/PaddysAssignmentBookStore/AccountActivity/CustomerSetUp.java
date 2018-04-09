package com.example.apple.PaddysAssignmentBookStore.AccountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.apple.PaddysAssignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CustomerSetUp extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_set_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText addressField = (EditText) findViewById(R.id.address);
        final EditText dobField = (EditText) findViewById(R.id.dob);
        final EditText paymentField = (EditText) findViewById(R.id.payment);
        final Spinner mySpinner=(Spinner) findViewById(R.id.cardTypeSpinner);
        Button submitButton= (Button) findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address      =  addressField.getText().toString();
                String dob      =  dobField.getText().toString();
                String payment     =  paymentField.getText().toString();
                String cardTypeSpinner = mySpinner.getSelectedItem().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("CustomerInformation" + FirebaseAuth.getInstance().getUid());
                myRef.setValue(address+dob+ payment+cardTypeSpinner);
                startActivity(new Intent(CustomerSetUp.this, MainFeed.class));
            }
        });



        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.cardTypeSpinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"Debit Card", "Credit Card", "Papal"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }


}
