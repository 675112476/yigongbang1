package com.example.sl.yigongbang.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sl.yigongbang.R;
import com.example.sl.yigongbang.util.Manager.OkHttpClientManager;
import com.example.sl.yigongbang.util.entity.Ip;
import com.squareup.okhttp.Request;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Information extends AppCompatActivity {

    Button act_release;
    EditText act_name;
    EditText act_time;
    EditText act_location;
    EditText act_person;
    EditText act_descript;
    ImageView act_image;
    CircleImageView img_add;

    private File outputImage;
    SelectPicPopupWindow menuWindow;
    private Uri imageUri;
    public static final int TAKE_PHOTO=1;//字符串常量值为1
    public static final int CHOOSE_PHOTO=2;

    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    DateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, month);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //将页面TextView的显示更新为最新时间
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        act_release =(Button)findViewById(R.id.fabu);
        act_name=(EditText)findViewById(R.id.act_name);
        act_time=(EditText)findViewById(R.id.act_time);
        act_location=(EditText)findViewById(R.id.act_location);
        act_person=(EditText)findViewById(R.id.act_person);
        act_descript=(EditText)findViewById(R.id.act_descript);
        act_image = (ImageView) findViewById(R.id.act_image);
        img_add = (CircleImageView) findViewById(R.id.img_add);

        act_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendDataToServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuWindow = new SelectPicPopupWindow(Information.this, itemsOnClick);
                menuWindow.showAtLocation(Information.this.findViewById(R.id.mainLayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });

        act_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(Information.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //修改日历控件的年，月，日
                        //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
                        dateAndTime.set(Calendar.YEAR, year);
                        dateAndTime.set(Calendar.MONTH, month);
                        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {

                            //同DatePickerDialog控件
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dateAndTime.set(Calendar.MINUTE, minute);
                                act_time.setText(dateAndTimeFormat.format(dateAndTime.getTime()));
                            }
                        };
                        TimePickerDialog timePickerDialog = new TimePickerDialog(Information.this,t,Calendar.HOUR_OF_DAY,Calendar.MINUTE,true);
                        timePickerDialog.show();
                    }
                });
                datePickerDialog.show();
            }
        });
    }
    public void sendDataToServer() throws IOException {
//        Map<String,String> map=new HashMap<String,String>();
//        map.put("actName",act_name.getText().toString());
//        map.put("actTime",act_time.getText().toString());
//        map.put("actLocation",act_location.getText().toString());
//        map.put("actIntroduction",act_descript.getText().toString());
//        map.put("maxPeople",act_person.getText().toString());

        OkHttpClientManager.Param[] params = new OkHttpClientManager.Param[5];
        params[0] = new OkHttpClientManager.Param("actName",act_name.getText().toString());
        params[1] = new OkHttpClientManager.Param("actTime",act_time.getText().toString());
        params[2] = new OkHttpClientManager.Param("actLocation",act_location.getText().toString());
        params[3] = new OkHttpClientManager.Param("actIntroduction",act_descript.getText().toString());
        params[4] = new OkHttpClientManager.Param("maxPeople",act_person.getText().toString());

        OkHttpClientManager.postAsyn(Ip.getIp() + "Volunteer_ssh/activity_release", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(Information.this,"网络异常，请检查您的网络！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String string) {

                if(string .equals("success")){
                    Toast.makeText(Information.this,"发布活动成功",Toast.LENGTH_SHORT).show();
                }else
                    if(string.equals("failed")){
                        Toast.makeText(Information.this,"发布活动失败",Toast.LENGTH_SHORT).show();
                        Log.e("---Information_string",string);
                    }else{
                        Log.e("---Information_string",string);
                        Toast.makeText(Information.this,"未选择图片",Toast.LENGTH_SHORT).show();
                    }
            }
        },outputImage,"file",params);

    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    //创建File对象，用于存储拍照后的图片
                    outputImage=new File(getExternalCacheDir(),"output_image.jpg");//得到缓存目录 指定图片的内存控件 拍出来的图片赋予唯一标识符output_image.jpg
                    try {
                        if(outputImage.exists()){
                            outputImage.delete();//如果照片已经存在 则删除 为了多次拍摄
                        }
                        outputImage.createNewFile();//拍摄相片完成
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT>=24){
                        imageUri= FileProvider.getUriForFile(Information.this,"com.example.sl.cameraalbumtest.fileprovider",outputImage);
                        //高版本 将File对象封装成URI对象 通过内容提供器完成将File对象与相机程序完成数据共享 第一参数为context 第二参数为内容提供其的唯一标识符 第三参数则是要共享的File对象
                    }
                    else{
                        imageUri= Uri.fromFile(outputImage);//低版本直接化成Uri对象 上一个是安全性高的 这个安全性不高
                    }
                    Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");//使用intent启用相机程序 行为指定 响应相机的intent
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);//使用putExtra()方法传递我们的URI对象给相机程序
                    startActivityForResult(intent,TAKE_PHOTO);//请求码为字符串常量1 使用函数来控制拍摄效果
                    break;
                case R.id.btn_pick_photo:
                    if (ContextCompat.checkSelfPermission(Information.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                            PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(Information.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);//权限请求码 这是第一个权限 高版本申请权限
                    }else{//低版本直接调用此方法
                        openAlbum();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private  void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");//新建一个intent对象 action匹配启动相机的行为
        intent.setType("image/*");//必要参数
        startActivityForResult(intent,CHOOSE_PHOTO);//调用这个要返回数据的方法，其中CHOOSE_PHOTO是用在case中的请求码
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//运行时权限申请
        switch (requestCode){
            case 1://运行时权限的请求码值为1时
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this, "你拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)  {
        switch(requestCode){//请求码为选择项
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){//拍照成功的话
                    try{
                        //将拍摄的照片显示出来
                        //pa*******
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Log.e("拍照Width",Integer.toString(bitmap.getWidth()));
                        bitmap=cutImage(bitmap);
                        Log.e("拍照Width",Integer.toString(bitmap.getWidth()));
//                        try{
//
//                            File file=saveMyBitmap(bitmap);
//                            sendfile(file);
//                        }catch (java.io.IOException e){
//
//                        }

                        outputImage=saveMyBitmap(bitmap);

                        act_image.setImageBitmap(bitmap);
                        //将URI对象变成最基本的Bitmap对象 指定分辨率
//                        Intent intent2=new Intent(Setting.this,HomeFragment.class);
//                        intent2.putExtra("bitmap",bitmap);
//                        startActivity(intent2);
                        //逻辑控制控件显示,由于控件XML在别的活动页面 所以要传递数据
                    }catch(FileNotFoundException e){
                        e.printStackTrace();//如果没有文件或者磁盘控件不够 抛出异常信息
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{//4.4以下版本使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    public File saveMyBitmap(Bitmap mBitmap){
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = null;
        try {
            file = File.createTempFile(
                    "123",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            FileOutputStream out=new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  file;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void handleImageOnKitKat(Intent data){//全部解析成路径
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri，则通过document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];//解析出数字格式的id
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath=getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath=uri.getPath();
        }
        displayImage(imagePath);//调用方法 根据路径显示图片的方法
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage (String imagePath){
        if(imagePath!=null){
            //****
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            Log.e("Width",Integer.toString(bitmap.getWidth()));
            bitmap=cutImage(bitmap);
            Log.e("Width",Integer.toString(bitmap.getWidth()));
//            try
//            {
//                Log.e("---displayImage",imagePath);
//                sendfile(compressImage(bitmap,imagePath,null));
//            }catch (java.io.IOException e){
//            }

            outputImage = saveMyBitmap(bitmap);

            act_image.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this, "未能找到图片", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(Information.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return  true;
    }

    public Bitmap cutImage(Bitmap bitmap){
        while(bitmap.getWidth()>500||bitmap.getHeight()>500){
            Matrix matrix = new Matrix();
            matrix.setScale(0.5f, 0.5f);
            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }
        return bitmap;
    }
    public static File compressImage(Bitmap bm,String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
}
