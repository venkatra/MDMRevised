package ca.effpro.mdm.beans.entity;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.Neo4jTemplate;

@NodeEntity
public abstract class NodeBase extends EntityBase {

	protected String nodeEntityTypeInfo = null;
	
	protected NodeBase() {
		//super.initialize(getNodeEntityTypeInfo());
	}
	
	protected String getNodeEntityTypeInfo() {
		if(StringUtils.isNotEmpty(nodeEntityTypeInfo))
			return nodeEntityTypeInfo;
		
		Annotation annotation = this.getClass().getAnnotation(TypeAlias.class); 
	    
		if(annotation == null)
			throw new RuntimeException("Annotation [@TypeAlias] is not defined for node entity " + getClass().getName());
		
	    TypeAlias typAlias = (TypeAlias)annotation;
	    nodeEntityTypeInfo = typAlias.value();
	   
	    return nodeEntityTypeInfo;
	}
	
	public NodeBase getNodeForSave(String indexName, String key, Object value, Neo4jTemplate template) {
		Map<String,Object> propsOfNode;
		
		String uuid = getUUID();
		
		propsOfNode = new HashMap<>();
		propsOfNode.put("uuid", uuid);
		propsOfNode.put("entityCreatedDate", getEntityCreatedDate());
		
		Node retrievedNode = template.getOrCreateNode(indexName, key, 
				value, propsOfNode);
	
		if(1==2) {
		
		for(Iterator<String> iter = retrievedNode.getPropertyKeys().iterator(); iter.hasNext(); ) {
			 key = iter.next();
			System.out.println("Prop : " + key + " = " + retrievedNode.getProperty(key));
		}
		
		for(ResourceIterator<Label> iter = retrievedNode.getLabels().iterator();
			iter.hasNext();) {
			Label lbl = iter.next();
			System.out.println("Label : " + lbl.name());
		}
	}		
		retrievedNode.addLabel(DynamicLabel.label("__TYPE__"+ this.nodeEntityTypeInfo));
		retrievedNode.addLabel(DynamicLabel.label(this.nodeEntityTypeInfo));
		retrievedNode.addLabel(DynamicLabel.label(NodeBase.class.getName()));
				
		NodeBase projected = (NodeBase)template.projectTo(retrievedNode, getClass());
		
		setId(retrievedNode.getId());
		setUUID(projected.getUUID());
		setEntityCreatedDate(projected.getEntityCreatedDate());
			
		return projected;
	}

}
