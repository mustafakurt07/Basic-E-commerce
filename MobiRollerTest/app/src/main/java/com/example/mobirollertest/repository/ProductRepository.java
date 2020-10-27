package com.example.mobirollertest.repository;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mobirollertest.model.ProductUpdate;
import com.example.mobirollertest.model.ProductUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;

public class ProductRepository {
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference() ;
    private  FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public MutableLiveData<String>insertProductFireStore(final ProductUser productUser, Uri uri)
    {
        MutableLiveData<String>insertResultLiveData=new MutableLiveData<>();
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        StorageReference image_path=storageReference.child("product_image").child(currentUser).child(productUser.getProductId()+".jpg");
        image_path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String,String>productMap=new HashMap<>();
                        productMap.put("product_id",productUser.getProductId());
                        productMap.put("product_name",productUser.getProductName());
                        productMap.put("product_kategory",productUser.getProductKategory());
                        productMap.put("product_price",productUser.getProductPrice());
                        productMap.put("product_date",productUser.getProductDate());
                        productMap.put("product_desc",productUser.getProductDesc());
                        productMap.put("product_image",uri.toString());
                        productMap.put("product_search",productUser.getProductId());
                        //put the firestore
                        firebaseFirestore.collection("ProductList").document(currentUser).collection("Product").document(productUser.getProductId()).set(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                insertResultLiveData.setValue("İnsert Successfully");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                insertResultLiveData.setValue(e.toString());

                            }
                        });


                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                insertResultLiveData.setValue(e.toString());

            }
        });






        return insertResultLiveData;



    }
    public MutableLiveData<List<ProductUser>>getDataFromFireStore()
    {
        MutableLiveData<List<ProductUser>>getFireStoreMutableLiveData=new MutableLiveData<>();
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        List<ProductUser>productUserList=new ArrayList<>();
        firebaseFirestore.collection("ProductList").document(currentUser).collection("Product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                productUserList.clear();
                for(DocumentSnapshot documentSnapshot:task.getResult())
                {
                    String id=documentSnapshot.getString("product_id");
                    String name=documentSnapshot.getString("product_name");
                    String kat=documentSnapshot.getString("product_kategory");
                    String price=documentSnapshot.getString("product_price");
                    String date=documentSnapshot.getString("product_date");
                    String desc=documentSnapshot.getString("product_desc");
                    String image=documentSnapshot.getString("product_image");
                    ProductUser productUser=new ProductUser(id,name,kat,price,date,desc,image);
                    productUserList.add(productUser);
                }
                getFireStoreMutableLiveData.setValue(productUserList);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return getFireStoreMutableLiveData;

    }
    public void deleteDataFirebase(final String id)
    {
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        StorageReference deleteImage=storageReference.child("product_image").child(currentUser).child(id+".jpg");
        deleteImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseFirestore.collection("ProductList").document(currentUser).collection("Product").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                });

            }
        });

    }
    public void updateImageFirebase(final  String id,Uri uri)
    {
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        StorageReference image_path=storageReference.child("product_image").child(currentUser).child(id+".jpg");
        image_path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        firebaseFirestore.collection("ProductList").document(currentUser).collection("Product").document(id).update("product_image",uri.toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });

                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });

    }
    public void updateInfoFirebase(ProductUpdate productUpdate){
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("ProductList").document(currentUser).collection("Product").document(productUpdate.getProductId())
                .update("product_name",productUpdate.getProductName(),"product_kategory",productUpdate.getProductKategory(),"produc_price",productUpdate.getProductPrice(),"product_date",productUpdate.getProductDate(),"product_desc",productUpdate.getProductDesc())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public MutableLiveData<List<ProductUser>>searchDataFromFireBase(String s)
    {
        MutableLiveData<List<ProductUser>>getSearchMutableLiveData=new MutableLiveData<>();
        String currentUser=firebaseAuth.getCurrentUser().getUid();
        final List<ProductUser>searchList=new ArrayList<>();
        firebaseFirestore.collection("ProductList").document(currentUser).collection("Product").whereEqualTo("product_search",s).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot documentSnapshot:task.getResult())
                {
                    String id=documentSnapshot.getString("product_id");
                    String name=documentSnapshot.getString("product_name");
                    String kat=documentSnapshot.getString("product_kategory");
                    String price=documentSnapshot.getString("product_price");
                    String date=documentSnapshot.getString("product_date");
                    String desc=documentSnapshot.getString("product_desc");
                    String image=documentSnapshot.getString("product_image");
                    ProductUser productUser=new ProductUser(id,name,kat,price,date,desc,image);
                    searchList.add(productUser);
                }
                getSearchMutableLiveData.setValue(searchList);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return getSearchMutableLiveData;
    }
}
