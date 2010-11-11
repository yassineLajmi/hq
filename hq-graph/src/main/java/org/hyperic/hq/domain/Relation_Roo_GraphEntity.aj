// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.hyperic.hq.domain;

import java.lang.Iterable;
import java.lang.Long;
import javax.annotation.Resource;
import org.hyperic.hq.domain.Relation;
import org.neo4j.graphdb.Node;
import org.springframework.datastore.graph.neo4j.finder.Finder;
import org.springframework.datastore.graph.neo4j.finder.FinderFactory;

privileged aspect Relation_Roo_GraphEntity {
    
    @Resource
    private FinderFactory Relation.finderFactory;
    
    public Relation.new(Node n) {
        setUnderlyingState(n);
    }

    public Relation Relation.findById(Long id) {
        return finderFactory.getFinderForClass(Relation.class).findById(id);

    }
    
    public long Relation.count() {
        return finderFactory.getFinderForClass(Relation.class).count();

    }
    
    public Iterable<Relation> Relation.findAll() {
        return finderFactory.getFinderForClass(Relation.class).findAll();

    }
    
}