package org.catchyou.api;

import org.catchyou.api.models.ChatData;
import org.catchyou.api.models.ChatType;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.serialization.DeserializeException;
import org.json.serialization.JSONConvert;
import org.json.serialization.SerializeException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by XuPeiYao on 2015/10/14.
 */
public abstract class ChatWebSocket extends WebSocketClient {

    public ChatWebSocket(String Token) throws URISyntaxException {
        super(new URI("ws://test.gofa.tw/api/chatroom/socket?token=" + Token),new Draft_17());
    }
    
    Timer timer = null;
    int timeout = 50;
    @Override
    public void onOpen(ServerHandshake handshakedata){
        startPingTimer();
        timeout = 50;
        onConnect(handshakedata);
    }
    
    @Override
    public void onClose(int code, String reason, boolean remote){
        timer.cancel();
    }
    
    public abstract void onConnect(ServerHandshake handshakedata);

    
    public void startPingTimer(){
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeout --;
                if(timeout >= 0)return;
                ChatData ping = new ChatData();
                ping.type= ChatType.Ping;
                send(ping);
            }
        }, 0,1000);
    }
    
    @Override
    public abstract void onError(Exception ex);

    @Override
    public void onMessage(String message) {
        try {
            timeout--;
            onMessage(JSONConvert.deserialize(ChatData.class,new JSONObject(message)));
        } catch (DeserializeException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void onMessage(ChatData data);

    public void sendTyping(String TargetUId) {
        ChatData data = new ChatData();
        data.type = ChatType.Typing;
        data.targetUId = TargetUId;
        send(data);
    }

    public void sendRequestInfo(String TargetUId,Object Content) {
        ChatData data = new ChatData();
        data.type = ChatType.RequestInfo;
        data.targetUId = TargetUId;
        data.content = Content;
        send(data);
    }

    public void sendText(String TargetUId,String Message) {
        ChatData data = new ChatData();
        data.type = ChatType.Text;
        data.targetUId = TargetUId;
        data.content = Message;
        send(data);
    }

    public void sendHookStatus(String TargetUId) {
        ChatData data = new ChatData();
        data.type = ChatType.HookStatus;
        data.targetUId = TargetUId;
        send(data);
    }

    public void sendUnhookStatus(String TargetUId) {
        ChatData data = new ChatData();
        data.type = ChatType.UnhookStatus;
        data.targetUId = TargetUId;
        send(data);
    }

    public void sendClearHook() {
        ChatData data = new ChatData();
        data.type = ChatType.ClearHook;
        send(data);
    }


    public void send(ChatData data) {
        try{
            this.send(JSONConvert.serialize(data).toString());
            timeout = 50;
        }catch(Exception e){
            
        }
    }
}
