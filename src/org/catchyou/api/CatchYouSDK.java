package org.catchyou.api;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import org.catchyou.api.models.UserInfo;
import org.catchyou.api.models.ScanLog;
import org.catchyou.api.models.FbUserInfo;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.serialization.JSONConvert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatchYouSDK extends AsyncTask<Object, Integer, Object> {
    private String Host = "http://test.gofa.tw/api/";
    public final String LOGIN = "LOGIN";
    public final String USERMAPPING = "USERMAPPING";
    public final String SCANLOGS = "SCANLOGS";
    public final String USERMAPPING_CHAT = "USERMAPPING_CHAT";
    public final String CHAT_HISTORY_MESSAGE = "CHAT_HISTORY_MESSAGE";
    public final String CHAT_HISTORY_USERLIST = "CHAT_HISTORY_USERLIST";

    public String Token;

    public CatchYouSDK(String Token) {
        this.Token = Token;
    }

    public UserInfo Login(String Mac) throws UnsupportedEncodingException, IOException, JSONException {
        try {
            List<NameValuePair> Params = new ArrayList<NameValuePair>();
            Params.add(new BasicNameValuePair("Mac", Mac));
            return (UserInfo) JSONConvert.deserialize(UserInfo.class, RequestApi(Host + "user/RegisterMac", Params).get("result"));
        } catch (Exception ex) {
            Logger.getLogger(CatchYouSDK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public HashMap<String, FbUserInfo> Scan(String[] MacList) throws UnsupportedEncodingException {
        try {
            List<NameValuePair> Params = new ArrayList<NameValuePair>();

            String MacListString = "";
            for (String Mac : MacList) {
                if (MacListString.length() != 0) MacListString += ",";
                MacListString += Mac;
            }

            Params.add(new BasicNameValuePair("MacList", MacListString));

            JSONObject JSONResult = RequestApi(Host + "scan/usermapping", Params).getJSONObject("result");

            HashMap<String, FbUserInfo> Result = new HashMap<String, FbUserInfo>();
            Iterator Keys = JSONResult.keys();
            while (Keys.hasNext()) {
                String Key = (String) Keys.next();
                Result.put(Key, (FbUserInfo) JSONConvert.deserialize(FbUserInfo.class, JSONResult.getJSONObject(Key)));
            }

            return Result;
        } catch (Exception ex) {
            Logger.getLogger(CatchYouSDK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ScanLog> ScanLogs(int Index, int Length) {
        try {
            List<NameValuePair> Params = new ArrayList<NameValuePair>();

            Params.add(new BasicNameValuePair("index", Integer.toString(Index)));
            Params.add(new BasicNameValuePair("length", Integer.toString(Length)));

            JSONArray JSONResult = RequestApi(Host + "scan/logList", Params).getJSONArray("result");

            ArrayList<ScanLog> Result = new ArrayList<ScanLog>();

            for (int i = 0; i < JSONResult.length(); i++) {
                JSONObject obj = JSONResult.getJSONObject(i);
                Result.add((ScanLog) JSONConvert.deserialize(ScanLog.class, obj));
            }

            return Result;
        } catch (Exception ex) {
            Logger.getLogger(CatchYouSDK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    protected Object doInBackground(Object... params) {
        try {
            if (params[0].equals(LOGIN)) {
                return Login((String) params[1]);
            } else if (params[0].equals(USERMAPPING)) {
                return Scan((String[]) params[1]);
            } else if (params[0].equals(SCANLOGS)) {
                return ScanLogs((int) params[1], (int) params[2]);
            } else if (params[0].equals(USERMAPPING_CHAT)) {

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private JSONObject RequestApi(String Url, List<NameValuePair> Params) throws JSONException, IOException {
        HttpClient Client = new DefaultHttpClient();
        HttpPost Post = new HttpPost(Url);
        Params.add(new BasicNameValuePair("token", Token));

        UrlEncodedFormEntity Form = new UrlEncodedFormEntity(Params, "utf-8");
        Post.setEntity(Form);

        HttpContext localContext = new BasicHttpContext();
        HttpResponse responsePOST = Client.execute(Post, localContext);
        HttpEntity resEntity = responsePOST.getEntity();

        JSONObject Result = new JSONObject(EntityUtils.toString(resEntity, "utf-8"));
        Client.getConnectionManager().shutdown();

        return Result;
    }
}
