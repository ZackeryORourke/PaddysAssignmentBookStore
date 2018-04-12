package com.example.apple.PaddysAssignmentBookStore.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apple.PaddysAssignment.R;
import com.example.apple.PaddysAssignmentBookStore.BookStore.AddBook;
import com.example.apple.PaddysAssignmentBookStore.BookStore.BookListAdapter;
import com.example.apple.PaddysAssignmentBookStore.BookStore.Catalogue;
import com.example.apple.PaddysAssignmentBookStore.BookStore.CustomerIndex;
import com.example.apple.PaddysAssignmentBookStore.BookStore.SearchBook;
import com.example.apple.PaddysAssignmentBookStore.BookStore.UserShoppingCart;
import com.example.apple.PaddysAssignmentBookStore.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MainFeed extends AppCompatActivity {




    private List<Catalogue> catalogueListItems = new ArrayList<Catalogue>();
    DatabaseReference databaseCatalogue;
    private ListView listView;
    private List<Catalogue> booksList = new ArrayList<>();
    private BookListAdapter adapter;
    private ImageView imageView;
    private Button searchTitle, searchAuthor, searchPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfeed);
        listView = (ListView) findViewById(R.id.mainList);
        adapter = new BookListAdapter(this, catalogueListItems);
        listView.setAdapter((ListAdapter) adapter);
        imageView = (ImageView) findViewById(R.id.imgView);
        searchTitle= (Button) findViewById(R.id.sortTitle);
        searchAuthor= (Button) findViewById(R.id.sortAuthorButton);
        searchPrice= (Button) findViewById(R.id.sortPrice);

        mainFeed();

        searchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(catalogueListItems, new Comparator<Catalogue>() {
                    @Override
                    public int compare(Catalogue catalogue, Catalogue c1) {
                        return catalogue.getTitle().compareTo(c1.getTitle());
                    }
                });

                adapter.notifyDataSetChanged();
            }
        });

        searchAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(catalogueListItems, new Comparator<Catalogue>() {
                    @Override
                    public int compare(Catalogue catalogue, Catalogue c1) {
                        return catalogue.getAuthor().compareTo(c1.getAuthor());
                    }
                });

                adapter.notifyDataSetChanged();
            }
        });

        searchPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(catalogueListItems, new Comparator<Catalogue>() {
                    @Override
                    public int compare(Catalogue catalogue, Catalogue c1) {
                        return catalogue.getPrice().compareTo(c1.getPrice());
                    }
                });

                adapter.notifyDataSetChanged();
            }
        });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainFeed.this, CustomerIndex.class);
                i.putExtra("ValueKey", catalogueListItems.get(position).getTitle());
                i.putExtra("ValueKey2",catalogueListItems.get(position).getAuthor());
                i.putExtra("ValueKey3", catalogueListItems.get(position).getCategory());
                i.putExtra("ValueKey4", catalogueListItems.get(position).getImageUrl());
                i.putExtra("ValueKey5", catalogueListItems.get(position).getPrice());
                i.putExtra("ValueKey6", catalogueListItems.get(position).getQuantity());


                startActivity(i);

            }
        });





    }




    public void mainFeed(){

        databaseCatalogue = FirebaseDatabase.getInstance().getReference("catalogues");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot books : dataSnapshot.getChildren()) {
                    Catalogue catalogue = books.getValue(Catalogue.class);

                    catalogueListItems.add(catalogue);
                    listView.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        databaseCatalogue.addListenerForSingleValueEvent(valueEventListener);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){





            case R.id.searchView:
                Intent search = new Intent(this, SearchBook.class);
                this.startActivity(search);
                return true;



            case R.id.shoppingCart:
                Intent shoppingCart = new Intent(this, UserShoppingCart.class);
                this.startActivity(shoppingCart);
                return true;








            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

