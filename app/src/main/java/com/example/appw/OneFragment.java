package com.example.appw;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.appw.Login.LoginActivity;
import com.example.appw.TwoFragment.MyDialog;
import com.example.appw.nestia.biometriclib.BiometricPromptManager;

import java.io.IOException;
import java.util.List;

public class OneFragment<onActivityResult, ovrride> extends Fragment implements FingerDialog.OnCenterItemClickListener ,MyDialog.OnCenterItemClickListenerchoose{

    private CameraManager mCameraManager;
    private Camera camera;
    private Camera camera1;
    private int Hz=50;
    private String id;
    String databack;

    private boolean isRequestPermission = false;
    AppCompatImageButton tryfin;
    AppCompatImageButton trytowway;

    BiometricPromptManager fManager;
    Thread time;
    Thread time3;
    Camera.Parameters parameters;
//    AppCompatImageButton button;
    private SurfaceView displaySfv;
    private int currentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
    boolean openc;
    long start_timec;
    int numc;
    //需要申请的权限
    private String[] permissions = {Manifest.permission.CAMERA};
    private static final int CAMERA_PERMISSION_CODE = 1;
    FingerDialog mMyDialog;
    MyDialog sMyDialog;
    @Nullable

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            fManager=BiometricPromptManager.from(getActivity());
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("SDK version is "+ Build.VERSION.SDK_INT);
//            stringBuilder.append("\n");
//            stringBuilder.append("isHardwareDetected : "+fManager.isHardwareDetected());
//            stringBuilder.append("\n");
//            stringBuilder.append("hasEnrolledFingerprints : "+fManager.hasEnrolledFingerprints());
//            stringBuilder.append("\n");
//            stringBuilder.append("isKeyguardSecure : "+fManager.isKeyguardSecure());
//            stringBuilder.append("\n");
        }else{
            View vview = getLayoutInflater().inflate(R.layout.layout_biometric_prompt_dialog, null);
            mMyDialog = new FingerDialog(getContext(), 0, 0, vview, R.style.DialogTheme);
            mMyDialog.setOnCenterItemClickListener((FingerDialog.OnCenterItemClickListener) this);
        }
        View vview = getLayoutInflater().inflate(R.layout.choosedialog, null);
        sMyDialog = new MyDialog(getContext(), 0, 0, vview, R.style.DialogTheme,"choose");
        sMyDialog.setOnCenterItemClickListenerchoose((MyDialog.OnCenterItemClickListenerchoose) this);

//        Toast.makeText(getContext(), "stringBuilder", Toast.LENGTH_SHORT).show();
        threadtime3 mythread= new threadtime3();
        time3=new Thread(mythread);
//        trytowway=view.findViewById(R.id.button3);
//        trytowway.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), TowWayActivity.class);
//                startActivityForResult(intent,1);
////                trytowway.setEnabled(false);
////                String Data="10101011";
////                for (int i=0;i<25;i++) Data=Data+"10";
////                Data=Data+"1";
////                flash(Data);
////                databack="";
////                openc=true;
////                time3.start();
////                trytowway.setEnabled(true);
//            }
//        });
        tryfin=view.findViewById(R.id.button2);
        tryfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMyDialog.setCancelable(true);
                sMyDialog.show();
//                tryfin.setEnabled(false);
//                String Data="";
//                for (int i=0;i<25;i++) Data=Data+"10";
//                Data=Data+"1";
//                flash(Data);
//                tryfin.setEnabled(true);
//                Intent intent = new Intent(getContext(), TowWayActivity.class);
//                startActivityForResult(intent,1);


//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && fManager.isBiometricPromptEnable()) {
//                    fManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
//                        @Override
//                        public void onUsePassword() {
//                            Toast.makeText(getContext(), "onUsePassword", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onSucceeded() {
//                            Toast.makeText(getContext(), "onSucceeded", Toast.LENGTH_SHORT).show();
//                            if (!MainActivity.norlogin){
//                                Intent intent = new Intent(getContext(), LoginActivity.class);
//                                startActivityForResult(intent,1);
//                            }
//                            else {
//                                Intent intent = new Intent(getContext(), TowWayActivity.class);
//                                startActivityForResult(intent,1);
////                                tryfin.setEnabled(false);
////                                String Data="";
////                                for (int i=0;i<25;i++) Data=Data+"10";
////                                Data=Data+"1";
////                                flash(Data);
////                                tryfin.setEnabled(true);
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailed() {
//                            long now=System.currentTimeMillis()%300+1000;
//                            Log.e("TestTime",":"+now+"ms");
//                            Toast.makeText(getContext(), "onFailed", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onError(int code, String reason) {
//                            long now=System.currentTimeMillis()%300+1000;
//                            Log.e("TestTime",":"+now+"ms");
//                            Toast.makeText(getContext(), "onError", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onCancel() {
//
//                            Toast.makeText(getContext(), "onCancel", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }else {
//                    mMyDialog.setCancelable(true);
//                    mMyDialog.show();
//                    Intent intent = new Intent(getContext(), TowWayActivity.class);
//                    startActivityForResult(intent,1);
//                }
            }
        });
//        button=view.findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        return view;

    }
    @Override
    public void OnCenterItemClick(FingerDialog dialog, View view) {
        switch (view.getId()){
            case R.id.finger:
                Toast.makeText(getContext(),"okok",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){   //如果请求码是当前Activity的
            if(resultCode==1){    //如果结果码是某个目标Activity的，就取出对应的数据。这个判断主要是可能有多个目标Activity向当前Activity回传数据，这些Intent数据中可能具有相同的key

                Bundle bundle = data.getExtras();
                Log.e("TAG", "one"+bundle.getString("user"));
                callBackValue.SendMessageValue(bundle.getString("user"),bundle.getString("pwd"));
            }
        }
    }
    CallBackValue callBackValue;

    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue =(CallBackValue) getActivity();
    }


    @Override
    public void OnCenterItemClickchoose(MyDialog dialog, View view) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && fManager.isBiometricPromptEnable()) {
            fManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
                @Override
                public void onUsePassword() {
                    Toast.makeText(getContext(), "onUsePassword", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSucceeded() {
                    Toast.makeText(getContext(), "onSucceeded", Toast.LENGTH_SHORT).show();
                    if (!MainActivity.norlogin){
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivityForResult(intent,1);
                    }
                    else {
                        Intent intent = new Intent(getContext(), TowWayActivity.class);
                        startActivityForResult(intent,1);
//                                tryfin.setEnabled(false);
//                                String Data="";
//                                for (int i=0;i<25;i++) Data=Data+"10";
//                                Data=Data+"1";
//                                flash(Data);
//                                tryfin.setEnabled(true);
                    }

                }

                @Override
                public void onFailed() {
                    long now=System.currentTimeMillis()%300+1000;
                    Log.e("TestTime",":"+now+"ms");
                    Toast.makeText(getContext(), "onFailed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int code, String reason) {
                    long now=System.currentTimeMillis()%300+1000;
                    Log.e("TestTime",":"+now+"ms");
                    Toast.makeText(getContext(), "onError", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {

                    Toast.makeText(getContext(), "onCancel", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            mMyDialog.setCancelable(true);
            mMyDialog.show();
            Intent intent = new Intent(getContext(), TowWayActivity.class);
            startActivityForResult(intent,1);
        }


    }

    //定义一个回调接口
    public interface CallBackValue{
        public void SendMessageValue(String user,String pwd);
    }


    /**
     * 打开手电筒
     */
    private View.OnClickListener openOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mCameraManager =   (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
                try {
                    id = mCameraManager.getCameraIdList()[0];
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            else {
                camera = Camera.open();
                camera.startPreview();
                parameters = camera.getParameters();
            }
            open();
        }
    };

    /**
     * 关闭手电筒
     */
    private View.OnClickListener closeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        mCameraManager =   (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            }
            else {
                camera.release();
            }
        }
    };

    /**
     * 开启闪烁
     */
    public void flash(String data) {
        threadtime mythread= new threadtime();
        time=new Thread(mythread);
        int length=data.length();
        long t=(long )(1000* ((double)1 / (double)Hz ) );
        String s="";
        Log.e("msg","nonono");
        Log.e("msg","now"+Build.VERSION.SDK_INT);
        Log.e("msg","zhike"+Build.VERSION_CODES.LOLLIPOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraManager =   (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            try {
                id = mCameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        else {
            camera = Camera.open();
            camera.startPreview();
            parameters = camera.getParameters();
        }
        int p=0;
        long now=System.currentTimeMillis();
        for (Character c:data.toCharArray()){
            if (c=='1')
            {
                if (p==0){
                    p=1;
                    open();
                }

                long now2=System.currentTimeMillis();
                if (now2-now<t)
                {
                    try {
                        time.sleep(t-(now2-now));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                now=System.currentTimeMillis();

            }
            else
            {
                if (p==1) {
                    p=0;
                    close();
                }
                long now2=System.currentTimeMillis();
                if (now2-now<t)
                {
                    try {
                        time.sleep(t-(now2-now));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                now=System.currentTimeMillis();
            }
        }
            close();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            close();
//                        mCameraManager =   (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        }
        else {
            camera.release();
        }

    }


    /**
     * 关闭闪烁

     private OnClickListener closeFlickerOnClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
    isShanshuo = false;
    openFlicker.setEnabled(true);
    }
    }; */
    /**
     * 打开闪光灯
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.N)
//      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void open() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//                    if (mCameraManager==null)
//                        mCameraManager =   (CameraManager)getSystemService(Context.CAMERA_SERVICE);
            try {
                Log.e("msg", "open: "+id );
                Log.e("msg", "opencamera: "+mCameraManager );
                mCameraManager.setTorchMode(id,true);//打开闪光灯
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            //    mCameraManager.setTorchMode(mCameraManager.getCameraIdList(),false);//关闭闪光灯
//                   open23(true);
        }
        else
            try {
                if (camera ==null ) {
                    camera = Camera.open();
                    camera.startPreview();
                }
                if (parameters==null){
                    Camera.Parameters parameters = camera.getParameters();
                }
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * 关闭闪光灯
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.N)
    private void close() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if ( mCameraManager == null) {
                return;
            }
            try {
                mCameraManager.setTorchMode(id,false);//guanbi 闪光灯
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
//                    mCameraManager=null;
        }
        else
            try {
//                    Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
//                    camera.release();
//                    camera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    //    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        camera.release();
//    }

     class threadtime  implements Runnable {
        @Override
        public void run() {

        }

    }
    class threadtime3  implements Runnable {
        @Override
        public void run() {
            while (databack.length()<100){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            String data3="10101011";
            for (int i=0;i<25;i++) data3=data3+"10";
            data3=data3+"1";
            flash(data3);
            Log.e("msg","okokokok");

        }

    }


    private void checkAndInitCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(getContext(), permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                isRequestPermission = true;
                ActivityCompat.requestPermissions(getActivity(), permissions, CAMERA_PERMISSION_CODE);
            } else {
                initCamera();
            }
        } else {
            initCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.e(TAG, "onRequestPermissionsResult");
        if (requestCode == CAMERA_PERMISSION_CODE) {
            boolean isGranted = false;
            if (grantResults != null && grantResults.length > 0) {
                isGranted = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        isGranted = false;
                        break;
                    }
                }
            }
            if (isGranted) {
                initCamera();
            } else {
//                Toast.makeText(this, "请去设置授权", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 初始化照片
     */
    private void initCamera() {
        if (camera1 != null) {
            camera1.startPreview();
            setPreviewLight();
        }
//        Log.e(TAG, "initCamera");
        //1. Obtain an instance of Camera from open(int).
        //这里可以根据前后摄像头设置
        camera1 = openCamera(currentCameraType);
        if (camera1 == null) {
            return;
        }
        //2. Get existing (default) settings with getParameters().
        //获得存在的默认配置属性
        Camera.Parameters parameters = camera1.getParameters();

        //3. If necessary, modify the returned Camera.Parameters object and call setParameters(Camera.Parameters).
        //可以根据需要修改属性，这些属性包括是否自动持续对焦、拍摄的gps信息、图片视频格式及大小、预览的fps、
        // 白平衡和自动曝光补偿、自动对焦区域、闪光灯状态等。
        //具体可以参阅https://developer.android.com/reference/android/hardware/Camera.Parameters.html
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters
                .FOCUS_MODE_CONTINUOUS_PICTURE)) {
            //自动持续对焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        //在设置图片和预览的大小时要注意当前摄像头支持的大小，不同手机支持的大小不同，如果你的SurfaceView不是全屏，有可能被拉伸。
        // parameters.getSupportedPreviewSizes(),parameters.getSupportedPictureSizes()
        List<Camera.Size> picSizes = parameters.getSupportedPictureSizes();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Camera.Size picSize = getPictureSize(picSizes, width, height);
        parameters.setPictureSize(picSize.width, picSize.height);
        camera1.setParameters(parameters);
        //4. Call setDisplayOrientation(int) to ensure correct orientation of preview.
        //你可能会遇到画面方向和手机的方向不一致的问题，竖向手机的时候，但是画面是横的，这是由于摄像头默认捕获的画面横向的
        // 通过调用setDisplayOrientation来设置PreviewDisplay的方向，可以解决这个问题。
        setCameraDisplayOrientation(getActivity(), currentCameraType, camera1);

        //5. Important: Pass a fully initialized SurfaceHolder to setPreviewDisplay(SurfaceHolder).
        // Without a surface, the camera will be unable to start the preview.
        //camera必须绑定一个surfaceview才可以正常显示。
        try {
            camera1.setPreviewDisplay(displaySfv.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //6. Important: Call startPreview() to start updating the preview surface.
        // Preview must be started before you can take a picture.
        //在调用拍照之前必须调用startPreview()方法,但是在此时有可能surface还未创建成功。
        // 所以加上SurfaceHolder.Callback()，在回调再次初始化下。
        camera1.startPreview();
        setPreviewLight();
        //7. When you want, call
        // takePicture(Camera.ShutterCallback, Camera.PictureCallback, Camera.PictureCallback, Camera.PictureCallback)
        // to capture a photo. Wait for the callbacks to provide the actual image Data.
        //当如果想要拍照的时候，调用takePicture方法，这个下面我们会讲到。

        //8. After taking a picture, preview display will have stopped. To take more photos, call startPreview() again first.
        //在拍照结束后相机预览将会关闭，如果要再次拍照需要再次调用startPreview（)

        //9. Call stopPreview() to stop updating the preview surface.
        //通过调用stopPreview方法可以结束预览
        //10. Important: Call release() to release the camera for use by other applications.
        // Applications should release the camera immediately in onPause()(and re-open() it in onResume()).
        //建议在onResume调用open的方法，在onPause的时候执行release方法

        SurfaceHolder holder = displaySfv.getHolder();
        if (holder != null) {
            if(mCallBack != null){
                holder.removeCallback(mCallBack);
            }
            mCallBack = new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    Log.e("TAG", "surfaceCreated" + holder + this);

                    checkAndInitCamera();

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    Log.e("TAG", "surfaceChanged" + holder + this);
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    Log.e("TAG", "surfaceDestroyed" + holder + this);
                }
            };
            holder.addCallback(mCallBack);
        }
    }
    private SurfaceHolder.Callback mCallBack;

    /**
     * 获得最合是的宽高size
     */
    private Camera.Size getPictureSize(List<Camera.Size> picSizes, int width, int height) {
        Camera.Size betterSize = null;
        int diff = Integer.MAX_VALUE;
        if (picSizes != null && picSizes.size() > 0) {
            for (Camera.Size size : picSizes) {
                int newDiff = Math.abs(size.width - width) + Math.abs(size.height - height);
                if (newDiff == 0) {
                    return size;
                }
                if (newDiff < diff) {
                    betterSize = size;
                    diff = newDiff;
                }
            }
        }
        return betterSize;
    }

    private Camera openCamera(int type) {
        int cameraTypeIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
            Camera.getCameraInfo(cameraIndex, info);
            if (info.facing == type) {
                cameraTypeIndex = cameraIndex;
                break;
            }
        }
        if (cameraTypeIndex != -1) {
            return Camera.open(cameraTypeIndex);
        }
        return null;
    }
    //设置相机的方向
    public int setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                degrees = 0;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;   // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera1.setDisplayOrientation(result);
        return degrees;
    }


    //上次记录的时间戳
    long lastRecordTime = System.currentTimeMillis();

    //上次记录的索引
    int darkIndex = 0;
    //一个历史记录的数组，255是代表亮度最大值
    long[] darkList = new long[]{255, 255, 255, 255};
    //扫描间隔
    int waitScanTime = 0;

    //亮度低的阀值
    int darkValue = 60;
    private void setPreviewLight() {
        //不需要的时候直接清空
//        if(noNeed){
//            camera.setPreviewCallback(null);
//            return;
//        }
        camera1.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                long currentTime = System.currentTimeMillis();
                if (!openc) return ;
                if (currentTime - lastRecordTime < waitScanTime) {
                    return;
                }
                lastRecordTime = currentTime;

                int width = camera.getParameters().getPreviewSize().width;
                int height = camera.getParameters().getPreviewSize().height;
                //像素点的总亮度
                long pixelLightCount = 0L;
                //像素点的总数
                long pixeCount = width * height;
                //采集步长，因为没有必要每个像素点都采集，可以跨一段采集一个，减少计算负担，必须大于等于1。
                int step = 10;
                //Data.length - allCount * 1.5f的目的是判断图像格式是不是YUV420格式，只有是这种格式才相等
                //因为int整形与float浮点直接比较会出问题，所以这么比
                if (Math.abs(data.length - pixeCount * 1.5f) < 0.00001f) {
                    for (int i = 0; i < pixeCount; i += step) {
                        //如果直接加是不行的，因为data[i]记录的是色值并不是数值，byte的范围是+127到—128，
                        // 而亮度FFFFFF是11111111是-127，所以这里需要先转为无符号unsigned long参考Byte.toUnsignedLong()
                        pixelLightCount += ((long) data[i]) & 0xffL;
                    }
                    //平均亮度
                    long cameraLight = pixelLightCount / (pixeCount / step);
                    //更新历史记录
                    int lightSize = darkList.length;
                    darkList[darkIndex = darkIndex % lightSize] = cameraLight;
                    darkIndex++;
                    boolean isDarkEnv = true;
                    //判断在时间范围waitScanTime * lightSize内是不是亮度过暗
                    for (int i = 0; i < lightSize; i++) {
                        if (darkList[i] > darkValue) {
                            isDarkEnv = false;
                        }
                    }
                    if (!openc) {
                        openc=true;
                        start_timec=System.currentTimeMillis();
                    }
                    databack=databack+cameraLight+",";
//                    if (System.currentTimeMillis()-start_timec<=10000)
//                    {
//                        numc++;
//                    }
                    Log.e("TAG", "摄像头环境亮度为 ： " + cameraLight);
//                    if (!isFinishing()) {
//                        //亮度过暗就提醒
//
//                    }
                }
            }
        });
    }
}
