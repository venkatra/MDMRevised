package ca.effpro.mdm.beans.entity.customer;

import org.springframework.data.neo4j.annotation.RelationshipEntity;

import ca.effpro.mdm.beans.entity.RelationShipBase;

@RelationshipEntity(type = "IDENTIFIES_PARTY")
public class IdentifiesPartyRelation extends
		RelationShipBase<SystemPartyIdentifier, Person> {

	

}
