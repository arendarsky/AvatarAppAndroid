package com.avatar.ava.presentation.main.fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.domain.entities.PersonRatingDTO;
import com.avatar.ava.domain.entities.VideoDTO;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.avatar.ava.presentation.main.fragments.rating.RatingPresenter;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

import static android.app.Activity.RESULT_OK;
import static com.avatar.ava.DataModule.SERVER_NAME;


public class ProfileFragment extends MvpAppCompatFragment implements ProfileView {

    ArrayList<VideoDTO> videos = new ArrayList<VideoDTO>();

    @Inject
    Context appContext;

    @InjectPresenter
    ProfilePresenter presenter;

    @BindView(R.id.fragment_profile_image)
    ImageView profileImage;

    @BindView(R.id.fragment_profile_name)
    TextView name;

    @BindView(R.id.fragment_profile_likes)
    TextView likes;

    @BindView(R.id.fragment_profile_description)
    EditText description;


    @BindView(R.id.fragment_profile_edit_photo)
    TextView editPhoto;

    @BindView(R.id.fragment_profile_btn_edit)
    TextView editProfile;

    @ProvidePresenter
    ProfilePresenter getPresenter(){
        return Toothpick.openScope(App.class).getInstance(ProfilePresenter.class);
    }


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        description.setEnabled(false);
        presenter.getProfile();
    }


    @Override
    public void setDataProfile(PersonRatingDTO person) {
        //set Data
        Glide.with(getView())
                .load(SERVER_NAME + "/api/profile/photo/get/" + person.getPhoto())
                .circleCrop()
                .into(profileImage);
        name.setText(person.getName());
        likes.setText(person.getLikesNumber() + " Лайков");
        description.setText(person.getDescription());
//        videos.addAll(person.getPersonDTO().getVideo());
    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    //profileImage.setImageURI(selectedImage);
                    presenter.uploadPhoto(selectedImage);
                    Log.d("ProfileFragment", "setImage");
                }
        }
    }

    private boolean edit = false;

    @OnClick(R.id.fragment_profile_btn_edit)
    public void editProfile(){
        if(!edit){
            edit = true;
            description.setEnabled(true);
            editPhoto.setVisibility(View.VISIBLE);
            editProfile.setText("Применить");
        }else{
            edit = false;
            presenter.setDescription(description.getText().toString());
            description.setEnabled(false);
            editPhoto.setVisibility(View.GONE);
            editProfile.setText("Редактировать");
        }

    }

    @OnClick(R.id.fragment_profile_edit_photo)
    void changePhoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }
}
