package com.larsen.magnus.guessthenumber;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class RegActivity extends AppCompatActivity {

    TextView txtFinalScore;
    EditText txtName;
    Button btnReg;
    SQLiteDatabase db;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        AppEventsLogger.activateApp(this);

        txtFinalScore = (TextView) findViewById(R.id.txtFinalScore);
        txtName = (EditText) findViewById(R.id.txtName);
        btnReg = (Button) findViewById(R.id.btnReg);

        Intent intent = getIntent();
        txtFinalScore.setText("" + intent.getExtras().getLong("theScore"));
        final long endingScore = intent.getExtras().getLong("theScore");
        final int theNumberGuessedRight = intent.getExtras().getInt("theNumber");
        final String levelName = intent.getExtras().getString("theLevelName");

        final ContentValues playerScoreInsert = new ContentValues();
        SQLiteOpenHelper openHelper = new DBhelper(this);
        db = openHelper.getReadableDatabase();

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("I have guessed correctly on " + levelName + " difficulty")
                    .setContentDescription(
                            "I guessed the secret number and scored " + endingScore + " points. The secret number was " + theNumberGuessedRight)
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();

            shareDialog.show(linkContent);
            //shareDialog.show(linkContent, ShareDialog.Mode.WEB);
        }

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = txtName.getText().toString();

                    playerScoreInsert.put("name", name);
                    playerScoreInsert.put("secretnum", theNumberGuessedRight);
                    playerScoreInsert.put("score", endingScore);
                    playerScoreInsert.put("level", levelName);
                    db.insert("scoreTable", null, playerScoreInsert);

                    Intent hallOfFame = new Intent(RegActivity.this, HallActivity.class);
                    startActivity(hallOfFame);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnToMain = new Intent(RegActivity.this, MainActivity.class);
        startActivity(returnToMain);
    }
}
