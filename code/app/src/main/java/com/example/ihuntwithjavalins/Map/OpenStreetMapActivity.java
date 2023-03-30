package com.example.ihuntwithjavalins.Map;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ihuntwithjavalins.Player.Player;
import com.example.ihuntwithjavalins.QRCode.QRCode;
import com.example.ihuntwithjavalins.QuickNavActivity;
import com.example.ihuntwithjavalins.R;
import com.example.ihuntwithjavalins.TitleActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

/**
 * The OpenStreetMapActivity class is an activity that displays a map using osmdroid library.
 * It loads the osmdroid configuration and inflates the map layout, which contains the map and some UI elements.
 * It also handles location tracking, map zooming and panning, and adding overlays to the map.
 * TODO: fix issue where map seems to overtake app and nearly-freezes back button action.
 * TODO: add algorithm to find codes within a lat/lon range
 */
public class OpenStreetMapActivity extends AppCompatActivity {
    private String TAGmap = "Sample"; // used as string tag for debug-log messaging
    private MapView map = null;
    private CompassOverlay mCompassOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private Button backButton;
    private FusedLocationProviderClient fusedLocationClient;
    private Player myPlayer;
    private ArrayList<Player> playerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAGmap, "playerlist 1");
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

        // grabbed any store username variables within app local date storage
        SharedPreferences mPrefs = getSharedPreferences("Login", 0);
        String mStringU = mPrefs.getString("UsernameTag", "default_username_not_found");
        // Access a Firestore instance
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionRef_Users = db.collection("Users");
//        DocumentReference docRef_myPlayer = collectionRef_Users.document(mStringU);
//        CollectionReference subColRef_myCodes = docRef_myPlayer.collection("QRCodesSubCollection");
        // grab all players and their codes from firebase and put into player list
//        Log.d(TAGmap, "playerlist 2");
        playerList = new ArrayList<>();
        collectionRef_Users
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        Log.d(TAGmap, "playerlist 3");
                        if (task.isSuccessful()) {
//                            Log.d(TAGmap, "playerlist 4");
                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                                Log.d(TAGmap, "playerlist 5");
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
//                                                Log.d(TAGmap, "playerlist 6");
                                                if (task.isSuccessful()) {

//                                                    Log.d(TAGmap, "playerlist 7");
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                                                        Log.d(TAGmap, "playerlist 8");
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
                                                    playerList.add(tempPlayer);
                                                    //if you, also put you in separate obj
                                                    if ((tempPlayer.getUsername()).equals(mStringU)) {
                                                        myPlayer = new Player(mStringU);
                                                        myPlayer.addCodes(tempCodeList);
                                                    }
//                                                    Log.d(TAGmap, "playerlist 9 size: " + playerList.size());
                                                }
                                            }
                                        });
                            }
                            Log.d(TAGmap, "playerlist 10 size: " + playerList.size());
                        } else {
                            Log.d(TAGmap, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //inflate and create the map
        setContentView(R.layout.open_street_map);

        backButton = findViewById(R.id.map_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

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


        // tracking my location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //delay timer since so code on this page lags behinds others, so this must be done last (timer ensures its done last)
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("MissingPermission")
            @Override
            public void run() {
                //example map points (adding to array of points)
                ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
//        GeoPoint Point_uofa = new GeoPoint(53.52730, -113.52841);
//        items.add(new OverlayItem("NREF building poster1", "450", new GeoPoint(53.52670, -113.52895))); // Lat/Lon decimal degrees 'd'
//                items.add(new OverlayItem("quad poster2", "1250", new GeoPoint(53.52724, -113.52779))); // Lat/Lon decimal degrees
//                items.add(new OverlayItem("tree poster3", "2454", new GeoPoint(53.52744, -113.52723))); // Lat/Lon decimal degrees
//                items.add(new OverlayItem("CSC building poster4", "12", new GeoPoint(53.52694, -113.52740))); // Lat/Lon decimal degrees
//                items.add(new OverlayItem("DICE building poster5", "76", new GeoPoint(53.52793, -113.52888))); // Lat/Lon decimal degrees

                ArrayList<QRCode> pointsCodeList = new ArrayList<>();
                ArrayList<String> pointsCodeListStrings = new ArrayList<>();
                for (Player player : playerList) {
//                    items.add(new OverlayItem(player.getUsername(), player.getRegion(), new GeoPoint(53.52793, -113.52888))); // Lat/Lon decimal degrees
                    ArrayList<QRCode> tempCodeList = (ArrayList<QRCode>) player.getCodes();
                    for (QRCode code : tempCodeList) {
                        if (!pointsCodeListStrings.contains(code.getCodeHash())) {
                            pointsCodeListStrings.add(code.getCodeHash());
                            pointsCodeList.add(code);
                        }
                    }
                }
                Log.d(TAGmap, "geopoints size: " + pointsCodeListStrings.size());
                for (QRCode code : pointsCodeList) {
                    if (code.getCodeLat() != null) {
                        if (!(code.getCodeLat()).equals("")) {
                            items.add(new OverlayItem(code.getCodeName(), code.getCodePoints(), new GeoPoint(Float.parseFloat(code.getCodeLat()), Float.parseFloat(code.getCodeLon())))); // Lat/Lon decimal degrees
                        }
                    }
                }

                CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
                final GeoPoint[] myGPS_point = new GeoPoint[1];
                fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken()) // ignore this error
                        .addOnSuccessListener(OpenStreetMapActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    myGPS_point[0] = new GeoPoint(location.getLatitude(), location.getLongitude()); // current 'location tracker' point
                                    //my location map point 'item'
                                    if ((location.getLatitude() < 0.05f) & (location.getLatitude() > -0.05f)) { //setup for emulator going to 0lat, 0long
                                        myGPS_point[0] = new GeoPoint(53.52730, -113.52841); //     GeoPoint Point_uofa = new GeoPoint(53.52730, -113.52841);
                                    }
                                    OverlayItem myGPSoverlayItem = new OverlayItem("My Location", " ", myGPS_point[0]);
                                    items.add(myGPSoverlayItem);
                                    mapController.setCenter(myGPS_point[0]);
                                } else {
//                            Log.w(TAG, "No current location could be found");
                                }
                            }
                        });


                //the overlay
                ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                        new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                            @Override
                            public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                                mapController.setCenter(item.getPoint());
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
                myGPS_point[0] = new GeoPoint(53.52730, -113.52841); // testing uofa
                OverlayItem myGPSoverlayItem = new OverlayItem("My Location", " ", myGPS_point[0]);
                items.add(myGPSoverlayItem);
                mapController.setCenter(myGPS_point[0]);

            }
        }, 1000);


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

}

