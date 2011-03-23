package org.hyperic.hq.agent.mgmt.data;

import java.util.List;

import javax.persistence.QueryHint;

import org.hyperic.hq.agent.mgmt.domain.Agent;
import org.hyperic.hq.inventory.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AgentRepository extends JpaRepository<Agent, Integer>, AgentRepositoryCustom {

    // TODO check this syntax
    @Transactional(readOnly = true)
    @Query("select count(distinct a) from Agent a where a.managedResources not empty")
    int countUsed();

    List<Agent> findByAddress(String address);

    Agent findByAddressAndPort(String address, int port);

    @Transactional(readOnly = true)
    @Query("select a from Agent a where a.agentToken = :agentToken")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true"),
                 @QueryHint(name = "org.hibernate.cacheRegion", value = "Agent.findByAgentToken") })
    Agent findByAgentToken(@Param("agentToken") String token);
    
    @Transactional(readOnly = true)
    @Query("select a from Agent a join a.managedResources r where r=:resource")
    Agent findByManagedResource(@Param("resource") Resource resource);

}