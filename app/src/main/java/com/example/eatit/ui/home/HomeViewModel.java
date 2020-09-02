package com.example.eatit.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatit.Callback.IBestDealCallbackListener;
import com.example.eatit.Callback.IPopularCallbackListener;
import com.example.eatit.Common.Common;
import com.example.eatit.Model.BestDealModel;
import com.example.eatit.Model.PopularCategoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IPopularCallbackListener, IBestDealCallbackListener {

    private MutableLiveData<List<PopularCategoryModel>> polularList ;
    private MutableLiveData<List<BestDealModel>> bestDealList ;
    private MutableLiveData<String> messageError ;
    private IPopularCallbackListener popularCallbackListener ;
    private IBestDealCallbackListener bestDealCallbackListener ;





    public HomeViewModel() {
        popularCallbackListener = this;
        bestDealCallbackListener = this ;



    }

    public MutableLiveData<List<BestDealModel>> getBestDealList() {
        if(bestDealList == null)
        {
            bestDealList = new MutableLiveData<>(  );
            messageError = new MutableLiveData<>(  );
            loadBestDealList();
        }
        return bestDealList;
    }

    private void loadBestDealList() {
        List<BestDealModel> tempList  = new ArrayList<>(  );
        DatabaseReference bestDealRef = FirebaseDatabase.getInstance().getReference( Common.BEST_DEALS_REF);
        bestDealRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemSnapShot:snapshot.getChildren())
                {
                    BestDealModel model = itemSnapShot.getValue(BestDealModel.class);
                    tempList.add(model);

                }
                bestDealCallbackListener.onBestDealLoadSuccess( tempList );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                bestDealCallbackListener.onBestDealLoadFailed( error.getMessage() );

            }
        } );

    }

    public MutableLiveData<List<PopularCategoryModel>> getPolularList() {
        if(polularList == null)
        {
            polularList = new MutableLiveData<>(  );
            messageError = new MutableLiveData<>(  );
            loadPopularList();
        }
        return polularList;
    }

    private void loadPopularList() {
        List<PopularCategoryModel> tempList  = new ArrayList<>(  );
        DatabaseReference popularRef = FirebaseDatabase.getInstance().getReference( Common.POPULAR_CATEGORY_REF);
        popularRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemSnapShot:snapshot.getChildren())
                {
                    PopularCategoryModel model = itemSnapShot.getValue(PopularCategoryModel.class);
                    tempList.add(model);

                }
                popularCallbackListener.onPopularLoadSuccess( tempList );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                popularCallbackListener.onPopularLoadFailed(error.getMessage());

            }
        } );
    }

    public MutableLiveData<String> getMessageError() {
        return messageError;
    }

    @Override
    public void onPopularLoadSuccess(List<PopularCategoryModel> popularCategoryModels) {
        polularList.setValue( popularCategoryModels );

    }

    @Override
    public void onPopularLoadFailed(String message) {
        messageError.setValue( message );

    }

    @Override
    public void onBestDealLoadSuccess(List<BestDealModel> bestDealModels) {
        bestDealList.setValue( bestDealModels );
    }

    @Override
    public void onBestDealLoadFailed(String message) {
        messageError.setValue( message );

    }
}