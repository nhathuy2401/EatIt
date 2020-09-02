package com.example.eatit.ui.fooddetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.eatit.Common.Common;
import com.example.eatit.Model.FoodModel;
import com.example.eatit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FoodDetailFragment extends Fragment {

    private Unbinder unbinder;

    @BindView( R.id.img_food )
    ImageView img_food ;
    @BindView( R.id.btnCart )
    CounterFab btnCart ;
    @BindView( R.id.btn_rating )
    FloatingActionButton btn_rating ;
    @BindView( R.id.food_name )
    TextView food_name ;
    @BindView( R.id.food_description )
    TextView food_description ;
    @BindView( R.id.food_price )
    TextView food_price ;
    @BindView( R.id.number_button )
    ElegantNumberButton numberButton;
    @BindView( R.id.ratingBar )
    RatingBar ratingBar ;
    @BindView( R.id.btnShowComment )
    Button btnShowComment ;

    @OnClick(R.id.btn_rating)
    void  onRatingButtonClick(){

        showDialogRating();


    }

    private void showDialogRating() {


        androidx.appcompat.app.AlertDialog.Builder builder= new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Rating food");
        builder.setMessage("Please Fill Information");

        View itemView= LayoutInflater.from(getContext()).inflate(R.layout.layout_rating, null);

        RatingBar rating  = (RatingBar)itemView.findViewById( R.id.rating_bar );
        EditText edt_comment = (EditText)itemView.findViewById( R.id.edt_comment );

        builder.setView( itemView );

        builder.setNegativeButton( "CANCEL", (dialogInterface, i) -> {

            dialogInterface.dismiss();

        } );
        builder.setPositiveButton( "OK", (dialogInterface, i) -> {


        } );
        AlertDialog dialog = builder.create();
        dialog.show();



    }


    private FoodDetailViewModel foodDetailViewModel;
    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodDetailViewModel =
                ViewModelProviders.of( this ).get( FoodDetailViewModel.class );
        View root = inflater.inflate( R.layout.fragment_food_detail, container, false );
        unbinder = ButterKnife.bind( this,root );
        foodDetailViewModel.getMutableLiveDataFood().observe( this, foodModel -> {
            displayInfo(foodModel);


        } );



        return root;
    }

    private void displayInfo(FoodModel foodModel) {
        Glide.with( getContext() ).load( foodModel.getImage() ).into( img_food );
        food_name.setText( new StringBuilder( foodModel.getName() ) );
        food_description.setText( new StringBuilder( foodModel.getDescription() ) );
        food_price.setText( new StringBuilder( foodModel.getPrice().toString() ) );


        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle( Common.selectedFood.getName() );


    }
}