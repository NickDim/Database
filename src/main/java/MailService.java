import com.sendgrid.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

public class MailService {

    public static void main(String[] args) throws SQLException {
        MailService mail = new MailService();
        //Database database = mail.database;
        String[] toEmails = {"nikolad21889@isd273.org", "sc.richik@gmail.com"};

        String mailReturn = mail.mailer(toEmails);
        mail.setEmailHistory(mailReturn);
        String history = mail.getEmailHistory();

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

    public String mailer(String[] toEmails) {
        try {

            StringBuilder sb = new StringBuilder();

            for(String toAddress : toEmails) {

                Email toEmail = new Email(toAddress);
                Mail mail = new Mail(from, subject, toEmail, content);

                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);

                sb.append(response.getStatusCode());
                sb.append(response.getBody());
                sb.append(response.getHeaders());
            }

            return sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "error check mailer";
    }

    private File emails;
    private PrintWriter writer;

    public void setEmailHistory(String mailReturn) {
        try {
            this.emails = new File("Documents/emailHistory.txt");
            this.writer = new PrintWriter(emails);

            writer.println(mailReturn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Scanner scanner;

    public String getEmailHistory() {
        try {
            this.emails = new File("Documents/emailHistory.txt");
            this.scanner = new Scanner(emails);
            StringBuilder sb = new StringBuilder();

            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}