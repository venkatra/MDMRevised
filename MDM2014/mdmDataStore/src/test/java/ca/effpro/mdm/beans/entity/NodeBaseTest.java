package ca.effpro.mdm.beans.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.data.annotation.TypeAlias;

import ca.effpro.mdm.TestBase;

public class NodeBaseTest extends TestBase {
	private final static Logger log = Logger.getLogger(NodeBaseTest.class
			.getName());
	
	@TypeAlias("CHILD")
	class childNodeBase extends NodeBase {
	};
	
	@Test
	public void testHashCode() {
		childNodeBase A, B;
		
		A = new childNodeBase();
		A.setId(new Long(123));
		log.info("A UUID : " + A.getUUID());
		
		B = new childNodeBase();
		B.setId(new Long(345));
		log.info("B UUID : " + B.getUUID());
		
		assertFalse(A.hashCode() == B.hashCode());
	}

	@Test
	public void testEqual() {

		childNodeBase A, B;
		
		A = new childNodeBase();
		A.setId(new Long(123));
		log.info("A UUID : " + A.getUUID());
		
		assertFalse(A.equals(null));
		
		assertFalse(A.equals("HELLO"));
		
		B = new childNodeBase();
		log.info("B UUID : " + B.getUUID());
		
		assertFalse(A.equals(B));
		
		B.setId(new Long(345));
		assertFalse(A.equals(B));
		
		assertTrue(A.equals(A));
	}

}
