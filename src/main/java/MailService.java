import com.sendgrid.*;

import java.io.IOException;
import java.util.ArrayList;

public class MailService {

    public static void main(String[] args) {
        MailService mail = new MailService();
        Database database = mail.database;

        ArrayList<String> toEmails = database.getEmails();
        ArrayList<String> fNames = database.getfNames();
        ArrayList<String> lNames = database.getlNames();

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

    public void mailer(ArrayList<String> toEmails, ArrayList<String> fNames, ArrayList<String> lNames) {
        try {

            for(int i = 0; i < toEmails.size(); i++) {

                Email to = new Email(toEmails.get(i));
                Content content = new Content("text/plain", "Hello, " + fNames.get(i)
                    + " " + lNames.get(i) + ", welcome to the NickDim database.");

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
}