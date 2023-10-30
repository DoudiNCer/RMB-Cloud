package org.sipc.controlserver.controller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint("/websocket/{hwId}")  // 接口路径 ws://localhost:8087/webSocket/userId;
public class WebSockerController {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 垃圾桶硬件ID
     */
    private String hwId;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    private static CopyOnWriteArraySet<WebSockerController> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value="hwId")String hwId) {
        try {
            this.session = session;
            this.hwId = hwId;
            webSockets.add(this);
            sessionPool.put(hwId, session);
            log.info("[WebSocket]New Connect, now the connections: "+webSockets.size());
        } catch (Exception ignored) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.hwId);
            log.info("[WebSocket]Close Connect, now the connections: "+webSockets.size());
        } catch (Exception ignored) {
        }
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 收到的消息
     */
    @OnMessage
    public void onMessage(String message) {
        /*ObjectMapper objectMapper = new ObjectMapper();
        WebSocketMessage msg = null;
        try {
             msg = objectMapper.readValue(message, WebSocketMessage.class);
        } catch (JsonProcessingException e) {
            log.warn("[WebSocket]ObjectMapper read Error, origin message: " +  message);
            e.printStackTrace();
        }
        if (msg == null){
            return;
        }
        // 发送的是图片还是经纬度
        if (msg.getPhoto() == null){
            hardwareService.updateLocation(new UpdateLocationParam(msg.getLongitude(), msg.getLatitude(), hwId));
        } else {
            byte[] decode = Base64.getDecoder().decode(msg.getPhoto());
            MultipartFile file = null;
            try {
                file = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), new ByteArrayInputStream(decode));
            } catch (IOException ignored) {

            }
            if (file == null){
                return;
            }
            CommonResult<UploadResult> identify = null;
            try {
                identify = garbageController.identify(new Integer(hwId), null, file);
            } catch (Exception ignored) {

            }
            if (identify == null || !Objects.equals(identify.getCode(), ResultEnum.SUCCESS.getCode()) || identify.getData() == null){
                return;
            }
            Set<Integer> result = new HashSet<>(4);
            for (String s : identify.getData().getList()) {
                if (garbageType.get(s) != null) {
                    result.add(garbageType.get(s));
                }
            }
            StringBuilder sb = new StringBuilder();
            for (Object o : result.toArray()) {
                sb.append(o);
            }
            session.getAsyncRemote().sendText(sb.toString());
        }*/
    }

    /** 发送错误时的处理
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("[WebSocket]Error: "+error.getMessage());
        error.printStackTrace();
    }

    public void sendOneMessage(String hwId, String message) {
        Session session = sessionPool.get(hwId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("[WebSocket]Message: "+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
