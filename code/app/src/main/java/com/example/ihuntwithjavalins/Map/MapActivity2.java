package com.example.ihuntwithjavalins.Map;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import java.util.ArrayList;

public class MapActivity2 extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
//    private MyLocationNewOverlay mLocationOverlay;
//    private GpsMyLocationProvider mGPSLocationProvider;
    private CompassOverlay mCompassOverlay;
    private ScaleBarOverlay mScaleBarOverlay;

    ArrayList<Player> playerList;
    LocationTrack locationTrack; // location tracker

    Button backButton;

    //custom BACK button control (since back doesnt work when map enabled) (*still doesnt work!!!)
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    //custom BACK button control (END)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //(handle permissions first, before map is created. not depicted here)
        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
        //tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.open_street_map);

        backButton = findViewById(R.id.map_backButton);

        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");

        // Access a Firestore instance
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionRef_Users = db.collection("Users");
        DocumentReference docRef_myPlayer = collectionRef_Users.document(mStringU);
        CollectionReference subColRef_myCodes = docRef_myPlayer.collection("QRCodesSubCollection");
        // grab all players and their codes from firebase and put into player list
        collectionRef_Users
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Player tempPlayer = new Player(doc.getId());
                                tempPlayer.setEmail((String) doc.getData().get("Email"));
                                tempPlayer.setRegion((String) doc.getData().get("Region"));
                                tempPlayer.setDateJoined((String) doc.getData().get("Date Joined"));
                                DocumentReference docRef_thisPlayer = collectionRef_Users.document(doc.getId());
                                CollectionReference subColRef_Codes = docRef_thisPlayer.collection("QRCodesSubCollection");
                                ArrayList<QRCode> tempCodeList = new ArrayList<>();
                                subColRef_Codes
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                                        String codeHash = doc.getId();
                                                        String codeName = (String) doc.getData().get("Code Name");
                                                        String codePoints = (String) doc.getData().get("Point Value");
                                                        String codeImgRef = (String) doc.getData().get("Img Ref");
                                                        String codeLatValue = (String) doc.getData().get("Lat Value");
                                                        String codeLonValue = (String) doc.getData().get("Lon Value");
                                                        String codePhotoRef = (String) doc.getData().get("Photo Ref");
                                                        String codeDate = (String) doc.getData().get("Code Date:");
                                                        tempCodeList.add(new QRCode(codeHash, codeName, codePoints, codeImgRef, codeLatValue, codeLonValue, codePhotoRef, codeDate));
                                                    }
                                                    tempPlayer.addCodes(tempCodeList);
                                                    //if you, also put you in separate obj
                                                    if (!(tempPlayer.getUsername()).equals(mStringU)) {
                                                        playerList.add(tempPlayer);
                                                    }
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        String[] permissions = {
                // if you need to show the current location, uncomment the line below
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,

                android.Manifest.permission.ACCESS_COARSE_LOCATION //added
//                android.Manifest.permission.ACCESS_WIFI_STATE,
//                android.Manifest.permission.INTERNET,
//                android.Manifest.permission.ACCESS_NETWORK_STATE,
        };
        requestPermissionsIfNecessary(permissions);

        // location tracker https://www.digitalocean.com/community/tutorials/android-location-api-tracking-gps
        locationTrack = new LocationTrack(MapActivity2.this);
        double longitude = 53.5;
        double latitude = -113.5;
        if (locationTrack.canGetLocation()) {
            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();
            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) +
                    "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {
            locationTrack.showSettingsAlert();
        }
        // location tracker (END)

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        //How to add Map 'Scale bar' overlay
        final DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        //play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(this.mScaleBarOverlay);
        //How to add Map 'Scale bar' overlay (END)

        //How to add a 'compass' overlay
        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);
        //How to add a compass overlay (END)

        //Map controller
        IMapController mapController = map.getController();
        mapController.setZoom(18.5);
        GeoPoint Point_uofa = new GeoPoint(53.52730, -113.52841);

        GeoPoint myGPS_point = new GeoPoint(latitude, longitude); // current 'location tracker' point

        //example map points
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
//        items.add(new OverlayItem("NREF building poster1", "450", new GeoPoint(53.52670, -113.52895))); // Lat/Lon decimal degrees 'd'
//        items.add(new OverlayItem("quad poster2", "1250", new GeoPoint(53.52724, -113.52779))); // Lat/Lon decimal degrees
//        items.add(new OverlayItem("tree poster3", "2454", new GeoPoint(53.52744, -113.52723))); // Lat/Lon decimal degrees
//        items.add(new OverlayItem("CSC building poster4", "12", new GeoPoint(53.52694, -113.52740))); // Lat/Lon decimal degrees
//        items.add(new OverlayItem("DICE building poster5", "76", new GeoPoint(53.52793, -113.52888))); // Lat/Lon decimal degrees
for ( Player player : playerList){
    for ( QRCode code : player.getCodes()){
        items.add(new OverlayItem(code.getCodeName(), code.getCodePoints(), new GeoPoint(Float.parseFloat(code.getCodeLat()), Float.parseFloat(code.getCodeLon())))); // Lat/Lon decimal degrees
    }
}
        items.add(new OverlayItem("tree poster4", "24", new GeoPoint(53.52745, -113.52722))); // Lat/Lon decimal degrees


        //my location map point 'item'
        OverlayItem myGPSoverlayItem = new OverlayItem("My Location", " ", myGPS_point);
        items.add(myGPSoverlayItem);

        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        mapController.setCenter(item.getPoint());
                        return false;
                    }
                }, ctx);
        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay); // add 'item' array of points
//        mapController.setCenter(Point_uofa);
        mapController.setCenter(myGPS_point);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }


}

