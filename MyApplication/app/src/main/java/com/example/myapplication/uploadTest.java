package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.connect.Connect;
import com.example.myapplication.connect.HttpUtil;
import com.example.myapplication.connect.ProgressListener;
import com.example.myapplication.connect.UriTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class uploadTest extends AppCompatActivity {
    public static final String TAG = uploadTest.class.getName();
    public  final static int VEDIO_KU = 101;
    private String path = "";//文件路径
    private ProgressBar post_progress;
    private TextView post_text;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(uploadTest.this,"发布中",Toast.LENGTH_LONG).show();
            //finish();

        }
    };
    private static String getLastName(String name){
        String[] tmp = name.split("/");
        return tmp[tmp.length-1];
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_upload_test);
        getSupportActionBar().setTitle("视频上传");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final EditText video_name = (EditText)findViewById(R.id.upload_video_name);
        post_progress = (ProgressBar) findViewById(R.id.post_progress);
        post_text = (TextView) findViewById(R.id.post_text);
        findViewById(R.id.video_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleteVedio();
                video_name.setText(path);
            }
        });
        findViewById(R.id.video_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(uploadTest.this, path, Toast.LENGTH_LONG).show();
                if(path.equals(""))
                    Toast.makeText(uploadTest.this, "请选择视频后，再点击上传！", Toast.LENGTH_LONG).show();
                else {
                    File file = new File( path);
                    String postUrl = "http://39.105.21.114:12306/file/video/upload";

                    HttpUtil.postFile(postUrl, new ProgressListener() {
                        @Override
                        public void onProgress(long currentBytes, long contentLength, boolean done) {
                            Log.i(TAG, "currentBytes==" + currentBytes + "==contentLength==" + contentLength + "==done==" + done);
                            int progress = (int) (currentBytes * 100 / contentLength);
                            post_progress.setProgress(progress);
                            post_text.setText(progress + "%");
                        }
                    }, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response != null) {
                                String result = response.body().string();
                                Log.i(TAG, "result===" + result);
                            }
                        }
                    }, file);

                }
                String textA = ((EditText)findViewById (R.id.editText2)).getText().toString();
                String textB = ((EditText)findViewById (R.id.editText14)).getText().toString();
                try {
                    JSONObject object= new JSONObject();
                    object.put("courseTitle",textA);
                    object.put("title",textB);
                    object.put("file",getLastName(path));
                    Connect conn = new Connect();
                    String result = null;
                    result = conn.sendReq3(handler,1,"api/v1/video/add","POST",String.valueOf(object),MainActivity.TOKEN);
                    System.out.println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void seleteVedio() {
        // TODO 启动相册
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,uploadTest.VEDIO_KU);
    }
    /**
     * 选择回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // TODO 视频
            case uploadTest.VEDIO_KU:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        uri = geturi(this, data);
                        File file = null;
                        if (uri.toString().indexOf("file") == 0) {
                            file = new File(new URI(uri.toString()));
                            path = file.getPath();
                            ((EditText)findViewById(R.id.upload_video_name)).setText(path);
                        } else {
                            //file = new File(new URI(uri.toString()));
                            //path = file.getPath();
                           path = getPath(uri);
                           file = new File(path);
                            ((EditText)findViewById(R.id.upload_video_name)).setText(path);
                        }
                        if (!file.exists()) {
                            break;
                        }
                        if (file.length() > 100 * 1024 * 1024) {
//                            "文件大于100M";
                            break;
                        }
                        //视频播放
//                        mVideoView.setVideoURI(uri);
//                        mVideoView.start();
                        //开始上传视频，
//                        submitVedio();
                    } catch (Exception e) {
                        String  a=e+"";
                    } catch (OutOfMemoryError e) {
                        String  a=e+"";
                    }
                }
                break;
        }

    }
    public static Uri geturi(Context context, android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                        Log.i("urishi", uri.toString());
                    }
                }
            }
        }
        return uri;
    }
    private String getPath(Uri uri) {
        /*System.out.println("!!!"+uri.toString());
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();*/
        return UriTool.getFilePathByUri(this,uri);
    }
}
