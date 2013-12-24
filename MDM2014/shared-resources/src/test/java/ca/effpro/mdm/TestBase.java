package ca.effpro.mdm;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

public class TestBase {
	private final static Logger log = Logger
			.getLogger(TestBase.class.getName());

	@Rule
	public TestName testName = new TestName();

	@Before
	public void before() {
		
		log.info("Testing Method :  #" + testName.getMethodName());
		log.info("++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	@After
	public void after() {
		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

}
