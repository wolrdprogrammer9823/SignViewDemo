package com.wolfsea.signviewdemo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wolfsea.signviewdemo.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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
                //存储
//                Bitmap bitmap = signView.convertDataToBitmap();
//
//                signView.setVisibility(View.GONE);
//                showIv.setVisibility(View.VISIBLE);
//
//                showIv.setImageBitmap(bitmap);
                new SaveBitmapTask(this).execute();
                break;
            }
            case R.id.redraw_btn: {
                //重绘
                //signView.setVisibility(View.VISIBLE);
                //showIv.setVisibility(View.GONE);

                signView.redraw();
                break;
            }
            case R.id.store_show_btn: {
//                Observable.fromCallable(() -> {
//
//                    int sum = 0;
//                    int i = 1;
//                    while (i <= 10) {
//
//                        sum += i;
//                        Log.d(TAG, "sum:" + sum);
//                        i++;
//                        Thread.sleep(2000);
//                    }
//
//                    return sum;
//                }).subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(integer -> Log.d(TAG, "result:" + integer));
                //存储-显示
                storeShowBitmap(false);
                break;
            }
            case R.id.cut_store_show_btn: {
                //截取-存储-显示
                storeShowBitmap(true);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {


                break;
            }
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     *@desc 初始化方法
     *@author:liuliheng
     *@time: 2020/11/8 10:31
    **/
    private void init() {

        storeBtn = findViewById(R.id.store_btn);
        storeBtn.setOnClickListener(this);

        redrawBtn = findViewById(R.id.redraw_btn);
        redrawBtn.setOnClickListener(this);

        storeShowBtn = findViewById(R.id.store_show_btn);
        storeShowBtn.setOnClickListener(this);

        cutStoreShowBtn = findViewById(R.id.cut_store_show_btn);
        cutStoreShowBtn.setOnClickListener(this);

        signView = findViewById(R.id.sign_view);
        showIv = findViewById(R.id.show_iv);
    }

    /**
     *@desc 验证是否有存储权限
     *@author:liuliheng
     *@time: 2020/11/8 10:31
    **/
    private void verifyStoragePermission() {

        int permission = ActivityCompat.checkSelfPermission(this, PERMISSION_STORAGE[0]);
        boolean notGranted = permission != PackageManager.PERMISSION_GRANTED;
        if (notGranted) {
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_PERMISSIONS);
        }
    }

    /**
     *@desc 存储显示Bitmap
     *@author:liuliheng
     *@time: 2020/11/28 18:09
    **/
    private void storeShowBitmap(boolean cutBitmap) {
        addDisposable(Observable.fromCallable((Callable<Boolean>) () -> {

            verifyStoragePermission();
            signView.setCutBitmap(cutBitmap);
            FileUtils.saveBitmap(MainActivity.this, signView.convertDataToBitmap());
            return true;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    Intent intent = new Intent(MainActivity.this, ShowSignatureActivity.class);
                    startActivity(intent);
                }));
    }

    private static final String TAG = "MainActivity";

    private static final int REQUEST_PERMISSIONS = 0x01;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private AppCompatButton storeBtn;
    private AppCompatButton redrawBtn;
    private AppCompatButton storeShowBtn;
    private AppCompatButton cutStoreShowBtn;

    private SignView signView;
    private AppCompatImageView showIv;

    /**
     *@desc 保存图片到本地类
     *@author:liuliheng
     *@time: 2020/11/8 10:32
    **/
    private static class SaveBitmapTask extends AsyncTask<Void, Void, Integer> {

        private final WeakReference<MainActivity> weakReference;

        public SaveBitmapTask(MainActivity activity) {

            weakReference = new WeakReference<>(activity);
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            boolean weakReferenceIsNull = weakReference == null || weakReference.get() == null;
            if (weakReferenceIsNull) {
                return null;
            }

            MainActivity mainActivity = weakReference.get();
            mainActivity.verifyStoragePermission();
            FileUtils.saveBitmap(mainActivity, mainActivity.signView.convertDataToBitmap());
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

}