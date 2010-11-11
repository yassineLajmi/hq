// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.hyperic.hq.domain;

import java.lang.Iterable;
import java.lang.Long;
import javax.annotation.Resource;
import org.hyperic.hq.domain.AlertDefinition;
import org.neo4j.graphdb.Node;
import org.springframework.datastore.graph.neo4j.finder.Finder;
import org.springframework.datastore.graph.neo4j.finder.FinderFactory;

privileged aspect AlertDefinition_Roo_GraphEntity {
    
    @Resource
    private FinderFactory AlertDefinition.finderFactory;
    
    public AlertDefinition.new(Node n) {
        setUnderlyingState(n);
    }

    public AlertDefinition AlertDefinition.findById(Long id) {
        return finderFactory.getFinderForClass(AlertDefinition.class).findById(id);

    }
    
    public long AlertDefinition.count() {
        return finderFactory.getFinderForClass(AlertDefinition.class).count();

    }
    
    public Iterable<AlertDefinition> AlertDefinition.findAll() {
        return finderFactory.getFinderForClass(AlertDefinition.class).findAll();

    }
    
}