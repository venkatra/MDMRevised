package ca.effpro.mdm.beans.entity.customer;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import ca.effpro.mdm.beans.entity.NodeBase;

@TypeAlias("PERSON")
public class Person extends NodeBase {
	
	@Fetch
	@RelatedToVia(direction = Direction.OUTGOING)
	Set<HasEmailRelation> hasEmailsRel;

	public final Set<HasEmailRelation> getHasEmailsRel() {
		return hasEmailsRel;
	}

	public final void setHasEmailsRel(Set<HasEmailRelation> hasEmailsRel) {
		this.hasEmailsRel = hasEmailsRel;
	}

	
	
	
}
