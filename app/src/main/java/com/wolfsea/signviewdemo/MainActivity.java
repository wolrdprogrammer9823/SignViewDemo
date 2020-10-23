package com.wolfsea.signviewdemo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void onClick(View view) {

        final int VIEW_ID = view.getId();
        switch (VIEW_ID) {
            case R.id.store_btn: {

                break;
            }
            case R.id.redraw_btn: {

                signView.redraw();
                break;
            }
            default:
                break;
        }
    }

    //初始化方法
    private void init() {

        storeBtn = findViewById(R.id.store_btn);
        storeBtn.setOnClickListener(this);
        redrawBtn = findViewById(R.id.redraw_btn);
        redrawBtn.setOnClickListener(this);

        signView = findViewById(R.id.sign_view);
    }

    private AppCompatButton storeBtn;
    private AppCompatButton redrawBtn;

    private SignView signView;
}