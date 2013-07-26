import org.joda.time.DateTime;

public class MainTest {

	public static void main(String[] args) {

		DateTime primeiroDiaJaneiro = new DateTime(2013, 1, 1, 0, 0, 0);
		DateTime ultimoDiaJaneiro = new DateTime(2013, 1, primeiroDiaJaneiro.dayOfMonth().withMaximumValue().dayOfMonth().get(), 0, 0, 0);

		System.out.println(primeiroDiaJaneiro);
		System.out.println(ultimoDiaJaneiro);

	}
}