package ca.effpro.mdm.datastore.graph.repositories.extn;

import org.springframework.data.neo4j.support.Neo4jTemplate;

import ca.effpro.mdm.beans.entity.NodeBase;

public interface NodeEntityRepositoryExtn {

	public NodeBase getOrCreateNodeEntity(String indexName, String key,
			Object value, NodeBase nodeEntity, Neo4jTemplate template);
}
