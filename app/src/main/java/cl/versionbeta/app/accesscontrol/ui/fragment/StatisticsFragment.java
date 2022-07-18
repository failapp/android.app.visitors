package cl.versionbeta.app.accesscontrol.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.versionbeta.app.accesscontrol.R;
import cl.versionbeta.app.accesscontrol.io.remote.ApiUtils;
import cl.versionbeta.app.accesscontrol.io.remote.StatService;
import cl.versionbeta.app.accesscontrol.model.DonutChart;
import cl.versionbeta.app.accesscontrol.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticsFragment extends Fragment {


    private StatService statService;

    private PieChart chartAccessLog;

    private static String TAG = "Statistics";
    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        chartAccessLog = view.findViewById(R.id.chartAccessLog);

        /// ///////////////
        /// Test ..

        this.loadStatData();

        /*
        List<PieEntry> pieChartEntries = new ArrayList<>();

        pieChartEntries.add(new PieEntry(10.5f, "Planta"));
        pieChartEntries.add(new PieEntry(20.1f, "Administración"));
        pieChartEntries.add(new PieEntry(10.2f, "RR.HH"));
        pieChartEntries.add(new PieEntry(50.2f, "Producción"));

        PieDataSet pieDataSet = new PieDataSet(pieChartEntries, "Visitas");
        PieData pieData = new PieData(pieDataSet);

        chartAccessLog.setData(pieData);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        */
        ///
        /////////////////////////

        Date date = new Date();
        String month = Util.formatDateMonthYear.format(date);
        Description description = new Description();
        description.setText("Estadísticas " + month);

        chartAccessLog.setDescription(description);
        chartAccessLog.setRotationEnabled(true);

        //chartAccessLog.setHoleRadius(25f);
        chartAccessLog.setHoleRadius(40f);
        chartAccessLog.setTransparentCircleAlpha(0);
        chartAccessLog.setCenterText("Áreas Visitadas");
        chartAccessLog.setCenterTextSize(10);

        //addDataSet();
        chartAccessLog.animateY(1000);

        chartAccessLog.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Log.e(TAG, "onValueSelected: Value select from chart.");
                Log.e(TAG, "onValueSelected: " + e.toString());
                Log.e(TAG, "onValueSelected: " + h.toString());

                Toast.makeText(getActivity(), "Test .. ", Toast.LENGTH_LONG).show();

                /*
                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(sales)){
                        pos1 = i;
                        break;
                    }
                }
                String employee = xData[pos1 + 1];
                Toast.makeText(getActivity(), "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_LONG).show();
                */

            }

            @Override
            public void onNothingSelected() {

            }
        });

        return view;
    }



    private void addDataSet() {

        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Estadísticas Visitas");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = chartAccessLog.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        //legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        //legend.setDirection(Legend.LegendDirection.RIGHT_TO_LEFT);


        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        chartAccessLog.setData(pieData);
        chartAccessLog.invalidate();

    }




    private void loadStatData() {

        this.statService = ApiUtils.getStatService();

        this.statService.loadData().enqueue(new Callback<List<DonutChart>>() {

            @Override
            public void onResponse(Call<List<DonutChart>> call, Response<List<DonutChart>> response) {

                if (response.isSuccessful()) {

                    List<PieEntry> pieChartEntries = new ArrayList<>();
                    List<DonutChart> list = response.body();

                    for (DonutChart dc : list) {
                        PieEntry pieEntry = new PieEntry(dc.getValue(), dc.getLabel());
                        pieChartEntries.add(pieEntry);
                    }

                    PieDataSet pieDataSet = new PieDataSet(pieChartEntries, "Visitas");
                    PieData pieData = new PieData(pieDataSet);
                    chartAccessLog.setData(pieData);
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    //Log.e("TEST-WS", "listPieEntry.size() -> " + pieChartEntries.size());
                }
            }

            @Override
            public void onFailure(Call<List<DonutChart>> call, Throwable t) {
                Log.e("TEST-WS", ":( .." + t.getMessage());
            }
        });

    }


    /// ///////////////////////////////////////////////////////////////////////////////////////
}
