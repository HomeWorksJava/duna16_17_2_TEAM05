package team05.chatclient;

import team05.common.ChatConfiguration;

public class CharClientRunner {

    public static void main(String args[]) {
        new ChatClient(ChatConfiguration.HOST, ChatConfiguration.PORT);
    }

}
