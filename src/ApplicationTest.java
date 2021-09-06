import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ApplicationTest {

	private Application application;

	@Test
	public void shouldOpenCSVFile() {
		this.application = new Application();
		
		boolean actualValue = application.openCSVFile();

		// TODO: assert scenario
		assertEquals(true, actualValue);
	}
}
