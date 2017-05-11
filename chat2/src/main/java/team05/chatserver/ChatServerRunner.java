package team05.chatserver;

import team05.common.ChatConfiguration;

public class ChatServerRunner {

    public static void main(String args[]) {
        new ChatServer(ChatConfiguration.PORT);
    }

}
