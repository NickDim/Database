package me.nickdim.intdatabase;

import com.sendgrid.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MailService {

    public static void main(String[] args) {
        MailService mail = new MailService();
        Database database = mail.database;
    }

    private Database database;
    private User[] users;

    private Email from;
    private SendGrid sg;
    private Request request;

    private Properties sgProperties;

    public MailService() {
        try {

            this.sgProperties = new Properties();
            sgProperties.load(new FileInputStream("sg.properties"));

            this.database = new Database();
            this.users = database.getUsers();

            this.from = new Email(sgProperties.getProperty("email"));
            this.sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

            this.request = new Request();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void mailer(User[] users) {
        try {
            for (User user : users) {

                Email to = new Email(user.getEmail());
                Content content = new Content("text/plain",
                    String.format("Hello %s %s,\n Thank you for participating " +
                            "in the NickDim database. You are currently #%s of %s members.",
                        user.getfName(), user.getlName(), user.getPK(), database.getFanCount()
                    )
                );

                Mail mail = new Mail(from, "NickDim " +
                    "Database Report", to, content);

                Attachments attachment = new Attachments();
                String data = sgProperties
                    .getProperty("base64Image").trim();
                attachment.setContent(data);
                attachment.setType("image/png");
                attachment.setFilename("nickdim-1.5.png");
                attachment.setDisposition("inline");
                attachment.setContentId("NickDim");
                mail.addAttachments(attachment);

                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                sg.api(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void singleMail(User user) {
        try {
            Content content = new Content("text/plain",
                String.format("Hello %s %s,\n" +
                        "Thank you for joining the NickDim database." +
                        " You are currently " +
                        "member %s of %s", user.getfName(),
                    user.getlName(), user.getPK()
                    , database.getFanCount()));

            Mail mail = new Mail(from, "Welcome " +
                "To The NickDim Database",
                new Email(user.getEmail()), content);

            Attachments attachment = new Attachments()
            attachment.setContent("NickDim");
            attachment.setType("image/png");
            attachment.setFilename("nickdim-1.5.png");
            attachment.setDisposition("inline");
            attachment.setContentId("NickDim");
            mail.addAttachments(attachment);

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            sg.api(request);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
