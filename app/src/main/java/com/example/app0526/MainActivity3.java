package com.example.app0526;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    MapFragment mapFrag;
    GroundOverlayOptions placeMark;
    Button btnPrev, btnNext;
    List<String> lines = new ArrayList<>();
    int placeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("경기도 으뜸 맛집");
        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);

        readCSV();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line = lines.get(placeCount++);
                if (placeCount > lines.size()-1)
                    placeCount = 0;
                String[] tokens = line.split(",");
                double lat = Double.parseDouble(tokens[0]);
                double lon = Double.parseDouble(tokens[1]);
                String restName = tokens[2];

                LatLng point;
                point = new LatLng(lat, lon);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
                placeMark = new GroundOverlayOptions().image(
                                BitmapDescriptorFactory.fromResource(R.drawable.food))
                        .position(point, 500f, 500f);
                gMap.addGroundOverlay(placeMark);
                Toast.makeText(getApplicationContext(), restName, Toast.LENGTH_LONG).show();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line = lines.get(placeCount--);
                if (placeCount < 0)
                    placeCount = lines.size()-1;
                String[] tokens = line.split(",");
                double lat = Double.parseDouble(tokens[0]);
                double lon = Double.parseDouble(tokens[1]);
                String restName = tokens[2];

                LatLng point;
                point = new LatLng(lat, lon);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
                placeMark = new GroundOverlayOptions().image(
                                BitmapDescriptorFactory.fromResource(R.drawable.food))
                        .position(point, 500f, 500f);
                gMap.addGroundOverlay(placeMark);
                Toast.makeText(getApplicationContext(), restName, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240), 13));
        gMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void readCSV() {
        InputStream inputStream = getResources().openRawResource(R.raw.good_place);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            line = reader.readLine(); // 첫 제목행을 읽어서 버림
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (Exception e) {}
    }
}
