package ca.effpro.mdm.beans.entity;

import java.lang.annotation.Annotation;
import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import ca.effpro.mdm.refdata.SourceSystemEnum;

public abstract class RelationShipBase<S,E> extends EntityBase {

	private boolean current = true;
	private boolean active = true;
	private SourceSystemEnum fromSourceSystem;

	private Date relStartDate;
	private Date relEndDate;
	
	@StartNode
	private S startNode;
	
	@EndNode
	private E endNode;
	
	public RelationShipBase() {
		Annotation annotation = this.getClass().getAnnotation(RelationshipEntity.class);

		if (annotation == null)
			throw new RuntimeException(
					"Annotation [@RelationshipEntity] is not defined for relationship entity "
							+ getClass().getName());

		RelationshipEntity realtionType = (RelationshipEntity) annotation;
		super.setUUID(realtionType.type());

	}

	/***********************************************************************/
	
	public final boolean isCurrent() {
		return current;
	}

	public final void setCurrent(boolean current) {
		this.current = current;
	}

	public final Date getRelEndDate() {
		return relEndDate;
	}

	public final void setRelEndDate(Date relEndDate) {
		this.relEndDate = relEndDate;
	}

	public final SourceSystemEnum getFromSourceSystem() {
		return fromSourceSystem;
	}

	public final void setFromSourceSystem(SourceSystemEnum fromSourceSystem) {
		this.fromSourceSystem = fromSourceSystem;
	}

	public final Date getRelStartDate() {
		return relStartDate;
	}

	public final void setRelStartDate(Date relStartDate) {
		this.relStartDate = relStartDate;
	}

	public final S getStartNode() {
		return startNode;
	}

	public final void setStartNode(S startNode) {
		this.startNode = startNode;
	}

	public final E getEndNode() {
		return endNode;
	}

	public final void setEndNode(E endNode) {
		this.endNode = endNode;
	}


	public final boolean isActive() {
		return active;
	}

	public final void setActive(boolean active) {
		this.active = active;
	}

}
