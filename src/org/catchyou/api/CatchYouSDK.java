package org.catchyou.api;

import android.os.AsyncTask;
import android.text.TextUtils;

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

import org.catchyou.api.models.ChatUser;
import org.catchyou.api.models.UserInfo;
import org.catchyou.api.models.ScanLog;
import org.catchyou.api.models.FbUserInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.serialization.JSONConvert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.catchyou.api.models.ChatData;
import org.json.serialization.DeserializeException;

public abstract class CatchYouSDK extends AsyncTask<Object, Integer, Object> {
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

    public HashMap<String, FbUserInfo> Scan(Object[] MacList) throws UnsupportedEncodingException {
        try {
            List<NameValuePair> Params = new ArrayList<NameValuePair>();

            String MacListString = TextUtils.join(",", MacList);
            
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

    public ArrayList<ChatUser> ChatUsers(int Index , int Length){
        try {
            List<NameValuePair> Params = new ArrayList<NameValuePair>();

            Params.add(new BasicNameValuePair("index", Integer.toString(Index)));
            Params.add(new BasicNameValuePair("length", Integer.toString(Length)));

            JSONArray JSONResult = RequestApi(Host + "chatroom/chatUsers", Params).getJSONArray("result");

            ArrayList<ChatUser> Result = new ArrayList<ChatUser>();

            for (int i = 0; i < JSONResult.length(); i++) {
                JSONObject obj = JSONResult.getJSONObject(i);
                Result.add((ChatUser) JSONConvert.deserialize(ChatUser.class, obj));
            }

            return Result;
        } catch (Exception ex) {
            Logger.getLogger(CatchYouSDK.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ChatData> ChatLogs(long baseTime,int Index,int Length) throws JSONException, IOException, DeserializeException{
        List<NameValuePair> Params = new ArrayList<NameValuePair>();

        Params.add(new BasicNameValuePair("basetime", Long.toString(baseTime)));
        Params.add(new BasicNameValuePair("index", Integer.toString(Index)));
        Params.add(new BasicNameValuePair("length", Integer.toString(Length)));

        JSONArray JSONResult = RequestApi(Host + "chatroom/ChatHistory", Params).getJSONArray("result");
        
        ArrayList<ChatData> Result = new ArrayList<ChatData>();

        for (int i = 0; i < JSONResult.length(); i++) {
            JSONObject obj = JSONResult.getJSONObject(i);
            Result.add((ChatData) JSONConvert.deserialize(ChatData.class, obj));
        }

        return Result;
    }
    
    @Override
    protected Object doInBackground(Object... params) {
        try {
            if (params[0].equals(LOGIN)) {
                return Login((String) params[1]);
            } else if (params[0].equals(USERMAPPING)) {
                return Scan((Object[]) params[1]);
            } else if (params[0].equals(SCANLOGS)) {
                return ScanLogs((int) params[1], (int) params[2]);
            } else if (params[0].equals(CHAT_HISTORY_USERLIST)) {
                return ChatUsers((int) params[1],(int)params[2]);
            } else if(params[0].equals(CHAT_HISTORY_MESSAGE)){
                return ChatLogs((long)params[1], (int)params[2], (int)params[3]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    protected abstract void onPostExecute(Object Result);
    
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
