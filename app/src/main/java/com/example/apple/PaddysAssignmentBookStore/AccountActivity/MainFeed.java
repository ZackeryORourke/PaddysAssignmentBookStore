package com.example.apple.PaddysAssignmentBookStore.AccountActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.apple.PaddysAssignment.R;
import com.example.apple.PaddysAssignmentBookStore.BookStore.BookListAdapter;
import com.example.apple.PaddysAssignmentBookStore.BookStore.Catalogue;
import com.example.apple.PaddysAssignmentBookStore.BookStore.CustomerIndex;
import com.example.apple.PaddysAssignmentBookStore.BookStore.SearchBook;
import com.example.apple.PaddysAssignmentBookStore.BookStore.UserShoppingCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainFeed extends AppCompatActivity {
    private List<Catalogue> catalogueListItems = new ArrayList<>();
    DatabaseReference databaseCatalogue;
    private ListView listView;
    private BookListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfeed);
        listView = findViewById(R.id.mainList);
        adapter = new BookListAdapter(this, catalogueListItems);
        listView.setAdapter(adapter);
        Button searchTitle, searchAuthor, searchPrice;
        searchTitle= findViewById(R.id.sortTitle);
        searchAuthor= findViewById(R.id.sortAuthorButton);
        searchPrice= findViewById(R.id.sortPrice);
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

