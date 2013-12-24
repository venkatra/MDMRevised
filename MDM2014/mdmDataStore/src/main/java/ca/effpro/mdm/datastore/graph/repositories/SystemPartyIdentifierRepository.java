package ca.effpro.mdm.datastore.graph.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import ca.effpro.mdm.beans.entity.customer.SystemPartyIdentifier;
import ca.effpro.mdm.refdata.SourceSystemEnum;

public interface SystemPartyIdentifierRepository extends
		GraphRepository<SystemPartyIdentifier> {

	@Query("START n=node:SYSPARTY_IDENTIFIER(identifier={0}) MATCH (n) "
			+ "WHERE  n.sourceSystem={1} " 
			+ "RETURN n")
	SystemPartyIdentifier getByPartyIDAndSourceSystem(String partyIdentifier,
			SourceSystemEnum sourceSystem);

}
