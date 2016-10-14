package net.rjbatista.retryviewsample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.rjbatista.retryview.RetryView;
import net.rjbatista.retryview.RetryViewListener;

public class MainActivity extends AppCompatActivity implements RetryViewListener {

    private View mainView;
    private View loadingView;
    private RetryView retryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = findViewById(R.id.mainView);
        loadingView = findViewById(R.id.loadingView);
        retryView = (RetryView) findViewById(R.id.retryView);
        retryView.setListener(this);

        Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

    }

    private void connect() {

        // show loading
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // hide loading
                loadingView.setVisibility(View.GONE);

                // hide main
                mainView.setVisibility(View.GONE);

                // show retry
                retryView.setVisibility(View.VISIBLE);

            }
        }, 2000);
    }

    // RetryViewListener

    @Override
    public void onRetryButtonClicked() {
        connect();
    }
}
