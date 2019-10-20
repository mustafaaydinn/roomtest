package com.example.roomtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.roomtest.database.dataBase;
import com.example.roomtest.database.toyInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity.class";
    dataBase dataInstance = null;
    boolean isReady = false;
    SharedPreferences shared = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //avoid data repeat setting
        shared = getSharedPreferences("test", MODE_PRIVATE);
        isReady = shared.getBoolean("isReady", false);
        if(!isReady) {
            boolean isFakeDataReady = true;
            shared.edit()
                    .putBoolean("isReady", isFakeDataReady)
                    .commit();
            Log.d(TAG, "dataBase is empty");
        } else {
            Log.d(TAG, "dataBase already setting");
        }

        //init
        dataInstance = dataBase.getInstance(this);

        // do not run in main thread
        testTask asyncTask = new testTask(this);
        asyncTask.execute("test");

        init();
    }

    public void init() {

    }

    public void printData() {
        List<toyInfo> toyList = dataBase
                .getInstance(this)
                .getToyDao()
                .getAll();

        if(toyList.size() > 0){
            for(int i = 0 ; i < toyList.size() ; i ++) {
                Log.d(TAG, "printData: name = " + toyList.get(i).getName() +
                        ", price = " + toyList.get(i).getPrice() +
                        ", web = " + toyList.get(i).getWeb());
            }
        }
    }

    public void saveData() {
        toyInfo toy1 = new toyInfo();
        toy1.setName("EAA-051");
        toy1.setPrice(2390);
        toy1.setWeb("https://www.toy-people.com/?p=38665");
        toy1.setImageUri("https://drive.google.com/file/d/1phylied1IKbdJ1-5ygMNs6XWShxqocF2/view?usp=sharing");
        dataInstance.getToyDao().insert(toy1);

        toyInfo toy2 = new toyInfo();
        toy2.setName("EAA-099");
        toy2.setPrice(2390);
        toy2.setWeb("https://www.toy-people.com/?p=48751");
        toy2.setImageUri("https://drive.google.com/file/d/1-yafV1iGLZ46yavpw0abTtRE0WROs7EP/view?usp=sharing");
        dataInstance.getToyDao().insert(toy2);

        toyInfo toy3 = new toyInfo();
        toy3.setName("EAA-060");
        toy3.setPrice(2990);
        toy3.setWeb("https://www.toy-people.com/?p=45927");
        toy3.setImageUri("https://drive.google.com/file/d/1Mv-GNJFqejMuciGKsvSh9OK8aJTUPMHL/view?usp=sharing");
        dataInstance.getToyDao().insert(toy3);

        toyInfo toy4 = new toyInfo();
        toy4.setName("EAA-074");
        toy4.setPrice(2390);
        toy4.setWeb("https://www.toy-people.com/?p=48110");
        toy4.setImageUri("https://drive.google.com/file/d/177q5d9A8E6INh40in8zOB41gJHB5dMTy/view?usp=sharing");
        dataInstance.getToyDao().insert(toy4);

        toyInfo toy5 = new toyInfo();
        toy5.setName("EAA-098");
        toy5.setPrice(2390);
        toy5.setWeb("https://www.facebook.com/pg/BKTOYS000/photos/?tab=album&album_id=10157629786597277");
        toy5.setImageUri("https://drive.google.com/file/d/1_opj3MrC3CTFjC4BoiTMYCL9DAYKG0_z/view?usp=sharing");
        dataInstance.getToyDao().insert(toy5);
    }

    class testTask extends AsyncTask<String, Integer, Boolean> {

        Context mContext = null;

        testTask(Context context){
            mContext = context;
        }

        protected Boolean doInBackground(String... strings) {

            if(!isReady)
                saveData();

            printData();
            return true;
        }

        protected void onPostExecute(Boolean result) {

        }
    }
}
