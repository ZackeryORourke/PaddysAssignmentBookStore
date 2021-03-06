package com.example.apple.PaddysAssignmentBookStore.BookStore;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.apple.PaddysAssignment.R;
import java.util.List;
public class BookListAdapter extends ArrayAdapter<Catalogue>{

    private Activity activity;
    private List<Catalogue> catalogueList;
    private LayoutInflater inflater;


    public BookListAdapter(Activity activity, List<Catalogue> catalogueList){
      super(activity, R.layout.list_layout,catalogueList);
      this.activity = activity;
      this.catalogueList = catalogueList;
  }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(inflater== null)
            inflater = (LayoutInflater) activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView = inflater.inflate(R.layout.list_layout,null);





        TextView textViewTitle = convertView.findViewById(R.id.listViewTitle);
        TextView textViewAuthor = convertView.findViewById(R.id.listViewAuthor);
        TextView textViewPrice = convertView.findViewById(R.id.listViewPrice);
        TextView textViewQuantity = convertView.findViewById(R.id.listViewQnt);
        TextView textViewCategory= convertView.findViewById(R.id.listViewCategory);
        Catalogue catalogue = catalogueList.get(position);
        ImageView imageView = convertView.findViewById(R.id.bookImageView);

        String url = (catalogue.getImageUrl());
        Glide.with(activity)
                .load(url)
                .override(300, 200)
                .into(imageView);


        textViewTitle.setText(catalogue.getTitle());
        textViewAuthor.setText(catalogue.getAuthor());
        textViewPrice.setText(catalogue.getPrice());
        textViewQuantity.setText(catalogue.getQuantity());
        textViewCategory.setText(catalogue.getCategory());

        return convertView;
    }


}
