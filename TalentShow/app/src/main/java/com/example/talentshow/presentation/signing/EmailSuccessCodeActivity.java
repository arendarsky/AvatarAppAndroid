package com.example.talentshow.presentation.signing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.talentshow.App;
import com.example.talentshow.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;
import toothpick.Toothpick;

public class EmailSuccessCodeActivity extends AppCompatActivity {

    @Inject
    Context appContext;

    @BindView(R.id.activity_email_success_code)
    EditText codeEdit;

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(appContext, EmailEnterActivity.class));
    }
}
