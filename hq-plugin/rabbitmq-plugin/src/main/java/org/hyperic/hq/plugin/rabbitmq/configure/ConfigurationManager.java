/**
 * NOTE: This copyright does *not* cover user programs that use Hyperic
 * program services by normal system calls through the application
 * program interfaces provided as part of the Hyperic Plug-in Development
 * Kit or the Hyperic Client Development Kit - this is merely considered
 * normal use of the program, and does *not* fall under the heading of
 * "derived work".
 *
 *  Copyright (C) [2010], VMware, Inc.
 *  This file is part of Hyperic.
 *
 *  Hyperic is free software; you can redistribute it and/or modify
 *  it under the terms version 2 of the GNU General Public License as
 *  published by the Free Software Foundation. This program is distributed
 *  in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 *  even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 *  USA.
 *
 */
package org.hyperic.hq.plugin.rabbitmq.configure;
 
import org.hyperic.hq.plugin.rabbitmq.core.HypericRabbitAdmin; 
import org.hyperic.hq.product.PluginException;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Map;

/**
 * ConfigurationManager
 * @author Helena Edelson
 */
public interface ConfigurationManager {

    boolean isInitialized();
 
    void resetConfiguration() throws PluginException;

    void createVirtualHostsForNode(List<String> virtualHosts, Configuration comparableKey) throws PluginException;

    HypericRabbitAdmin createVirtualHostForNode(Configuration c) throws PluginException;

    HypericRabbitAdmin getVirtualHostForNode(String virtualHost, String node);
    
    Map<String, HypericRabbitAdmin> getVirtualHostsForNode();

    CachingConnectionFactory getConnectionFactory();

    RabbitTemplate getRabbitTemplate();

    void removeVirtualHostForNode(String key);

}