package ca.effpro.mdm.beans.entity;

import java.lang.annotation.Annotation;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public abstract class NodeBase extends EntityBase {
	
	protected NodeBase() {
		Annotation annotation = this.getClass().getAnnotation(TypeAlias.class); 
	    
		if(annotation == null)
			throw new RuntimeException("Annotation [@TypeAlias] is not defined for node entity " + getClass().getName());
		
	    TypeAlias typAlias = (TypeAlias)annotation;
	    super.setUUID(typAlias.value());
	    
	}
	
}
