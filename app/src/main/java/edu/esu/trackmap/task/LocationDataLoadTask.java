package edu.esu.trackmap.task;

import android.os.AsyncTask;
import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hanke.kimm on 9/24/16.
 */


public class LocationDataLoadTask extends AsyncTask<String, String, List<LatLng>>{

    private GoogleMap map;
    private Context applicationContext;

    private final int latitudeIndex = 1;
    private final int longitudeIndex = 2;

    public LocationDataLoadTask(GoogleMap googleMap, Context context) {
        this.map = googleMap;
        this.applicationContext = context;
    }

    @Override
    protected List<LatLng> doInBackground(String... filePath) {
        List<LatLng> latLngList = null;
        try {
            latLngList = readLocationInputStream(new InputStreamReader(applicationContext.getAssets().open("DMS.csv")));
        }
        catch (FileNotFoundException ef) {
            ef.printStackTrace();
            Toast.makeText(applicationContext, "Could not retrieve file", Toast.LENGTH_SHORT).show();
        }
        catch (IOException iof) {
            iof.printStackTrace();
            Toast.makeText(applicationContext, "IO Exception", Toast.LENGTH_SHORT).show();
        }
        return latLngList;
    }

    @Override
    protected void onPostExecute(List<LatLng> latLngList) {
        LatLng[] latLngArray = latLngList.toArray(new LatLng[latLngList.size()]);
        map.addPolyline((new PolylineOptions()).add(latLngArray).width(5).color(Color.BLUE).geodesic(false));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngArray[0], 20));
    }

    private List<LatLng> readLocationInputStream(InputStreamReader inputStreamReader) throws IOException {
        List<LatLng> latLngList = new ArrayList<LatLng>();
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] dataArray = line.split(",");
            latLngList.add(new LatLng(Double.parseDouble(dataArray[latitudeIndex]), Double.parseDouble(dataArray[longitudeIndex])));
        }
        return latLngList;
    }
}
