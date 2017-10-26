import com.sendgrid.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner

public class Mail {

    private Database database = new Database();

    private Email from = new Email("nikolad21889@isd273.org");
    private Email to = new Email(database.getEmails());
    private String subject = "Hello Email world";
    private Content content = new Content("I can't believe this works");
    private Mail mail = new Mail(from, subject, to, content);

    private SendGrid sg = new SendGrid(system.getenv("SENDGRID_API_KEY"));
    private Request request = new Request();

    public static void main() {

        StringBuilder mailReturn = mailer();
        setEmailHistory(mailReturn);
        StringBuilder history = getEmailHistory();

    }
    public StringBuilder mailer() {
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);

        StringBuilder sb = new StringBuilder

        sb.append(response.getStatusCode());
        sb.append(response.getBody());
        sb.append(response.getHeaders());

        return sb;
    }

    private File emails;
    private PrintWriter writer;

    public void setEmailHistory(StringBuilder mailReturn);
    this.emails = new File("Documents/emailHistory.txt");
    this.writer = new PrintWriter(emails);

    writer.println(mailReturn);
}

private Scanner scanner;

public StringBuilder getEmailHistory() {

    this.emails = new File("Documents/emailHistory.txt");
    this.scanner = new Scanner(emails);
    StringBuilder sb = new StringBuilder;

    while(scanner.hasNextLine) {
        sb.append(scanner.nextLine());
    }
    return sb;
}