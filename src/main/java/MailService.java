import com.sendgrid.*;
import java.io.IOException;

public class MailService {

    public static void main(String[] args) {
        MailService mail = new MailService();
        Database database = mail.database;

        mail.mailer(mail.users);
    }

    private Database database;
    private User[] users;

    private Email from;
    private String subject;
    private Mail mail;

    private SendGrid sg;
    private Request request;

    public MailService() {

        this.database = new Database();
        this.users = database.getUsers();

        this.from = new Email("nikolad21889@isd273.org");
        this.subject = "Welcome To My Database";
        this.sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        this.request = new Request();
    }

    public void mailer(User[] users) {
        try {
            for (User user : users) {
                Email to = new Email(user.getEmail());

                Content content = new Content("text/plain",
                    String.format("Hello %s %s,\n Welcome to the " +
                            "NickDim database. You are currently #%s of %s members.",
                        user.getfName(), user.getlName(), user.getPK(), database.getFanCount()
                    )
                );

                this.mail = new Mail(from, subject, to, content);

                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                sg.api(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}