package com.rummy51.ireifej.rummy51;

import java.util.Iterator;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class Rummy51Activity extends Activity {
    // Called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rummy51);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        final int textSize = 25;
        final String shufflerLabel = "Shuffler = ";

        final EditText editTextName1 = (EditText) findViewById(R.id.edittext_playerone);
        final EditText editTextName2 = (EditText) findViewById(R.id.edittext_playertwo);
        final EditText editTextName3 = (EditText) findViewById(R.id.edittext_playerthree);
        final EditText editTextName4 = (EditText) findViewById(R.id.edittext_playerfour);

        final TextView textViewName1 = (TextView) findViewById(R.id.textview_playerone);
        final TextView textViewName2 = (TextView) findViewById(R.id.textview_playertwo);
        final TextView textViewName3 = (TextView) findViewById(R.id.textview_playerthree);
        final TextView textViewName4 = (TextView) findViewById(R.id.textview_playerfour);

        // Find Tablelayout defined in main.xml

        final TableLayout tableLayout = (TableLayout)findViewById(R.id.myTableLayout);
        final Players players = new Players();

        final Button startButton = (Button) findViewById(R.id.start_button);
        final TextView editTextShuffler = (TextView) findViewById(R.id.edittext_shuffler);
        editTextShuffler.setTextSize(textSize);

        startButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on clicks
                players.AddPlayerName(String.valueOf(editTextName1.getText()));
                players.AddPlayerName(String.valueOf(editTextName2.getText()));
                players.AddPlayerName(String.valueOf(editTextName3.getText()));
                players.AddPlayerName(String.valueOf(editTextName4.getText()));

                textViewName1.setText(String.valueOf(editTextName1.getText()));
                textViewName2.setText(String.valueOf(editTextName2.getText()));
                textViewName3.setText(String.valueOf(editTextName3.getText()));
                textViewName4.setText(String.valueOf(editTextName4.getText()));

                if (String.valueOf(editTextName1.getText()).length() == 0)
                    editTextName1.setVisibility(View.GONE);

                if (String.valueOf(editTextName2.getText()).length() == 0)
                    editTextName2.setVisibility(View.GONE);

                if (String.valueOf(editTextName3.getText()).length() == 0)
                    editTextName3.setVisibility(View.GONE);

                if (String.valueOf(editTextName4.getText()).length() == 0)
                    editTextName4.setVisibility(View.GONE);

                ClearTextBoxes();

                Iterator<String> player = players.getPlayerNames().iterator();

                // create a new row to be added
                TableRow tableRow = new TableRow(Rummy51Activity.this);

                TextView gameNumberTextView = new TextView(Rummy51Activity.this);
                gameNumberTextView.setText("#   ");
                gameNumberTextView.setTextSize(textSize);
                gameNumberTextView.setLayoutParams(layoutParams);

                editTextShuffler.setText(shufflerLabel + players.GetNextShuffler());

                // add Game Number to row
                tableRow.addView(gameNumberTextView);

                // add Player names to row
                while (player.hasNext()) {
                    tableRow.setLayoutParams(layoutParams);
                    // Create a TextView to be the row-content
                    TextView textView = new TextView(Rummy51Activity.this);
                    textView.setText(player.next() + "    ");
                    textView.setLayoutParams(layoutParams);
                    textView.setTextSize(textSize);
                    tableRow.addView(textView);
                }

                // Add row to TableLayout
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(layoutParams));

                Button startButton = (Button) findViewById(R.id.start_button);
                startButton.setVisibility(View.GONE);

                Button continueButton = (Button) findViewById(R.id.continue_button);
                continueButton.setVisibility(View.VISIBLE);
            }
        });

        final Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                // player 1 score
                if (players.PlayerCount() > 0)
                    players.getListOfPlayers().get(0).IncrementCurrentGameTotalScore(Integer.parseInt(String.valueOf(editTextName1.getText())));

                // player 2 score
                if (players.PlayerCount() > 1) {
                    players.getListOfPlayers().get(1).IncrementCurrentGameTotalScore(Integer.parseInt(String.valueOf(editTextName2.getText())));
                }

                // player 3 score
                if (players.PlayerCount() > 2) {
                    players.getListOfPlayers().get(2).IncrementCurrentGameTotalScore(Integer.parseInt(String.valueOf(editTextName3.getText())));
                }

                // player 4 score
                if (players.PlayerCount() > 3) {
                    players.getListOfPlayers().get(3).IncrementCurrentGameTotalScore(Integer.parseInt(String.valueOf(editTextName4.getText())));
                }

                // Create a new row to be added
                TableRow tableRow = new TableRow(Rummy51Activity.this);
                tableRow.setLayoutParams(layoutParams);

                // add game number to row
                TextView textViewGameNo = new TextView(Rummy51Activity.this);
                textViewGameNo.setText(String.valueOf(players.GetCurrentGameNo()));
                textViewGameNo.setLayoutParams(layoutParams);
                textViewGameNo.setTextSize(textSize);
                tableRow.addView(textViewGameNo);

                // add player scores to row
                for (int i = 0; i < players.PlayerCount(); i++) {
                    // Create a TextView to be the row-content
                    TextView textView = new TextView(Rummy51Activity.this);
                    textView.setText(String.valueOf(players.getListOfPlayers().get(i).getCurrentGameTotalScore()));
                    textView.setLayoutParams(layoutParams);
                    textView.setTextSize(textSize);
                    tableRow.addView(textView);
                }

                // add row to Table Layout
                tableLayout.addView(tableRow, new TableLayout.LayoutParams(layoutParams));

                // clear current entered scores
                ClearTextBoxes();

                // increment game number
                if (players.IncrementCurrentGameNo()) {
                    // game over
                    Display("Game Over! The winner is " + players.GetWinnerName() + "!!!", true);
                    editTextShuffler.setText("");
                } else
                    editTextShuffler.setText(shufflerLabel + players.GetNextShuffler());
            }
        });
    }

    public void ClearTextBoxes()
    {
        EditText editTextName1 = (EditText) findViewById(R.id.edittext_playerone);
        EditText editTextName2 = (EditText) findViewById(R.id.edittext_playertwo);
        EditText editTextName3 = (EditText) findViewById(R.id.edittext_playerthree);
        EditText editTextName4 = (EditText) findViewById(R.id.edittext_playerfour);

        editTextName1.setText("");
        editTextName2.setText("");
        editTextName3.setText("");
        editTextName4.setText("");
    }

    public void Display(String text)
    {
        Display(text, false);
    }

    public void Display(String text, Boolean longShow)
    {
        if (longShow)
            Toast.makeText(Rummy51Activity.this, text, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(Rummy51Activity.this, text, Toast.LENGTH_SHORT).show();
    }
}