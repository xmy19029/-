package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.connect.Connect;
import com.example.myapplication.data.LineChartMarkView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fenxi extends Activity {
    private LineChart lineChart;
    private XAxis xAxis;
    private YAxis leftYAxis,rightYAxis;
    private Legend legend;
    private JSONArray res;
    private LimitLine limitLine;
    private String username;
    public static List<String> date;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            System.out.println("fenxi----------"+msg.obj);
            if(msg.obj!=null&&msg.obj.toString().length()>5){
                //onCreate(null);
                try {
                    res=new JSONArray(msg.obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<Integer> dlist=new ArrayList<>();//要展示的数据
                date=new ArrayList<>();
                for(int i=0;i<res.length();i++){
                    try {
                        dlist.add(res.getJSONObject(i).getInt("score"));
                        date.add(res.getJSONObject(i).getString("timeStamp"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showLineChart(dlist,"我的成绩", Color.RED);
            }else {
                Toast.makeText(fenxi.this,"你还没有考试",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
    //初始化
    private void initChart(LineChart lChart){
        lChart.setDrawGridBackground(false);//网格线
        lChart.setDrawBorders(true);//边界
        lChart.setDragEnabled(false);//拖动
        lChart.setTouchEnabled(true);//触摸
        lChart.animateY(2500);
        lChart.animateX(1500);//xy轴动画效果
        xAxis=lChart.getXAxis();
        leftYAxis=lChart.getAxisLeft();
        rightYAxis=lChart.getAxisRight();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        leftYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMinimum(0f);
        legend=lChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }
    //曲线初始化
    private void initLineDataSet(LineDataSet lineDataSet,int color,LineDataSet.Mode mode){
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        lineDataSet.setHighlightEnabled(true);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }
    //曲线展示
    private void showLineChart(List<Integer> dataList,final String name, int color){
        List<Entry> entries=new ArrayList<>();
        for(int i=0;i<dataList.size();i++){
            Entry entry=new Entry(i,dataList.get(i));
            entries.add(entry);
        }
        LineDataSet lineDataSet=new LineDataSet(entries,name);
        /*xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return date.get(Math.round(value));
            }
        });*/
        initLineDataSet(lineDataSet,color,LineDataSet.Mode.LINEAR);
        LineData lineData=new LineData(lineDataSet);
        lineChart.setData(lineData);
    }
    private void setMarkView(){
        LineChartMarkView markView=new LineChartMarkView(this,xAxis.getValueFormatter());
        markView.setChartView(lineChart);
        lineChart.setMarker(markView);
        lineChart.invalidate();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenxi);
        username=getIntent().getStringExtra("username");
        TextView title=findViewById(R.id.textView10);
        title.setText(username);
        lineChart=findViewById(R.id.lineChart);
        initChart(lineChart);
        setMarkView();
        Connect connect=new Connect();
        connect.sendReq(handler,0,"submit/getByUsername?username="+username,"GET",null,MainActivity.TOKEN);
    }
}
