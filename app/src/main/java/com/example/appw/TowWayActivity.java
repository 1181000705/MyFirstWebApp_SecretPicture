package com.example.appw;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appw.nestia.biometriclib.BiometricPromptManager;
import com.example.appw.util.CameraUtils;


public class TowWayActivity extends AppCompatActivity {
    private static final long PREVIEW_SIZE_MIN = 720 * 480;
    boolean opens;
    long start_times;
    boolean openc;

    long start_timec;
    int nums;
    int numc;
    private int Hz = 50;
    private final String TAG = this.getClass().getSimpleName();
    private static final int ALL_PERMISSION = 100;
    @BindView(R.id.preview_texture)
    TextureView previewTexture;
    BiometricPromptManager fManager;
    private String mCameraId;
    private Size mPreviewSize;
    private HandlerThread mCameraThread;
    private Handler mCameraHandler;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CaptureRequest mCaptureRequest;
    private CameraCaptureSession mCameraCaptureSession;
    CameraManager cameraManager;
    CameraManager c2ameraManager;
    CameraCharacteristics cameraCharacteristics;
    /**
     * 前后置摄像头
     */
    public static final String CAMERA_FRONT = "1";
    public static final String CAMERA_BACK = "0";

    AppCompatImageButton trytowway;
    AppCompatImageButton trytowwaygo;

    private int currentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;

    //需要申请的权限
    private String[] permissions = {Manifest.permission.CAMERA};
    private static final int CAMERA_PERMISSION_CODE = 1;
    private List<Size> outputSizes = null;

    //    Button button0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tow_way);
        CameraUtils.init(this);
        initCamera();
        openc = false;
        opens = false;
        nums = 0;
        numc = 0;
        trytowway = findViewById(R.id.button222);
        trytowwaygo = findViewById(R.id.button223);
//        button0=findViewById(R.id.button0);
        ButterKnife.bind(this);
        //获取相机权限
        methodRequiresPermisson();
//        button0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String Data="10101010";
////                for (int i=0;i<=20;i++)
////                    Data=Data+"10";
////                flash(Data);
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                flash(Data);
//                w=0;
//                try {
//                    if (cameraManager.getCameraIdList()[0].equals("0")) w=1;
//                    if (w==1)
//                    {
//
////                        mCameraCaptureSession.stopRepeating();
//
//                        if (mCameraDevice != null) {
//                            mCameraDevice.close();
//                        }
//                        if (mCameraCaptureSession != null) {
//                            mCameraCaptureSession = null;
//                        }
//                        mCameraThread.quit();
////                        if (mCameraCaptureSession != null) {
////                            mCameraCaptureSession = null;
////                        }
////                                setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
//                    }
//                    //Log.e("msg","okkkkkk");
//                } catch (CameraAccessException e) {
//                    e.printStackTrace();
//                }
//                Thread ft=new Thread(new threadflash0());
//                ft.start();
//                if (w==1)
//                {
////                    onDestroy();
////                    finish();
////                    startCameraThread();
////                    if (!previewTexture.isAvailable()) {
////                    previewTexture.setSurfaceTextureListener(textureListener);
////                    } else {
////                        startPreview();
////                    }
//                }
////                camera.stopPreview();
////                camera.release();
////                camera=null;
//
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                    mCameraManager =   (CameraManager) getSystemService(Context.CAMERA_SERVICE);
////                    try {
////                        id = mCameraManager.getCameraIdList()[0];
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////
////
////                            mCameraManager.setTorchMode(id,true);//打开闪光灯
////                            Thread.sleep(1000);
////                            mCameraManager.setTorchMode(id,false);//打开闪光灯
////                        }
////                    } catch (CameraAccessException e) {
////                        e.printStackTrace();
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////                checkAndInitCamera();
//
//            }
//        });
        trytowway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                w = 0;
                try {
                    if (cameraManager.getCameraIdList()[0].equals("0")) w = 1;
                    if (w == 1) {
//                        mCameraCaptureSession.stopRepeating();
                        if (mCameraDevice != null) {
                            mCameraDevice.close();
                        }
                        if (mCameraCaptureSession != null) {
                            mCameraCaptureSession = null;
                        }
                        mCameraThread.quit();
                    }
                    //Log.e("msg","okkkkkk");
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                Thread ft = new Thread(new threadflash0());
                ft.start();
                if (w == 1) {
                }
            }
        });

        trytowwaygo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                w = 0;
                try {
                    if (cameraManager.getCameraIdList()[0].equals("0")) w = 1;
                    if (w == 1) {
//                        mCameraCaptureSession.stopRepeating();
                        if (mCameraDevice != null) {
                            mCameraDevice.close();
                        }
                        if (mCameraCaptureSession != null) {
                            mCameraCaptureSession = null;
                        }
                        mCameraThread.quit();
                    }
                    //Log.e("msg","okkkkkk");
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                Thread ft = new Thread(new threadflash());
                ft.start();
                if (w == 1) {
                }
            }
        });
//        takePicBtn = findViewById(R.id.btn_take_picture_demo_camera);
//        picFl = findViewById(R.id.fl_picture_demo_camera);
//        picIV = findViewById(R.id.iv_picture_demo_camera);

//        takePicBtn.setOnClickListener(this);
//        picFl.setOnClickListener(this);
        startCameraThread();
        if (!previewTexture.isAvailable()) {
            previewTexture.setSurfaceTextureListener(textureListener);
        } else {
            startPreview();
        }
    }

    int w = 0;
    private void sendRecord(final String user, final String obj) {
        String url = "http://129.28.162.46:8000/mine/ask/";
        String tag = "Record";    //注②
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String time=formatter.format(date);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);
        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // try {
                        //JSONObject jsonObject = (JSONObject) new JSONObject(response);  //注③
                        //Log.e("+++++++++++TAG+++++++",jsonObject.getString("data1") );
                        // Log.e("+++++++++++TAG+++++++",jsonObject.getString("data2") );
//                            Log.e("+++++++++++TAG+++++++",jsonObject.getString("Time") );
//                            Log.e("+++++++++++TAG+++++++",jsonObject.getString("Obj") );
                        Log.e("+++++++++++TAG+++++++",response);
                        Toast.makeText(getApplicationContext(),"成功收到",Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            //做自己的请求异常操作，如Toast提示（“无网络连接”等）
//                            Toast.makeText(getApplicationContext(),"网络异常",Toast.LENGTH_SHORT).show();
//                            Log.e("TAG", e.getMessage(), e);
//                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wrroy",error.toString() );
                Toast.makeText(getApplicationContext(),"网络异常失败",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.e("wrroy","sendto" );
                Log.e("+++++++++++TAG+++++++",user );
                Log.e("+++++++++++TAG+++++++",time );
                Log.e("+++++++++++TAG+++++++",obj );
                Map<String, String> params = new HashMap<>();
//                params.put("format", "json");
                params.put("UserID", user);  //注⑥
                params.put("Time", time);
                params.put("ObjID", obj);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy(20*1000, 0, 1.0f));
        //将请求添加到队列中
        requestQueue.add(request);
    }

    public void open() {
        //CONTROL_AE_MODE_ON_ALWAYS_FLASH
//    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);


//    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
//    mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
//    mCaptureRequest = mCaptureRequestBuilder.build();
//    try {
//        mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
//    } catch (CameraAccessException e) {
//        e.printStackTrace();
//    }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
//    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);

//    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
//    mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF);
//    mCaptureRequest = mCaptureRequestBuilder.build();
//    try {
//        mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
//    } catch (CameraAccessException e) {
//        e.printStackTrace();
//    }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    class threadtime implements Runnable {
        @Override
        public void run() {

        }

    }

    class threadflash implements Runnable {
        @Override
        public void run() {
//            c2ameraManager= (CameraManager)getSystemService(AppCompatActivity.CAMERA_SERVICE);
            String data1 = "10101010";
            for (int i = 0; i < 17; i++) {
                data1 = data1 + (((int) (10 * Math.random())) % 2);
            }

            flash(data1);
            close();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String data;
            if (Data.HandwareId.contains("1")) data = "10101010";
            else data = "10101010";

            for (int i = 0; i < 50; i++) {
                data = data + (((int) (10 * Math.random())) % 2);
            }
            flash(data);
            close();

            sendRecord(Data.Userid,"1");

//            Thread thread = new Thread(){
//                @Override
//                public void run() {
//                    //Toast.makeText(MainActivity.this, "gogogo", Toast.LENGTH_SHORT).show();
//
//                }
//            };
//            thread.start();
            finish();
        }

    }

    class threadflash0 implements Runnable {
        @Override
        public void run() {
//            c2ameraManager= (CameraManager)getSystemService(AppCompatActivity.CAMERA_SERVICE);
            String data1 = "10101010";
            for (int i = 0; i < 17; i++) {
                data1 = data1 + (((int) (10 * Math.random())) % 2);
            }

            flash(data1);
            close();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String data;
            if (Data.HandwareId.contains("1")) data = "10101010";
            else data = "10101010";

            for (int i = 0; i < 50; i++) {
                data = data + (((int) (10 * Math.random())) % 2);
            }
            flash(data);
            close();
            finish();
        }

    }

    public void flash(String data) {
        Thread time;
        threadtime mythread = new threadtime();
        time = new Thread(mythread);
        int length = data.length();
        long t = (long) (1000 * ((double) 1 / (double) Hz));
        Log.e("msg", "nonono");
        Log.e("msg", "now" + Build.VERSION.SDK_INT);
        Log.e("msg", "zhike" + Build.VERSION_CODES.LOLLIPOP);

        int p = 0;
        long now = System.currentTimeMillis();
        for (Character c : data.toCharArray()) {
            if (c == '1') {
                if (p == 0) {
                    p = 1;
                    open();
                }

                long now2 = System.currentTimeMillis();
                if (now2 - now < t) {
                    try {
                        time.sleep(t - (now2 - now));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                now = System.currentTimeMillis();

            } else {
                if (p == 1) {
                    p = 0;
                    close();
                }
                long now2 = System.currentTimeMillis();
                if (now2 - now < t) {
                    try {
                        time.sleep(t - (now2 - now));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                now = System.currentTimeMillis();
            }
        }
        close();
        Log.e("msg", "yesyesyes");

    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        startCameraThread();
//        if (!previewTexture.isAvailable()) {
//            previewTexture.setSurfaceTextureListener(textureListener);
//        } else {
//            startPreview();
//        }
//    }

    @Override
    protected void onDestroy() {

        if (mCameraDevice != null) {
            mCameraDevice.close();
        }
        if (mCameraCaptureSession != null) {
            mCameraCaptureSession = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(ALL_PERMISSION)
    private void methodRequiresPermisson() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "请授权允许打开相机权限",
                    ALL_PERMISSION, perms);
        }
    }

    private void initCamera() {
        cameraManager = CameraUtils.getInstance().getCameraManager();
        mCameraId = CameraUtils.getInstance().getCameraId(false);//默认使用后置相机
        outputSizes = CameraUtils.getInstance().getCameraOutputSizes(mCameraId, SurfaceTexture.class);
        //初始化预览尺寸
    }


    private void startCameraThread() {
        mCameraThread = new HandlerThread("CameraTextureViewThread");
        mCameraThread.start();
        mCameraHandler = new Handler(mCameraThread.getLooper());
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            setupCamera(width, height);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
            // transformImage(width, height);
            updateCameraPreviewWithVideoMode();
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

//    @OnClick(R.id.convert_img)
//    public void onViewClicked() {
//        switchCamera();
//    }

    private void switchCamera() {
        if (mCameraId.equals(CAMERA_FRONT)) {
            mCameraId = CAMERA_BACK;
            mCameraDevice.close();
            reopenCamera();
        } else if (mCameraId.equals(CAMERA_BACK)) {
            mCameraId = CAMERA_FRONT;
            mCameraDevice.close();
            reopenCamera();
        }
    }

    private void reopenCamera() {
        if (!previewTexture.isAvailable()) {
            openCamera();
        } else {
            previewTexture.setSurfaceTextureListener(textureListener);
        }
    }


    private void setupCamera(int width, int height) {
        Size[] sizes = new Size[outputSizes.size()];
        outputSizes.toArray(sizes);
        mPreviewSize = getOptimalSize(sizes, width, height);
    }


    private Size getOptimalSize(Size[] sizeMap, int width, int height) {
        List<Size> sizeList = new ArrayList<>();
        for (Size option : sizeMap) {
            if (width > height) {
                if (option.getWidth() > width && option.getHeight() > height) {
                    sizeList.add(option);
                }
            } else {
                if (option.getWidth() > height && option.getHeight() > width) {
                    sizeList.add(option);
                }
            }
        }
        if (sizeList.size() > 0) {
            return Collections.min(sizeList, (lhs, rhs) -> Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getHeight() * rhs.getWidth()));
        }
        return sizeMap[0];
    }


    private void openCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            cameraManager.openCamera(mCameraId, mStateCallback, mCameraHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
//            if (mCameraDevice != null) {
//                mCameraDevice.close();
//                cameraDevice.close();
//                mCameraDevice = null;
//            }
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
//            if (mCameraDevice != null) {
//                mCameraDevice.close();
//                cameraDevice.close();
//                mCameraDevice = null;
//            }
        }
    };

    private void startPreview() {
        SurfaceTexture mSurfaceTexture = previewTexture.getSurfaceTexture();
        mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        Surface previewSurface = new Surface(mSurfaceTexture);

        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(previewSurface);
            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    mCaptureRequest = mCaptureRequestBuilder.build();
                    mCameraCaptureSession = cameraCaptureSession;

                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                }
            }, mCameraHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转角度
     */
    private void transformImage(int viewWidth, int viewHeight) {
        if (null == previewTexture || null == mPreviewSize) {
            return;
        }
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        previewTexture.setTransform(matrix);
        //}
    }

    private void updateCameraPreviewWithVideoMode() {
        List<Size> outputSizes = CameraUtils.getInstance().getCameraOutputSizes(mCameraId, SurfaceTexture.class);
        List<Size> sizes = new ArrayList<>();
        //计算预览窗口高宽比，高宽比，高宽比
        float ratio = ((float) previewTexture.getHeight() / previewTexture.getWidth());
        //首先选取宽高比与预览窗口高宽比一致且最大的输出尺寸
        for (int i = 0; i < outputSizes.size(); i++) {
            if (((float) outputSizes.get(i).getWidth()) / outputSizes.get(i).getHeight() == ratio) {
                sizes.add(outputSizes.get(i));
            }
        }
        if (sizes.size() > 0) {
            mPreviewSize = Collections.max(sizes, new CameraUtils.CompareSizesByArea());
            //previewTexture.setAspectRation(previewSize.getHeight(), previewSize.getWidth());
            //createPreviewSession();
            return;
        }
        //如果不存在宽高比与预览窗口高宽比一致的输出尺寸，则选择与其高宽比最接近的输出尺寸
        sizes.clear();
        float detRatioMin = Float.MAX_VALUE;
        for (int i = 0; i < outputSizes.size(); i++) {
            Size size = outputSizes.get(i);
            float curRatio = ((float) size.getWidth()) / size.getHeight();
            if (Math.abs(curRatio - ratio) < detRatioMin) {
                detRatioMin = curRatio;
                mPreviewSize = size;
            }
        }
        if (previewTexture.getWidth() * mPreviewSize.getHeight() > PREVIEW_SIZE_MIN) {
            //previewTexture.setAspectRation(mPreviewSize.getHeight(), mPreviewSize.getWidth());
            //createPreviewSession();
        }
        //如果宽高比最接近的输出尺寸太小，则选择与预览窗口面积最接近的输出尺寸
        long area = previewTexture.getWidth() * previewTexture.getHeight();
        long detAreaMin = Long.MAX_VALUE;
        for (int i = 0; i < outputSizes.size(); i++) {
            Size size = outputSizes.get(i);
            long curArea = size.getWidth() * size.getHeight();
            if (Math.abs(curArea - area) < detAreaMin) {
                detAreaMin = curArea;
                mPreviewSize = size;
            }
        }
    }

}

//++++++++++++++++++++++++++++++++
//package com.example.myfirstwebapp;
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.ImageFormat;
//import android.graphics.Matrix;
//import android.graphics.RectF;
//import android.graphics.SurfaceTexture;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCaptureSession;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.CameraMetadata;
//import android.hardware.camera2.CaptureRequest;
//import android.hardware.camera2.params.StreamConfigurationMap;
//import android.media.MediaRecorder;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.util.Size;
//import android.view.Surface;
//import android.view.TextureView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;
//import android.hardware.Camera;
//import android.hardware.SensorManager;
//import android.os.Build;
//import android.util.Log;
//import android.view.View;
//import androidx.appcompat.widget.AppCompatImageButton;
//
//import com.example.myfirstwebapp.Login.LoginActivity;
//import com.example.myfirstwebapp.nestia.biometriclib.BiometricPromptManager;
//
//
//public class TowWayActivity extends AppCompatActivity {
//    boolean opens;long start_times;
//    boolean openc;
//
//    long start_timec;
//    int nums;
//    int numc;
//    private int Hz=50;
//    private final String TAG = this.getClass().getSimpleName();
//    private static final int ALL_PERMISSION = 100;
//    @BindView(R.id.preview_texture)
//    TextureView previewTexture;
////    @BindView(R.id.convert_img)
////    ImageView convertImg;
//BiometricPromptManager fManager;
//    private String mCameraId;
//    private Size mPreviewSize;
//    private HandlerThread mCameraThread;
//    private Handler mCameraHandler;
//    private CameraDevice mCameraDevice;
//    private CaptureRequest.Builder mCaptureRequestBuilder;
//    private CaptureRequest mCaptureRequest;
//    private CameraCaptureSession mCameraCaptureSession;
//    CameraManager cameraManager;
//    CameraManager c2ameraManager;
//    CameraCharacteristics cameraCharacteristics;
//    /**
//     * 前后置摄像头
//     */
//    public static final String CAMERA_FRONT = "1";
//    public static final String CAMERA_BACK = "0";
//
//    AppCompatImageButton trytowway;
//
//
//    private int currentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
//
//    //需要申请的权限
//    private String[] permissions = {Manifest.permission.CAMERA};
//    private static final int CAMERA_PERMISSION_CODE = 1;
////    Button button0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tow_way);
//        openc=false;
//        opens=false;
//        nums=0;
//        numc=0;
//        trytowway=findViewById(R.id.button222);
////        button0=findViewById(R.id.button0);
//        ButterKnife.bind(this);
//        //获取相机权限
//        methodRequiresPermisson();
////        button0.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//////                String Data="10101010";
//////                for (int i=0;i<=20;i++)
//////                    Data=Data+"10";
//////                flash(Data);
//////                try {
//////                    Thread.sleep(1000);
//////                } catch (InterruptedException e) {
//////                    e.printStackTrace();
//////                }
//////                flash(Data);
////                w=0;
////                try {
////                    if (cameraManager.getCameraIdList()[0].equals("0")) w=1;
////                    if (w==1)
////                    {
////
//////                        mCameraCaptureSession.stopRepeating();
////
////                        if (mCameraDevice != null) {
////                            mCameraDevice.close();
////                        }
////                        if (mCameraCaptureSession != null) {
////                            mCameraCaptureSession = null;
////                        }
////                        mCameraThread.quit();
//////                        if (mCameraCaptureSession != null) {
//////                            mCameraCaptureSession = null;
//////                        }
//////                                setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
////                    }
////                    //Log.e("msg","okkkkkk");
////                } catch (CameraAccessException e) {
////                    e.printStackTrace();
////                }
////                Thread ft=new Thread(new threadflash0());
////                ft.start();
////                if (w==1)
////                {
//////                    onDestroy();
//////                    finish();
//////                    startCameraThread();
//////                    if (!previewTexture.isAvailable()) {
//////                    previewTexture.setSurfaceTextureListener(textureListener);
//////                    } else {
//////                        startPreview();
//////                    }
////                }
//////                camera.stopPreview();
//////                camera.release();
//////                camera=null;
////
//////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//////                    mCameraManager =   (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//////                    try {
//////                        id = mCameraManager.getCameraIdList()[0];
//////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//////
//////
//////                            mCameraManager.setTorchMode(id,true);//打开闪光灯
//////                            Thread.sleep(1000);
//////                            mCameraManager.setTorchMode(id,false);//打开闪光灯
//////                        }
//////                    } catch (CameraAccessException e) {
//////                        e.printStackTrace();
//////                    } catch (InterruptedException e) {
//////                        e.printStackTrace();
//////                    }
//////                }
//////                checkAndInitCamera();
////
////            }
////        });
//        trytowway.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String Data="10101010";
////                for (int i=0;i<=20;i++)
////                    Data=Data+"10";
////                flash(Data);
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                flash(Data);
//                w=0;
//                try {
//                    if (cameraManager.getCameraIdList()[0].equals("0")) w=1;
//                    if (w==1)
//                    {
////                        mCameraCaptureSession.stopRepeating();
//                        if (mCameraDevice != null) {
//                            mCameraDevice.close();
//                        }
//                        if (mCameraCaptureSession != null) {
//                            mCameraCaptureSession = null;
//                        }
//                        mCameraThread.quit();
////                        if (mCameraCaptureSession != null) {
////                            mCameraCaptureSession = null;
////                        }
////                                setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
//                    }
//                    //Log.e("msg","okkkkkk");
//                } catch (CameraAccessException e) {
//                    e.printStackTrace();
//                }
//                Thread ft=new Thread(new threadflash());
//                ft.start();
//                if (w==1)
//                {
////                    onDestroy();
////                    finish();
////                    startCameraThread();
////                    if (!previewTexture.isAvailable()) {
////                    previewTexture.setSurfaceTextureListener(textureListener);
////                    } else {
////                        startPreview();
////                    }
//                }
////                camera.stopPreview();
////                camera.release();
////                camera=null;
//
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                    mCameraManager =   (CameraManager) getSystemService(Context.CAMERA_SERVICE);
////                    try {
////                        id = mCameraManager.getCameraIdList()[0];
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////
////
////                            mCameraManager.setTorchMode(id,true);//打开闪光灯
////                            Thread.sleep(1000);
////                            mCameraManager.setTorchMode(id,false);//打开闪光灯
////                        }
////                    } catch (CameraAccessException e) {
////                        e.printStackTrace();
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////                checkAndInitCamera();
//            }
//        });
////        takePicBtn = findViewById(R.id.btn_take_picture_demo_camera);
////        picFl = findViewById(R.id.fl_picture_demo_camera);
////        picIV = findViewById(R.id.iv_picture_demo_camera);
//
////        takePicBtn.setOnClickListener(this);
////        picFl.setOnClickListener(this);
//        startCameraThread();
//        if (!previewTexture.isAvailable()) {
//            previewTexture.setSurfaceTextureListener(textureListener);
//        } else {
//            startPreview();
//        }
//
//    }
//    int w=0;
//public void open()
//{
//    //CONTROL_AE_MODE_ON_ALWAYS_FLASH
////    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
//
//
////    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
////    mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
////    mCaptureRequest = mCaptureRequestBuilder.build();
////    try {
////        mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
////    } catch (CameraAccessException e) {
////        e.printStackTrace();
////    }
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        try {
//            cameraManager.setTorchMode(cameraManager.getCameraIdList()[0],true);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//public void close()
//{
////    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
//
////    mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
////    mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_OFF);
////    mCaptureRequest = mCaptureRequestBuilder.build();
////    try {
////        mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
////    } catch (CameraAccessException e) {
////        e.printStackTrace();
////    }
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        try {
//            cameraManager.setTorchMode(cameraManager.getCameraIdList()[0],false);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//    class threadtime  implements Runnable {
//        @Override
//        public void run() {
//
//        }
//
//    }
//    class threadflash  implements Runnable {
//        @Override
//        public void run() {
////            c2ameraManager= (CameraManager)getSystemService(AppCompatActivity.CAMERA_SERVICE);
//            String data1="1111";
//            for (int i=0;i<21;i++)
//            {
//                data1=data1+(((int)(10 * Math.random())) % 2);
//            }
//
//            flash(data1);
//            close();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String data;
//            if (Data.HandwareId.contains("1")) data="101";
//            else data="100";
//
//            for (int i=0;i<47;i++)
//            {
//                data=data+(((int)(10 * Math.random())) % 2);
//            }
//            flash(data);
//            close();
//            finish();
//        }
//
//    }
//    class threadflash0  implements Runnable {
//        @Override
//        public void run() {
////            c2ameraManager= (CameraManager)getSystemService(AppCompatActivity.CAMERA_SERVICE);
//            String data1="1111";
//            for (int i=0;i<21;i++)
//            {
//                data1=data1+(((int)(10 * Math.random())) % 2);
//            }
//
//            flash(data1);
//            close();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            String data="100";
//            for (int i=0;i<47;i++)
//            {
//                data=data+(((int)(10 * Math.random())) % 2);
//            }
//            flash(data);
//            close();
//            finish();
//        }
//
//    }
//public void flash(String data)
//{
//    Thread time;
//      threadtime mythread= new threadtime();
//    time=new Thread(mythread);
//    int length=data.length();
//    long t=(long )(1000* ((double)1 / (double)Hz ) );
//    Log.e("msg","nonono");
//    Log.e("msg","now"+Build.VERSION.SDK_INT);
//    Log.e("msg","zhike"+Build.VERSION_CODES.LOLLIPOP);
//
//    int p=0;
//    long now=System.currentTimeMillis();
//    for (Character c:data.toCharArray()){
//        if (c=='1')
//        {
//            if (p==0){
//                p=1;
//                open();
//            }
//
//            long now2=System.currentTimeMillis();
//            if (now2-now<t)
//            {
//                try {
//                    time.sleep(t-(now2-now));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            now=System.currentTimeMillis();
//
//        }
//        else
//        {
//            if (p==1) {
//                p=0;
//                close();
//            }
//            long now2=System.currentTimeMillis();
//            if (now2-now<t)
//            {
//                try {
//                    time.sleep(t-(now2-now));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            now=System.currentTimeMillis();
//        }
//    }
//    close();
//    Log.e("msg","yesyesyes");
//
//}
////    @Override
////    protected void onResume() {
////        super.onResume();
////        startCameraThread();
////        if (!previewTexture.isAvailable()) {
////            previewTexture.setSurfaceTextureListener(textureListener);
////        } else {
////            startPreview();
////        }
////    }
//
//    @Override
//    protected void onDestroy() {
//
//        if (mCameraDevice != null) {
//            mCameraDevice.close();
//        }
//        if (mCameraCaptureSession != null) {
//            mCameraCaptureSession = null;
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//
//    @AfterPermissionGranted(ALL_PERMISSION)
//    private void methodRequiresPermisson() {
//        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        if (EasyPermissions.hasPermissions(this,perms)) {
//            Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
//        } else {
//            EasyPermissions.requestPermissions(this, "请授权允许打开相机权限",
//                    ALL_PERMISSION, perms);
//        }
//    }
//
//
//    private void startCameraThread() {
//        mCameraThread = new HandlerThread("CameraTextureViewThread");
//        mCameraThread.start();
//        mCameraHandler = new Handler(mCameraThread.getLooper());
//    }
//
//    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
//        @Override
//        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
//            setupCamera(width, height);
//            transformImage(width, height);
//
//            openCamera();
//        }
//
//        @Override
//        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
//
//        }
//
//        @Override
//        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
//            return false;
//        }
//
//        @Override
//        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
//
//        }
//    };
//
////    @OnClick(R.id.convert_img)
////    public void onViewClicked() {
////        switchCamera();
////    }
//
//    private void switchCamera() {
//        if (mCameraId.equals(CAMERA_FRONT)) {
//            mCameraId = CAMERA_BACK;
//            mCameraDevice.close();
//            reopenCamera();
//        } else if (mCameraId.equals(CAMERA_BACK)) {
//            mCameraId = CAMERA_FRONT;
//            mCameraDevice.close();
//            reopenCamera();
//        }
//    }
//
//    private void reopenCamera() {
//        if (!previewTexture.isAvailable()) {
//            openCamera();
//        } else {
//            previewTexture.setSurfaceTextureListener(textureListener);
//        }
//    }
//
//
//    private void setupCamera(int width, int height) {
//        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            for (String cameraId : cameraManager.getCameraIdList()) {
//                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
//                //默认打开hou摄像机
//                Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
//
//                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT)
//                    continue;
//
//                //获取图片输出的尺寸
//                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
////                Integer map = cameraCharacteristics.get(CameraCharacteristics.SCALER_CROPPING_TYPE);
//
//                assert map != null;
//
//                //摄像头支持的预览Size数组
//                mPreviewSize = getOptimalSize(map.getOutputSizes(SurfaceTexture.class), width, height);
////                mPreviewSize = getOptimalSize(map.getOutputSizes(SurfaceTexture.class),   18, 36);
////                mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
////                mPreviewSize =map.getOutputSizes(MediaRecorder.class)[0];
////                mPreviewSize = Collections.max(
//
//
////                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
////                        new CompareSizesByArea());
//                mCameraId = cameraId;
//            }
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private Size getOptimalSize(Size[] sizeMap, int width, int height) {
//        List<Size> sizeList = new ArrayList<>();
//        for (Size option : sizeMap) {
//            if (width > height) {
//                if (option.getWidth() > width && option.getHeight() > height) {
//                    sizeList.add(option);
//                }
//            } else {
//                if (option.getWidth() > height && option.getHeight() > width) {
//                    sizeList.add(option);
//                }
//            }
//        }
//        if (sizeList.size() > 0) {
//            return Collections.min(sizeList, (lhs, rhs) -> Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getHeight() * rhs.getWidth()));
//        }
//        return sizeMap[0];
//    }
//
//
//    private void openCamera() {
//        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        try {
//            cameraManager.openCamera(mCameraId, mStateCallback, mCameraHandler);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
//        @Override
//        public void onOpened(@NonNull CameraDevice cameraDevice) {
//            mCameraDevice = cameraDevice;
//            startPreview();
//        }
//
//        @Override
//        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
////            if (mCameraDevice != null) {
////                mCameraDevice.close();
////                cameraDevice.close();
////                mCameraDevice = null;
////            }
//        }
//
//        @Override
//        public void onError(@NonNull CameraDevice cameraDevice, int i) {
////            if (mCameraDevice != null) {
////                mCameraDevice.close();
////                cameraDevice.close();
////                mCameraDevice = null;
////            }
//        }
//    };
//
//    private void startPreview () {
//        SurfaceTexture mSurfaceTexture = previewTexture.getSurfaceTexture();
//        mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
//        Surface previewSurface = new Surface(mSurfaceTexture);
//
//        try {
//            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//            mCaptureRequestBuilder.addTarget(previewSurface);
//            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface), new CameraCaptureSession.StateCallback() {
//                @Override
//                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
//                    mCaptureRequest = mCaptureRequestBuilder.build();
//                    mCameraCaptureSession = cameraCaptureSession;
//
//                    try {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                            mCameraCaptureSession.setRepeatingRequest(mCaptureRequest, null, mCameraHandler);
//                    } catch (CameraAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
//                }
//            }, mCameraHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 旋转角度
//     *
//     * @param width
//     * @param height
//     */
//    private void transformImage ( int width, int height){
//        if (mPreviewSize == null || previewTexture == null) {
//            return;
//        }
//        Matrix matrix = new Matrix();
//        int rotation = getWindowManager().getDefaultDisplay().getRotation();
//        RectF textureRectF = new RectF(0, 0, width, height);
//        RectF previewRectF = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
//        float centerX = textureRectF.centerX();
//        float centery = textureRectF.centerY();
//
//        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270) {
//        } else if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
//            previewRectF.offset(centerX - previewRectF.centerX(), centery - previewRectF.centerY());
//            matrix.setRectToRect(textureRectF, previewRectF, Matrix.ScaleToFit.FILL);
//            float scale = Math.max((float) width / mPreviewSize.getWidth(), (float) height / mPreviewSize.getHeight());
//
//            matrix.postScale(scale, scale, centerX, centery);
//            matrix.postRotate(90 * (rotation - 2), centerX, centery);
//            previewTexture.setTransform(matrix);
//        }
//    }
//
//}
