package com.example.kazuhiroshigenobu.googlemaptraining;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.widget.LinearLayout.VERTICAL;
import static com.example.kazuhiroshigenobu.googlemaptraining.MapsActivity.CalculationByDistance;
import static com.example.kazuhiroshigenobu.googlemaptraining.MapsActivity.round;

public class EditViewListActivity extends AppCompatActivity {



    Spinner typeSpinner;
    //Spinner waitingTimeSpinner;
    Spinner floorSpinner;
    Spinner startHoursSpinner;
    Spinner startMinutesSpinner;
    Spinner endHoursSpinner;
    Spinner endMinutesSpinner;

    Integer openData = 0;
    Integer endData = 2400;



    EditText textToiletName;
    EditText textHowToAccess;
    //EditText textFeedback;

    //RatingBar ratingBar;
    ImageView mainImage;
    ImageView subImage1;
    ImageView subImage2;

    Button addPhoto;
    Button buttonRenewInfo;

    //private GoogleApiClient client;

    Boolean typeSpinnerLoaded = false;
    Boolean startHourSpinnerLoaded = false;
    Boolean startMinutesSpinnerLoaded = false;
    Boolean closeHourSpinnerLoaded = false;
    Boolean closeMinutesSpinnerLoaded = false;
    Boolean floorSpinnerLoaded = false;


    Boolean typeSpinnerSelected = false;
    Boolean startHourSpinnerSelected = false;
    Boolean startMinutesSpinnerSelected = false;
    Boolean closeHourSpinnerSelected = false;
    Boolean closeMinutesSpinnerSelected = false;
    Boolean floorSpinnerSelected = false;



    Integer photoSelected = 0;

//    private Toolbar toolbar;
    //private TextView toolbarTitle;
    private DatabaseReference toiletRef;
//    private DatabaseReference toiletLocationRef;

    Toilet toilet =  new Toilet();
//    DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("ToiletLocations");
    //GeoFire geoFire = new GeoFire(locationRef);

    ArrayAdapter<CharSequence> adapterType;
    ArrayAdapter<CharSequence> adapterWaitingtime;
    ArrayAdapter<CharSequence> adapterFloor;
    ArrayAdapter<CharSequence> adapterStartHours;
    ArrayAdapter<CharSequence> adapterStartMinutes;
    ArrayAdapter<CharSequence> adapterEndHours;
    ArrayAdapter<CharSequence> adapterEndMinutes;

    private String urlOne = "";
    private String urlTwo = "";
    private String urlThree = "";

    SparseArray<FilterBooleans> filterSparseArray = new SparseArray<>();
//    private RecyclerView recyclertView;
//    private RecyclerView.LayoutManager layoutManager;
//    private AddBooleansListAdapter adapter;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child("images");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_view_list);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.edit_app_bar);
        //toolbarTitle = (TextView) toolbar.findViewById(R.id.editAppBarTitle);

        //switchesReady();
        layoutReady();
        othersReady();


        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        final String originalkey = getIntent().getStringExtra("EXTRA_SESSION_ID");
        toilet.latitude = getIntent().getDoubleExtra("toiletLatitude",0);
        toilet.longitude = getIntent().getDoubleExtra("toiletLongitude",0);


        toileGetData(originalkey);



        toolbar.setNavigationOnClickListener(
                new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),DetailViewActivity.class);
                        intent.putExtra("EXTRA_SESSION_ID", originalkey);
                        intent.putExtra("toiletLatitude",toilet.latitude);
                        intent.putExtra("toiletLongitude",toilet.longitude);


                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    private void layoutReady(){


        mainImage = (ImageView) findViewById(R.id.picture1);
        subImage1 = (ImageView) findViewById(R.id.picture2);
        subImage2 = (ImageView) findViewById(R.id.picture3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.editviewbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.postEdit) {
            Toast.makeText(this, "Hey Post Exection!!", Toast.LENGTH_SHORT).show();
            Log.i("toilet.keyBeforePtEdit",toilet.key);

            firebaseRenewdata();
            //firebaseDeleteData();

            //firebaseEditAction();
            ///////////////////////// 1pm 25th Feb
            return true;

        }


        //edit exection.....
        //firebaseUpdate()
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }


    private void othersReady(){


        textToiletName = (EditText) findViewById(R.id.writeToiletName);
        textHowToAccess = (EditText) findViewById(R.id.inputHowToAccess);
        //textFeedback = (EditText) findViewById(R.id.kansou);

//        textFeedback.setHint("トイレがとても綺麗でした。ありがとうございます。");
//        textFeedback.setMaxLines(Integer.MAX_VALUE);
//        textFeedback.setHorizontallyScrolling(false);

//        ratingBar = (RatingBar) findViewById(R.id.editRating);
//        ratingBar.setRating(3);
        addPhoto = (Button) findViewById(R.id.buttonEditPicture);
        buttonRenewInfo = (Button) findViewById(R.id.buttonEditInfo);
        //buttonchangePinLocation = (Button) findViewById(R.id.buttonEditPinMap);

        mainImage = (ImageView) findViewById(R.id.picture1);
//         ImageView subImage1 = (ImageView) findViewById(R.id.picture2);
//         ImageView subImage2 = (ImageView) findViewById(R.id.picture3);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndAddPhoto();
            }
        });


        buttonRenewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                 pictureUpload(); March 3 18pm

                toiletNameCheck();
//                 firebaseUpdate();

            }
        });

//        buttonchangePinLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Moved to change pin
//
//
//
//
//            }
//        });

    }

    public void checkPermissionAndAddPhoto() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //request permission...
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

            } else {
                //Have a permission
                imageSetPlaceChoose();
            }
        } else {
            //Build.VERSION.SDK_INT < Build.VERSION_CODES.M(23)

            imageSetPlaceChoose();

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

//                mainImage = (ImageView) findViewById(R.id.picture1);
//                subImage1 = (ImageView) findViewById(R.id.picture2);
//                subImage2 = (ImageView) findViewById(R.id.picture3);

                //I wrote this one twice
                ImageView targetView = mainImage;


                if (photoSelected == 0) {
                    targetView = mainImage;
                    uploadImageToDatabase(0, selectedImage);

                } else if (photoSelected == 1) {
                    targetView = subImage1;
                    uploadImageToDatabase(1, selectedImage);
                    //subOnefilePath = selectedImage;

                } else if (photoSelected == 2) {
                    targetView = subImage2;
                    uploadImageToDatabase(3, selectedImage);
                    //subTwofilePath = selectedImage;

                }

                targetView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void toiletNameCheck(){
        String tName = textToiletName.getText().toString();

        if(TextUtils.isEmpty(tName)) {

            textToiletName.setError("Your message");
            Log.i("HEy", "00");
        } else {
            Log.i("Valid", "00");
            //there is a valid name

            //firebaseUpdate();
        }
    }


    private void imageSetPlaceChoose(){
        //final Integer imageNum = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("どこに写真を追加しますか");
        builder.setItems(new CharSequence[]
                        {"メインイメージ", "サブイメージ1", "サブイメージ2"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                photoSelected = 0;
                                showPhoto();

//                                Toast.makeText(this, "clicked 1", 0).show();
                                break;

                            case 1:
                                photoSelected = 1;
                                showPhoto();
//                                Toast.makeText(context, "clicked 2", 0).show();
                                break;
                            case 2:
                                photoSelected = 2;
                                showPhoto();
                                //Toast.makeText(context, "clicked 3", 0).show();
                                break;

                        }
                    }
                });
        builder.create().show();


    }





    private void showPhoto(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2);


    }


    private void sppinnerReady(){

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        //waitingTimeSpinner = (Spinner) findViewById(R.id.spinnerWaitingTime);
        floorSpinner = (Spinner) findViewById(R.id.locationFloorSpinner);
        startHoursSpinner = (Spinner) findViewById(R.id.startHoursSpinner);
        startMinutesSpinner = (Spinner) findViewById(R.id.startMinutesSpinner);
        endHoursSpinner = (Spinner) findViewById(R.id.endHoursSpinner);
        endMinutesSpinner = (Spinner) findViewById(R.id.endMinutesSpinner);

        adapterType = ArrayAdapter.createFromResource(this,R.array.places_names,android.R.layout.simple_spinner_item);
        adapterWaitingtime = ArrayAdapter.createFromResource(this,R.array.waitingTimeArray,android.R.layout.simple_spinner_item);
        adapterFloor = ArrayAdapter.createFromResource(this,R.array.floorCount,android.R.layout.simple_spinner_item);
        adapterStartHours = ArrayAdapter.createFromResource(this,R.array.hoursOption,android.R.layout.simple_spinner_item);
        adapterStartMinutes = ArrayAdapter.createFromResource(this,R.array.minutesOption,android.R.layout.simple_spinner_item);
        adapterEndHours = ArrayAdapter.createFromResource(this,R.array.hoursOption,android.R.layout.simple_spinner_item);
        adapterEndMinutes = ArrayAdapter.createFromResource(this,R.array.minutesOption,android.R.layout.simple_spinner_item);

        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterWaitingtime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterFloor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterStartHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterStartMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEndHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEndMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        typeSpinner.setAdapter(adapterType);
        //waitingTimeSpinner.setAdapter(adapterWaitingtime);
        floorSpinner.setAdapter(adapterFloor);

        startHoursSpinner.setAdapter(adapterStartHours);
        startMinutesSpinner.setAdapter(adapterStartMinutes);
        endHoursSpinner.setAdapter(adapterEndHours);
        endMinutesSpinner.setAdapter(adapterEndMinutes);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                      ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                                                      ((TextView) parent.getChildAt(0)).setTextSize(16);

                                                      if (!typeSpinnerLoaded){
                                                          ((TextView) parent.getChildAt(0)).setText(String.valueOf(toilet.type));
                                                          Log.i("TypeCalled","111");
                                                          typeSpinnerLoaded = true;
                                                      } else {
                                                          typeSpinnerSelected = true;
                                                      }

                                                  }
                                                  @Override
                                                  public void onNothingSelected(AdapterView<?> parent) {
                                                  }
                                              }
        );

//       // waitingTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                                                         @Override
//                                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                                             ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
//                                                             ((TextView) parent.getChildAt(0)).setTextSize(16);
//
//                                                             ((TextView) parent.getChildAt(0)).setText("待ち時間  " + parent.getItemAtPosition(position) + "分");
//
//
//
//                                                         }
//                                                         @Override
//                                                         public void onNothingSelected(AdapterView<?> parent) {
//                                                         }
//                                                     }
//
//        );

        floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                       ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                                                       ((TextView) parent.getChildAt(0)).setTextSize(16);
//                                                            ((TextView) parent.getChildAt(0)).setText(parent.getItemAtPosition(position) + "以上を検索");
                                                       if (!floorSpinnerLoaded){
                                                           ((TextView) parent.getChildAt(0)).setText(String.valueOf(toilet.floor));
                                                           floorSpinnerLoaded = true;
                                                       } else {
                                                           floorSpinnerSelected = true;

                                                       }
                                                   }
                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parent) {


                                                   }
                                               }

        );
        startHoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                                        @Override
                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                                                            ((TextView) parent.getChildAt(0)).setTextSize(16);
                                                            Integer startHours = toilet.openHours/100;
                                                            if (!startHourSpinnerLoaded) {
                                                                //This part is loaded only for the first time

                                                                String selected = parent.getItemAtPosition(position).toString();

                                                                ((TextView) parent.getChildAt(0)).setText(String.valueOf(startHours));
                                                                startHourSpinnerLoaded = true;
                                                                Log.i("User", "Initial NOT Selected88888");
                                                                Log.i("User", "Initial NOT Selected88888" + String.valueOf(selected));
                                                            } else {
                                                                startHourSpinnerSelected = true;
                                                                String selected = parent.getItemAtPosition(position).toString();
                                                                Log.i("User", "Selected88888");
                                                                Log.i("User", "Selected88888" + String.valueOf(selected));

                                                            }
                                                        }


                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> parent) {



                                                        }
                                                    }
        );

        startMinutesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                              ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                                                              ((TextView) parent.getChildAt(0)).setTextSize(16);
                                                              Integer startMinutes = toilet.openHours % 100;

                                                              Log.i("StartMiNutes 88888", "FUCK");

                                                              if (!startMinutesSpinnerLoaded) {
                                                                  startMinutesSpinnerLoaded = true;
                                                                  if (startMinutes != 0) {
                                                                      ((TextView) parent.getChildAt(0)).setText(String.valueOf(startMinutes));
                                                                  } else {
                                                                      String selected = parent.getItemAtPosition(0).toString();
                                                                      ((TextView) parent.getChildAt(0)).setText(selected);
                                                                  }
                                                              } else {
                                                                  startMinutesSpinnerSelected = true;
                                                              }

                                                          }
                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> parent) {
                                                          }
                                                      }

        );

        endHoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                                                          ((TextView) parent.getChildAt(0)).setTextSize(16);
                                                          Integer endHours = toilet.closeHours/100;
                                                          if (!closeHourSpinnerLoaded) {

                                                              ((TextView) parent.getChildAt(0)).setText(String.valueOf(endHours));
                                                              closeHourSpinnerLoaded = true;
                                                          } else {

                                                              closeHourSpinnerSelected = true;

                                                          }

                                                      }
                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {
                                                      }
                                                  }

        );

        endMinutesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                                        @Override
                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                                                            ((TextView) parent.getChildAt(0)).setTextSize(16);
                                                            Integer endMinutes = toilet.closeHours % 100;
                                                            if (!closeMinutesSpinnerLoaded) {
                                                                closeMinutesSpinnerLoaded = true;
                                                                if (endMinutes != 0) {

                                                                    ((TextView) parent.getChildAt(0)).setText(String.valueOf(endMinutes));
                                                                } else {
                                                                    String selected = parent.getItemAtPosition(0).toString();
                                                                    ((TextView) parent.getChildAt(0)).setText(selected);

                                                                }
                                                            } else {
                                                                closeMinutesSpinnerSelected = true;

                                                            }

                                                        }
                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> parent) {
                                                        }
                                                    }
        );


        //onCreatedSpinner = true;

    }

    private void toileGetData(final String originalKey) {

        toiletRef = FirebaseDatabase.getInstance().getReference().child("Toilets");

        toiletRef.child(originalKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("OnDataChangeCalled", "777");
                // for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {

                    Log.i("OnDataChangeCalled", "777888");
                    //Boolean removedToilet = false;

                    Log.i("OnDataChangeCalled", "777888999");
//                    Toilet toilet =  new Toilet();
                    // List<String> toiletData = new ArrayList<>();


                    Log.i("UserInfo.latitude", String.valueOf(UserInfo.latitude));
                    Log.i("UserInfo.longitude", String.valueOf(UserInfo.longitude));

                    Log.i("OnDataChangeCalled", "777888999");
                    LatLng centerLocation = new LatLng(UserInfo.latitude, UserInfo.longitude);
                    //get from the location Manager
                    Log.i("IS THIS THE ERROR???", "4");

                    Log.i("IS THIS THE ERROR???", "1");
                    toilet.latitude = (Double) dataSnapshot.child("latitude").getValue();
                    toilet.longitude = (Double) dataSnapshot.child("longitude").getValue();
                    Log.i("IS THIS THE ERROR???", "2");


                    LatLng toiletLocation = new LatLng(toilet.latitude, toilet.longitude);
                    //get from the database
                    Log.i("IS THIS THE ERROR???", "5");

                    double distance = CalculationByDistance(centerLocation, toiletLocation);
                    Log.i("IS THIS THE ERROR???", "6");

                    if (distance > 1) {
                        toilet.distance = String.valueOf(round(distance, 1)) + "km";
                        Log.i("toilet.distance", String.valueOf(toilet.distance));
                        //Km

                    } else {
                        Double meterDistance = distance * 100;
                        Integer meterA = meterDistance.intValue();
                        Integer meterB = meterA * 10;


                        toilet.distance = String.valueOf(meterB) + "m";

                        Log.i("toilet.distance", String.valueOf(toilet.distance));

                    }


                    Log.i("IS THIS THE ERROR???", "3");
                    Log.i("BOOOL???", "1");

//


                    toilet.key = originalKey;
                    toilet.address = (String) dataSnapshot.child("address").getValue();
                    //Not sure about how to call key....

                    toilet.name = (String) dataSnapshot.child("name").getValue();
                    toilet.openAndCloseHours = (String) dataSnapshot.child("openAndCloseHours").getValue();
                    Long typeLong = (Long) dataSnapshot.child("type").getValue();
                    toilet.type = typeLong.intValue();

                    toilet.urlOne = (String) dataSnapshot.child("urlOne").getValue();
                    toilet.urlTwo = (String) dataSnapshot.child("urlTwo").getValue();
                    toilet.urlThree = (String) dataSnapshot.child("urlThree").getValue();

                    urlOne = toilet.urlOne;
                    urlTwo = toilet.urlTwo;
                    urlThree = toilet.urlThree;

                    Log.i("BOOOL???", "2");

                    toilet.addedBy = (String) dataSnapshot.child("addedBy").getValue();
                    toilet.editedBy = (String) dataSnapshot.child("editedBy").getValue();
                    toilet.averageStar = (String) dataSnapshot.child("averageStar").getValue();
                    toilet.address = (String) dataSnapshot.child("address").getValue();
                    toilet.howtoaccess = (String) dataSnapshot.child("howtoaccess").getValue();

                    Log.i("BOOOL???", "3");

                    Long openh = (Long) dataSnapshot.child("openHours").getValue();
                    toilet.openHours = openh.intValue();
                    openData = toilet.openHours;
                    Long closeh = (Long) dataSnapshot.child("closeHours").getValue();
                    toilet.closeHours = closeh.intValue();
                    endData = toilet.closeHours;


                    Long reviewCount = (Long) dataSnapshot.child("reviewCount").getValue();
                    toilet.reviewCount = reviewCount.intValue();
                    Long averageWait = (Long) dataSnapshot.child("averageWait").getValue();
                    toilet.averageWait = averageWait.intValue();
                    Long toiletFloor = (Long) dataSnapshot.child("toiletFloor").getValue();
                    toilet.floor = toiletFloor.intValue();

                    Log.i("BOOOL???", "4");


                    //Copied from Detail View


                    toilet.available = (Boolean) dataSnapshot.child("available").getValue();
                    toilet.japanesetoilet = (Boolean) dataSnapshot.child("japanesetoilet").getValue();
                    toilet.westerntoilet = (Boolean) dataSnapshot.child("westerntoilet").getValue();
                    toilet.onlyFemale = (Boolean) dataSnapshot.child("onlyFemale").getValue();
                    toilet.unisex = (Boolean) dataSnapshot.child("unisex").getValue();

                    AddDetailBooleans.japanesetoilet = toilet.japanesetoilet;
                    AddDetailBooleans.westerntoilet = toilet.westerntoilet;
                    AddDetailBooleans.onlyFemale = toilet.onlyFemale;
                    AddDetailBooleans.unisex = toilet.unisex;

                    Log.i("BOOOL???","5");

                    toilet.washlet = (Boolean) dataSnapshot.child("washlet").getValue();
                    toilet.warmSeat = (Boolean) dataSnapshot.child("warmSeat").getValue();
                    toilet.autoOpen = (Boolean) dataSnapshot.child("autoOpen").getValue();
                    toilet.noVirus = (Boolean) dataSnapshot.child("noVirus").getValue();
                    toilet.paperForBenki = (Boolean) dataSnapshot.child("paperForBenki").getValue();
                    toilet.cleanerForBenki = (Boolean) dataSnapshot.child("cleanerForBenki").getValue();
                    toilet.autoToiletWash = (Boolean) dataSnapshot.child("nonTouchWash").getValue();

                        AddDetailBooleans.washlet = toilet.washlet;
                        AddDetailBooleans.warmSeat = toilet.warmSeat;
                        AddDetailBooleans.autoOpen = toilet.autoOpen;
                        AddDetailBooleans.noVirus = toilet.noVirus;
                        AddDetailBooleans.paperForBenki = toilet.paperForBenki;
                        AddDetailBooleans.cleanerForBenki = toilet.cleanerForBenki;
                        AddDetailBooleans.autoToiletWash = toilet.autoToiletWash;





                    Log.i("BOOOL???","6");

                    Log.i("Passed Boolean","1");

                    toilet.sensorHandWash = (Boolean) dataSnapshot.child("sensorHandWash").getValue();
                    toilet.handSoap = (Boolean) dataSnapshot.child("handSoap").getValue();
                    toilet.autoHandSoap = (Boolean) dataSnapshot.child("nonTouchHandSoap").getValue();
                    toilet.paperTowel = (Boolean) dataSnapshot.child("paperTowel").getValue();
                    toilet.handDrier = (Boolean) dataSnapshot.child("handDrier").getValue();

                    AddDetailBooleans.sensorHandWash = toilet.sensorHandWash;
                    AddDetailBooleans.handSoap = toilet.handSoap;
                    AddDetailBooleans.autoHandSoap = toilet.autoHandSoap;
                    AddDetailBooleans.paperTowel = toilet.paperTowel;
                    AddDetailBooleans.handDrier = toilet.handDrier;





                    Log.i("Passed Boolean","2");
                    //From Maps Activity
                    //others one

                    toilet.fancy = (Boolean) dataSnapshot.child("fancy").getValue();
                    toilet.smell = (Boolean) dataSnapshot.child("smell").getValue();
                    toilet.conforatableWide = (Boolean) dataSnapshot.child("confortable").getValue();
                    toilet.clothes = (Boolean) dataSnapshot.child("clothes").getValue();
                    toilet.baggageSpace = (Boolean) dataSnapshot.child("baggageSpace").getValue();
                    AddDetailBooleans.fancy = toilet.fancy;
                    AddDetailBooleans.smell = toilet.smell;
                    AddDetailBooleans.conforatableWide = toilet.conforatableWide;
                    AddDetailBooleans.clothes = toilet.clothes;
                    AddDetailBooleans.baggageSpace = toilet.baggageSpace;



                    Log.i("Passed Boolean","3");

                    //others two
                    toilet.noNeedAsk = (Boolean) dataSnapshot.child("noNeedAsk").getValue();
                    toilet.english = (Boolean) dataSnapshot.child("english").getValue();
                    toilet.parking = (Boolean) dataSnapshot.child("parking").getValue();
                    toilet.airCondition = (Boolean) dataSnapshot.child("airCondition").getValue();
                    toilet.wifi = (Boolean) dataSnapshot.child("wifi").getValue();

                    AddDetailBooleans.noNeedAsk = toilet.noNeedAsk;
                    AddDetailBooleans.english = toilet.english;
                    AddDetailBooleans.parking = toilet.parking;
                    AddDetailBooleans.airCondition = toilet.airCondition;
                    AddDetailBooleans.wifi = toilet.wifi;


                    Log.i("Passed Boolean","4");
                    //for ladys

                    toilet.otohime = (Boolean) dataSnapshot.child("otohime").getValue();
                    toilet.napkinSelling = (Boolean) dataSnapshot.child("napkinSelling").getValue();
                    toilet.makeuproom = (Boolean) dataSnapshot.child("makeuproom").getValue();
                    toilet.ladyOmutu = (Boolean) dataSnapshot.child("ladyOmutu").getValue();
                    toilet.ladyBabyChair = (Boolean) dataSnapshot.child("ladyBabyChair").getValue();
                    toilet.ladyBabyChairGood = (Boolean) dataSnapshot.child("ladyBabyChairGood").getValue();
                    toilet.ladyBabyCarAccess = (Boolean) dataSnapshot.child("ladyBabyCarAccess").getValue();


                    AddDetailBooleans.otohime = toilet.otohime;
                    AddDetailBooleans.napkinSelling = toilet.napkinSelling;
                    AddDetailBooleans.makeuproom = toilet.makeuproom;
                    AddDetailBooleans.ladyOmutu = toilet.ladyOmutu;
                    AddDetailBooleans.ladyBabyChair = toilet.ladyBabyChair;
                    AddDetailBooleans.ladyBabyChairGood = toilet.ladyBabyChairGood;
                    AddDetailBooleans.ladyBabyCarAccess = toilet.ladyBabyCarAccess;

                    //for Mans
                    toilet.maleOmutu = (Boolean) dataSnapshot.child("maleOmutu").getValue();
                    toilet.maleBabyChair = (Boolean) dataSnapshot.child("maleBabyChair").getValue();
                    toilet.maleBabyChairGood = (Boolean) dataSnapshot.child("maleBabyChairGood").getValue();
                    toilet.maleBabyCarAccess = (Boolean) dataSnapshot.child("maleBabyCarAccess").getValue();

                    AddDetailBooleans.maleOmutu = toilet.maleOmutu;
                    AddDetailBooleans.maleBabyChair = toilet.maleBabyChair;
                    AddDetailBooleans.maleBabyChairGood = toilet.maleBabyChairGood;
                    AddDetailBooleans.maleBabyCarAccess = toilet.maleBabyCarAccess;
                    //for Family Restroom
                    Log.i("Passed Boolean","6");

                    toilet.wheelchair = (Boolean) dataSnapshot.child("wheelchair").getValue();
                    toilet.wheelchairAccess = (Boolean) dataSnapshot.child("wheelchairAccess").getValue();
                    toilet.autoDoor = (Boolean) dataSnapshot.child("autoDoor").getValue();
                    toilet.callHelp = (Boolean) dataSnapshot.child("callHelp").getValue();
                    toilet.ostomate = (Boolean) dataSnapshot.child("ostomate").getValue();
                    toilet.braille = (Boolean) dataSnapshot.child("braille").getValue();
                    toilet.voiceGuide = (Boolean) dataSnapshot.child("voiceGuide").getValue();
                    toilet.familyOmutu = (Boolean) dataSnapshot.child("familyOmutu").getValue();
                    toilet.familyBabyChair = (Boolean) dataSnapshot.child("familyBabyChair").getValue();

                    AddDetailBooleans.wheelchair = toilet.wheelchair;
                    AddDetailBooleans.wheelchairAccess = toilet.wheelchairAccess;
                    AddDetailBooleans.autoDoor= toilet.autoDoor;
                    AddDetailBooleans.callHelp = toilet.callHelp;
                    AddDetailBooleans.ostomate = toilet.ostomate;
                    AddDetailBooleans.braille = toilet.braille;
                    AddDetailBooleans.voiceGuide = toilet.voiceGuide;
                    AddDetailBooleans.familyOmutu = toilet.familyOmutu;
                    AddDetailBooleans.familyBabyChair = toilet.familyBabyChair;
                    //From Maps Activity
                    ///
                    Log.i("Passed Boolean","7");




                    ////
                    toilet.milkspace = (Boolean) dataSnapshot.child("milkspace").getValue();
                    toilet.babyroomOnlyFemale = (Boolean) dataSnapshot.child("babyRoomOnlyFemale").getValue();
                    toilet.babyroomManCanEnter = (Boolean) dataSnapshot.child("babyRoomMaleEnter").getValue();
                    toilet.babyPersonalSpace = (Boolean) dataSnapshot.child("babyRoomPersonalSpace").getValue();
                    toilet.babyPersonalSpaceWithLock = (Boolean) dataSnapshot.child("babyRoomPersonalSpaceWithLock").getValue();
                    toilet.babyRoomWideSpace = (Boolean) dataSnapshot.child("babyRoomWideSpace").getValue();

                    AddDetailBooleans.milkspace = toilet.milkspace;
                    AddDetailBooleans.babyroomOnlyFemale = toilet.onlyFemale;
                    AddDetailBooleans.babyroomManCanEnter = toilet.babyroomManCanEnter;
                    AddDetailBooleans.babyPersonalSpace = toilet.babyPersonalSpace;
                    AddDetailBooleans.babyPersonalSpaceWithLock = toilet.babyPersonalSpaceWithLock;
                    AddDetailBooleans.babyRoomWideSpace = toilet.babyRoomWideSpace;


                    toilet.babyCarRental = (Boolean) dataSnapshot.child("babyCarRental").getValue();
                    toilet.babyCarAccess = (Boolean) dataSnapshot.child("babyCarAccess").getValue();
                    toilet.omutu = (Boolean) dataSnapshot.child("omutu").getValue();
                    toilet.hipWashingStuff = (Boolean) dataSnapshot.child("hipCleaningStuff").getValue();
                    toilet.babyTrashCan = (Boolean) dataSnapshot.child("omutuTrashCan").getValue();
                    toilet.omutuSelling = (Boolean) dataSnapshot.child("omutuSelling").getValue();

                    AddDetailBooleans.babyCarRental = toilet.babyCarRental;
                    AddDetailBooleans.babyCarAccess = toilet.babyCarAccess;
                    AddDetailBooleans.omutu = toilet.omutu;
                    AddDetailBooleans.hipWashingStuff = toilet.hipWashingStuff;
                    AddDetailBooleans.babyTrashCan = toilet.babyTrashCan;
                    AddDetailBooleans.omutuSelling = toilet.omutuSelling;


                    toilet.babyRoomSink = (Boolean) dataSnapshot.child("babySink").getValue();
                    toilet.babyWashStand = (Boolean) dataSnapshot.child("babyWashstand").getValue();
                    toilet.babyHotWater = (Boolean) dataSnapshot.child("babyHotwater").getValue();
                    toilet.babyMicroWave = (Boolean) dataSnapshot.child("babyMicrowave").getValue();
                    toilet.babyWaterSelling = (Boolean) dataSnapshot.child("babyWaterSelling").getValue();
                    toilet.babyFoddSelling = (Boolean) dataSnapshot.child("babyFoodSelling").getValue();
                    toilet.babyEatingSpace = (Boolean) dataSnapshot.child("babyEatingSpace").getValue();

                    AddDetailBooleans.babyRoomSink = toilet.babyRoomSink;
                    AddDetailBooleans.babyWashStand = toilet.babyWashStand;
                    AddDetailBooleans.babyHotWater = toilet.babyHotWater;
                    AddDetailBooleans.babyMicroWave = toilet.babyMicroWave;
                    AddDetailBooleans.babyWaterSelling = toilet.babyWaterSelling;
                    AddDetailBooleans.babyFoddSelling = toilet.babyFoddSelling;
                    AddDetailBooleans.babyEatingSpace = toilet.babyEatingSpace;


                    toilet.babyChair = (Boolean) dataSnapshot.child("babyChair").getValue();
                    toilet.babySoffa = (Boolean) dataSnapshot.child("babySoffa").getValue();
                    toilet.babyKidsToilet = (Boolean) dataSnapshot.child("kidsToilet").getValue();
                    toilet.babyKidsSpace = (Boolean) dataSnapshot.child("kidsSpace").getValue();
                    toilet.babyHeightMeasure = (Boolean) dataSnapshot.child("babyHeight").getValue();
                    toilet.babyWeightMeasure = (Boolean) dataSnapshot.child("babyWeight").getValue();
                    toilet.babyToy = (Boolean) dataSnapshot.child("babyToy").getValue();
                    toilet.babyFancy = (Boolean) dataSnapshot.child("babyFancy").getValue();
                    toilet.babySmellGood = (Boolean) dataSnapshot.child("babySmellGood").getValue();

                    AddDetailBooleans.babyChair = toilet.babyChair;
                    AddDetailBooleans.babySoffa = toilet.babySoffa;
                    AddDetailBooleans.babyKidsToilet = toilet.babyKidsToilet;
                    AddDetailBooleans.babyKidsSpace = toilet.babyKidsSpace;
                    AddDetailBooleans.babyHeightMeasure = toilet.babyHeightMeasure;
                    AddDetailBooleans.babyWeightMeasure = toilet.babyWeightMeasure;
                    AddDetailBooleans.babyToy = toilet.babyToy;
                    AddDetailBooleans.babyFancy = toilet.babyFancy;
                    AddDetailBooleans.babySmellGood = toilet.babySmellGood;


                    textToiletName.setText(toilet.name);

                    //Float averaegeStarFloat = Float.parseFloat(toilet.averageStar);
                    sppinnerReady();
                    sparseArrayReady();
                    setIntialImage();



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String TAG = "Error";
                Log.w(TAG, "DatabaseError", databaseError.toException());

            }
        });

    }


    private void setIntialImage(){


        Log.i("Set!!!","1");

        if (!toilet.urlOne.equals("")){
            Uri uri = Uri.parse(toilet.urlOne);
            Picasso.with(getApplicationContext()).load(uri).into(mainImage);

        } else {

            Log.i("SetMainImage","1");
            mainImage.setImageResource(R.drawable.default_photo_white_drawable);
            Log.i("SetMainImage","2");

        }

        if (!toilet.urlTwo.equals("")){
            Uri uri = Uri.parse(toilet.urlTwo);
            Picasso.with(getApplicationContext()).load(uri).into(subImage1);
        } else {
            Log.i("SetSubOneImage","1");
            subImage1.setImageResource(R.drawable.default_photo_white_drawable);
            Log.i("SetSubOnenImage","2");
        }

        if (!toilet.urlThree.equals("")){
            Uri uri = Uri.parse(toilet.urlThree);
            Picasso.with(getApplicationContext()).load(uri).into(subImage2);

        } else {
            Log.i("SetSubTwoImage","1");
            subImage2.setImageResource(R.drawable.default_photo_white_drawable);
            Log.i("SetSubTwoImage","2");
        }


    }



    private void sparseArrayReady() {


        filterSparseArray.append(0, new FilterBooleans("設備", false));

        filterSparseArray.append(1, new FilterBooleans("和式トイレ", toilet.japanesetoilet));
        filterSparseArray.append(2, new FilterBooleans("洋式トイレ", toilet.westerntoilet));
        filterSparseArray.append(3, new FilterBooleans("女性専用トイレ", toilet.onlyFemale));
        filterSparseArray.append(4, new FilterBooleans("男女兼用トイレ", toilet.unisex));


        filterSparseArray.append(5, new FilterBooleans("機能", false));
        filterSparseArray.append(6, new FilterBooleans("ウォシュレット",  toilet.washlet));
        filterSparseArray.append(7, new FilterBooleans("暖房便座",  toilet.warmSeat));
        filterSparseArray.append(8, new FilterBooleans("自動開閉便座",  toilet.autoOpen));
        filterSparseArray.append(9, new FilterBooleans("抗菌便座",  toilet.noVirus));
        filterSparseArray.append(10, new FilterBooleans("便座用シート",  toilet.paperForBenki));
        filterSparseArray.append(11, new FilterBooleans("便座クリーナー",  toilet.cleanerForBenki));
        filterSparseArray.append(12, new FilterBooleans("自動洗浄",  toilet.autoToiletWash));


        filterSparseArray.append(13, new FilterBooleans("洗面台設備", false));
        filterSparseArray.append(14, new FilterBooleans("センサー式お手洗い",  toilet.sensorHandWash));
        filterSparseArray.append(15, new FilterBooleans("ハンドソープ",  toilet.handSoap));
        filterSparseArray.append(16, new FilterBooleans("自動ハンドソープ",  toilet.autoHandSoap));
        filterSparseArray.append(17, new FilterBooleans("ペーパータオル",  toilet.paperTowel));
        filterSparseArray.append(18, new FilterBooleans("ハンドドライヤー",  toilet.handDrier));


        filterSparseArray.append(19, new FilterBooleans("1,その他", false));
        filterSparseArray.append(20, new FilterBooleans("おしゃれ",  toilet.fancy));
        filterSparseArray.append(21, new FilterBooleans("いい香り",  toilet.smell));
        filterSparseArray.append(22, new FilterBooleans("快適な広さ",  toilet.conforatableWide));
        filterSparseArray.append(23, new FilterBooleans("洋服掛け",  toilet.clothes));
        filterSparseArray.append(24, new FilterBooleans("荷物置き",  toilet.baggageSpace));

        filterSparseArray.append(25, new FilterBooleans("2,その他", false));
        filterSparseArray.append(26, new FilterBooleans("利用の際の声かけ不要", toilet.noNeedAsk));
        filterSparseArray.append(27, new FilterBooleans("英語表記",  toilet.english));
        filterSparseArray.append(28, new FilterBooleans("駐車場",  toilet.parking));
        filterSparseArray.append(29, new FilterBooleans("冷暖房",  toilet.airCondition));
        filterSparseArray.append(30, new FilterBooleans("無料Wi-Fi",  toilet.wifi));


        filterSparseArray.append(31, new FilterBooleans("女性トイレ", false));
        filterSparseArray.append(32, new FilterBooleans("音姫",  toilet.otohime));
        filterSparseArray.append(33, new FilterBooleans("ナプキン販売機",  toilet.napkinSelling));
        filterSparseArray.append(34, new FilterBooleans("パウダールーム",  toilet.makeuproom));
        filterSparseArray.append(35, new FilterBooleans("おむつ交換台",  toilet.ladyOmutu));
        filterSparseArray.append(36, new FilterBooleans("ベビーキープ",  toilet.ladyBabyChair));
        filterSparseArray.append(37, new FilterBooleans("安全なベビーキープ",  toilet.ladyBabyChairGood));
        filterSparseArray.append(38, new FilterBooleans("ベビーカーでのアクセス", toilet.ladyBabyCarAccess));


        filterSparseArray.append(39, new FilterBooleans("男性トイレ", false));
        filterSparseArray.append(40, new FilterBooleans("おむつ交換台",  toilet.maleOmutu));
        filterSparseArray.append(41, new FilterBooleans("ベビーキープ",  toilet.maleBabyChair));
        filterSparseArray.append(42, new FilterBooleans("安全なベビーキープ",  toilet.maleBabyChairGood));
        filterSparseArray.append(43, new FilterBooleans("ベビーカーでのアクセス",  toilet.maleBabyCarAccess));


        filterSparseArray.append(44, new FilterBooleans("多目的トイレ", false));

        filterSparseArray.append(45, new FilterBooleans("車イス対応",  toilet.wheelchair));
        filterSparseArray.append(46, new FilterBooleans("車イスでアクセス可能",  toilet.wheelchairAccess));
        filterSparseArray.append(47, new FilterBooleans("自動ドア",  toilet.autoDoor));
        filterSparseArray.append(48, new FilterBooleans("呼び出しボタン",  toilet.callHelp));
        filterSparseArray.append(49, new FilterBooleans("オストメイト", toilet.ostomate));
        filterSparseArray.append(50, new FilterBooleans("点字案内",  toilet.braille));
        filterSparseArray.append(51, new FilterBooleans("音声案内",  toilet.voiceGuide));
        filterSparseArray.append(52, new FilterBooleans("おむつ交換台",  toilet.familyOmutu));
        filterSparseArray.append(53, new FilterBooleans("ベビーチェア",  toilet.familyBabyChair));


        filterSparseArray.append(54, new FilterBooleans("1,ベビールームについて", false));
        filterSparseArray.append(55, new FilterBooleans("授乳スペース",  toilet.milkspace));
        filterSparseArray.append(56, new FilterBooleans("女性限定",  toilet.babyroomOnlyFemale));
        filterSparseArray.append(57, new FilterBooleans("男性入室可能",  toilet.babyroomManCanEnter));
        filterSparseArray.append(58, new FilterBooleans("個室あり",  toilet.babyPersonalSpace));
        filterSparseArray.append(59, new FilterBooleans("鍵付き個室あり",  toilet.babyPersonalSpaceWithLock));
        filterSparseArray.append(60, new FilterBooleans("広いスペース",  toilet.babyRoomWideSpace));


        filterSparseArray.append(61, new FilterBooleans("2,ベビールームについて", false));
        filterSparseArray.append(62, new FilterBooleans("ベビーカー貸出し",  toilet.babyCarRental));
        filterSparseArray.append(63, new FilterBooleans("ベビーカーでアクセス可能",  toilet.babyCarAccess));
        filterSparseArray.append(64, new FilterBooleans("おむつ交換台", toilet.omutu));
        filterSparseArray.append(65, new FilterBooleans("おしりふき",  toilet.hipWashingStuff));
        filterSparseArray.append(66, new FilterBooleans("おむつ用ゴミ箱",  toilet.babyTrashCan));
        filterSparseArray.append(67, new FilterBooleans("おむつ販売機",  toilet.omutuSelling));


        filterSparseArray.append(68, new FilterBooleans("3,ベビールームについて", false));
        filterSparseArray.append(69, new FilterBooleans("シンク",  toilet.babyRoomSink));
        filterSparseArray.append(70, new FilterBooleans("洗面台",  toilet.babyWashStand));
        filterSparseArray.append(71, new FilterBooleans("給湯器",  toilet.babyHotWater));
        filterSparseArray.append(72, new FilterBooleans("電子レンジ",  toilet.babyMicroWave));
        filterSparseArray.append(73, new FilterBooleans("飲料自販機",  toilet.babyWaterSelling));
        filterSparseArray.append(74, new FilterBooleans("離乳食販売機",  toilet.babyFoddSelling));
        filterSparseArray.append(75, new FilterBooleans("飲食スペース",  toilet.babyEatingSpace));


        filterSparseArray.append(76, new FilterBooleans("4,ベビールームについて", false));
        filterSparseArray.append(77, new FilterBooleans("ベビーチェア",  toilet.babyChair));
        filterSparseArray.append(78, new FilterBooleans("ソファ", toilet.babySoffa));
        filterSparseArray.append(79, new FilterBooleans("キッズトイレ",  toilet.babyKidsToilet));
        filterSparseArray.append(80, new FilterBooleans("キッズスペース",  toilet.babyKidsSpace));
        filterSparseArray.append(81, new FilterBooleans("身長計",  toilet.babyHeightMeasure));
        filterSparseArray.append(82, new FilterBooleans("体重計",  toilet.babyWeightMeasure));
        filterSparseArray.append(83, new FilterBooleans("おもちゃ",  toilet.babyToy));
        filterSparseArray.append(84, new FilterBooleans("おしゃれ",  toilet.babyFancy));
        filterSparseArray.append(85, new FilterBooleans("いい香り",  toilet.babySmellGood));


        createRecyclerView(filterSparseArray);


    }


    @SuppressWarnings("unchecked")
    private void createRecyclerView(SparseArray array) {
        RecyclerView recyclertView;
        RecyclerView.LayoutManager layoutManager;
        AddBooleansListAdapter adapter;
        Log.i("reviewRecycle", "Called");
        recyclertView = (RecyclerView) findViewById(R.id.toiletReviewList);
        adapter = new AddBooleansListAdapter(array);
        //adapter = new FilterListAdapter(array);
        layoutManager = new LinearLayoutManager(this);
        recyclertView.setLayoutManager(layoutManager);
        recyclertView.setHasFixedSize(true);
        recyclertView.setAdapter(adapter);
        Log.i("reviewRecycle", "Ended");

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclertView.getContext(), VERTICAL);
        recyclertView.addItemDecoration(dividerItemDecoration);

        recyclertView.setHasFixedSize(true);
        recyclertView.setNestedScrollingEnabled(false);


    }

    private void firebaseRenewdata(){


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            Integer updateStHour;
            Integer updateStMinute;
            Integer updateEndHour;
            Integer updateEndMinute;
            Integer updateFloor;
            Integer updateType;


            if (startHourSpinnerSelected) {
                updateStHour = Integer.parseInt(String.valueOf(startHoursSpinner.getSelectedItem()));
            } else {
                updateStHour = toilet.openHours / 100;
            }

            if (startMinutesSpinnerSelected) {
                updateStMinute = Integer.parseInt(String.valueOf(startMinutesSpinner.getSelectedItem()));
            } else {
                updateStMinute = toilet.openHours % 100;
            }

            if (closeHourSpinnerSelected) {
                updateEndHour = Integer.parseInt(String.valueOf(endHoursSpinner.getSelectedItem()));
            } else {
                updateEndHour = toilet.closeHours / 100;
            }

            if (closeMinutesSpinnerSelected) {
                updateEndMinute = Integer.parseInt(String.valueOf(endMinutesSpinner.getSelectedItem()));
            } else {
                updateEndMinute = toilet.closeHours % 100;
            }

            if (floorSpinnerSelected) {
                Log.i("SelectedPosition88888", String.valueOf(floorSpinner.getSelectedItemPosition()));


                //Log.i("SelectedPosition88888", floorSpinner.getSelectedItem().toString());
                // updateFloor = Integer.parseInt(floorSpinner.getSelectedItem().toString());


                //It cannot be integet because it is like "一階"　


            } else {
                Log.i("SelectedPosition88888", String.valueOf(floorSpinner.getSelectedItemPosition()));

                // Log.i("SelectedPosition88888", floorSpinner.getSelectedItem().toString());
                //updateFloor = toilet.floor;
            }


            if (typeSpinnerSelected) {
                updateType = typeSpinner.getSelectedItemPosition();
            } else {
                updateType = toilet.type;
            }


            Integer openTime = updateStHour * 100 + updateStMinute;
            Integer endTime = updateEndHour * 100 + updateEndMinute;

            String updateStMinuteString;
            String updateEndMinuteString;

            if (updateStMinute == 0) {
                updateStMinuteString = "00";
            } else {
                updateStMinuteString = String.valueOf(updateStMinute);
            }

            if (updateEndMinute == 0) {
                updateEndMinuteString = "00";
            } else {
                updateEndMinuteString = String.valueOf(updateStMinute);
            }


            String openingString = String.valueOf(updateStHour) + ":" + updateStMinuteString + "〜" + String.valueOf(updateEndHour) + ":" + updateEndMinuteString;


            String tName = textToiletName.getText().toString();


            toiletRef = FirebaseDatabase.getInstance().getReference().child("Toilets");
//            DatabaseReference toiletLocationRef;
//            toiletLocationRef = FirebaseDatabase.getInstance().getReference().child("ToiletLocations");


            // geolocationUpdate(toilet.key);


            DatabaseReference updateToiletRef = toiletRef.child(toilet.key);


            //String firekey = updateRef.getKey();

            //delete original data in toilets brunch
            //delete original data in toiletLocations brunch

            Log.i("datbaseUpdateLat", String.valueOf(toilet));
            Log.i("datbaseUpdateLon", String.valueOf(toilet.longitude));
            //Log.i("datbaseUpdateAVSTAR", String.valueOf(avStar));


            Map<String, Object> childUpdates = new HashMap<>();


            //I could not get tName
            //Maybe I could not get other values either


            childUpdates.put("name", tName);
            childUpdates.put("openAndCloseHours", openingString);
            childUpdates.put("type", updateType);

            childUpdates.put("urlOne", urlOne);
            childUpdates.put("urlTwo", urlTwo);
            childUpdates.put("urlThree", urlThree);
            childUpdates.put("editedBy", uid);
            childUpdates.put("howtoaccess", "");
            childUpdates.put("openHours", openTime);
            childUpdates.put("closeHours", endTime);
            childUpdates.put("toiletFloor", 3);


            childUpdates.put("japanesetoilet", AddDetailBooleans.japanesetoilet);
            childUpdates.put("westerntoilet", AddDetailBooleans.westerntoilet);
            childUpdates.put("onlyFemale", AddDetailBooleans.onlyFemale);
            childUpdates.put("unisex", AddDetailBooleans.unisex);

            childUpdates.put("washlet", AddDetailBooleans.washlet);
            childUpdates.put("warmSeat", AddDetailBooleans.warmSeat);
            childUpdates.put("autoOpen", AddDetailBooleans.autoOpen);
            childUpdates.put("noVirus", AddDetailBooleans.noVirus);
            childUpdates.put("paperForBenki", AddDetailBooleans.paperForBenki);
            childUpdates.put("cleanerForBenki", AddDetailBooleans.cleanerForBenki);
            childUpdates.put("nonTouchWash", AddDetailBooleans.autoToiletWash);


            childUpdates.put("sensorHandWash", AddDetailBooleans.sensorHandWash);
            childUpdates.put("handSoap", AddDetailBooleans.handSoap);
            childUpdates.put("nonTouchHandSoap", AddDetailBooleans.autoHandSoap);
            childUpdates.put("paperTowel", AddDetailBooleans.paperTowel);
            childUpdates.put("handDrier", AddDetailBooleans.handDrier);


            childUpdates.put("fancy", AddDetailBooleans.fancy);
            childUpdates.put("smell", AddDetailBooleans.smell);
            childUpdates.put("confortable", AddDetailBooleans.conforatableWide);
            childUpdates.put("clothes", AddDetailBooleans.clothes);
            childUpdates.put("baggageSpace", AddDetailBooleans.baggageSpace);

            childUpdates.put("noNeedAsk", AddDetailBooleans.noNeedAsk);
            childUpdates.put("english", AddDetailBooleans.english);
            childUpdates.put("parking", AddDetailBooleans.parking);
            childUpdates.put("airCondition", AddDetailBooleans.airCondition);
            childUpdates.put("wifi", AddDetailBooleans.wifi);

            childUpdates.put("otohime", AddDetailBooleans.otohime);
            childUpdates.put("napkinSelling", AddDetailBooleans.napkinSelling);
            childUpdates.put("makeuproom", AddDetailBooleans.makeuproom);
            childUpdates.put("ladyOmutu", AddDetailBooleans.ladyOmutu);
            childUpdates.put("ladyBabyChair", AddDetailBooleans.ladyBabyChair);
            childUpdates.put("ladyBabyChairGood", AddDetailBooleans.ladyBabyChairGood);
            childUpdates.put("ladyBabyCarAccess", AddDetailBooleans.ladyBabyCarAccess);

            childUpdates.put("maleOmutu", AddDetailBooleans.maleOmutu);
            childUpdates.put("maleBabyChair", AddDetailBooleans.maleBabyChair);
            childUpdates.put("maleBabyChairGood", AddDetailBooleans.maleBabyChairGood);
            childUpdates.put("maleBabyCarAccess", AddDetailBooleans.maleBabyCarAccess);

            //for Family Restroom
            Log.i("Passed Boolean", "6");

            childUpdates.put("wheelchair", AddDetailBooleans.wheelchair);
            childUpdates.put("wheelchairAccess", AddDetailBooleans.wheelchairAccess);
            childUpdates.put("autoDoor", AddDetailBooleans.autoDoor);
            childUpdates.put("callHelp", AddDetailBooleans.callHelp);
            childUpdates.put("ostomate", AddDetailBooleans.ostomate);
            childUpdates.put("braille", AddDetailBooleans.braille);
            childUpdates.put("voiceGuide", AddDetailBooleans.voiceGuide);
            childUpdates.put("familyOmutu", AddDetailBooleans.familyOmutu);
            childUpdates.put("familyBabyChair", AddDetailBooleans.familyBabyChair);


            childUpdates.put("milkspace", AddDetailBooleans.milkspace);
            childUpdates.put("babyRoomOnlyFemale", AddDetailBooleans.babyroomOnlyFemale);
            childUpdates.put("babyRoomMaleEnter", AddDetailBooleans.babyroomManCanEnter);
            childUpdates.put("babyRoomPersonalSpace", AddDetailBooleans.babyPersonalSpace);
            childUpdates.put("babyRoomPersonalSpaceWithLock", AddDetailBooleans.babyPersonalSpaceWithLock);
            childUpdates.put("babyRoomWideSpace", AddDetailBooleans.babyRoomWideSpace);


            childUpdates.put("babyCarRental", AddDetailBooleans.babyCarRental);
            childUpdates.put("babyCarAccess", AddDetailBooleans.babyCarAccess);
            childUpdates.put("omutu", AddDetailBooleans.omutu);
            childUpdates.put("hipCleaningStuff", AddDetailBooleans.hipWashingStuff);
            childUpdates.put("omutuTrashCan", AddDetailBooleans.babyTrashCan);
            childUpdates.put("omutuSelling", AddDetailBooleans.omutuSelling);


            childUpdates.put("babySink", AddDetailBooleans.babyRoomSink);
            childUpdates.put("babyWashstand", AddDetailBooleans.babyWashStand);
            childUpdates.put("babyHotwater", AddDetailBooleans.babyHotWater);
            childUpdates.put("babyMicrowave", AddDetailBooleans.babyMicroWave);
            childUpdates.put("babyWaterSelling", AddDetailBooleans.babyWaterSelling);
            childUpdates.put("babyFoodSelling", AddDetailBooleans.babyFoddSelling);
            childUpdates.put("babyEatingSpace", AddDetailBooleans.babyEatingSpace);


            childUpdates.put("babyChair", AddDetailBooleans.babyChair);
            childUpdates.put("babySoffa", AddDetailBooleans.babySoffa);
            childUpdates.put("kidsToilet", AddDetailBooleans.babyKidsToilet);
            childUpdates.put("kidsSpace", AddDetailBooleans.babyKidsSpace);
            childUpdates.put("babyHeight", AddDetailBooleans.babyHeightMeasure);
            childUpdates.put("babyWeight", AddDetailBooleans.babyWeightMeasure);
            childUpdates.put("babyToy", AddDetailBooleans.babyToy);
            childUpdates.put("babyFancy", AddDetailBooleans.babyFancy);
            childUpdates.put("babySmellGood", AddDetailBooleans.babySmellGood);


            //childUpdates.put("editedBy",uid);

            //We dont need to updata editedBy uid....


            updateToiletRef.updateChildren(childUpdates);


            Log.i("please", "...");
            // geolocationUpdate(firekey);

            Intent intent = new Intent(getApplicationContext(), DetailViewActivity.class);
            intent.putExtra("EXTRA_SESSION_ID", toilet.key);
            intent.putExtra("toiletLatitude", toilet.latitude);
            intent.putExtra("toiletLongitude", toilet.longitude);

            startActivity(intent);
            finish();
        }

    }

    private void uploadImageToDatabase(final int placeNumber, Uri file) {


        String photoId = UUID.randomUUID().toString();


// Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

// Upload file and metadata to the path 'images/mountains.jpg'
        UploadTask uploadTask = storageRef.child(photoId).putFile(file, metadata);

// Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete

                if (taskSnapshot.getMetadata() != null) {

                    Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                    if (downloadUrl != null) {

                        if (placeNumber == 0) {
                            Log.i("urlOne found", downloadUrl.toString());
                            urlOne = downloadUrl.toString();
                        }
                        if (placeNumber == 1) {
                            Log.i("urlTwo found", downloadUrl.toString());
                            urlTwo = downloadUrl.toString();
                        }
                        if (placeNumber == 2) {
                            Log.i("urlThree found", downloadUrl.toString());
                            urlThree = downloadUrl.toString();
                        }

                        //changed urlOne to this downloadUrl...
                    }

                }

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            //Photo Permission

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageSetPlaceChoose();


            }
        }

    }

}