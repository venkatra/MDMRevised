package ca.effpro.mdm.beans.entity.customer;

import org.springframework.data.neo4j.annotation.RelationshipEntity;

import ca.effpro.mdm.beans.entity.RelationShipBase;

@RelationshipEntity(type = "HAS_EMAIL")
public class HasEmailRelation extends RelationShipBase<Person,Email> {
	
	private String usageType;

	public final String getUsageType() {
		return usageType;
	}

	public final void setUsageType(String usageType) {
		this.usageType = usageType;
	}

}
