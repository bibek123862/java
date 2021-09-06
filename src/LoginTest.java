import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoginTest {
	private Login login;

	@Test
	public void shouldLogin() {
		this.login = new Login();

		String username = "admin";
		String password = "admin";

		boolean actualValue = login.login(username, password);

		assertEquals(true, actualValue);

	}

	@Test
	public void shouldNotLogin() {
		this.login = new Login();

		String username = "wrong username";
		String password = "wrong password";

		boolean actualValue = login.login(username, password);

		assertEquals(false, actualValue);

	}
}
