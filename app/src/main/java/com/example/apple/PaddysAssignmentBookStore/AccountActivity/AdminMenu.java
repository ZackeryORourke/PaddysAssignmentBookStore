package com.example.apple.PaddysAssignmentBookStore.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.apple.PaddysAssignment.R;
import com.example.apple.PaddysAssignmentBookStore.BookStore.AddBook;
import com.example.apple.PaddysAssignmentBookStore.LoginActivity;

public class AdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
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




            case R.id.addBook:
                Intent userIntent2 = new Intent(this, AddBook.class);
                this.startActivity(userIntent2);
                return true;





            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
