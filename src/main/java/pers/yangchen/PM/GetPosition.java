package pers.yangchen.PM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Created by yangchen on 16-4-8.
 */
public class GetPosition {
    public void getMethod() throws IOException {
        String filePath = "/home/yangchen/ycdoc/citypositon.csv";
        InputStream is = new FileInputStream(filePath);
        String lineW;
        BufferedReader readerW = new BufferedReader(new InputStreamReader(is));
        lineW = readerW.readLine();
        while (lineW != null) {
            try {
                String[] temp = lineW.split(",");
                String pos = temp[0]+temp[1];
                String getURL = "http://api.map.baidu.com/geocoder/v2/?address="+ pos +"&output=json&ak=X2FGKdTqlfoXvwXhdz8UYEko";
                URL getUrl = new URL(getURL);
                HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("Content-Type","application/json)");
                connection.setRequestMethod("GET");
                connection.connect();
                if(connection.getResponseCode()==200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String lineI;
                    String result = "";
                    while ((lineI = reader.readLine()) != null) {
                        result = result + lineI;
                        System.out.println(lineI);
                    }
                    JSONObject positionInfo = new JSONObject(result);
                    JSONObject resultInfo = positionInfo.getJSONObject("result");
                    JSONObject locationInfo = resultInfo.getJSONObject("location");
                    double lng = locationInfo.getDouble("lng");
                    double lat = locationInfo.getDouble("lat");
                    reader.close();
                    connection.disconnect();

                    String content = lineW + "," + lng + "," + lat;
                    String str = new String();
                    String s1 = new String();
                    String newFilePath = "/home/yangchen/ycdoc/cpll";
                    try {
                        File f = new File(newFilePath);
                        if (f.exists()) {
                            System.out.print("文件存在");
                        } else {
                            System.out.print("文件不存在");
                            f.createNewFile();
                        }
                        BufferedReader input = new BufferedReader(new FileReader(f));

                        while ((str = input.readLine()) != null) {
                            s1 += str + "\n";
                        }
                        System.out.println(s1);
                        input.close();
                        s1 += content;

                        BufferedWriter output = new BufferedWriter(new FileWriter(f));
                        output.write(s1);
                        output.close();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lineW = readerW.readLine();
        }
        readerW.close();
        is.close();
    }

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了

        }
        reader.close();
        is.close();
    }

    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        GetPosition.readToBuffer(sb, filePath);
        return sb.toString();
    }


    public static void contentToTxt(String filePath, String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
