package com.avatar.ava.presentation.main.fragments.instruction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.avatar.ava.App;
import com.avatar.ava.R;
import com.avatar.ava.presentation.main.MainScreenTitles;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Toothpick;

public class InstructionFragment extends MvpAppCompatFragment implements InstructionView {
    @InjectPresenter
    InstructionPresenter presenter;

    @BindView(R.id.info_text)
    TextView infoText;

    public InstructionFragment() {
    }

    @ProvidePresenter
    InstructionPresenter getPresenter() {
        return Toothpick.openScope(App.class).getInstance(InstructionPresenter.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        presenter.setInfo(MainScreenTitles.valueOf(getArguments().getString("title")));
    }

    @Override
    public void setInfo(String text) {
        infoText.setText(text);
    }
}
