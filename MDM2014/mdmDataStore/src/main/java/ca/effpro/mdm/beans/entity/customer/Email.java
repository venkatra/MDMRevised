package ca.effpro.mdm.beans.entity.customer;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;

import ca.effpro.mdm.beans.entity.NodeBase;

@TypeAlias("EMAIL")
public class Email extends NodeBase {

	@Indexed(unique = true)
	private String emailAddress;
	private boolean reachable;

	
	public final String getEmailAddress() {
		return emailAddress;
	}

	public final void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public final boolean isReachable() {
		return reachable;
	}

	public final void setReachable(boolean reachable) {
		this.reachable = reachable;
	}

}
