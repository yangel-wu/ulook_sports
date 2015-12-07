package sports.sports.LeanCloud;

import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMFileMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import de.greenrobot.event.EventBus;
import tv.liangzi.quantum.base.Constants;
import tv.liangzi.quantum.event.ImTypeMessageEvent;
import tv.liangzi.quantum.utils.LogUtils;

/**
 * Created by zhangxiaobo on 15/4/20.
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

  private Context context;

  public MessageHandler(Context context) {
    this.context = context;
  }

  @Override
  public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    LogUtils.e("收到消息:" + message.getContent(), ", msgId:" + message.getMessageId());
    switch (message.getMessageType()){
                  case AVIMMessageType.TEXT_MESSAGE_TYPE:
                    AVIMTextMessage textMsg = (AVIMTextMessage)message;
                    sendEvent(message, conversation);
                    LogUtils.e("收到文本消息:" + textMsg.getText(), ", msgId:" + textMsg.getMessageId());
                    break;

                  case AVIMMessageType.FILE_MESSAGE_TYPE:
                    AVIMFileMessage fileMsg = (AVIMFileMessage)message;
                    LogUtils.e("收到FILE消息:" + fileMsg.getText(), ", msgId:" + fileMsg.getMessageId());
                    break;

                  case AVIMMessageType.VIDEO_MESSAGE_TYPE:
                    AVIMImageMessage imageMsg = (AVIMImageMessage)message;
                    LogUtils.e("收到VIDEO消息:" + imageMsg.getText(), ", msgId:" + imageMsg.getMessageId());
                    break;
                }
  }

  /**
   * 因为没有 db，所以暂时先把消息广播出去，由接收方自己处理
   * 稍后应该加入 db
   * @param message
   * @param conversation
   */
  private void sendEvent(AVIMTypedMessage message, AVIMConversation conversation) {
    ImTypeMessageEvent event = new ImTypeMessageEvent();
    event.message = message;
    event.conversation = conversation;
    EventBus.getDefault().post(event);
  }

  private void sendNotification(AVIMTypedMessage message, AVIMConversation conversation) {
    String notificationContent = message instanceof AVIMTextMessage ?
      ((AVIMTextMessage)message).getText() : "123";

    Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
    intent.putExtra(Constants.CONVERSATION_ID, conversation.getConversationId());
    intent.putExtra(Constants.MEMBER_ID, message.getFrom());
    NotificationUtils.showNotification(context, "", notificationContent, null, intent);
  }
}
