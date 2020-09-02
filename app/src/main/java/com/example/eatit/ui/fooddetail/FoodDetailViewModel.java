package com.example.eatit.ui.fooddetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatit.Common.Common;
import com.example.eatit.Model.FoodModel;

public class FoodDetailViewModel extends ViewModel {

    private MutableLiveData<FoodModel>  mutableLiveDataFood ;

    public MutableLiveData<FoodModel> getMutableLiveDataFood() {

        if(mutableLiveDataFood == null)
            mutableLiveDataFood = new MutableLiveData<>(  );
        mutableLiveDataFood.setValue( Common.selectedFood );

        return mutableLiveDataFood;
    }

    public  FoodDetailViewModel(){



    }

}
