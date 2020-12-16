package com.example.myapplication;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostingActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    //PersonalData personalData = new PersonalData();
    private ImageView imageView;
    private EditText TextMemo;
    private EditText Textlocation;
    private EditText Texttitle;
    private Spinner spinner;
    private RatingBar ratingBar;
    private float ratingscore;
    private String Image;//이미지 저장값.
    private Bitmap img;
    public String id2;
    public  String address;
    public String age;
    public String name;
    public String password;
    public String phone;

    private static String IP_ADDRESS = "220.69.208.116";
    private static String TAG = "phptest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        //값들 불러오기.
//죽어도 지켜야 될거
        Intent intent = new Intent(getIntent());
        id2 = intent.getStringExtra("id");
        age = intent.getStringExtra("age");
        name = intent.getStringExtra("name");
        password = intent.getStringExtra("password");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("loc");
//


        TextMemo = (EditText)findViewById(R.id.memo);
        Textlocation = (EditText)findViewById(R.id.title2);
        Textlocation.setText(address);
        Texttitle = (EditText)findViewById(R.id.title);
        spinner = (Spinner)findViewById(R.id.spinner);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingscore = rating;
            }

        });
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        Button button = (Button) findViewById(R.id.post); //등록 버튼
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postClick(v);
            }
        });
        Button button1 = (Button) findViewById(R.id.cancel);//취소 버튼
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextMemo.setText("");
                Textlocation.setText("");
                Texttitle.setText("");
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                intent.putExtra("id",id2);
                intent.putExtra("age",age);
                intent.putExtra("name",name);
                intent.putExtra("password",password);
                intent.putExtra("phone",phone);
                intent.putExtra("loc",address);

                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //이미지 선택
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    //img = resize(img);
                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    imageView.setImageBitmap(img);
                    BitMapToString(img); // 비트맵을 String으로
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(),"이미지를 등록하십시오.",Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void postClick (View v){ //게시물 등록 버튼
        AlertDialog.Builder builder = new AlertDialog.Builder(PostingActivity.this);
        builder.setTitle("게시물 등록");
        builder.setMessage("게시물을 등록하시겠습니까?");
        builder.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getBaseContext(),"등록",Toast.LENGTH_SHORT).show();
                String weather = spinner.getSelectedItem().toString();

                String memo = TextMemo.getText().toString();
                String location = Textlocation.getText().toString();
                String title = Texttitle.getText().toString();
                String score = Float.toString(ratingscore);
                Textlocation.setText(address);

                System.out.println();
                System.out.println(id2 + " " + weather + " " + memo + " " + location + " " + title + " " + score);

                InsertDate task = new InsertDate();
                task.execute("http://" + IP_ADDRESS + "/posting.php", id2, weather, memo, location, title, score);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getBaseContext(),"취소",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }
    //bitmap -> string
    public void BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);    //bitmap compress
        byte[] arr = baos.toByteArray();
        String image = Base64.encodeToString(arr, Base64.DEFAULT);
        Image = "";
        try {
            Image = "&image=" + URLEncoder.encode(image, "utf-8"); //UTF8로 변환.
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    class InsertDate extends AsyncTask<String, Void, String> { //첫번째는 doln~ 의 인수 타입, 두번째는 onPro~ 의 인수 타입, 세번째는 onPost~의 인수 타입.
        ProgressDialog progressDialog; //로딩중 화면 띄워주는 역할.

        @Override
        protected void onPreExecute() { //로딩 화면
            super.onPreExecute();

            progressDialog = ProgressDialog.show(PostingActivity.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();//로딩창 종료
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            TextMemo.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String) params[0];
            String id = (String) params[1];
            String weather = (String) params[2];
            String memo = (String) params[3];
            String location = (String) params[4];
            String title = (String) params[5];
            String score = (String) params[6];
            //Image 도 앞에 & 이거 이미 있다.
            String postParameters = "id=" + id + "&weather=" + weather + "&memo=" + memo + "&location=" + location + "&title=" + title + "&score=" + score;// + Image;
            Log.d(TAG, "postParameters" + postParameters);
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST"); //POST 방식인거 명시
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();
                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
        }
    }
}