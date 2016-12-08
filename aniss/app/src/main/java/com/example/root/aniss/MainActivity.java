package com.example.root.aniss;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button confirm;
    private EditText urlText;
    private Button showLogs;
    private String html;
    private TextView textView;
    private String codeHasehd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


        confirm = (Button) findViewById(R.id.btnConfirm);

        showLogs = (Button) findViewById(R.id.btnShowLogs);
        urlText = (EditText) findViewById(R.id.etUrl);


        textView = (TextView) findViewById(R.id.ivTextView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        showLogs.setOnClickListener(this);
        confirm.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){


            case R.id.btnConfirm:


                if (urlText.getText().toString().length() == 0){

                    Toast.makeText(this.getApplicationContext(), "Please select a url", Toast.LENGTH_LONG).show();

                }else {

                    String url = urlText.getText().toString();


                    try {

                        html = Jsoup.connect(url).get().html();

                        if (html.contains("<script>") && html.contains("function sendInfo(info)")){

                            Toast.makeText(this.getApplicationContext(), "Warning!!! this url is suspicious!!", Toast.LENGTH_LONG).show();

                            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

                            messageDigest.update(html.getBytes());

                            byte[] byteHtml = messageDigest.digest();

                            StringBuffer stringBuffer = new StringBuffer();

                            for (int i = 0; i < byteHtml.length; i++){
                                stringBuffer.append(Integer.toString((byteHtml[i] & 0xff) + 0x100, 16).substring(1));

                            }

                            codeHasehd = stringBuffer.toString();
                            System.out.println(html);
                        }

                    }catch(Exception ex){
                        ex.printStackTrace();
                    }


                }

                break;


            case R.id.btnShowLogs:


                if (html.length() != 0){

                    textView.setText(codeHasehd);

                }else{

                    Toast.makeText(this.getApplicationContext(), "Nothing to show!!!", Toast.LENGTH_LONG).show();
                }




        }

    }
}
