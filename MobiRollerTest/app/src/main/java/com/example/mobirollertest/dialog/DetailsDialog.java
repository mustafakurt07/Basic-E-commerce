package com.example.mobirollertest.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.mobirollertest.R;
import com.example.mobirollertest.model.ProductUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsDialog  extends DialogFragment {
    private CircleImageView circleImageView;
    private TextView idTextView,nameTextView,katTextView,priceTextView;
    private List<ProductUser>productUserList;
    private int position;

    public DetailsDialog(List<ProductUser> productUserList, int position) {
        this.productUserList = productUserList;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.details_dialogue,null);
        builder.setView(view).setTitle("Product Details").setIcon(R.drawable.ic_view2).setCancelable(true).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        circleImageView=view.findViewById(R.id.detailsImageId);
        idTextView=view.findViewById(R.id.detailsId);
        nameTextView=view.findViewById(R.id.detailsNameId);
        katTextView=view.findViewById(R.id.detailsCategoryId);
        priceTextView=view.findViewById(R.id.detailsPriceId);
        Glide.with(view.getContext()).load(productUserList.get(position).getProductImage()).centerCrop().placeholder(R.drawable.profile).into(circleImageView);
        idTextView.setText("ID :"+" "+productUserList.get(position).getProductId());
        nameTextView.setText("Name :"+" "+""+productUserList.get(position).getProductName());
        katTextView.setText("Kategory :"+" "+productUserList.get(position).getProductKategory());
        priceTextView.setText("Price :"+" "+productUserList.get(position).getProductPrice());



        return builder.create();
    }
}
