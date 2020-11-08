package com.wolfsea.signviewdemo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import java.io.File;
/**
 *@desc 显示签名位图Activity
 *@author:liuliheng
 *@time: 2020/11/8 11:38
**/
public class ShowSignatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_signature);

        showSignatureBmp();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        signatureIv = findViewById(R.id.signature_iv);
    }

    /**
     *@desc 显示签名位图
     *@author:liuliheng
     *@time: 2020/11/8 11:09
    **/
    private void showSignatureBmp() {

        String path = FileUtils.ROOT_DIR + File.separator + FileUtils.SIGNATURE_PNG;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        boolean bitmapNotNull = bitmap != null;
        if (bitmapNotNull) {
            Log.d("result", "bitmapNotNull->bitmapNotNull");
            signatureIv.setImageBitmap(bitmap);
        } else {
            Log.d("result", "bitmapIsNull->bitmapIsNull");
        }
    }

    private AppCompatImageView signatureIv;
}