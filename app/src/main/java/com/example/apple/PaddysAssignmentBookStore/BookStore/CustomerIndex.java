package com.example.apple.PaddysAssignmentBookStore.BookStore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.PaddysAssignment.R;
import com.example.apple.PaddysAssignmentBookStore.AccountActivity.MainFeed;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CustomerIndex extends AppCompatActivity {


    private TextView title,author,category,price,quantity,image;
    private Button back,shoppingCart;
    private FirebaseAuth mAuth;
    private FirebaseUser fbUser;
    private DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("catalogues");
        setContentView(R.layout.activity_customer_index);
        Intent intent = getIntent();
        final String bookTitle = intent.getExtras().getString("ValueKey");
        final String bookAuthor = intent.getExtras().getString("ValueKey2");
        final String bookCategory = intent.getExtras().getString("ValueKey3");
        final String bookImage = intent.getExtras().getString("ValueKey4");
        final String bookPrice = intent.getExtras().getString("ValueKey5");
        final String bookQuantity = intent.getExtras().getString("ValueKey6");
        title = (TextView) findViewById(R.id.listViewTitle);
        author = (TextView) findViewById(R.id.listViewAuthor);
        category = (TextView) findViewById(R.id.listViewCategory);
        price = (TextView) findViewById(R.id.listViewPrice);
        quantity = (TextView) findViewById(R.id.listViewQuantity);
        back = (Button) findViewById(R.id.btn_Home);
        shoppingCart = (Button) findViewById(R.id.shoppingCartButton);
        title.setText(bookTitle);
        author.setText(bookAuthor);
        category.setText(bookCategory);
        price.setText(bookPrice);
        quantity.setText(bookQuantity);
        final ImageView image = (ImageView) findViewById(R.id.bookImageView);
        Picasso.with(this)
                .load(bookImage)
                .placeholder(R.drawable.common_google_signin_btn_icon_dark_normal_background) // optional
                .error(R.drawable.common_full_open_on_phone)         // optional

                .into(image);

        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerIndex.this, MainFeed.class));

            }
        });



        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("shoppingCart").child(fbUser.getUid());
                ShoppingCartModel shoppingCart = new ShoppingCartModel(bookTitle,bookAuthor,bookPrice,bookQuantity,bookCategory,bookImage,bookPrice);
                String cartId = databaseReference.push().getKey();
                databaseReference.child(cartId).setValue(shoppingCart);
                Toast.makeText(CustomerIndex.this, "Added To Shopping Cart", Toast.LENGTH_SHORT).show();

                //order by child firebase




            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }
















}
