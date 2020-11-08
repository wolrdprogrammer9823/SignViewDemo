package com.wolfsea.signviewdemo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * @author liuliheng
 * @desc  文件工具类
 * @time 2020/11/6  0:32
 **/
public class FileUtils {

    //存储根目录
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + File.separator
            + "local" + File.separator + "signature" + File.separator
            + "images";

    //签名图片文件名
    public static final String SIGNATURE_PNG = "signature.png";


    public static void saveBitmap(Context context, Bitmap bitmap) {

        //创建跟目录
        File rootDir = new File(ROOT_DIR);
        boolean rootDirNotExits = !rootDir.exists();
        if (rootDirNotExits) {

            boolean makeDirSucceed = rootDir.mkdirs();
            if (makeDirSucceed) {}
        }

        //删除旧文件
        File oldFile = new File(ROOT_DIR, SIGNATURE_PNG);
        boolean originFileExits = oldFile.exists();
        if (originFileExits) {

            boolean deleteFileSucceed = oldFile.delete();
            if (deleteFileSucceed) {
                refreshMedia(context, oldFile.getAbsolutePath());
            }
        }

        //创建新文件
        File newFile = new File(ROOT_DIR, SIGNATURE_PNG);
        boolean newNotExit = !newFile.exists();
        if (newNotExit) {
            try {
               boolean createFileSucceed =  newFile.createNewFile();
                if (createFileSucceed) {}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //把文件数据写入到流中
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        boolean fosNotNull = fos != null;
        if (fosNotNull) {

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }

        if (fosNotNull) {
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fosNotNull) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //插入图片到本地存储库
        try {
            String imagePath = newFile.getAbsolutePath();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, SIGNATURE_PNG, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //通知媒体库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri uri;
        boolean android_n_or_later = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
        if (android_n_or_later) {

            String authority = context.getPackageName() + ".file_provider";
            uri = DataFileProvider.getUriForFile(context, authority, newFile);
        } else {

            String uriString = "file://" + newFile;
            uri = Uri.parse(uriString);
        }
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     *@desc 刷新单个文件
     *@author:liuliheng
     *@time: 2020/11/8 11:34
    **/
    public static void refreshMedia(Context context, String filePath) {

        String[] paths = new String[]{filePath};
        String[] mimeTypes = new String[]{"image/jpeg", "image/png", "image/jpg"};
        MediaScannerConnection.scanFile(context, paths, mimeTypes, (path, uri) -> {
            //回调处理
        });
    }
}
