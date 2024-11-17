package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * 
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesHandlerByAccountId);
        app.get("/messages", this::getAllMessagesHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler); 
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{message_id}",this::deleteMessageHandler); 
        app.post("/login", this::loginMessageHandler);
        app.post("/register", this::registerMessageHandler);
        return app;
    }

 
    //Get all handler for messages Response should be 200
    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
        ctx.status(200);
    }
    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = (messageService.getMessageById(message_id));
        if(message != null){
            ctx.status(200);
            ctx.json(messageService.getMessageById(message_id));
        }else
        ctx.status(200);
    }
    //Get handler for account id to show all messages
    private void getAllMessagesHandlerByAccountId(Context ctx) throws JsonProcessingException{
        int posted_by= Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAccountMessage(posted_by));
        ctx.status(200);
    }
    //Creation post handler for messages
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if(addedMessage==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedMessage));
            
        }
    }
    //Update handler for messages
    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageClass = mapper.readValue(ctx.body(), Message.class);
        String message = messageClass.getMessage_text();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.patchMessage(message_id, message);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
            ctx.status(200);
        }

    }
    //Deletion handler for messags
    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message addedMessage = messageService.deleteMessage(message_id);
        System.out.println(addedMessage);
        if(addedMessage == null){
            ctx.status(200); 
        }else
        ctx.json(mapper.writeValueAsString(addedMessage));
        ctx.status(200); 
        
      
        
    }
    //register post handler for Account
    private void registerMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if(addedAccount==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }//login post handler for Account
    private void loginMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.loginAccount(account);
        if(addedAccount==null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }
}