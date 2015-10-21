package org.catchyou.api;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpProvider;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketListener;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;
import java.net.URI;
import java.net.URISyntaxException;
import org.catchyou.api.models.ChatData;
import org.catchyou.api.models.ChatType;
import org.json.JSONObject;
import org.json.serialization.JSONConvert;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by XuPeiYao on 2015/10/14.
 */
public abstract class ChatWebSocket implements WebSocketTextListener{
    public final Object RequestUserData = null;
    public final boolean RequestUserInfoOK = true;
    
    WebSocket websocket;
    String token;

    public ChatWebSocket(String Token) {
        this.token = Token;
    }

    @Override
    public void onMessage(String string) {
        try {
            ChatData Data = JSONConvert.deserialize(ChatData.class,new JSONObject(string));

            switch(Data.type){
                case Status:
                    this.onReceiveStatus(Data);
                    break;
                case RequestInfo:
                    this.onReceiveRequestInfo(Data);
                    break;
                case Text:
                    this.onReceiveText(Data);
                    break;
                case Typing:
                    this.onReceiveTyping(Data);
                    break;
            }
        } catch (Exception ex) {
            
        }
    }
    
    @Override
    public void onOpen(WebSocket ws) {
        this.onConnect();
    }

    @Override
    public void onClose(WebSocket ws) {
        this.onDisconnect();
    }

    @Override
    public abstract void onError(Throwable thrwbl);
    
    /**
     * 當成功與CatchYou聊天API連線成功事件
     */
    public abstract void onConnect();

    /**
     * 與CatchYou聊天API離線事件
     */
    public abstract void onDisconnect();

    /**
     * 當WebSocket接收到個人資訊相關資訊時
     * @param data  接收資料
     */
    public abstract void onReceiveRequestInfo(ChatData data);

    /**
     * 當WebSocket接收到其他使用者上下線資訊
     * @param data 接收資料
     */
    public abstract void onReceiveStatus(ChatData data);

    /**
     * 當WebSocket接收到其他使用者送出的文字資料
     * @param data 接收資料
     */
    public abstract void onReceiveText(ChatData data);

    /**
     * 當WebSocket接收到其他使用者打字中通知
     * @param data 接收資料
     */
    public abstract void onReceiveTyping(ChatData data);

    /**
     * 對指定目標使用者送出打字中通知
     * @param TargetUId 目標使用者UId
     */
    public void sendTyping(String TargetUId) {
        ChatData data = new ChatData();
        data.type = ChatType.Typing;
        data.targetUId = TargetUId;
        this.send(data);
    }

    /**
     * 對伺服器或只用者送出資訊要求訊息
     * @param TargetUId 要求類型(RequestUserData,RequestUserInfoOK)
     */
    public void sendRequestInfo(String TargetUId,Object Content) {
        ChatData data = new ChatData();
        data.type = ChatType.RequestInfo;
        data.targetUId = TargetUId;
        data.content = Content;
        this.send(data);
    }
    
    /**
     * 對指定目標使用者送出文字訊息
     * @param TargetUId 目標使用者UId
     * @param Message 訊息內容
     */
    public void sendText(String TargetUId,String Message) {
        ChatData data = new ChatData();
        data.type = ChatType.Text;
        data.targetUId = TargetUId;
        data.content = Message;
        this.send(data);
    }

    /**
     * 監聽指定使用者上下線狀態
     * @param TargetUId 目標使用者UId
     */
    public void sendHookStatus(String TargetUId) {
        ChatData data = new ChatData();
        data.type = ChatType.HookStatus;
        data.targetUId = TargetUId;
        this.send(data);
    }

    /**
     * 解除監聽指定使用者上下線狀態
     * @param TargetUId  目標使用者UId
     */
    public void sendUnhookStatus(String TargetUId) {
        ChatData data = new ChatData();
        data.type = ChatType.UnhookStatus;
        data.targetUId = TargetUId;
        this.send(data);
    }

    /**
     * 清除所有監聽指定使用者上下線狀態
     */
    public void sendClearHook() {
        ChatData data = new ChatData();
        data.type = ChatType.ClearHook;
        this.send(data);
    }

    /**
     * 送出空訊息以防逾時
     */
    public void sendPing() {
        ChatData data = new ChatData();
        data.type = ChatType.Ping;
        this.send(data);
    }

    /**
     * 使用訊息模型送出訊息
     * @param data 訊息模型實體
     */
    public void send(ChatData data) {
        try{
            this.websocket.sendMessage(JSONConvert.serialize(data).toString());
        }catch(Exception ex){
            
        }
    }
    
    /**
     * 檢查WebSocket目前是否開啟中
     */
    public boolean isOpen(){
        return this.websocket.isOpen();
    }
    
    /**
     * 開啟連線
     */
    public void open() throws InterruptedException, ExecutionException{
        AsyncHttpClient client = new AsyncHttpClient();   
        this.websocket = (WebSocket) client.prepareGet("ws://test.gofa.tw/api/chatroom/socket?token=" + token).execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(this).build()).get();
    }
    
    /**
     * 關閉連線
     */
    public void close(){
        this.websocket.close();
    }
}