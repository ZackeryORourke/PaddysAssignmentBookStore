package com.example.apple.PaddysAssignmentBookStore.AccountActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import com.example.apple.PaddysAssignment.R;
import com.example.apple.PaddysAssignmentBookStore.BookStore.SearchBook;
import com.example.apple.PaddysAssignmentBookStore.BookStore.UserShoppingCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static com.example.apple.PaddysAssignment.R.*;

public class ViewCustomersDetails extends AppCompatActivity {

    private List<CustomerModel> customerModelList = new ArrayList<>();
    DatabaseReference databaseCatalogue;
    private ListView listView;
    private CustomerListAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.customersdetails);
        ListView listView = findViewById(R.id.customersList);
        adapter = new CustomerListAdapter(this, customerModelList);
        listView.setAdapter(adapter);

        mainFeed();



    }



    public void mainFeed(){

             FirebaseDatabase database = FirebaseDatabase.getInstance();
             databaseCatalogue = database.getReference("CustomerInformation").child(FirebaseAuth.getInstance().getUid());


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot customers : dataSnapshot.getChildren()) {

                    CustomerModel customerModel= customers.getValue(CustomerModel.class);

                    customerModelList.add(customerModel);
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





            case id.searchView:
                Intent search = new Intent(this, SearchBook.class);
                this.startActivity(search);
                return true;



            case id.shoppingCart:
                Intent shoppingCart = new Intent(this, UserShoppingCart.class);
                this.startActivity(shoppingCart);
                return true;








            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
