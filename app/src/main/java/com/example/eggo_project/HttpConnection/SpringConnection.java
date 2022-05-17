package com.example.eggo_project.HttpConnection;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SpringConnection {

    String url = "http://192.168.0.8:8080/";

    public String HttpConnPOSTUser(String path, UserDTO userDTO) {
        String result = "";
        try {
            String page = url + path;
            URL urls = new URL(page);

            // conn으로 url connection을 open하기
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            // url 뒤에 붙여서 보낼 파라미터
            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                // 연결하는데 시간이 오래 걸리는 경우 Time out 설정
                conn.setConnectTimeout(10000);

                // 연결 방법 설정
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setUseCaches(false);
                conn.setDoOutput(true); // OutputStream으로 데이터를 넘겨주겠다고 설정
                conn.setDoInput(true); // InputStream으로 데이터를 읽겠다고 설정

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", userDTO.getUserId());
                jsonObject.put("name", userDTO.getName());
                jsonObject.put("email", userDTO.getEmail());
                jsonObject.put("password", userDTO.getPassword());

                OutputStream os = conn.getOutputStream();
                os.write(jsonObject.toString().getBytes());
                os.flush();

                // 결과값을 받아온다.
                // getResponseCode() : 연결 상태 확인
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byte[] byteData = baos.toByteArray();

                    JSONObject responseJSON = new JSONObject(new String(byteData));
                    System.out.println("name:"+responseJSON.get("Name"));
                    result = responseJSON.get("Message").toString();
                }
                conn.disconnect();
            }
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String HttpConnGETUser(String path) {
        String result = "";
        try {
            String page = url + path;
            URL urls = new URL(page);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            System.out.println("path:"+page);

            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                System.out.println("conn 성공");
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("conn 성공2");
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byte[] byteData = baos.toByteArray();

                    JSONObject responseJSON = new JSONObject(new String(byteData));
                    result = responseJSON.get("Message").toString();
                }
                conn.disconnect();
            }
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public JSONObject HttpConnGETBill(String path) {
        JSONObject responseJSON = new JSONObject();
        try {
            String page = url + path;
            URL urls = new URL(page);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                conn.setConnectTimeout(100000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byte[] byteData = baos.toByteArray();

                    responseJSON = new JSONObject(new String(byteData));
                }
                conn.disconnect();
            }
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseJSON;
    }

    public JSONObject HttpConnPOSTBill(String path, BillDTO billDTO) {
        JSONObject resultJSON = new JSONObject();
        try {
            String page = url + path;
            URL urls = new URL(page);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                conn.setConnectTimeout(1000000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", billDTO.getUserId());
                jsonObject.put("date", billDTO.getDate());
                jsonObject.put("totalFee", billDTO.getTotalFee());
                jsonObject.put("waterFee", billDTO.getWaterFee());
                jsonObject.put("waterUsage", billDTO.getWaterUsage());
                jsonObject.put("electricityFee", billDTO.getElectricityFee());
                jsonObject.put("electricityUsage", billDTO.getElectricityUsage());


                OutputStream os = conn.getOutputStream();
                os.write(jsonObject.toString().getBytes());
                os.flush();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byte[] byteData = baos.toByteArray();

                    resultJSON = new JSONObject(new String(byteData));
                }
                conn.disconnect();
            }
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultJSON;
    }

}