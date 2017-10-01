package com.example.anna_.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String BUTTON = "button";
    private boolean isOn = false;
    private static final String COUNTAR = "counter";
    private int counter = 0;
    private Button button = null;
    private String num_convering(int number) {
        if (number == 1)
            return "один";
        if (number == 2)
            return "два";
        if (number == 3)
            return "три";
        if (number == 4)
            return "четыре";
        if (number == 5)
            return "пять";
        if (number == 6)
            return "шесть";
        if (number == 7)
            return "семь";
        if (number == 8)
            return "восемь";
        if (number == 9)
            return "девять";
        return "";
    }
    private String convering(int number) {
        String s = "";
        if (number == 1000)
            return "тысяча";
        int i = number % 10;
        int j = ((number - i) / 10) % 10;
        int k = (number - i - 10 * j) / 100;
        if (k > 0) {
            if (k > 4) {
                s = num_convering(k) + "сот";
            } else if (k > 2) {
                s = num_convering(k) + "сто";
            } else if (k == 2) {
                s = "двести";
            } else if (k == 1) {
                s = "сто";
            }
        }
        if (j > 0) {
            if (k > 0) {
                s += " ";
            }
            if (j == 9) {
                s += "девяносто";
            } else if (j > 4) {
                s += num_convering(j) + "десят";
            } else if (j == 4) {
                s+= "сорок";
            } else if (j > 1) {
                s += num_convering(j) + "дцать";
            } else if (j == 1) {
                if (i > 3) {
                    s += num_convering(i).substring(0, num_convering(i).length() - 1) + "надцать";
                } else if (i == 2) {
                    s += "двенадцать";
                } else if (i != 0 ) {
                    s += num_convering(i) + "надцать";
                } else {
                    s += "десять";
                }
            }
        }
        if (i > 0) {
            if (j > 1) {
                s += " " + num_convering(i);
            } else if (j == 0) {
                s = num_convering(i);
            }
        }
        return s;
    }
    private CountDownTimer timer = new CountDownTimer(1001000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            counter = (int) (1001 - millisUntilFinished / 1000);
            ((TextView) findViewById(R.id.counter)).setText(convering(counter));
        }

        @Override
        public void onFinish() {
            button.setText("Start");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = (Button) findViewById(R.id.start);
        button.setOnClickListener(this);
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTAR);
            isOn = savedInstanceState.getBoolean(BUTTON);
            if(isOn == true) {
                button.setText("Stop");
            }
            if(counter != 0 && isOn == true) {
                timer = new CountDownTimer(1001000 - counter * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        counter = (int) (1001 - millisUntilFinished / 1000);
                        ((TextView) findViewById(R.id.counter)).setText(convering(counter));
                    }

                    @Override
                    public void onFinish() {
                        button.setText("Start");
                    }
                }.start();
            }
            if (counter != 0 && isOn == false) {
                ((TextView) findViewById(R.id.counter)).setText(convering(counter));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTAR, counter);
        outState.putBoolean(BUTTON, isOn);
    }

    @Override
    public void onClick(final View v) {
        if (((Button) v).getText().equals("Start")) {
            isOn = true;
            ((Button) v).setText("Stop");
            timer.start();
        } else if (((Button) v).getText() == "Stop") {
            isOn = false;
            ((Button) v).setText("Start");
            timer.cancel();
        }
    }
}
