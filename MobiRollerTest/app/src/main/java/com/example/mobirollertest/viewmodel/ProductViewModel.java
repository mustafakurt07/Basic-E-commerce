package com.example.mobirollertest.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mobirollertest.model.ProductUpdate;
import com.example.mobirollertest.model.ProductUser;
import com.example.mobirollertest.repository.ProductRepository;

import java.util.List;

public class ProductViewModel  extends AndroidViewModel {
    private ProductRepository repository;
    public LiveData<String>insertResultLiveData;
    public LiveData<List<ProductUser>>getProductLiveData;
    public LiveData<List<ProductUser>>searchLiveData;
    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository=new ProductRepository();
    }

    public void insert(ProductUser productUser, Uri uri)
    {
        insertResultLiveData=repository.insertProductFireStore(productUser,uri);

    }
    public void show()
    {
        getProductLiveData=repository.getDataFromFireStore();

    }
    public void delete(String id)
    {
        repository.deleteDataFirebase(id);
    }
    public void updateImage(String id,Uri uri)
    {
        repository.updateImageFirebase(id,uri);
    }
    public void  updateInfo(ProductUpdate productUpdate)
    {
        repository.updateInfoFirebase(productUpdate);
    }
    public  void search(String s)
    {
        searchLiveData=repository.searchDataFromFireBase(s);
    }
}
