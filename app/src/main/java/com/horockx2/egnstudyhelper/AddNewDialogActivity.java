package com.horockx2.egnstudyhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNewDialogActivity extends AppCompatActivity {

    RadioButton speakerAOption;
    RadioButton speakerBOption;

    EditText engSentence;
    EditText korSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_dialog);

        speakerAOption = findViewById(R.id.SpeakerA);
        speakerBOption = findViewById(R.id.SpeakerB);

        engSentence = findViewById(R.id.InputEngSentence);
        korSentence = findViewById(R.id.InputKorSentence);

        speakerAOption.setChecked(true);
    }


    public void OnClickAddSentence(View view) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("engSentence : " + engSentence.getText() +
                "\nkorSentence : " + korSentence.getText());

        String checkedRadioButtonName = speakerAOption.isChecked() ? "A" : "B";

        stringBuilder.append("\nSpeaker is : " + checkedRadioButtonName);

        Toast toast = Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
