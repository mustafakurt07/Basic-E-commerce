package com.example.mobirollertest.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobirollertest.R;
import com.example.mobirollertest.model.ProductUser;
import com.example.mobirollertest.viewmodel.ProductViewModel;
import com.example.mobirollertest.viewmodel.SignInViewModel;
import com.sdsmdg.tastytoast.TastyToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;


public class InsertFragment extends Fragment {
    private CircleImageView insertImageView;
    private EditText insertNameEditText;
    private EditText insertKategoryEditText;
    private EditText insertPriceEditText;
    private EditText insertDescEditText;
    private EditText insertDateEditText;
    private ProductViewModel productViewModel; //view Model
    private Uri insertImageUri=null;
    private Button saveButton;
    private static final int CAPTURE_PICCODE = 989;



    public InsertFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        insertNameEditText=view.findViewById(R.id.insertNameId);
        insertKategoryEditText=view.findViewById(R.id.insertKategoryId);
        insertPriceEditText=view.findViewById(R.id.insertPriceId);
        insertDateEditText=view.findViewById(R.id.insertDateId);
        insertDescEditText=view.findViewById(R.id.insertDescId);
        saveButton=view.findViewById(R.id.saveButtonId);
        insertImageView=view.findViewById(R.id.insertImageId);
        insertImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=randomDigit();
                String name=insertNameEditText.getText().toString();
                String kat=insertKategoryEditText.getText().toString();
                String price=insertPriceEditText.getText().toString();
                String date=insertDateEditText.getText().toString();
                String desc=insertDescEditText.getText().toString();
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(kat)&&!TextUtils.isEmpty(price)&&!TextUtils.isEmpty(date)&&!TextUtils.isEmpty(desc)&&insertImageUri!=null){
                    AlertDialog alertDialog=new SpotsDialog.Builder().setContext(getActivity()).setTheme(R.style.Custom).setCancelable(true).build();
                    alertDialog.show();
                    ProductUser productUser=new ProductUser(id,name,kat,price,date,desc,"image_uri");
                    productViewModel.insert(productUser,insertImageUri);
                    productViewModel.insertResultLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            alertDialog.dismiss();
                            TastyToast.makeText(getActivity().getApplicationContext(), ""+s, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                           // Toast.makeText(getActivity(), ""+s, Toast.LENGTH_SHORT).show();
                        }
                    });
                    insertImageView.setImageResource(R.drawable.profile);
                    insertNameEditText.setText("");
                    insertKategoryEditText.setText("");
                    insertPriceEditText.setText("");
                    insertDateEditText.setText("");
                    insertDescEditText.setText("");

                }
                else{
                    TastyToast.makeText(getActivity().getApplicationContext(), "Please Fill all Field !", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    //Toast.makeText(getActivity(),"Please Fill all Field",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void initViewModel() {
        productViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ProductViewModel.class);
    }

    private void uploadImage() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

            }
            else {
                imagePick();
            }
        }
       else {
            imagePick();
        }
    }
    private void imagePick() {

        Intent intent= new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CAPTURE_PICCODE);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(getActivity(),this);
    }
    private String randomDigit() {

        char[] chars= "1234567890".toCharArray();
        StringBuilder stringBuilder= new StringBuilder();
        Random random= new Random();
        for(int i=0;i<4;i++){
            char c= chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==getActivity().RESULT_OK){
                insertImageUri= result.getUri();
                insertImageView.setImageURI(insertImageUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }

    }
}