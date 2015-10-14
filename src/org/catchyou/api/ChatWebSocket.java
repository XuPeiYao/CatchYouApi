package org.catchyou.api;

import java.net.URI;
import java.net.URISyntaxException;
import org.catchyou.api.models.ChatData;
import org.catchyou.api.models.ChatType;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.serialization.*;

public abstract class ChatWebSocket extends WebSocketClient{
    public ChatWebSocket(String Token) throws URISyntaxException {
        super(new URI("ws://test.gofa.tw/api/chatroom/socket?token=" + Token),new Draft_17());
    }

    @Override
    public abstract void onOpen(ServerHandshake handshakedata);

    @Override
    public void onClose(int code, String reason, boolean remote){
        this.connect();//re open!
    }

    @Override
    public abstract void onError(Exception ex);

    @Override
    public void onMessage(String message) {
        try {
            onMessage(JSONConvert.deserialize(ChatData.class,new JSONObject(message)));
        } catch (DeserializeException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void onMessage(ChatData data);

    public void sendTyping(String TargetUId) throws SerializeException {
        ChatData data = new ChatData();
        data.type = ChatType.Typing;
        data.targetUId = TargetUId;
        send(data);
    }

    public void sendRequestInfo(String TargetUId,Object Content) throws SerializeException {
        ChatData data = new ChatData();
        data.type = ChatType.RequestInfo;
        data.targetUId = TargetUId;
        data.content = true;
        send(data);
    }

    public void sendText(String TargetUId,String Message) throws SerializeException {
        ChatData data = new ChatData();
        data.type = ChatType.Text;
        data.targetUId = TargetUId;
        data.content = Message;
        send(data);
    }

    public void sendHookStatus(String TargetUId) throws SerializeException {
        ChatData data = new ChatData();
        data.type = ChatType.HookStatus;
        data.targetUId = TargetUId;
        send(data);
    }

    public void sendUnhookStatus(String TargetUId) throws SerializeException {
        ChatData data = new ChatData();
        data.type = ChatType.UnhookStatus;
        data.targetUId = TargetUId;
        send(data);
    }

    public void sendClearHook() throws SerializeException {
        ChatData data = new ChatData();
        data.type = ChatType.ClearHook;
        send(data);
    }


    public void send(ChatData data) throws SerializeException {
        this.send(JSONConvert.serialize(data).toString());
    }
}
