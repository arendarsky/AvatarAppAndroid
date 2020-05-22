package com.avatar.ava.presentation.main.BottomSheetFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avatar.ava.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ProfileVideoBottomSheet extends BottomSheetDialogFragment
        implements View.OnClickListener {

    public static final String TAG = "ProfileVideoBottomDialog";
    public static final int CASTING = R.id.profile_video_bottom_sheet_casting;
    public static final int DELETE = R.id.profile_video_bottom_sheet_delete;
    public static final int INTERVAL = R.id.profile_video_bottom_sheet_interval;

    private ProfileVideoBottomSheet.ItemClickListener mListener;
    public static ProfileVideoBottomSheet newInstance() {
        return new ProfileVideoBottomSheet();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_video_bottom_sheet, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(CASTING).setOnClickListener(this);
        view.findViewById(DELETE).setOnClickListener(this);
        view.findViewById(INTERVAL).setOnClickListener(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileVideoBottomSheet.ItemClickListener) {
            mListener = (ProfileVideoBottomSheet.ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override public void onClick(View view) {
        mListener.onItemVideoClick(view.getId());

        dismiss();
    }


    public interface ItemClickListener {
        void onItemVideoClick(int item);
    }

}