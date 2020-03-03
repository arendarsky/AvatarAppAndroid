package com.example.talentshow.presentation.main.fragments.casting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.talentshow.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CastingDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    EditText editText;
    private ItemClickListener mListener;
    public static CastingDialogFragment newInstance() {
        return new CastingDialogFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_casting, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.casting_dialog_cancel).setOnClickListener(this);
        view.findViewById(R.id.casting_dialog_btn).setOnClickListener(this);
        editText = view.findViewById(R.id.casting_dialog_text);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
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
    @Override public void onClick(View view) {
        mListener.onItemClick(view.getId());

        dismiss();
    }
    public interface ItemClickListener {
        void onItemClick(int item);
    }

    public String getText(){
        return editText.getText().toString();
    }
}
