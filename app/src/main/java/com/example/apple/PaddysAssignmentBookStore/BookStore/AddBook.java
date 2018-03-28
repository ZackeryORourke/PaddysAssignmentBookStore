package com.example.apple.PaddysAssignmentBookStore.BookStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apple.PaddysAssignment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddBook extends AppCompatActivity {


    EditText titleText, authorText, priceText ;
    Button addButton, UpdateButton, clearButton, deleteButton;
    private List<Catalogue> catalogueListItems = new ArrayList<Catalogue>();
    DatabaseReference databaseCatalogue;
    private ListView listView;
    private List<Catalogue> booksList = new ArrayList<>();
    private BookListAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        listView = (ListView) findViewById(R.id.listView);
        databaseCatalogue = FirebaseDatabase.getInstance().getReference("catalogues");
        titleText = (EditText) findViewById(R.id.addtitle);
        authorText = (EditText) findViewById(R.id.addAuthor);
        priceText = (EditText) findViewById(R.id.addPrice);
        addButton = (Button) findViewById(R.id.addBookButton);
        adapter = new BookListAdapter(this, catalogueListItems);
        listView.setAdapter((ListAdapter) adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });

    }




    private void addBook(){
        String title = titleText.getText().toString().trim();
        String author = authorText.getText().toString().trim();
        String price = priceText.getText().toString().trim();

        if(!TextUtils.isEmpty(title)&!TextUtils.isEmpty(author)&!TextUtils.isEmpty(price)){
            String id = databaseCatalogue.push().getKey();
            Catalogue catalogue = new Catalogue(id,title,author,price);
            databaseCatalogue.child(id).setValue(catalogue);
            Toast.makeText(this,"The Book Has Been Added",Toast.LENGTH_LONG).show();



        }else{
            Toast.makeText(this,"Please Fill Out All Text Fields",Toast.LENGTH_LONG).show();
        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        databaseCatalogue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                catalogueListItems.clear();

                for (DataSnapshot catalogueSnapshot: dataSnapshot.getChildren()){
                    Catalogue catalogue = catalogueSnapshot.getValue(Catalogue.class);

                    catalogueListItems.add(catalogue);
                }

                BookListAdapter adapter = new BookListAdapter(AddBook.this, catalogueListItems);
                listView.setAdapter(adapter);  //it does not know what this adabpter is
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
