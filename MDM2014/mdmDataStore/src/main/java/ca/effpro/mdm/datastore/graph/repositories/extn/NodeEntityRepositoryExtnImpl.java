package ca.effpro.mdm.datastore.graph.repositories.extn;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import ca.effpro.mdm.beans.entity.NodeBase;

public class NodeEntityRepositoryExtnImpl implements NodeEntityRepositoryExtn {

	private final static Logger log = Logger
			.getLogger(NodeEntityRepositoryExtnImpl.class.getName());

	private static Map<String, String> mapOfNodeEntityClassToTypeInfo = new HashMap<String, String>();

	public NodeBase getOrCreateNodeEntity(String indexName, String key,
			Object value, NodeBase nodeEntity, Neo4jTemplate template) {
		Map<String, Object> propsOfNode;

		String nodeEntityType = getNodeEntityTypeInfo(nodeEntity);

		if (StringUtils.isEmpty(nodeEntity.getUUID()))
			nodeEntity.setUUID(getUUIDForNodeEntityType(nodeEntityType));

		if (nodeEntity.getEntityCreatedDate() == null)
			nodeEntity.setEntityCreatedDate(new Date());

		propsOfNode = new HashMap<>();
		propsOfNode.put("uuid", nodeEntity.getUUID());
		propsOfNode.put("entityCreatedDate", nodeEntity.getEntityCreatedDate());

		Node retrievedNode = template.getOrCreateNode(indexName, key, value,
				propsOfNode);

		if (log.isTraceEnabled()) {

			for (Iterator<String> iter = retrievedNode.getPropertyKeys()
					.iterator(); iter.hasNext();) {
				key = iter.next();
				System.out.println("Prop : " + key + " = "
						+ retrievedNode.getProperty(key));
			}

			for (ResourceIterator<Label> iter = retrievedNode.getLabels()
					.iterator(); iter.hasNext();) {
				Label lbl = iter.next();
				System.out.println("Label : " + lbl.name());
			}
		}
		retrievedNode.addLabel(DynamicLabel.label("__TYPE__" + nodeEntityType));
		retrievedNode.addLabel(DynamicLabel.label(nodeEntityType));
		retrievedNode.addLabel(DynamicLabel.label(nodeEntity.getClass()
				.getName()));

		NodeBase projected = (NodeBase) template.projectTo(retrievedNode,
				nodeEntity.getClass());

		nodeEntity.setId(projected.getId());
		nodeEntity.setUUID(projected.getUUID());
		nodeEntity.setEntityCreatedDate(projected.getEntityCreatedDate());

		log.info("\n " 
				+ propsOfNode.get("uuid") + " | " 
				+ retrievedNode.getProperty("uuid") + " | " 
				+ projected.getUUID() + " | " 
				+ nodeEntity.getUUID() 
				);
		
		return projected;
	}

	@SuppressWarnings("static-access")
	String getUUIDForNodeEntityType(String entityType) {
		return UUID.nameUUIDFromBytes(entityType.getBytes()).randomUUID()
				.toString();
	}

	String getNodeEntityTypeInfo(NodeBase nodeEntity) {
		Class<? extends NodeBase> neClazz = nodeEntity.getClass();

		if (mapOfNodeEntityClassToTypeInfo.containsKey(neClazz.getName()))
			return mapOfNodeEntityClassToTypeInfo.get(neClazz.getName());

		Annotation annotation = neClazz.getAnnotation(TypeAlias.class);

		if (annotation == null)
			throw new RuntimeException(
					"Annotation [@TypeAlias] is not defined for node entity "
							+ neClazz.getName());

		TypeAlias typAlias = (TypeAlias) annotation;
		mapOfNodeEntityClassToTypeInfo.put(neClazz.getName(), typAlias.value());

		return typAlias.value();
	}
}
