package at.sw2015.ontvapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    ArrayList<String> allTimes, allTitles;
    String[] channels;
    String channel;
    Spinner sp;
    TextView nowTime, nowContent, nextTime, nextContent;
    View view;

    ImageView logo;
    List<MyTask> tasks;
    Calendar today;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton imageButtonNext = (ImageButton) findViewById(R.id.imageButtonNext);
        imageButtonNext.setOnClickListener(this);

        logo = (ImageView) findViewById(R.id.logo);
        //view = (TextView) findViewById(R.id.spinnerMain);

        tasks = new ArrayList<>();

        nowTime=(TextView) findViewById(R.id.now_time);
        nowContent=(TextView) findViewById(R.id.now_content);
        nextTime=(TextView) findViewById(R.id.next_time);
        nextContent=(TextView) findViewById(R.id.next_content);

        today = Calendar.getInstance();
        sp = (Spinner) findViewById(R.id.spinnerMain);
        addSpinner(today);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageButtonNext:
                button1Click();
                break;
        }
    }

    public void button1Click(){

        Intent intent = new Intent(getApplicationContext(),AdditionalActivity.class);
        intent.putStringArrayListExtra("times", allTimes);
        intent.putStringArrayListExtra("titles", allTitles);
        intent.putExtra("channel", channel);
        startActivity(intent);
    }


    public void requestData(String uri){
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public void getLogo(String channel){
        switch(channel){
            case ("orf1"):
                logo.setImageResource(R.drawable.orf1);
                break;
            case ("orf2"):
                logo.setImageResource(R.drawable.orf2);
                break;
            case ("orf3"):
                logo.setImageResource(R.drawable.orf3);
                break;
            case ("puls4"):
                logo.setImageResource(R.drawable.puls4);
                break;
            case ("sportplus"):
                logo.setImageResource(R.drawable.sportplus);
                break;
        }
    }


    public void addSpinner(final Calendar c){
        channels = getResources().getStringArray(R.array.channels_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, channels);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int index = parent.getSelectedItemPosition();
                channel = channels[index];
                getLogo(channel);

                String date = Helper.getDate(c);
                String makeUrl = Helper.makeQueryString(channel, date);

                if (isOnline()) {
                    requestData(makeUrl);
                } else {
                    Toast.makeText(getBaseContext(), "Network isn't availabe", Toast.LENGTH_LONG).show();
                }
                ((TextView)view).setText("choose channel");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    protected void updateDisplay(String message){
        int start, stop;
        String title, nextTitle;
        allTimes = new ArrayList<String>();
        allTitles = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(message);
            JSONObject jsontv = obj.getJSONObject("jsontv");

            try {
                JSONArray programme = jsontv.getJSONArray("programme");

                Long currentTimeLong = System.currentTimeMillis();
                Long currentTimeQuery = currentTimeLong/1000;//time problems +7200!!!!!!!!!!!!!!!!!!!
                start = Integer.parseInt(programme.getJSONObject(0).getString("start"));
                if(currentTimeQuery<start){
                    today.add(Calendar.DATE,-1);
                    addSpinner(today);
                }

                for(int i=0; i<programme.length(); i++ ){
                    start = Integer.parseInt(programme.getJSONObject(i).getString("start"));
                    stop = Integer.parseInt(programme.getJSONObject(i).getString("stop"));
                    title = programme.getJSONObject(i).getJSONObject("title").getString("de");

                    allTimes.add(Helper.getRealTime(start));
                    allTitles.add(title);

                    if((currentTimeQuery >= start)&&(currentTimeQuery < stop)){
                        String nowT = Helper.getRealTime(start);
                        nowTime.setText(nowT);
                        nowContent.setText(title);

                        String nextT = Helper.getRealTime(stop);
                        nextTime.setText(nextT);

                        if(i!=(programme.length()-1)){
                            nextTitle = programme.getJSONObject(i+1).getJSONObject("title").getString("de");
                            nextContent.setText(nextTitle);
                        }else{
                            nextContent.setText("End of program");
                        }
                    }

                        /*JSONObject titleObj = jsontv.getJSONObject("title");*/

                    //Log.d("title json", title);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class MyTask extends AsyncTask<String, String, String>{

        protected String doInBackground(String... params) {
            return HttpManager.getData(params[0]);
        }

        protected void onPostExecute(String result){
            updateDisplay(result);
        }
    }
}
