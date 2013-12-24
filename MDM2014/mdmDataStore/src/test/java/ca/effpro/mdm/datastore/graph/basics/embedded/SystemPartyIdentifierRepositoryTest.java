package ca.effpro.mdm.datastore.graph.basics.embedded;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.effpro.mdm.TestBase;
import ca.effpro.mdm.beans.entity.customer.IdentifiesPartyRelation;
import ca.effpro.mdm.beans.entity.customer.Person;
import ca.effpro.mdm.beans.entity.customer.SystemPartyIdentifier;
import ca.effpro.mdm.datastore.graph.repositories.PartyRepository;
import ca.effpro.mdm.datastore.graph.repositories.SystemPartyIdentifierRepository;
import ca.effpro.mdm.refdata.SourceSystemEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testGraphDBBasics.xml")
public class SystemPartyIdentifierRepositoryTest extends TestBase {
	private final static Logger log = Logger
			.getLogger(SystemPartyIdentifierRepositoryTest.class.getName());

	@Autowired(required = true)
	Neo4jTemplate neo4jTemplate;

	@Autowired(required = true)
	SystemPartyIdentifierRepository sysPartyIdRepo;

	@Autowired(required = true)
	PartyRepository partyRepo;

	@Test
	public void insertTest() {
		SystemPartyIdentifier sysPartyId = new SystemPartyIdentifier();
		sysPartyId.setIdentifier("A0001");
		sysPartyId.setSourceSystem(SourceSystemEnum.STORES);
		log.info("Inserting : " + sysPartyId.toString());

		log.info("Is repo null : " + (sysPartyIdRepo == null));
		sysPartyId = sysPartyIdRepo.save(sysPartyId);
		log.info("Saved nodeid : " + sysPartyId.getIdentifier());

		assertNotNull(sysPartyId.getIdentifier());
	}

	@Test
	public void findByPartyIdTest() {

		log.info("Querying ... ");

		SystemPartyIdentifier sysPartyId = sysPartyIdRepo
				.getByPartyIDAndSourceSystem("A0001", SourceSystemEnum.STORES);
		log.info("Saved nodeid : " + sysPartyId);
		assertNotNull(sysPartyId);

	}

	@Test
	public void findAndUpdateTest() {
		log.info("Querying ... ");

		SystemPartyIdentifier sysPartyId = sysPartyIdRepo
				.getByPartyIDAndSourceSystem("A0001", SourceSystemEnum.STORES);
		log.info("Saved nodeid : " + sysPartyId.getId());

		assertNotNull(sysPartyId);

		log.info("Updating ... ");
		sysPartyId.setSourceSystem(SourceSystemEnum.ONLINE);
		sysPartyId.setLastUpdatedDate(new Date());
		sysPartyId.setLastUpdateTxn("TXN001");
		sysPartyId = sysPartyIdRepo.save(sysPartyId);

		log.info("Saved nodeid : " + sysPartyId.getIdentifier());

		sysPartyId = sysPartyIdRepo.getByPartyIDAndSourceSystem("A0001",
				SourceSystemEnum.STORES);
		Assert.assertNull(sysPartyId);

		sysPartyId = sysPartyIdRepo.getByPartyIDAndSourceSystem("A0001",
				SourceSystemEnum.ONLINE);
		assertNotNull(sysPartyId);
	}

	@Test
	public void identifiesPartyRelationShipTest() {
		SystemPartyIdentifier sysPartyId = new SystemPartyIdentifier();

		sysPartyId.setIdentifier("A0002");
		sysPartyId.setSourceSystem(SourceSystemEnum.STORES);

		log.info("Inserting system party identifier ...");
		sysPartyId = sysPartyIdRepo.save(sysPartyId);

		Person p = new Person();
		log.info("Inserting person ...");
		p = partyRepo.save(p);

		IdentifiesPartyRelation identifiesRelation = new IdentifiesPartyRelation();
		identifiesRelation.setStartNode(sysPartyId);
		identifiesRelation.setEndNode(p);
		identifiesRelation.setActive(true);
		identifiesRelation.setFromSourceSystem(SourceSystemEnum.STORES);
		log.info("Creating relation between party and identifier ...");
		neo4jTemplate.save(identifiesRelation);

		log.info("Querying ... ");
		sysPartyId = sysPartyIdRepo.getByPartyIDAndSourceSystem("A0002",
				SourceSystemEnum.STORES);

		log.info("Is relation null : "
				+ (sysPartyId.getIdentifiesPartyRel() == null));
		log.info(sysPartyId.getIdentifiesPartyRel().toString());

	}

	@Test
	public void getOrCreateTest() {
		SystemPartyIdentifier sysPartyId, updSysPartyId;
		Node sysPartyIdNode;
		String identifier = "A0003";
		SourceSystemEnum src = SourceSystemEnum.STORES;

		log.info("Creating for first time ... ");

		sysPartyId = new SystemPartyIdentifier();
		sysPartyId.setIdentifier(identifier);
		sysPartyId.setSourceSystem(src);

		sysPartyId = sysPartyIdRepo.save(sysPartyId);
		log.info("Saved nodeid [" + sysPartyId.getId() + "] : "
				+ sysPartyId.getUUID());

		log.info("Checking for existing ... ");
		updSysPartyId = sysPartyIdRepo.findByPropertyValue("identifier", identifier);
		log.info("Existing nodeid [" + updSysPartyId.getId() + "] : "
				+ updSysPartyId.getUUID());
		
		log.info("Updating via different instance ... ");
		updSysPartyId = new SystemPartyIdentifier();
		updSysPartyId.setIdentifier(identifier);
		updSysPartyId.setSourceSystem(SourceSystemEnum.ONLINE);
		
		
		updSysPartyId = sysPartyIdRepo.save(updSysPartyId);
		log.info("Saved nodeid [" + updSysPartyId.getId() + "] : "
				+ updSysPartyId.getUUID());
		
		
	}

}
