package com.example.mobirollertest.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobirollertest.R;
import com.example.mobirollertest.adapter.ProductAdapter;
import com.example.mobirollertest.dialog.DetailsDialog;
import com.example.mobirollertest.model.ProductUpdate;
import com.example.mobirollertest.model.ProductUser;
import com.example.mobirollertest.viewmodel.ProductViewModel;
import com.sdsmdg.tastytoast.TastyToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class ListFragment extends Fragment implements ProductAdapter.ClickInterface{
    private ProductViewModel productViewModel;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<ProductUser>productUserList=new ArrayList<>();
    private ProductAdapter productAdapter=new ProductAdapter(this);
    private int userPosition;
    private Button updateImageButton,updateInfoButton;
    private TextView idTextView;
    private EditText nameEditText,katEditText,priceEditText,dateEditText,descEditText;
    private Uri updateUri=null;
    private static final int CAPTURE_PICCODE = 989;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        setUpRecycler();
        searchView=view.findViewById(R.id.searchViewId);
        recyclerView=view.findViewById(R.id.recycleViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                final AlertDialog dialog=new SpotsDialog.Builder().setContext(getActivity()).setTheme(R.style.Custom).setCancelable(true).build();
                dialog.show();
                productViewModel.search(s);
                productViewModel.searchLiveData.observe(getViewLifecycleOwner(), new Observer<List<ProductUser>>() {
                    @Override
                    public void onChanged(List<ProductUser> productUsers) {
                        dialog.dismiss();
                        productUserList=productUsers;
                        productAdapter.getProductList(productUsers);
                        recyclerView.setAdapter(productAdapter);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void setUpRecycler() {
        AlertDialog alertDialog=new SpotsDialog.Builder().setContext(getActivity()).setTheme(R.style.Custom).setCancelable(true).build();
        alertDialog.show();
        productViewModel.show();
        productViewModel.getProductLiveData.observe(getViewLifecycleOwner(), new Observer<List<ProductUser>>() {
            @Override
            public void onChanged(List<ProductUser> productUsers) {
               /* String name=productUsers.get(0).getProductName();
                Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show();*/
                    alertDialog.dismiss();
                    productUserList=productUsers;
                    productAdapter.getProductList(productUserList);
                    recyclerView.setAdapter(productAdapter);

            }
        });
    }

    private void initViewModel() {
        productViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ProductViewModel.class);
    }

    @Override
    public void onItemClick(int position) {
       openDetailsDialog(position);

    }
    private void openDetailsDialog(int position)
    {
        DetailsDialog detailsDialog=new DetailsDialog(productUserList,position);
        detailsDialog.show(getChildFragmentManager(),"Details Dialog");
    }

    @Override
    public void onLongItemClick(int position) {
        final String id=productUserList.get(position).getProductId();
      AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
      String[]option={"Update","Delete"};
      builder.setItems(option, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              if(which==0)
              {
                  update(position);

              }
              if(which==1)
              {
                  productViewModel.delete(id);
                  Toast.makeText(getActivity(), "Deleted :(", Toast.LENGTH_SHORT).show();
                  productUserList.remove(position);
                  productAdapter.notifyItemRemoved(position);

              }

          }
      }).create().show();

    }

    private void update(int position) {
        userPosition=position;
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.update_dialog,null);
        builder.setView(view).setTitle("Update Product").setIcon(R.drawable.ic_update).setCancelable(true)
        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        updateImageButton=view.findViewById(R.id.updateImageButtonId);
        updateInfoButton=view.findViewById(R.id.updateInfoButtonId);
        idTextView=view.findViewById(R.id.updateId);
        nameEditText=view.findViewById(R.id.updateNameId);
        katEditText=view.findViewById(R.id.updateKategoryId);
        priceEditText=view.findViewById(R.id.updatePriceId);
        dateEditText=view.findViewById(R.id.updateDateId);
        descEditText=view.findViewById(R.id.updateDescId);
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=productUserList.get(position).getProductId();
                String name=nameEditText.getText().toString();
                String kat=katEditText.getText().toString();
                String price=priceEditText.getText().toString();
                String date=dateEditText.getText().toString();
                String desc=descEditText.getText().toString();
                ProductUpdate productUpdate=new ProductUpdate(id,name,kat,price,date,desc);
                productViewModel.updateInfo(productUpdate);
                TastyToast.makeText(getActivity().getApplicationContext(), "Updated", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                //Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                

            }
        });
        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();

            }
        });
        idTextView.setText("ID :"+" "+productUserList.get(position).getProductId());
        nameEditText.setText(productUserList.get(position).getProductName());
        katEditText.setText(productUserList.get(position).getProductKategory());
        priceEditText.setText(productUserList.get(position).getProductPrice());
        dateEditText.setText(productUserList.get(position).getProductDate());
        descEditText.setText(productUserList.get(position).getProductDesc());
        builder.create().show();




    }

    private void pickImage() {
        Intent intent= new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CAPTURE_PICCODE);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(getContext(),this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==getActivity().RESULT_OK){
                updateUri= result.getUri();
                String id=productUserList.get(userPosition).getProductId();
                productViewModel.updateImage(id,updateUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}