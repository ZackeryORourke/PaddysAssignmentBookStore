package com.example.apple.PaddysAssignmentBookStore.AccountActivity;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.apple.PaddysAssignment.R;
import android.widget.ListView;

import java.util.List;


public class CustomerListAdapter extends ArrayAdapter<CustomerModel>{

    private Activity activity;
    private List<CustomerModel> customersList;
    private LayoutInflater inflater;


    CustomerListAdapter(Activity activity, List<CustomerModel> customersList){
        super(activity, R.layout.customersdetails,customersList);
        this.activity = activity;
        this.customersList = customersList;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            assert inflater != null;
            convertView = inflater.inflate(R.layout.customersdetails, null);
        }



        TextView textViewTitle = null;
        if (convertView != null) {
            textViewTitle = convertView.findViewById(R.id.listViewTitle);
        }

        CustomerModel customerModel = customersList.get(position);


        if (textViewTitle != null) {
            textViewTitle.setText(customerModel.getDetails());
        }


        return convertView;
    }


}
