import com.sendgrid.*;
import java.io.IOException;

public class MailService {

    public static void main(String[] args) {
        MailService mail = new MailService();
        Database database = mail.database;
        String[] toEmails = database.getEmails();

        mail.mailer(toEmails);
    }
    private Database database;

    private Email from;
    private String subject;
    private Content content;

    private SendGrid sg;
    private Request request;

    public MailService() {

        this.from = new Email("nikolad21889@isd273.org");
        this.database = new Database();
        this.subject = "Hello Email world";
        this.content = new Content("text/plain", "I can't believe this works");
        sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        request = new Request();
    }

    public void mailer(String[] toEmails) {
        try {

            for(String toAddress : toEmails) {

                Email toEmail = new Email(toAddress);
                Mail mail = new Mail(from, subject, toEmail, content);

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
}