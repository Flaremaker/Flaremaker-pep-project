package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    // Get all Messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Get Message By id
    public Message getMessageById(int id) {
        if(messageDAO.getMessageById(id) == null){
            return null;
        }
        return messageDAO.getMessageById(id);
    }

    // Get Message By posted_by
    public List<Message> getAccountMessage(int id) {
        return messageDAO.getMessagesByPostedBy(id);

    }

    // Create new messages
    public Message createMessage(Message message) {
        if (message.message_text.isBlank() || message.message_text.equals(null)) {
            return null;
        }
        if (message.message_text.length() > 255 || accountDAO.getAccountById(message.getPosted_by())) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }

    }

    // Patch of the message service
    public Message patchMessage(int id, String message) {
        if (message.equals(null) || (message.isBlank())) {
            return null;
        }  if(message.length() > 255){
            return null;
        }
        else {
            return messageDAO.updateMessage(id, message);
        }

    }

    // Deletion of a message handler
    public Message deleteMessage(int id) {

        return messageDAO.deleteMessage(id);
    }
}