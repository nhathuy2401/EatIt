package com.example.eatit.Callback;

import com.example.eatit.Model.PopularCategoryModel;

import java.util.List;

public interface IPopularCallbackListener {
    void onPopularLoadSuccess(List<PopularCategoryModel>popularCategoryModels);
    void onPopularLoadFailed(String message);

}
