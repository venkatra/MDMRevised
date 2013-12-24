package ca.effpro.mdm.datastore.graph.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import ca.effpro.mdm.beans.entity.customer.Email;

public interface EmailRepository extends GraphRepository<Email> {

}
