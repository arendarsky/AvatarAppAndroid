package com.avatar.ava.presentation.main.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenPostman;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toothpick.Toothpick;

public class FragmentFileLoadMain extends Fragment {

    @Inject
    Context appContext;

    private Activity activity;
    private final int LOAD_VIDEO = 5;
    private final int CAPTURE_VIDEO = 8;

    @BindView(R.id.file_load_main_progressbar)
    ProgressBar progressBar;

    BottomSheetDialog bottomSheetDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toothpick.inject(this, Toothpick.openScope(App.class));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_file_load, null);
        bottomSheetDialog.setContentView(sheetView);
        LinearLayout camera = sheetView.findViewById(R.id.bottom_sheet_file_load_camera);
        LinearLayout galery = sheetView.findViewById(R.id.bottom_sheet_file_load_file);
        galery.setOnClickListener(v -> {
            try {
                bottomSheetDialog.hide();
                ((MainScreenPostman) activity).fragmentAction(LOAD_VIDEO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        });

        camera.setOnClickListener(v -> {
            try {
                bottomSheetDialog.hide();
                ((MainScreenPostman) activity).fragmentAction(CAPTURE_VIDEO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file_load_main, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) activity = (Activity) context;
    }

    @OnClick(R.id.fragment_file_load_main_button_add)
    void loadFileClicked() {
        bottomSheetDialog.show();
//        progressBar.setVisibility(View.VISIBLE);
//        try {
//            ((MainScreenPostman) activity).fragmentAction(LOAD_VIDEO);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
