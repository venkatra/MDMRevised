package ca.effpro.mdm.datastore.graph.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;

import ca.effpro.mdm.beans.entity.customer.Person;

public interface PartyRepository extends GraphRepository<Person> {

}
