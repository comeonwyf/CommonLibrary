package com.example.serialportlibrary.message;

import com.example.serialportlibrary.util.TimeUtil;

/**
 * 发送的日志
 */

public class SendMessage implements IMessage {

    private String command;
    private String message;

    public SendMessage(String command) {
        this.command = command;
        this.message = TimeUtil.currentTime() + "    发送命令：" + command;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isToSend() {
        return true;
    }
}