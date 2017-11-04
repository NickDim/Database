import com.sendgrid.*;

import java.io.IOException;
import java.util.ArrayList;

public class MailService {

    public static void main(String[] args) {
        MailService mail = new MailService();
        Database database = mail.database;
        ArrayList<String> toEmails = database.getEmails();

        mail.mailer(toEmails);
    }
    private Database database;

    private Email from;
    private String subject;
    private Content content;

    private SendGrid sg;
    private Request request;

    public MailService() {
        System.out.println(System.getenv("SENDGRID_API_KEY"));
        this.from = new Email("nikolad21889@isd273.org");
        this.database = new Database();
        this.subject = "Hello Email world";
        this.content = new Content("text/plain", "I can't believe this works");
        this.sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        this.request = new Request();
    }

    public void mailer(ArrayList<String> toEmails) {
        try {

            for(int i = 0; i < toEmails.size(); i++) {

                Email toEmail = new Email(toEmails.get(1));
                Mail mail = new Mail(from, subject, toEmail, content);

                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

            }

            sg.api(request);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}