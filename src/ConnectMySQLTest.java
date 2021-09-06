import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConnectMySQLTest {

	private ConnectMySQL connectMySQL;

	@Test
	public void shouldVerifyUser() {
		connectMySQL = new ConnectMySQL();

		String user = "admin";
		String passwd = "admin";
		boolean expected = true;

		boolean actualValue = connectMySQL.verifyUser(user, passwd);

		assertEquals(expected, actualValue);
	}
}
