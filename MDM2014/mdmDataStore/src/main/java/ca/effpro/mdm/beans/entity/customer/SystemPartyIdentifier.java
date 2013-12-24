package ca.effpro.mdm.beans.entity.customer;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

import ca.effpro.mdm.beans.entity.NodeBase;
import ca.effpro.mdm.refdata.SourceSystemEnum;

@TypeAlias("SYSPARTY_IDENTIFIER")
public class SystemPartyIdentifier extends NodeBase {

	@Indexed(indexName = "SYSPARTY_IDENTIFIER", unique=true, indexType=IndexType.UNIQUE)
	private String identifier;

	private SourceSystemEnum sourceSystem;

	@Fetch
	@RelatedToVia( type = "IDENTIFIES_PARTY")
	private IdentifiesPartyRelation identifiesPartyRel;

	/***********************************************************/
	

	public final String getIdentifier() {
		return identifier;
	}

	public final void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	

	public final SourceSystemEnum getSourceSystem() {
		return sourceSystem;
	}

	public final void setSourceSystem(SourceSystemEnum sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public final IdentifiesPartyRelation getIdentifiesPartyRel() {
		return identifiesPartyRel;
	}

	public final void setIdentifiesPartyRel(IdentifiesPartyRelation identifiesParty) {
		this.identifiesPartyRel = identifiesParty;
	}

	
}
