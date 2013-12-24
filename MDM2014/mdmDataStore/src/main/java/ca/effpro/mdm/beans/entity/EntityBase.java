package ca.effpro.mdm.beans.entity;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.GraphId;

public abstract class EntityBase {

	@GraphId
	protected Long id;
	
	@Transient
	private String uuid;
	private boolean deleted = false;
	
	private Date entityCreatedDate;
	private Date entityEndDate;
	
	private Date lastUpdatedDate;
	private String lastUpdateTxn;
	
	
	/* =================================================================== */
	@SuppressWarnings("static-access")
	protected void setUUID(String entityType) {
		this.uuid = UUID.nameUUIDFromBytes(entityType.getBytes()).randomUUID().toString();
		entityCreatedDate = new Date();
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public boolean equals(Object other) {
		if(this == other) return true;
		
		if(! (other instanceof EntityBase))
			return false;
		
		EntityBase otherNode = (EntityBase)other;
		
		if (id == null) 
			return (uuid.equals(otherNode.uuid));
		
		return id.equals(otherNode.id);
	}

	public int hashCode() {
		return uuid.hashCode();
	}

	/* **************************************************************************** */
	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final boolean isDeleted() {
		return deleted;
	}

	public final void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public final Date getEntityCreatedDate() {
		return entityCreatedDate;
	}

	public final void setEntityCreatedDate(Date entityCreatedDate) {
		this.entityCreatedDate = entityCreatedDate;
	}

	public final Date getEntityEndDate() {
		return entityEndDate;
	}

	public final void setEntityEndDate(Date entityEndDate) {
		this.entityEndDate = entityEndDate;
	}

	public final Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public final void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public final String getLastUpdateTxn() {
		return lastUpdateTxn;
	}

	public final void setLastUpdateTxn(String lastUpdateTxn) {
		this.lastUpdateTxn = lastUpdateTxn;
	}

	public final String getUUID() {
		return uuid;
	}


	

}
