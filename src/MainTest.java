import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainTest {

	public static void main(String[] args) {

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime data = formatter.parseDateTime("1/6/2013 00:00:00");

		System.out.println(data.toString("dd/MM/yyyy"));
	}
}