package br.com.fenixdev.documentProviderFileExpose;

import androidx.appcompat.app.AppCompatActivity;
import br.com.fenixdev.documentProviderFileExpose.R;
import br.com.fenixdev.documentProviderFileExpose.log.LogExample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        Button logButton = findViewById(R.id.mainActivity_logTimeButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogExample.logTime(MainActivity.this);
                Toast.makeText(MainActivity.this,"Current time is saved on file!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}