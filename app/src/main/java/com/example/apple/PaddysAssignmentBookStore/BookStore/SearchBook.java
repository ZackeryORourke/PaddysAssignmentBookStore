package com.example.apple.PaddysAssignmentBookStore.BookStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.example.apple.PaddysAssignment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SearchBook extends AppCompatActivity {

    DatabaseReference databaseCatalogue;
    RecyclerView recyclerView;
    ArrayList<String> titleList;
    ArrayList<String> categoryList;
    ArrayList<String> authorList;
    ArrayList<String> priceList;
    ArrayList<String> quantityList;
    ArrayList<String> imageUrlList;
    MySearchAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        EditText searchTextField = (EditText) findViewById(R.id.searchBookList);
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);

        databaseCatalogue = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        titleList = new ArrayList<>();
        authorList = new ArrayList<>();
        categoryList = new ArrayList<>();
        priceList = new ArrayList<>();
        imageUrlList = new ArrayList<>();
        quantityList = new ArrayList<>();

        searchTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    setAdapter(editable.toString());
                } else {

                    titleList.clear();
                    categoryList.clear();
                    authorList.clear();
                    priceList.clear();
                    quantityList.clear();
                    imageUrlList.clear();
                    recyclerView.removeAllViews();

                }
            }
        });


    }

    public void setAdapter(final String searchString) {


        databaseCatalogue.child("catalogues").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Clear all lists
                titleList.clear();
                categoryList.clear();
                authorList.clear();
                priceList.clear();quantityList.clear();
                imageUrlList.clear();
                int counter = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {



                    String title = snapshot.child("title").getValue(String.class);
                    String category = snapshot.child("category").getValue(String.class);
                    String author = snapshot.child("author").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);
                    String quantity = snapshot.child("quantity").getValue(String.class);
                    String image = snapshot.child("imageUrl").getValue(String.class);

                    if (title.toLowerCase().contains(searchString)) {
                        titleList.add(title);
                        categoryList.add(category);
                        authorList.add(author);
                        priceList.add(price);
                        quantityList.add(quantity);
                        imageUrlList.add(image);
                        counter++;


                    }

                    else if (category.toLowerCase().contains(searchString)) {
                        titleList.add(title);
                        categoryList.add(category);
                        authorList.add(author);
                        priceList.add(price);
                        quantityList.add(quantity);
                        imageUrlList.add(image);
                        counter++;

                    } else if (author.toLowerCase().contains(searchString)) {
                        titleList.add(title);
                        categoryList.add(category);
                        authorList.add(author);
                        priceList.add(price);
                        quantityList.add(quantity);
                        imageUrlList.add(image);
                        counter++;
                    }

                    if (counter==15)
                    {
                        break;
                    }


                }

                searchAdapter = new MySearchAdapter(SearchBook.this,titleList, categoryList,authorList,priceList, quantityList, imageUrlList);
                recyclerView.setAdapter(searchAdapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}