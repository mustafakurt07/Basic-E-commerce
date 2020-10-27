package com.example.mobirollertest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobirollertest.R;
import com.example.mobirollertest.model.ProductUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductUser>productUserList;
    private ClickInterface clickInterface;

    public ProductAdapter(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }


    public void getProductList(List<ProductUser>productUserList)
    {
        this.productUserList=productUserList;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
      View view=  layoutInflater.inflate(R.layout.single_product,parent,false);
        return  new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(productUserList.get(position).getProductImage()).centerCrop().placeholder(R.drawable.profile).into(holder.circleImageView);
        holder.productId.setText("ID :"+" "+productUserList.get(position).getProductId());
        holder.productName.setText("Name :"+" "+""+productUserList.get(position).getProductName());
        holder.productKateg.setText("Kategory :"+" "+productUserList.get(position).getProductKategory());
        holder.productPrice.setText("Price :"+" "+productUserList.get(position).getProductPrice());
        holder.productDate.setText("Date :"+" "+productUserList.get(position).getProductDate());
        holder.productDesc.setText("Description :"+" "+productUserList.get(position).getProductDesc());



    }

    @Override
    public int getItemCount() {
        return productUserList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private CircleImageView circleImageView;
        private TextView productId,productName,productKateg,productPrice,productDate,productDesc;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.singleImageId);
            productId=itemView.findViewById(R.id.singleProductOwner);
            productName=itemView.findViewById(R.id.singleNameId);
            productKateg=itemView.findViewById(R.id.singleKategoryId);
            productPrice=itemView.findViewById(R.id.singlePriceId);
            productDate=itemView.findViewById(R.id.singleDateId);
            productDesc=itemView.findViewById(R.id.singleDescId);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickInterface.onItemClick(getAdapterPosition());//tıklanan pozisyonu alır

        }

        @Override
        public boolean onLongClick(View v) {
            clickInterface.onLongItemClick(getAdapterPosition());

            return false;
        }
    }
    public interface  ClickInterface{
        void onItemClick(int position);
        void onLongItemClick(int position);
    }
}
