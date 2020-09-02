package com.example.eatit.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.example.eatit.Adapter.MyBestDealAdapter;
import com.example.eatit.Adapter.MyPopularCategoriesAdapter;
import com.example.eatit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Unbinder unbinder;
    @BindView( R.id.recyler_popular )
    RecyclerView recyler_popular ;

    @BindView( R.id.viewpaper )
    LoopingViewPager viewPager;

    LayoutAnimationController layoutAnimationController ;




    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of( this ).get( HomeViewModel.class );
        View root = inflater.inflate( R.layout.fragment_home, container, false );
        unbinder = ButterKnife.bind( this,root );
        init();
        homeViewModel.getPolularList().observe( this,popularCategoryModels -> {
            //Create adapter

            MyPopularCategoriesAdapter adapter = new MyPopularCategoriesAdapter( getContext(),popularCategoryModels );
            recyler_popular.setAdapter( adapter );
            recyler_popular.setLayoutAnimation( layoutAnimationController );




        });
        homeViewModel.getBestDealList().observe( this,bestDealModels -> {
            MyBestDealAdapter adapter = new MyBestDealAdapter( getContext(),bestDealModels,true );
            viewPager.setAdapter( adapter );

        } );

        return root;
    }

    private void init() {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation( getContext(),R.anim.layout_item_from_left );
        recyler_popular.setHasFixedSize( true );
        recyler_popular.setLayoutManager( new LinearLayoutManager( getContext(),RecyclerView.HORIZONTAL,false ) );

    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.resumeAutoScroll();
    }

    @Override
    public void onPause() {
        viewPager.pauseAutoScroll();
        super.onPause();
    }
}