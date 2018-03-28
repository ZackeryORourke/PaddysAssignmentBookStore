package com.example.apple.PaddysAssignmentBookStore.BookStore;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.PaddysAssignment.R;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<Catalogue>{

    private Activity context;
    private List<Catalogue> catalogueList;
    private Activity activity;
    private LayoutInflater inflater;


    public BookListAdapter(Activity context, List<Catalogue> catalogueList){
      super(context, R.layout.list_layout,catalogueList);
      this.context = context;
      this.catalogueList = catalogueList;
  }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater  = context.getLayoutInflater();



        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.listViewTitle);
        TextView textViewAuthor = (TextView) listViewItem.findViewById(R.id.listViewAuthor);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.listViewPrice);

        Catalogue catalogue = catalogueList.get(position);

        textViewTitle.setText(catalogue.getTitle());
        textViewAuthor.setText(catalogue.getAuthor());
        textViewPrice.setText(catalogue.getPrice());

        return listViewItem;
    }


}
