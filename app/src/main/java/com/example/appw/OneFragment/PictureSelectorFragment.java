/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.example.appw.OneFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appw.R;
import com.example.appw.util.Constant;
import com.example.appw.view.PictureSelectorDialog;
import com.google.zxing.activity.CaptureActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


/**
 * 版权所有：XXX有限公司
 * <p/>
 * PictureSelectorFragment
 *
 * @author zhou.wenkai ,Created on 2016-3-25 21:17:01
 * Major Function：<b>带有图片选择功能的Fragment</b>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class PictureSelectorFragment extends Fragment implements PictureSelectorDialog.OnSelectedListener {

    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    /**
     * 相册选图标记
     */
    private static final int GALLERY_REQUEST_CODE = 0;
    /**
     * 相机拍照标记
     */
    private static final int CAMERA_REQUEST_CODE = 1;
    /**
     * 拍照临时图片
     */
    private String mTempPhotoPath;
    /**
     * 剪切后图像文件
     */
    private Uri mDestination;

    /**
     * 选择提示 PopupWindow
     */
    private PictureSelectorDialog mSelectPictureDialog;
    /**
     * 图片选择的监听回调
     */
    private OnPictureSelectedListener mOnPictureSelectedListener;

    private OnFragmentSelectedListener mCallback;//调式用
    private String sessionid;

    /**
     * 剪切图片
     */
    protected void selectPicture() {
        mSelectPictureDialog.show(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mDestination = Uri.fromFile(new File(getContext().getCacheDir(), "cropImage.jpeg"));//最后文件的格式
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";//要删除
        mSelectPictureDialog = PictureSelectorDialog.getInstance();
        mSelectPictureDialog.setOnSelectedListener(this);
    }

    @Override
    public void OnSelected(View v, int position) {
        switch (position) {
            case 0:
                // "拍照"按钮被点击了
                takePhoto();
                break;
            case 1:
                // "从相册选择"按钮被点击了
                pickFromGallery();
                break;
            case 2:
                // "取消"按钮被点击了
                mSelectPictureDialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
                pickFromGallery();
            } else if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION) {
                takePhoto();
            }else if (requestCode == Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE){
                startQrCode();
            }else if (requestCode == Constant.REQ_PERM_READ_EXTERNAL_STORAGE){
                startQrCode();
            }
        }
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            mSelectPictureDialog.dismiss();

            File tempPhotoFile = new File(mTempPhotoPath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 如果在Android7.0以上,使用FileProvider获取Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                String authority = getContext().getPackageName() + ".fileProvider";
                Uri contentUri = FileProvider.getUriForFile(getContext(), authority, tempPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPhotoFile));//相机拍照后的存储路径
            }
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            mSelectPictureDialog.dismiss();
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

    protected void startQrCode(){
        //申请相机权限
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            // 申请权限
            requestPermissions(new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE);//这里去掉了activity
            return;
        }
        //从相册选择申请权限
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_READ_EXTERNAL_STORAGE);
            return;
        }
        //二维码扫码
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // 调用相机拍照
                case CAMERA_REQUEST_CODE:
                    File temp = new File(mTempPhotoPath);
                    startCropActivity(Uri.fromFile(temp));//相机获得的开始剪裁图片
                    break;
                // 直接从相册获取
                case GALLERY_REQUEST_CODE:
                    startCropActivity(data.getData());//data.getdata()就是destinationuri
                    break;
                //扫描完后显示二维码信息
                case Constant.REQ_QR_CODE:
                    Bundle bundle = data.getExtras();
                    sessionid = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
                    System.out.println(sessionid);

//                    //fragment-to-fragment
//                    TwoFragment twoFragment = new TwoFragment();
//                    Bundle bundle1 = new Bundle();
//                    //传递sessionid
//                    bundle1.putString("sessionid",sessionid);
//                    twoFragment.setArguments(bundle1);
//                    showFragment(PictureSelectorFragment.this,twoFragment);
//
                    //++++++++++++
                    mCallback.onFragmentSelected(sessionid);//调式用
                    break;
                // 裁剪图片结果
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;
                // 裁剪图片错误
                case UCrop.RESULT_ERROR:
                    handleCropError(data);
                    break;
                default:
                    break;
            }
        }
    }

    //从fragment跳转到另一个fragment
    private void showFragment(Fragment fragment1,Fragment fragment2){
        //获取FragmentTransaction对象
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //如果fragment2没有被添加，就添加它替换当前的fragment1
        if (!fragment2.isAdded()){
            System.out.println("没有加");
            transaction.add(R.id.view_pager,fragment2)
                    .addToBackStack(null).commitAllowingStateLoss();
        }else {//如果已经添加过了的话就隐藏fragment1，显示fragment2
            System.out.println("加了的");
            transaction.hide(fragment1).show(fragment2)
                    .addToBackStack(null).commitAllowingStateLoss();
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param source
     */
    public void startCropActivity(Uri source) {
        UCrop.Options options = new UCrop.Options();
        // 修改标题栏颜色
        options.setToolbarColor(getResources().getColor(R.color.blue));
        // 修改状态栏颜色
        options.setStatusBarColor(getResources().getColor(R.color.blue));
        // 隐藏底部工具
        options.setHideBottomControls(true);
        // 图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        // 设置图片压缩质量
        options.setCompressionQuality(100);
        // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
        // 如果不开启，用户不能拖动选框，只能缩放图片
//        options.setFreeStyleCropEnabled(true);

        UCrop.of(source, mDestination)
                // 长宽比
                .withAspectRatio(1, 1)
                // 图片大小
                .withMaxResultSize(512, 512)
                // 配置参数
                .withOptions(options)
                .start(getContext(), this);
    }

    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        deleteTempPhotoFile();
        final Uri resultUri = UCrop.getOutput(result);//通过data获取返回的uri
        if (null != resultUri && null != mOnPictureSelectedListener) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), resultUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveImage(bitmap, Calendar.getInstance().getTimeInMillis()+".jpg");
            mOnPictureSelectedListener.onPictureSelected(resultUri, bitmap);//OneFragment要调用
        } else {
            Toast.makeText(getContext(), "零零零零无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    //保存图片到项目文件夹下
    public void saveImage(Bitmap bitmap,String picname){
        File temp = new File("/sdcard/cropimage/");//自己另建文件
        System.out.println(temp+"咿呀咿呀哟");
        if (!temp.exists()){
            System.out.println("创建呀");
            temp.mkdir();
        }
        File f = new File("/sdcard/cropimage/",picname);
        if (f.exists()){
            f.delete();
        }
        try{
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
            out.flush();
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 处理剪切失败的返回值
     *
     * @param result
     */
    private void handleCropError(Intent result) {
        deleteTempPhotoFile();
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(getContext(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除拍照临时文件
     */
    private void deleteTempPhotoFile() {
        File tempFile = new File(mTempPhotoPath);
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }

    /**
     * 请求权限
     * <p>
     * 如果权限被拒绝过，则提示用户需要权限
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
        } else {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     */
    protected void showAlertDialog(String title, String message,
                                   DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   String positiveText,
                                   DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }

    /**
     * 设置图片选择的回调监听
     *
     * @param l
     */
    public void setOnPictureSelectedListener(OnPictureSelectedListener l) {
        this.mOnPictureSelectedListener = l;
    }

    /**
     * 图片选择的回调接口
     */
    public interface OnPictureSelectedListener {
        /**
         * 图片选择的监听回调
         *
         * @param fileUri
         * @param bitmap
         */
        void onPictureSelected(Uri fileUri, Bitmap bitmap);
    }


    public interface OnFragmentSelectedListener {
        public void onFragmentSelected(String info);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnFragmentSelectedListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Mush implement OnFragmentSelectedListener ");
        }
    }

}