package com.example.kazuhiroshigenobu.googlemaptraining;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class FilterSearchActivity extends AppCompatActivity {

    Button button;
    Toolbar toolbar;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    ArrayAdapter<CharSequence> adapter4;

    Switch availableSwitch;
    Switch japaneseSwitch;
    Switch westernSwitch;
    Switch onlyFemaleSwitch;
    Switch unisexSwitch;

    Switch washletSwitch;
    Switch warmSeatSwitch;
    Switch autoOpenSwitch;
    Switch noVirusSwitch;
    Switch paperForBenkiSwitch;
    Switch cleanerForBenkiSwitch;
    Switch autoToiletWashSwitch;

    Switch sensorHandWashSwitch;
    Switch handSoapSwitch;
    Switch autoHandSoapSwicth;
    Switch paperTowelSwitch;
    Switch handDrierSwitch;

    Switch othimeSwitch;
    Switch omutuSwitch;
    Switch milkSwitch;
    Switch makeroomSwitch;
    Switch clothesSwitch;
    Switch baggageSpaceSwitch;

    Switch wheelChairSwitch;
    Switch handrailSwitch;
    Switch callHelpSwitch;
    Switch ostomateSwitch;
    Switch englishSwitch;
    Switch brailleSwitch;
    Switch voiceGuideSwitch;


    Switch fancySwitch;
    Switch smellSwitch;
    Switch confortableSwitch;
    Switch parkingSwitch;
    Switch airConditionSwitch;
    Switch wifiSwitch;







    Filter filter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);

        button = (Button) findViewById(R.id.startSearchButton);
        toolbar = (Toolbar) findViewById(R.id.app_bar2);
        toolbar.setTitle("条件検索");

        toolbar.setNavigationContentDescription("戻る");
//        toolbar.setNavigationIcon(R.drawable.earth);
        // I can set an image like X for the left side

        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.i("OKAY???","OKAY???5555");



        //Commented at 9am 24th


//        getSupportActionBar().setTitle("TITLE");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FilterSearchActivity.this, "Button 2222liCked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),MapsActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(FilterSearchActivity.this, "Button CliCked?????", Toast.LENGTH_SHORT).show();

            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Filter.availableFilter = false;
                Filter.japaneseFilter = false;
                Filter.westernFilter = false;
                Filter.onlyFemaleFilter = false;
                Filter.washletFilter = false;
                Filter.warmSearFilter = false;
                Filter.omutuFilter = false;
                Filter.milkspaceFilter = false;
                Filter.makeroomFilter = false;
                Filter.baggageSpaceFilter = false;
                Filter.wheelchairFilter = false;
                Filter.ostomateFilter = false;


//                filter.japaneseFilter = false;
//                filter.westernFilter = false;
//                filter.onlyFemaleFilter = false;
//                filter.unisexFilter = false;
//                filter.washletFilter = false;
//                filter.warmSearFilter = false;
//                filter.omutuFilter = false;
//                filter.milkspaceFilter = false;
//                filter.makeroomFilter = false;
//                filter.baggageSpaceFilter = false;
//                filter.wheelchairFilter = false;
//                filter.ostomateFilter = false;
//                filter.availableFilter = false;

                Intent intent = new Intent(v.getContext(),MapsActivity.class);
                startActivity(intent);
                finish();
             }
        }
        );

        spinnersReady();
        switchReady();
        Log.i("JAP98789",String.valueOf(Filter.japaneseFilter));

        Log.i("filter.distanceFil123",String.valueOf(Filter.distanceFilter));
        Log.i("filter.typeFilter123",String.valueOf(Filter.typeFilter));
        Log.i("filter.starFilter123",String.valueOf(Filter.starFilter));
        Log.i("filter.starFited123",String.valueOf(Filter.starFilterSetted));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.filter2, menu);
        //Commented for adding below code at 5pm
        //getMenuInflater().inflate(R.menu.filter,amvMenu.getMenu());


//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menufile);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.filter){
            //Search clicked
            Toast.makeText(this, "Hey Did you Click filter??", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
            finish();


            return  true;

        } else {

            Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();

//        if id == R.id.
            return super.onOptionsItemSelected(item);
        }
    }


    private void switchReady(){

        availableSwitch = (Switch) findViewById(R.id.availableSwitch);
        japaneseSwitch = (Switch) findViewById(R.id.japaneseSwich);
        westernSwitch = (Switch) findViewById(R.id.westernSwitch);
        onlyFemaleSwitch = (Switch) findViewById(R.id.onlyFemaleSwitch);
        unisexSwitch = (Switch) findViewById(R.id.unisexSwitch);

        washletSwitch = (Switch) findViewById(R.id.washletSwitch);
        warmSeatSwitch = (Switch) findViewById(R.id.warmSeatSwitch);
        autoOpenSwitch = (Switch) findViewById(R.id.autoOpenSwitch);
        noVirusSwitch = (Switch) findViewById(R.id.noVirusBenki);
        paperForBenkiSwitch = (Switch) findViewById(R.id.paperForBenki);
        cleanerForBenkiSwitch = (Switch) findViewById(R.id.cleanerForBenki);
        autoToiletWashSwitch = (Switch) findViewById(R.id.nonTouchWash);

        sensorHandWashSwitch = (Switch) findViewById(R.id.senserHandSwitch);
        handSoapSwitch = (Switch) findViewById(R.id.handSoapSwitch);
        autoHandSoapSwicth = (Switch) findViewById(R.id.nonTouchSoapSwitch);
        paperTowelSwitch = (Switch) findViewById(R.id.paperTowelSwitch);
        handDrierSwitch = (Switch) findViewById(R.id.handDrierSwitch);

        othimeSwitch = (Switch) findViewById(R.id.otohimeSwitch);
        omutuSwitch = (Switch) findViewById(R.id.omutuSwitch);
        milkSwitch = (Switch) findViewById(R.id.milkSwitch);
        makeroomSwitch = (Switch) findViewById(R.id.makeroomSwitch);
        clothesSwitch = (Switch) findViewById(R.id.clothesSwitch);
        baggageSpaceSwitch = (Switch) findViewById(R.id.baggageSwitch);

        wheelChairSwitch = (Switch) findViewById(R.id.wheelChairSwitch);
        handrailSwitch = (Switch) findViewById(R.id.handrailSwitch);
        callHelpSwitch = (Switch) findViewById(R.id.callHelpSwitch);
        ostomateSwitch = (Switch) findViewById(R.id.ostomateSwitch);
        englishSwitch = (Switch) findViewById(R.id.englishWrittenSwitch);
        brailleSwitch = (Switch) findViewById(R.id.brailleSwitch);
        voiceGuideSwitch = (Switch) findViewById(R.id.voiceGuideSwitch);

        fancySwitch = (Switch) findViewById(R.id.fancySwitch);
        smellSwitch = (Switch) findViewById(R.id.smellSwitch);
        confortableSwitch = (Switch) findViewById(R.id.comfontableWideSwitch);
        parkingSwitch = (Switch) findViewById(R.id.parkingSwitch);
        airConditionSwitch = (Switch) findViewById(R.id.airConditionSwitch);
        wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);




//        filter.japaneseFilter = false;
        //THis caused an error
        availableSwitch.setChecked(Filter.availableFilter);
        japaneseSwitch.setChecked(Filter.japaneseFilter);
        westernSwitch.setChecked(Filter.westernFilter);
        onlyFemaleSwitch.setChecked(Filter.onlyFemaleFilter);
        unisexSwitch.setChecked(Filter.unisexFilter);


        washletSwitch.setChecked(Filter.washletFilter);
        warmSeatSwitch.setChecked(Filter.warmSearFilter);
        autoOpenSwitch.setChecked(Filter.autoOpen);
        noVirusSwitch.setChecked(Filter.noVirusFilter);
        paperForBenkiSwitch.setChecked(Filter.paperForBenkiFilter);
        cleanerForBenkiSwitch.setChecked(Filter.cleanerForBenkiFilter);
        autoToiletWashSwitch.setChecked(Filter.autoToiletWashFilter);

        sensorHandWashSwitch.setChecked(Filter.sensorHandWashFilter);
        handSoapSwitch.setChecked(Filter.handSoapFilter);
        autoHandSoapSwicth.setChecked(Filter.autoHandSoapFilter);
        paperTowelSwitch.setChecked(Filter.paperTowelFilter);
        handDrierSwitch.setChecked(Filter.handDrierFilter);

        othimeSwitch.setChecked(Filter.otohime);
        omutuSwitch.setChecked(Filter.omutuFilter);
        milkSwitch.setChecked(Filter.milkspaceFilter);
        makeroomSwitch.setChecked(Filter.makeroomFilter);
        clothesSwitch.setChecked(Filter.clothes);
        baggageSpaceSwitch.setChecked(Filter.baggageSpaceFilter);


        wheelChairSwitch.setChecked(Filter.wheelchairFilter);
        handrailSwitch.setChecked(Filter.handrailFilter);
        callHelpSwitch.setChecked(Filter.callHelpFilter);
        ostomateSwitch.setChecked(Filter.ostomateFilter);
        englishSwitch.setChecked(Filter.writtenEnglish);
        brailleSwitch.setChecked(Filter.braille);
        voiceGuideSwitch.setChecked(Filter.voiceGuideFilter);


        fancySwitch.setChecked(Filter.fancy);
        smellSwitch.setChecked(Filter.smell);
        confortableSwitch.setChecked(Filter.confortableWise);
        parkingSwitch.setChecked(Filter.parking);
        airConditionSwitch.setChecked(Filter.airConditionFilter);
        wifiSwitch.setChecked(Filter.wifiFilter);


       //Basic info......

        availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.availableFilter = true;
                } else{
                    Filter.availableFilter = false;
                }
            }
        });

        japaneseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.japaneseFilter = true;
                } else{
                    Filter.japaneseFilter = false;
                }
            }
        });

        westernSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "western", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.westernFilter = true;
                } else{
                    Filter.westernFilter = false;
                }
            }
        });

        onlyFemaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "onlyFemaleSwitch", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.onlyFemaleFilter = true;
                } else{
                    Filter.onlyFemaleFilter = false;
                }
            }
        });

        unisexSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "unisexSwitch", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.unisexFilter = true;
                } else{
                    Filter.unisexFilter  = false;
                }
            }
        });

        //Toielt Functions.....



        washletSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, " washletSwitch", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.washletFilter = true;
                } else{
                    Filter.washletFilter = false;
                }
            }
        });

        warmSeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "warmSeatSwitch", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.warmSearFilter = true;
                } else{
                    Filter.warmSearFilter = false;
                }
            }
        });


        autoOpenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.autoOpen = true;
                } else{
                    Filter.autoOpen = false;
                }
            }
        });

        noVirusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.noVirusFilter = true;
                } else{
                    Filter.noVirusFilter= false;
                }
            }
        });

        paperForBenkiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.paperForBenkiFilter = true;
                } else{
                    Filter.paperForBenkiFilter= false;
                }
            }
        });

        cleanerForBenkiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.cleanerForBenkiFilter = true;
                } else{
                    Filter.cleanerForBenkiFilter= false;
                }
            }
        });

        autoToiletWashSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.autoToiletWashFilter = true;
                } else{
                    Filter.autoToiletWashFilter= false;
                }
            }
        });

        //WashStand...

        sensorHandWashSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.sensorHandWashFilter= true;
                } else{
                    Filter.sensorHandWashFilter= false;
                }
            }
        });

        handSoapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.handSoapFilter= true;
                } else{
                    Filter.handSoapFilter= false;
                }
            }
        });

        autoHandSoapSwicth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.autoHandSoapFilter= true;
                } else{
                    Filter.autoHandSoapFilter= false;
                }
            }
        });

        paperTowelSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.paperTowelFilter= true;
                } else{
                    Filter.paperTowelFilter= false;
                }
            }
        });

        handDrierSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Filter.handDrierFilter= true;
                } else{
                    Filter.handDrierFilter= false;
                }
            }
        });

        //Filter for ladies....

        othimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.otohime= true;
                } else{
                    Filter.otohime = false;
                }
            }
        });



        omutuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "omutuSwitch", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.omutuFilter = true;
                } else{
                    Filter.omutuFilter = false;
                }
            }
        });

        milkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.milkspaceFilter = true;
                } else{
                    Filter.milkspaceFilter = false;
                }
            }
        });
        makeroomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.makeroomFilter = true;
                } else{
                    Filter.makeroomFilter = false;
                }
            }
        });


        clothesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.clothes = true;
                } else{
                    Filter.clothes = false;
                }
            }
        });

        baggageSpaceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.baggageSpaceFilter = true;
                } else{
                    Filter.baggageSpaceFilter = false;
                }
            }
        });

        //Filter for barrier free..

        wheelChairSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.wheelchairFilter = true;
                } else{
                    Filter.wheelchairFilter = false;
                }
            }
        });

        handrailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.handrailFilter = true;
                } else{
                    Filter.handrailFilter = false;
                }
            }
        });

        callHelpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.callHelpFilter = true;
                } else{
                    Filter.callHelpFilter = false;
                }
            }
        });



        ostomateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(FilterSearchActivity.this, "", Toast.LENGTH_SHORT).show();
                if (isChecked){
                    Filter.ostomateFilter = true;
                } else{
                    Filter.ostomateFilter = false;
                }
            }
        });

        englishSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.writtenEnglish = true;
                } else{
                    Filter.writtenEnglish= false;
                }
            }
        });

        brailleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.braille = true;
                } else{
                    Filter.braille = false;
                }
            }
        });

        voiceGuideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.voiceGuideFilter = true;
                } else{
                    Filter.voiceGuideFilter = false;
                }
            }
        });


        //Filter for others







        fancySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.fancy = true;
                } else{
                    Filter.fancy = false;
                }
            }
        });

        smellSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.smell = true;
                } else{
                    Filter.smell = false;
                }
            }
        });

        confortableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.confortableWise = true;
                } else{
                    Filter.confortableWise = false;
                }
            }
        });



        parkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.parking= true;
                } else{
                    Filter.parking = false;
                }
            }
        });

        airConditionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.airConditionFilter = true;
                } else{
                    Filter.airConditionFilter = false;
                }
            }
        });

        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                return Filter.japaneseFilter();
                if (isChecked){
                    Filter.airConditionFilter = true;
                } else{
                    Filter.airConditionFilter = false;
                }
            }
        });







    }

    private void spinnersReady(){
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        adapter1 = ArrayAdapter.createFromResource(this,R.array.distance_names,android.R.layout.simple_spinner_item);
        adapter2 = ArrayAdapter.createFromResource(this,R.array.order_names,android.R.layout.simple_spinner_item);
        adapter3 = ArrayAdapter.createFromResource(this,R.array.places_names,android.R.layout.simple_spinner_item);
        adapter4 = ArrayAdapter.createFromResource(this,R.array.star_numbers,android.R.layout.simple_spinner_item);


        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(20);


                if (position == 0){
                    Log.i("DIDIDID", "9090");

                }

                if (position == 1){
                    Toast.makeText(getBaseContext(),"1",Toast.LENGTH_SHORT).show();
                    Filter.distanceSetted = true;
                    Filter.distanceFilter = 1.0;
                }

                if (position == 2){
                    Toast.makeText(getBaseContext(),"2",Toast.LENGTH_SHORT).show();
                    Filter.distanceSetted = true;
                    Filter.distanceFilter = 3.0;
                }

                if (position == 3){
                    Toast.makeText(getBaseContext(),"3",Toast.LENGTH_SHORT).show();
                    Filter.distanceSetted = true;
                    Filter.distanceFilter = 5.0;
                }

                if (position == 4){
                    Toast.makeText(getBaseContext(),"4",Toast.LENGTH_SHORT).show();
                    Filter.distanceSetted = true;
                    Filter.distanceFilter = 10.0;
                }

                Toast.makeText(getBaseContext(),String.valueOf(Filter.distanceFilter) + "After",Toast.LENGTH_SHORT).show();

                ((TextView) parent.getChildAt(0)).setText(Filter.distanceFilter + "kmのトイレを検索" );

               // ((TextView) parent.getChildAt(0)).setText(filter.distanceFilter + "kmのトイレを検索" );

//                Toast.makeText(getBaseContext(),String.valueOf(position) + "Selected",Toast.LENGTH_SHORT).show();
//                //This might cause an error 2pm 24th
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filterName;
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
//                ((TextView) parent.getChildAt(0)).setText(String.valueOf(parent.getItemAtPosition(position)));


                //I think i need one more filter

                if (position == 1){
                    Toast.makeText(getBaseContext(),"0",Toast.LENGTH_SHORT).show();
                    Filter.orderDistanceFilter = true;
                    Filter.orderStarFilter = false;
                    Filter.orderReviewFilter = false;

                }

                if (position == 2){
                    Toast.makeText(getBaseContext(),"1",Toast.LENGTH_SHORT).show();
                    Filter.orderStarFilter = true;
                    Filter.orderDistanceFilter = false;
                    Filter.orderReviewFilter = false;
                }

                if (position == 3){
                    Toast.makeText(getBaseContext(),"2",Toast.LENGTH_SHORT).show();
                    Filter.orderReviewFilter = true;
                    Filter.orderDistanceFilter = false;
                    Filter.orderStarFilter = false;
                }

                if (Filter.orderDistanceFilter == true){
                    ((TextView) parent.getChildAt(0)).setText("現在地から近い順");
                } else if (Filter.orderStarFilter == true){
                    ((TextView) parent.getChildAt(0)).setText("評価が高い順");
                } else if (Filter.orderReviewFilter == true){
                    ((TextView) parent.getChildAt(0)).setText("感想が多い順");
                } else {

                    ((TextView) parent.getChildAt(0)).setText("現在地から近い順");
                }

                //Commented 8pm 23th

//                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position) + "Selected",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
//                ((TextView) parent.getChildAt(0)).setText(String.valueOf(parent.getItemAtPosition(position)));

                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setText(Filter.typeFilter);
                }else if (position == 1) {
                    //全てのトイレ
                    Filter.typeFilterOn = false;
                    Filter.typeFilter = String.valueOf(parent.getItemAtPosition(position));
                } else {
                    Filter.typeFilterOn = true;
                    Filter.typeFilter = String.valueOf(parent.getItemAtPosition(position));

                    Toast.makeText(FilterSearchActivity.this, Filter.typeFilter, Toast.LENGTH_SHORT).show();

                }

//                if (filter.typeFilterOn == false) {
//
//                    ((TextView) parent.getChildAt(0)).setText("全てのトイレ");
//                }else{
//                    ((TextView) parent.getChildAt(0)).setText(filter.typeFilter);
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                //((TextView) parent.getChildAt(0)).setText(parent.getItemAtPosition(position) + "以上を検索");


                if (position == 0){
//                    filter.starFilter = 1.0;
                 }

                if (position == 1){
                    Filter.starFilter = 1.0;
                }

                if (position == 2){
                    Filter.starFilter = 2.0;
                }

                if (position == 3){
                    Filter.starFilter = 3.0;
                }
                if (position == 4){
                    Filter.starFilter = 4.0;
                }



                ((TextView) parent.getChildAt(0)).setText(Filter.starFilter + "以上を検索");


               // Toast.makeText(getBaseContext(),String.valueOf(filter.starFilter),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

//    private void startFilterSearch(){
//        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
//        startActivity(intent);
//
//
//    }



//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Toast.makeText(this, "SwitchChanged", Toast.LENGTH_SHORT).show();
//    }
}
