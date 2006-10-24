/*
 * NOTE: This copyright does *not* cover user programs that use HQ
 * program services by normal system calls through the application
 * program interfaces provided as part of the Hyperic Plug-in Development
 * Kit or the Hyperic Client Development Kit - this is merely considered
 * normal use of the program, and does *not* fall under the heading of
 * "derived work".
 * 
 * Copyright (C) [2004, 2005, 2006], Hyperic, Inc.
 * This file is part of HQ.
 * 
 * HQ is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */

package org.hyperic.hq.authz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.hyperic.hq.authz.shared.OperationValue;

/**
 * Operation generated by hbm2java
 */
public class Operation extends AuthzNamedEntity implements Serializable {

    // Fields
     private ResourceType resourceType;
     private Integer cid;
     private Collection roles = new ArrayList();

     private OperationValue operationValue = new OperationValue();
     
     // Constructors

    /** default constructor */
    public Operation() {
        super();
    }

	/** minimal constructor */
    public Operation(OperationValue val) {
        super();
        setOperationValue(val);
    }
    /** full constructor */
    public Operation(String name, ResourceType resourceType, Integer cid,
                     Collection roles) {
        super(name);
        this.resourceType = resourceType;
        this.cid = cid;
        this.roles = roles;
    }
    
   
    public ResourceType getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(ResourceType resourceTypeId) {
        resourceType = resourceTypeId;
    }
    public Integer getCid() {
        return cid;
    }
    
    public void setCid(Integer val) {
        cid = val;
    }
    public Collection getRoles() {
        return roles;
    }
    
    public void setRoles(Collection val) {
        roles = val;
    }

    public OperationValue getOperationValue() {
        operationValue.setId(getId());
        operationValue.setName(getName());
        return operationValue;
    }

    public void setOperationValue(OperationValue val) {
        setId(val.getId());
        setName(val.getName());
    }   

   public Object getValueObject() {
        return getOperationValue();
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof Operation) || !super.equals(obj)) {
            return false;
        }
        Operation o = (Operation)obj;
        return
            ((resourceType==o.getResourceType()) ||
             (resourceType!=null && o.getResourceType()!=null &&
              resourceType.equals(o.getResourceType())));
    }

    public int hashCode() {
        int result = super.hashCode();

        result = 37*result + (resourceType!=null ? resourceType.hashCode():0);

        return result;
    }
}


