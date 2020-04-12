package com.avatar.ava.presentation.main.BottomSheetFragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avatar.ava.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class ProfileBottomSheet extends BottomSheetDialogFragment
        implements View.OnClickListener {

     public static final String TAG = "ProfileBottomDialog";
     public static final int EDIT = R.id.profile_bottom_sheet_edit;
     public static final int SETTINGS = R.id.profile_bottom_sheet_settings;


     private ProfileBottomSheet.ItemClickListener mListener;
     public static ProfileBottomSheet newInstance() {
      return new ProfileBottomSheet();
     }
     @Nullable
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_profile_bottom_sheet, container, false);
     }
     @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      view.findViewById(EDIT).setOnClickListener(this);
      view.findViewById(SETTINGS).setOnClickListener(this);
     }
     @Override
     public void onAttach(Context context) {
      super.onAttach(context);
      if (context instanceof ProfileBottomSheet.ItemClickListener) {
       mListener = (ProfileBottomSheet.ItemClickListener) context;
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
      mListener.onItemClick(view.getId());

      dismiss();
     }



    public interface ItemClickListener {
      void onItemClick(int item);
     }




}
