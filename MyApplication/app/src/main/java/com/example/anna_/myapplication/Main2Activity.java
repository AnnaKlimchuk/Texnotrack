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
    private static final String COUNTER = "counter";
    private int counter = 0;
    private static final int TIMER_INTERVAL = 1000;
    private static final int MAX_VALUE = 1001;
    private Button button = null;
    private TextView text = null;

    private String num_convering(int number) {
        switch (number) {
            case 1:
                return getString(R.string.one);
            case 2:
                return getString(R.string.two);
            case 3:
                return getString(R.string.three);
            case 4:
                return getString(R.string.four);
            case 5:
                return getString(R.string.five);
            case 6:
                return getString(R.string.six);
            case 7:
                return getString(R.string.seven);
            case 8:
                return getString(R.string.eight);
            case 9:
                return getString(R.string.nine);
            default:
                return getString(R.string.nothing);
        }
    }

    private String convering(int number) {
        String s = getString(R.string.nothing);
        if (number == 1000) {
            return getString(R.string.thousand);
        }
        int i = number % 10;
        int j = ((number - i) / 10) % 10;
        int k = (number - i - 10 * j) / 100;
        if (k > 0) {
            if (k > 4) {
                s = num_convering(k) + getString(R.string.hundreds);
            } else if (k > 2) {
                s = num_convering(k) + getString(R.string.hundred);
            } else if (k == 2) {
                s = getString(R.string.two_hundred);
            } else if (k == 1) {
                s = getString(R.string.hundred);
            }
        }
        if (j > 0) {
            if (k > 0) {
                s += getString(R.string.space);
            }
            if (j == 9) {
                s += getString(R.string.ninety);
            } else if (j > 4) {
                s += num_convering(j) + getString(R.string.tens_after_4);
            } else if (j == 4) {
                s += getString(R.string.forty);
            } else if (j > 1) {
                s += num_convering(j) + getString(R.string.tens_2_3);
            } else if (j == 1) {
                if (i > 3) {
                    s += num_convering(i).substring(0, num_convering(i).length() - 1) + getString(R.string.tens);
                } else if (i == 2) {
                    s += getString(R.string.twelve);
                } else if (i != 0) {
                    s += num_convering(i) + getString(R.string.tens);
                } else {
                    s += getString(R.string.ten);
                }
            }
        }
        if (i > 0) {
            if (j > 1) {
                s += getString(R.string.space) + num_convering(i);
            } else if (j == 0) {
                s = num_convering(i);
            }
        }
        return s;
    }

    private void initTimer(int counter) {
        timer = new CountDownTimer(MAX_VALUE * TIMER_INTERVAL - counter * TIMER_INTERVAL,
                TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                Main2Activity.this.counter = (int) (MAX_VALUE - millisUntilFinished / TIMER_INTERVAL);
                text.setText(convering(Main2Activity.this.counter));
            }

            @Override
            public void onFinish() {
                button.setText(R.string.start);
            }
        };
    }

    private CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = (Button) findViewById(R.id.start);
        text = (TextView) findViewById(R.id.counter);
        button.setOnClickListener(this);
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER);
            isOn = savedInstanceState.getBoolean(BUTTON);
        }
        initTimer(counter);
        if (isOn) {
            button.setText(R.string.stop);
        }
        if (counter != 0 && isOn) {
            timer.start();
        }
        if (counter != 0 && !isOn) {
            text.setText(convering(counter));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER, counter);
        outState.putBoolean(BUTTON, isOn);
    }

    @Override
    public void onClick(final View v) {
        if (!isOn) {
            isOn = true;
            ((Button) v).setText(R.string.stop);
            timer.start();
        } else {
            isOn = false;
            ((Button) v).setText(R.string.start);
            timer.cancel();
        }
    }
}
