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

package org.hyperic.hq.authz.server.session;

import java.util.Collection;
import java.util.HashSet;

import org.hyperic.hq.authz.shared.AuthzConstants;
import org.hyperic.hq.authz.shared.AuthzSubjectValue;
import org.hyperic.hq.common.server.session.Crispo;
import org.hyperic.hq.inventory.domain.Resource;
import org.springframework.datastore.graph.annotation.NodeEntity;

@NodeEntity(partial=true)
public class AuthzSubject  {
    private String     _dsn;
    private String     _firstName;
    private String     _lastName;
    private String     _emailAddress;
    private String     _smsAddress;
    private String     _phoneNumber;
    private String     _department;
    private boolean    _active;
    private boolean    _system;
    private boolean    _htmlEmail;
    private Resource   _resource;
    private Collection _roles;
    private Crispo     _prefs;
    private String _name;
    private String _sortName;
    private Integer _id;

    // for hibernate optimistic locks -- don't mess with this.
    // Named ugly-style since we already use VERSION in some of our tables.
    // really need to use Long instead of primitive value
    // because the database column can allow null version values.
    // The version column IS NULLABLE for migrated schemas. e.g. HQ upgrade
    // from 2.7.5.
    private Long    _version_;
    
    private AuthzSubjectValue _valueObj;

    protected AuthzSubject() {
    }

    public AuthzSubject(boolean active, String authDsn, String dept,
                        String email, boolean useHtml, String first, 
                        String last, String name, String phone, String sms,
                        boolean system) 
    {
        setActive(active);
        setAuthDsn(authDsn);
        setDepartment(dept);
        setEmailAddress(email);
        setHtmlEmail(useHtml);
        setFirstName(first);
        setLastName(last);
        setName(name);
        setPhoneNumber(phone);
        setSMSAddress(sms);
        setSystem(system);
    }
    
    public void setId(Integer id) {
        _id = id;
    }

    public Integer getId() {
        return _id;
    }
    
    public String getName() {
        return _name;
    }

    public void setName(String name) {
        if (name == null)
            name = "";
        _name = name;
        setSortName(name);
    }

    public String getSortName() {
        return _sortName;
    }

    public void setSortName(String sortName) {
        _sortName = sortName != null ? sortName.toUpperCase() : null;
    }

    public String getAuthDsn() {
        return _dsn;
    }

    protected void setAuthDsn(String val) {
        _dsn = val;
    }

    public String getFirstName() {
        return _firstName;
    }

    protected void setFirstName(String val) {
        _firstName = val;
    }

    public String getLastName() {
        return _lastName;
    }

    protected void setLastName(String val) {
        _lastName = val;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
    
    public String getEmailAddress() {
        return _emailAddress;
    }

    protected void setEmailAddress(String val) {
        _emailAddress = val;
    }

    public String getSMSAddress() {
        return _smsAddress;
    }

    protected void setSMSAddress(String val) {
        _smsAddress = val;
    }

    public String getPhoneNumber() {
        return _phoneNumber;
    }

    protected void setPhoneNumber(String val) {
        _phoneNumber = val;
    }

    public String getDepartment() {
        return _department;
    }

    protected void setDepartment(String val) {
        _department = val;
    }

    public boolean isActive() {
        return _active;
    }

    public boolean getActive() {
        return isActive();
    }

    protected void setActive(boolean val) {
        _active = val;
    }

    public boolean isSystem() {
        return _system;
    }

    public boolean getSystem() {
        return isSystem();
    }
    
    public boolean isHtmlEmail() {
        return _htmlEmail;
    }
    
    public boolean getHtmlEmail() {
        return isHtmlEmail();
    }

    protected void setHtmlEmail(boolean useHtml) {
        _htmlEmail = useHtml;
    }

    protected void setSystem(boolean val) {
        _system = val;
    }

    public Resource getResource() {
        return _resource;
    }

    protected void setResource(Resource val) {
        _resource = val;
    }

    public Collection<Role> getRoles() {
        return _roles;
    }

    protected void setRoles(Collection val) {
        _roles = val;
    }

    public void addRole(Role role) {
        _roles.add(role);
    }

    public void removeRole(Role role) {
        _roles.remove(role);
    }

    public void removeAllRoles() {
        _roles.clear();
    }

    public Crispo getPrefs() {
        return _prefs;
    }
    
    protected void setPrefs(Crispo c) {
        _prefs = c;
    }
    
    public long get_version_() {
        return _version_ != null ? _version_.longValue() : 0;
    }

    protected void set_version_(Long newVer) {
        _version_ = newVer;
    }
    
    /**
     * @deprecated use (this) AuthzSubject instead
     */
    public AuthzSubjectValue getAuthzSubjectValue() {
        if (_valueObj == null) 
            _valueObj = new AuthzSubjectValue();

        _valueObj.setSortName(getSortName());
        _valueObj.setActive(getActive());
        _valueObj.setSystem(getSystem());
        _valueObj.setAuthDsn((getAuthDsn() == null) ? "" : getAuthDsn());
        _valueObj.setEmailAddress((getEmailAddress() == null) ? "" : 
                                  getEmailAddress());
        _valueObj.setHtmlEmail(getHtmlEmail());
        _valueObj.setSMSAddress((getSMSAddress() == null) ? "" : 
                                getSMSAddress());
        _valueObj.setFirstName((getFirstName() == null) ? "" : getFirstName());
        _valueObj.setLastName((getLastName() == null) ? "" : getLastName());
        _valueObj.setPhoneNumber((getPhoneNumber() == null) ? "" : 
                                 getPhoneNumber());
        _valueObj.setDepartment((getDepartment() == null) ? "" : 
                                getDepartment());
        _valueObj.setName((getName() == null) ? "" : getName());
        _valueObj.setId(getId());
        return _valueObj;
    }

    public boolean isRoot() {
        return getId().equals(AuthzConstants.rootSubjectId);
    }

    //TODO had to remove equals and hashCode b/c can't override in NodeBacked
}
