package com.miniware.blog.api.chat.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatChannelType {

    ROOM("chat:room:", "/sub/chat/room/");    //채팅방 ID 기준 실시간 구독

    private final String redisPrefix; // Redis Pub/Sub 채널 prefix
    private final String wsPrefix;    // WebSocket 구독 경로 prefix

    // Redis 채널 이름 생성 (방 ID 기반)
    public String getRedisChannel(Long roomId) {
        return redisPrefix + roomId;
    }

    // WebSocket 구독 경로 생성 (방 ID 기반)
    public String getWebSocketPath(Long roomId) {
        return wsPrefix + roomId;
    }

    // 채널 이름에서 roomId 추출
    public Long extractRoomId(String channel) {
        return Long.parseLong(channel.substring(redisPrefix.length()));
    }

    // 채널 이름이 해당 타입에 속하는지 확인
    public boolean isChannelOfType(String channel) {
        return channel.startsWith(redisPrefix);
    }

    // Redis 채널 패턴 생성 (구독용)
    public String getChannelPattern() {
        return redisPrefix + "*";
    }

}
