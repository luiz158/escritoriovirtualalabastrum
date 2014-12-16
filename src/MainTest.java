import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainTest {

	public static void main(String[] args) {

		System.out.println(new BigDecimal("6.000013").setScale(2, RoundingMode.HALF_UP));
	}
}