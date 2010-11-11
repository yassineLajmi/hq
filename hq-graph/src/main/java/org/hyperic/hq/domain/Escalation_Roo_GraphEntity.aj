// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.hyperic.hq.domain;

import java.lang.Iterable;
import java.lang.Long;
import javax.annotation.Resource;
import org.hyperic.hq.domain.Escalation;
import org.neo4j.graphdb.Node;
import org.springframework.datastore.graph.neo4j.finder.Finder;
import org.springframework.datastore.graph.neo4j.finder.FinderFactory;

privileged aspect Escalation_Roo_GraphEntity {
    
    @Resource
    private FinderFactory Escalation.finderFactory;
    
    public Escalation.new(Node n) {
        setUnderlyingState(n);
    }

    public Escalation Escalation.findById(Long id) {
        return finderFactory.getFinderForClass(Escalation.class).findById(id);

    }
    
    public long Escalation.count() {
        return finderFactory.getFinderForClass(Escalation.class).count();

    }
    
    public Iterable<Escalation> Escalation.findAll() {
        return finderFactory.getFinderForClass(Escalation.class).findAll();

    }
    
}