import com.sendgrid.*;
import java.io.IOException;

public class MailService {

    public static void main(String[] args) {
        MailService mail = new MailService();
        Database database = mail.database;

        String[] toEmails = database.getEmails();
        String[] fNames = database.getfNames();
        String[] lNames = database.getlNames();

        mail.mailer(toEmails, fNames, lNames);
    }

    private Database database;

    private Email from;
    private String subject;
    private Mail mail;

    private SendGrid sg;
    private Request request;

    public MailService() {

        this.from = new Email("nikolad21889@isd273.org");
        this.database = new Database();
        this.subject = "Welcome to my database";
        this.sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        this.request = new Request();
    }

    public void mailer(String[] toEmails, String[] fNames, String[] lNames) {
        try {

            for(int i = 0; i < toEmails.length; i++) {

                Email to = new Email(toEmails[i]);
                Content content = new Content("text/plain", "Hello, " + fNames[i]
                    + " " + lNames[i] + ", welcome to the NickDim database. You are " +
                    "currently one of " + database.getFanCount() + " database members.");

                this.mail = new Mail(from, subject, to, content);

                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                sg.api(request);

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mailer(User[] users) {
        try {
            for (User user : users) {
                Email to = new Email(user.getEmail());

                Content content = new Content("text/plain",
                    String.format("Hello %s %s,\n Welcome to the " +
                            "NickDim database. You are currently one of %s members.",
                        user.getfName(), user.getlName(), database.getFanCount()
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