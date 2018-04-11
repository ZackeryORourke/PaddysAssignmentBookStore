package com.example.apple.PaddysAssignmentBookStore.BookStore;

import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.view.View;
import android.webkit.MimeTypeMap;
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
import java.util.List;
import java.util.UUID;

public class AddBook extends AppCompatActivity {


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
    private String downloadUrl;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        listView = (ListView) findViewById(R.id.listView);
        databaseCatalogue = FirebaseDatabase.getInstance().getReference("catalogues");
        titleText = (EditText) findViewById(R.id.addtitle);
        quantityText = (EditText) findViewById(R.id.quantity);
        authorText = (EditText) findViewById(R.id.addAuthor);
        priceText = (EditText) findViewById(R.id.addPrice);
        addButton = (Button) findViewById(R.id.addBookButton);
        clearButton= (Button)findViewById(R.id.clearBook);
        uploadImageButton =(Button) findViewById(R.id.uploadBook);
        adapter = new BookListAdapter(this, catalogueListItems);
        listView.setAdapter((ListAdapter) adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });
        imageView = (ImageView) findViewById(R.id.imgView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Catalogue catalogue = catalogueListItems.get(i);
                showUpdateDialog(catalogue.getTitle(),catalogue.getAuthor(),catalogue.getPrice());

                return false;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              clearDetails();

            }









        });

       uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.topicSpinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"Fiction", "Thriller", "Science Fiction","Horror","Love", "Fantasy","Comic"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        //Updated Drop down spinner

    }



    public void deleteBook(String bookId){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("catalogues").child(bookId);
        databaseReference.removeValue();
        Toast.makeText(this,"Book Deleted",Toast.LENGTH_LONG).show();


    }

    public void clearDetails(){
            titleText.setText("");
            authorText.setText("");
           priceText.setText("");
           quantityText.setText("");

    }

    private void addBook(){
        uploadImage();

    }

    private void showUpdateDialog(final String bookId, final String bookName, final String bookAuthor){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);//pass the current context
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.updatedialog,null);
        dialogBuilder.setView(dialogView);



        final EditText editText= (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editText1= (EditText) dialogView.findViewById(R.id.editTextAuthor);
        final EditText editText2= (EditText) dialogView.findViewById(R.id.editTextPrice);
        final EditText editText3= (EditText) dialogView.findViewById(R.id.updateQuantity);
        final EditText editText4= (EditText) dialogView.findViewById(R.id.updateCategory);
        final Button  buttonUpdate= (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button  buttonDelete= (Button) dialogView.findViewById(R.id.buttonDelete);
        final Button uploadButton = (Button) dialogView.findViewById(R.id.buttonUpdateImage);
        dialogBuilder.setTitle("Updating Book" + bookId);


        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = editText.getText().toString().trim();
                String title = editText.getText().toString().trim();
                String author = editText1.getText().toString().trim();
                String price = editText2.getText().toString().trim();
                String updateQuantity = editText3.getText().toString().trim();
                String updateCategory = editText4.getText().toString().trim();
                String imageLocation = filePath.toString();
                updateCatalogue(id,title,author,price,updateQuantity,updateCategory,imageLocation);
                alertDialog.dismiss();

            }









        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook(bookId);

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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


    private boolean updateCatalogue(String bookId, String bookName, String bookAuthor, String bookPrice,String bookQuantity, String bookCategory,String imageLocation){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("catalogues").child(bookId);
        Catalogue catalogue = new Catalogue(bookId,bookName,bookAuthor,bookPrice, bookQuantity, bookCategory,imageLocation);
        updateImage();
        databaseReference.setValue(catalogue);
        Toast.makeText(this,"Book Updated Successfully",Toast.LENGTH_LONG).show();
        return true;

    }

    private void uploadImage() {
        //Firebase
        FirebaseStorage storage;
        StorageReference storageReference;
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            filePath=downloadUri;
                            String id = titleText.getText().toString().trim();
                            String title = titleText.getText().toString().trim();
                            String author = authorText.getText().toString().trim();
                            String price = priceText.getText().toString().trim();
                            String quantity = quantityText.getText().toString().trim();
                            String imageLocation = filePath.toString();
                            final Spinner mySpinner=(Spinner) findViewById(R.id.topicSpinner);
                            String topicSpinner = mySpinner.getSelectedItem().toString();
                            if(!TextUtils.isEmpty(title)&!TextUtils.isEmpty(author)&!TextUtils.isEmpty(price)){
                                Catalogue catalogue = new Catalogue(id,title,author,price,quantity,topicSpinner,imageLocation);
                                databaseCatalogue.child(id).setValue(catalogue);


                            }


                            progressDialog.dismiss();
                            Toast.makeText(AddBook.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddBook.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });




        }
    }

    private void updateImage() {
        //Firebase
        FirebaseStorage storage;
        StorageReference storageReference;
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            filePath = downloadUri;


                            progressDialog.dismiss();
                            Toast.makeText(AddBook.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddBook.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });


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
                listView.setAdapter(adapter);  //it does not know what this adapter is
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}

