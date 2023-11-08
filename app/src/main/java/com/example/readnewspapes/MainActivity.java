package com.example.readnewspapes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {
    ListView lvTieuDe;
    ArrayList<String> arrayLink;
    ArrayList<ItemTinTuc> arrayitemtintuc;
    TinTucAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTieuDe = (ListView) findViewById(R.id.listViewTitle);
        arrayLink = new ArrayList<String>();
        arrayitemtintuc = new ArrayList<ItemTinTuc>();
        adapter = new TinTucAdapter(this, R.layout.item_tintuc, arrayitemtintuc);
        lvTieuDe.setAdapter(adapter);


        new ReadRSS().execute("https://vnexpress.net/rss/the-thao.rss");

        lvTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                if (i >= 0 && i < arrayLink.size()) {
                    Intent intent = new Intent(MainActivity.this, NewActivity.class);
                    intent.putExtra("linktintuc", arrayLink.get(i));
                    startActivity(intent);
                } else {
                    // Xử lý trường hợp lỗi, ví dụ: thông báo cho người dùng về lỗi.
                    Toast.makeText(MainActivity.this, "Lỗi: Không tìm thấy liên kết.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private class ReadRSS extends AsyncTask<String, Void, ArrayList<ItemTinTuc>> {
        @Override
        protected ArrayList<ItemTinTuc> doInBackground(String... strings) {
            ArrayList<ItemTinTuc> items = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder content = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();

                XMLDOMParser parser = new XMLDOMParser();
                Document document = parser.getDocument(content.toString());
                NodeList nodeList = document.getElementsByTagName("item");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    String tieude = parser.getValue(element, "title");
                    String duonglienket = parser.getValue(element, "link");
                    String ngay = parser.getValue(element, "pubDate");
                    arrayLink.add(duonglienket);

                    Element descriptionElement = (Element) element.getElementsByTagName("description").item(0);
                    String mota = descriptionElement.getTextContent().trim();

                    // Extract the image URL from the description using regular expression
                    String imageRegex = "<img src=\"(.*?)\"";
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(imageRegex);
                    java.util.regex.Matcher matcher = pattern.matcher(mota);
                    String linkanh = "";
                    if (matcher.find()) {
                        linkanh = matcher.group(1);
                    }

                    // Load the image using Picasso
                    Bitmap hinhanh = null;
                    try {
                        hinhanh = Picasso.get().load(linkanh).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mota = mota.replaceAll("<.*?>", "");

                    items.add(new ItemTinTuc(hinhanh, tieude, mota, ngay));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemTinTuc> items) {
            super.onPostExecute(items);

            if (items != null) {
                arrayitemtintuc.addAll(items);
                adapter.notifyDataSetChanged();
            } else {
                // Xử lý lỗi (nếu cần)
            }
        }
    }



}