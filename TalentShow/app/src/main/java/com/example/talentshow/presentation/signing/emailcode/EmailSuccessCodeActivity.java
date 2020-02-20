package com.example.talentshow.presentation.signing.emailcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.talentshow.App;
import com.example.talentshow.R;
import com.example.talentshow.presentation.producer.ActivityProducerName;
import com.example.talentshow.presentation.signing.emailenter.EmailEnterActivity;
import com.example.talentshow.presentation.star.fileload.ActivityStarFileLoad;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;
import toothpick.Toothpick;

public class EmailSuccessCodeActivity extends MvpAppCompatActivity implements EmailSuccessCodeView{


    @InjectPresenter
    EmailSuccessCodePresenter presenter;

    @ProvidePresenter
    EmailSuccessCodePresenter providePresenter(){
        return Toothpick.openScope(App.class).getInstance(EmailSuccessCodePresenter.class);
    }

    @Inject
    Context appContext;

    @BindView(R.id.activity_email_success_code)
    EditText codeEdit;

    @BindView(R.id.activity_email_success_progressbar)
    ProgressBar progressBar;

    private Slot[] codeSlot = {
            PredefinedSlots.any(),
            PredefinedSlots.hardcodedSlot(' '),
            PredefinedSlots.any(),
            PredefinedSlots.hardcodedSlot(' '),
            PredefinedSlots.any(),
            PredefinedSlots.hardcodedSlot(' '),
            PredefinedSlots.any(),
            PredefinedSlots.hardcodedSlot(' '),
            PredefinedSlots.any(),
            PredefinedSlots.hardcodedSlot(' '),
            PredefinedSlots.any()
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_success_code);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScope(App.class));
        FormatWatcher formatWatcher = new MaskFormatWatcher(MaskImpl.createTerminated(codeSlot));
        formatWatcher.installOn(codeEdit);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(appContext, EmailEnterActivity.class));
//    }

    @OnClick(R.id.activity_email_success_continue)
    public void continueClicked(){
        progressBar.setVisibility(View.VISIBLE);
        presenter.checkCode(getIntent().getStringExtra("mail"), codeEdit.getText().toString());
    }

    @Override
    public void codeConfirmed() {
        progressBar.setVisibility(View.INVISIBLE);
        boolean role = getIntent().getBooleanExtra("role", true);
//        if (role) startActivity(new Intent(appContext, ActivityStarFileLoad.class));
//        else startActivity(new Intent(appContext, ActivityProducerName.class));
    }

    @Override
    public void codeDiffers() {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(appContext, "Code differs or some network issues",
                Toast.LENGTH_SHORT).show();
    }
}
