package com.rummy51.ireifej.rummy51;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
        new SimpleEula(this).show();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int textSize = 25;
        final String shufflerLabel = "Shuffler = ";

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);

        final List<String> list;
        list = new ArrayList<String>();
        list.add("");
        list.add("-30");
        list.add("100");
        list.add("x2");

        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, list);
        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adp);
        spinner2.setAdapter(adp);
        spinner3.setAdapter(adp);
        spinner4.setAdapter(adp);

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
                String name1 = String.valueOf(editTextName1.getText());
                String name2 = String.valueOf(editTextName2.getText());
                String name3 = String.valueOf(editTextName3.getText());
                String name4 = String.valueOf(editTextName4.getText());

                if (name1.isEmpty() && name2.isEmpty() && name3.isEmpty() && name4.isEmpty()) {
                    Display("Enter at least one name", false);
                    return;
                }

                players.AddPlayerName(name1);
                players.AddPlayerName(name2);
                players.AddPlayerName(name3);
                players.AddPlayerName(name4);

                textViewName1.setText(name1);
                textViewName2.setText(name2);
                textViewName3.setText(name3);
                textViewName4.setText(name4);

                if (players.PlayerCount() == 1) {
                    spinner1.setVisibility(View.VISIBLE);
                }
                if (players.PlayerCount() == 2) {
                    spinner1.setVisibility(View.VISIBLE);
                    spinner2.setVisibility(View.VISIBLE);
                }
                if (players.PlayerCount() == 3) {
                    spinner1.setVisibility(View.VISIBLE);
                    spinner2.setVisibility(View.VISIBLE);
                    spinner3.setVisibility(View.VISIBLE);
                }
                if (players.PlayerCount() == 4) {
                    spinner1.setVisibility(View.VISIBLE);
                    spinner2.setVisibility(View.VISIBLE);
                    spinner3.setVisibility(View.VISIBLE);
                    spinner4.setVisibility(View.VISIBLE);
                }

                if (String.valueOf(editTextName1.getText()).length() == 0) {
                    editTextName1.setVisibility(View.GONE);
                    spinner1.setVisibility(View.GONE);
                }

                if (String.valueOf(editTextName2.getText()).length() == 0) {
                    editTextName2.setVisibility(View.GONE);
                    spinner2.setVisibility(View.GONE);
                }

                if (String.valueOf(editTextName3.getText()).length() == 0) {
                    editTextName3.setVisibility(View.GONE);
                    spinner3.setVisibility(View.GONE);
                }

                if (String.valueOf(editTextName4.getText()).length() == 0) {
                    editTextName4.setVisibility(View.GONE);
                    spinner3.setVisibility(View.GONE);
                }

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
                String score1 = String.valueOf(editTextName1.getText());
                String score2 = String.valueOf(editTextName2.getText());
                String score3 = String.valueOf(editTextName3.getText());
                String score4 = String.valueOf(editTextName4.getText());

                String score_error_msg = "Enter a valid numeric score for every player";
                String integer_regex = getString(R.string.integer_regex);

                if (players.PlayerCount() == 1 && (score1.isEmpty() || !score1.matches(integer_regex))) {
                    Display(score_error_msg, false);
                    return;
                }

                if (players.PlayerCount() == 2 && (score2.isEmpty() || !score2.matches(integer_regex))) {
                    Display(score_error_msg, false);
                    return;
                }

                if (players.PlayerCount() == 3 && (score3.isEmpty() || !score3.matches(integer_regex))) {
                    Display(score_error_msg, false);
                    return;
                }

                if (players.PlayerCount() == 4 && (score4.isEmpty() || !score4.matches(integer_regex))) {
                    Display(score_error_msg, false);
                    return;
                }

                // player 1 score
                if (players.PlayerCount() > 0)
                    players.getListOfPlayers().get(0).IncrementCurrentGameTotalScore(Integer.parseInt(score1));

                // player 2 score
                if (players.PlayerCount() > 1) {
                    players.getListOfPlayers().get(1).IncrementCurrentGameTotalScore(Integer.parseInt(score2));
                }

                // player 3 score
                if (players.PlayerCount() > 2) {
                    players.getListOfPlayers().get(2).IncrementCurrentGameTotalScore(Integer.parseInt(score3));
                }

                // player 4 score
                if (players.PlayerCount() > 3) {
                    players.getListOfPlayers().get(3).IncrementCurrentGameTotalScore(Integer.parseInt(score4));
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

                // hide keyboard
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                // increment game number
                if (players.IncrementCurrentGameNo()) {
                    // game over
                    Display("Game Over! The winner is " + players.GetWinnerName() + "!!!", true);
                    editTextShuffler.setText("");
                } else {
                    editTextShuffler.setText(shufflerLabel + players.GetNextShuffler());
                }
            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String selected_value = list.get(arg2);
                if (selected_value != "x2") {
                    editTextName1.setText(selected_value);
                    return;
                }
                String score = String.valueOf(editTextName1.getText());
                String integer_regex = getString(R.string.integer_regex);
                if (score.isEmpty() || !score.matches(integer_regex)) {
                    return;
                }
                Integer score_int = Integer.parseInt(score);
                score_int = score_int * 2;
                editTextName1.setText(String.valueOf(score_int));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String selected_value = list.get(arg2);
                if (selected_value != "x2") {
                    editTextName2.setText(selected_value);
                    return;
                }
                String score = String.valueOf(editTextName2.getText());
                String integer_regex = getString(R.string.integer_regex);
                if (score.isEmpty() || !score.matches(integer_regex)) {
                    return;
                }
                Integer score_int = Integer.parseInt(score);
                score_int = score_int * 2;
                editTextName2.setText(String.valueOf(score_int));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String selected_value = list.get(arg2);
                if (selected_value != "x2") {
                    editTextName3.setText(selected_value);
                    return;
                }
                String score = String.valueOf(editTextName3.getText());
                String integer_regex = getString(R.string.integer_regex);
                if (score.isEmpty() || !score.matches(integer_regex)) {
                    return;
                }
                Integer score_int = Integer.parseInt(score);
                score_int = score_int * 2;
                editTextName3.setText(String.valueOf(score_int));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String selected_value = list.get(arg2);
                if (selected_value != "x2") {
                    editTextName4.setText(selected_value);
                    return;
                }
                String score = String.valueOf(editTextName4.getText());
                String integer_regex = getString(R.string.integer_regex);
                if (score.isEmpty() || !score.matches(integer_regex)) {
                    return;
                }
                Integer score_int = Integer.parseInt(score);
                score_int = score_int * 2;
                editTextName4.setText(String.valueOf(score_int));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //optionally do something here
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

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);

        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner3.setSelection(0);
        spinner4.setSelection(0);
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