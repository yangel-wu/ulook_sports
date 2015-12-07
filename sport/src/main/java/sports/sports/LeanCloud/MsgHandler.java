package sports.sports.LeanCloud;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;

import tv.liangzi.quantum.utils.LogUtils;

public class MsgHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        // 请按自己需求改写
        LogUtils.e("leancloud_________","wer");
        int a=message.getMessageType();
        message.getContent();
        switch(message.getMessageType()) {
            case AVIMMessageType.TEXT_MESSAGE_TYPE:
                break;
        }
    }

    @Override
    public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        // 请加入你自己需要的逻辑...
        LogUtils.e("leancloud_________","wer");
    }
}


