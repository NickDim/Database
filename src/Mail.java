import com.sendgrid.*;
import java.io.IOException;

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
        system.out.println(mailReturn);

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
    }
}
