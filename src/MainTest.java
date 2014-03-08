import java.math.BigDecimal;

public class MainTest {

	public static void main(String[] args) {

		BigDecimal x = new BigDecimal("1200");

		System.out.println(x.doubleValue() % 100);
	}
}