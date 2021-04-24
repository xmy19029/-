package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.myapplication.bean.vDanmu;
import com.example.myapplication.connect.BitmapUtils;
import com.example.myapplication.connect.Connect;
import com.example.myapplication.connect.MyCacheStuffer;
import com.kd.easybarrage.Barrage;
import com.kd.easybarrage.BarrageView;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class DMTest extends AppCompatActivity {
    private VideoView videoView;
    /**
     * 弹幕控件
     */
    private DanmakuView mDanmakuView;
    /**
     * 弹幕的上下文
     */
    private DanmakuContext mContext;
    /**
     * 背景的颜色
     */
    private EditText dmContent;
    private Button dmFly;
    private String[] mContentColorBg = {"#0099ff", "#b2d15c", "#b9b9f1", "#f46c77"};
    private List<vDanmu> vDanmus = new ArrayList<>();
    private int idx;
    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            System.out.println("resultis:"+result);
            JSONArray jsonArray = JSONArray.fromObject(result);
            for (int i = 0;i<jsonArray.size();i++) {
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonArray.get(i).toString());
                vDanmu temp = new vDanmu();
                temp.setContent((String)jsonObject.get("content"));
                temp.setTime((Integer)jsonObject.get("time"));
                vDanmus.add(temp);
            }
        }
    };
    private  Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dmtest);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        vDanmus.clear();
        idx = 0;
        Connect conn = new Connect();
        conn.sendReq2(handler,1,"vDanmu/get?idvideo="+getIntent().getIntExtra("id",1),"GET",null,MainActivity.TOKEN);
        bindViews();
        initDanmuKu();
        dmContent = findViewById(R.id.editText15);
        dmFly = findViewById(R.id.buttonFly);
        dmFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dm = dmContent.getText().toString();
                addDanmaku(true,dm);
                org.json.JSONObject object= new JSONObject();
                try {
                    object.put("idvideo", getIntent().getIntExtra("id",1));
                    object.put("who","hyf");
                    object.put("content",dm);
                    object.put("time",mDanmakuView.getCurrentTime());
                    Connect conn = new Connect();
                    conn.sendReq2(handler2,1,"vDanmu/add","POST",String.valueOf(object),MainActivity.TOKEN);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                long tm = mDanmakuView.getCurrentTime();
            }
        });
        /*BarrageView barrageView = (BarrageView)findViewById(R.id.barrageView);
        barrageView.destroy();
        barrageView.addBarrage(new Barrage("三天之内撒了你"));*/
    }
    private void bindViews(){
        videoView = findViewById(R.id.videoView2);
        videoView.setVideoPath("http://39.105.21.114:12306/file/video/get?filename="+getIntent().getStringExtra("url"));
        videoView.setMediaController(new MediaController(this));
        videoView.start();

    }
    private void initDanmuKu(){
        mDanmakuView = findViewById(R.id.sv_danmaku);
        //设置最大显示行数
        HashMap<Integer, Integer> maxLInesPair = new HashMap<>(16);
        maxLInesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 8);
        //设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>(16);
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        //创建弹幕上下文
        mContext = DanmakuContext.create();
        //设置一些相关的配置
        mContext.setDuplicateMergingEnabled(false)
                //是否重复合并
                .setScrollSpeedFactor(1.2f)
                //设置文字的比例
                .setScaleTextSize(1.2f)
                //图文混排的时候使用！
                .setCacheStuffer(new MyCacheStuffer(this), mBackgroundCacheStuffer)
                //设置显示最大行数
                .setMaximumLines(maxLInesPair)
                //设置防，null代表可以重叠
                .preventOverlapping(overlappingEnablePair);
        //设置解析器
        if (mDanmakuView != null) {
            BaseDanmakuParser defaultDanmakuParser = getDefaultDanmakuParser();
            //相应的回掉
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                    //定时器更新的时候回掉
                   /* System.out.println(timer.currMillisecond);
                    if (timer.currMillisecond > 1000 && timer.currMillisecond<1300){
                        addDanmaku(false,"测试"+(timer.currMillisecond));
                    }*/
                    if(!vDanmus.isEmpty()&&idx<vDanmus.size()){
                        if(vDanmus.get(idx).getTime()<=timer.currMillisecond){
                            addDanmaku(false,vDanmus.get(idx).getContent());
                            idx++;
                        }
                    }
                }

                @Override
                public void drawingFinished() {
                    //弹幕绘制完成时回掉
                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                    //弹幕展示的时候回掉
                }

                @Override
                public void prepared() {
                    //弹幕准备好的时候回掉，这里启动弹幕
                    mDanmakuView.start();
                }
            });
            mDanmakuView.prepare(defaultDanmakuParser, mContext);
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }
    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/7/13  11:21
     * description : 缓存相关的内容
     */
    private BaseCacheStuffer.Proxy mBackgroundCacheStuffer = new BaseCacheStuffer.Proxy() {
        @Override
        public void prepareDrawing(BaseDanmaku danmaku, boolean fromWorkerThread) {
            // 根据你的条件检查是否需要需要更新弹幕
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            //清理相应的数据
            danmaku.tag = null;
        }
    };
    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/7/12  18:26
     * description : 添加弹幕的方法
     */
    public void add(View view) {
        addDanmaku(false,"TT");
    }

    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/7/12  18:31
     * description : 添加弹幕的方法
     */
    private void addDanmaku(boolean islive,String text) {
        //创建一个弹幕对象，这里后面的属性是设置滚动方向的！
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        //设置相应的数据
        Bitmap showBitmap = getBitmap(DMTest.this, R.mipmap.ic_launcher);
        showBitmap = BitmapUtils.getShowPicture(showBitmap);
        Map<String, Object> map = new HashMap<>(16);
        map.put("content",text);
        map.put("bitmap", showBitmap);
        Random random = new Random();
        int randomNum = random.nextInt(mContentColorBg.length);
        map.put("color", mContentColorBg[randomNum]);
        danmaku.tag = map;

        //弹幕显示的文字
        danmaku.text = "这是一条弹幕" + System.nanoTime();
        //设置相应的边距
        danmaku.padding = 5;
        // 可能会被各种过滤器过滤并隐藏显示，若果是本机发送的弹幕，建议设置成1；
        danmaku.priority = 0;
        //是否是直播弹幕
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 1200);
        //设置文字大小
        danmaku.textSize = 15f;
        //设置文字颜色
        danmaku.textColor = Color.RED;
        //设置阴影的颜色
        danmaku.textShadowColor = Color.WHITE;
        // danmaku.underlineColor = Color.GREEN;
        //设置背景颜色
        danmaku.borderColor = Color.GREEN;
        //添加这条弹幕，也就相当于发送
        mDanmakuView.addDanmaku(danmaku);
    }

    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/7/13  11:27
     * description : 添加图文混排的弹幕
     */
    public void addImage(View view) {
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);

        if (danmaku == null || mDanmakuView == null) {
            return;
        }

        //设置相应的数据
        Bitmap showBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.account);
        showBitmap = BitmapUtils.getShowPicture(showBitmap);
        Map<String, Object> map = new HashMap<>(16);
        map.put("content", "这里是显示的内容");
        map.put("bitmap", showBitmap);
        Random random = new Random();
        int randomNum = random.nextInt(mContentColorBg.length);
        map.put("color", mContentColorBg[randomNum]);

        danmaku.tag = map;
        danmaku.textSize = 0;
        danmaku.padding = 10;
        danmaku.text = "";
        // 一定会显示, 一般用于本机发送的弹幕
        danmaku.priority = 1;
        danmaku.isLive = false;
        danmaku.setTime(mDanmakuView.getCurrentTime());
        danmaku.textColor = Color.WHITE;
        // 重要：如果有图文混排，最好不要设置描边(设textShadowColor=0)，否则会进行两次复杂的绘制导致运行效率降低
        danmaku.textShadowColor = 0;


        mDanmakuView.addDanmaku(danmaku);
    }


    /**
     * @author : 贺金龙
     * email : 753355530@qq.com
     * create at 2018/7/12  18:30
     * description : 最简单的解析器
     */
    public static BaseDanmakuParser getDefaultDanmakuParser() {
        return new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        };
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
}
