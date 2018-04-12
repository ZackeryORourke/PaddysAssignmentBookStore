package com.example.apple.PaddysAssignmentBookStore.BookStore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.apple.PaddysAssignment.R;

import java.util.ArrayList;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.SearchViewHolder> {

   Context context;
    ArrayList<String> titleList;
    ArrayList<String> categoryList;
    ArrayList<String> authorList;
    ArrayList<String> priceList;
    ArrayList<String> quantityList;
    ArrayList<String> imageUrlList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
            ImageView bookImage;
            TextView titleText, authorText, priceText, quantityText,categoryText;

        public SearchViewHolder(View itemView) {
            super(itemView);

            titleText = (TextView) itemView.findViewById(R.id.listViewTitle);
            categoryText = (TextView) itemView.findViewById(R.id.listViewCategory);
            authorText = (TextView) itemView.findViewById(R.id.listViewAuthor);
            quantityText = (TextView) itemView.findViewById(R.id.listViewQnt);
            priceText = (TextView) itemView.findViewById(R.id.listViewPrice);
            bookImage = (ImageView) itemView.findViewById(R.id.bookImageView);
        }
    }


    public MySearchAdapter (Context context, ArrayList<String> titleList, ArrayList<String> categoryList, ArrayList<String> authorList, ArrayList<String> priceList, ArrayList<String> quantityList, ArrayList<String> imageUrlList) {
        this.context = context;
        this.titleList = titleList;
        this.categoryList = categoryList;
        this.authorList = authorList;
        this.priceList = priceList;
        this.quantityList = quantityList;
        this.imageUrlList = imageUrlList;
    }

    @Override
    public MySearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layout,parent,false);
        return new MySearchAdapter.SearchViewHolder(view);
    }

    //Not Reaching this point

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.titleText.setText(titleList.get(position));
        holder.categoryText.setText(categoryList.get(position));
        holder.authorText.setText(authorList.get(position));
        holder.priceText.setText(priceList.get(position));
        holder.quantityText.setText(quantityList.get(position));



        Glide.with(context)
                .load(imageUrlList.get(position))
                .override(300, 200)
                .into(holder.bookImage);


    }




    @Override
    public int getItemCount() {
        return titleList.size();
    }
}
