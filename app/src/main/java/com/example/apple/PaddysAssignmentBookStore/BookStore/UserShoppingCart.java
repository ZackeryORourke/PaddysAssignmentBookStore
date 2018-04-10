package com.example.apple.PaddysAssignmentBookStore.BookStore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.apple.PaddysAssignment.R;
import com.example.apple.PaddysAssignmentBookStore.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserShoppingCart extends AppCompatActivity {


    EditText titleText, authorText, priceText, quantityText;
    Button addButton, uploadImageButton, clearButton;
    private List<Catalogue> catalogueListItems = new ArrayList<Catalogue>();
    DatabaseReference databaseCatalogue;
    private ListView listView;
    private List<Catalogue> booksList = new ArrayList<>();
    private BookListAdapter adapter;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shopping_cart);
        listView = (ListView) findViewById(R.id.shoppingCartList);
        databaseCatalogue = FirebaseDatabase.getInstance().getReference("shoppingCart");
        titleText = (EditText) findViewById(R.id.addtitle);
        quantityText = (EditText) findViewById(R.id.quantity);
        authorText = (EditText) findViewById(R.id.addAuthor);
        priceText = (EditText) findViewById(R.id.addPrice);
        addButton = (Button) findViewById(R.id.addBookButton);
        clearButton= (Button)findViewById(R.id.clearBook);
        uploadImageButton =(Button) findViewById(R.id.uploadBook);
        adapter = new BookListAdapter(this, catalogueListItems);
        listView.setAdapter((ListAdapter) adapter);
        imageView = (ImageView) findViewById(R.id.imgView);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
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

                BookListAdapter adapter = new BookListAdapter(UserShoppingCart.this, catalogueListItems);
                listView.setAdapter(adapter);  //it does not know what this adapter is
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.userMenu:
                Intent userIntent = new Intent(this, LoginActivity.class);
                this.startActivity(userIntent);
                return true;




            case R.id.searchView:
                Intent search = new Intent(this, SearchBook.class);
                this.startActivity(search);
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

