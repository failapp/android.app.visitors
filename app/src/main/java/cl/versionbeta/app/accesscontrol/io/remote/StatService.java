package cl.versionbeta.app.accesscontrol.io.remote;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

import cl.versionbeta.app.accesscontrol.model.DonutChart;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StatService {

    @GET("statistics")
    Call<List<DonutChart>> loadData();

}
