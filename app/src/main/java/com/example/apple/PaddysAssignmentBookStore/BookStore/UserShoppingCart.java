package com.example.apple.PaddysAssignmentBookStore.BookStore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apple.PaddysAssignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class UserShoppingCart extends AppCompatActivity {


    EditText titleText, authorText, priceText, quantityText;
    Button addButton, uploadImageButton, clearButton;
    TextView totalText;
    private List<Catalogue> catalogueListItems = new ArrayList<Catalogue>();
    DatabaseReference databaseCatalogue;
    private ListView listView;
    private List<Catalogue> booksList = new ArrayList<>();
    private BookListAdapter adapter;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private FirebaseUser fbUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shopping_cart);
        listView = (ListView) findViewById(R.id.shoppingCartList);
        titleText = (EditText) findViewById(R.id.addtitle);
        quantityText = (EditText) findViewById(R.id.quantity);
        authorText = (EditText) findViewById(R.id.addAuthor);
        priceText = (EditText) findViewById(R.id.addPrice);
        totalText = (TextView) findViewById(R.id.finalTotal);
        addButton = (Button) findViewById(R.id.addBookButton);
        clearButton= (Button)findViewById(R.id.clearBook);
        uploadImageButton =(Button) findViewById(R.id.uploadBook);
        adapter = new BookListAdapter(this, catalogueListItems);
        listView.setAdapter((ListAdapter) adapter);
        imageView = (ImageView) findViewById(R.id.imgView);
        shoppingListFeed();
        //totalText.setText(String.valueOf(totalPrice));



    }






    public void shoppingListFeed(){


        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseCatalogue = FirebaseDatabase.getInstance().getReference("shoppingCart").child(user);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot books : dataSnapshot.getChildren()) {
                   Catalogue catalogue = books.getValue(Catalogue.class);
                    String price = books.child("price").getValue(String.class);

                    int newIntValue = Integer.parseInt(price);
                    int currentvalue= newIntValue;
                    int totalPrice = currentvalue + newIntValue;

                    //New data base reference for total to add the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("CustomerTotal" + FirebaseAuth.getInstance().getUid());
                    myRef.setValue(totalPrice);
                    //Setting the list of items to the adapter
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



            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

