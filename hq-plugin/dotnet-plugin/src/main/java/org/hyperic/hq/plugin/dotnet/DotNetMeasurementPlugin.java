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
package org.hyperic.hq.plugin.dotnet;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.hq.product.*;
import org.hyperic.sigar.win32.Pdh;
import org.hyperic.sigar.win32.Win32Exception;
import org.hyperic.util.StringUtil;
import org.hyperic.util.config.ConfigResponse;

public class DotNetMeasurementPlugin
        extends Win32MeasurementPlugin {

    private static final String DATA_DOMAIN = ".NET CLR Data";
    private static final String DATA_PREFIX = "SqlClient: ";
    private static final String RUNTIME_NAME = "_Global_";
    private static Log log = LogFactory.getLog(DotNetMeasurementPlugin.class);
    private static final Map<String, String> sqlPidsCache = new HashMap<String, String>();

    @Override
    public MetricValue getValue(Metric metric) throws PluginException, MetricNotFoundException, MetricUnreachableException {
        MetricValue val = null;
        if (metric.getDomainName().equalsIgnoreCase("pdh")) {
            val = getPDHMetric(metric);
        } else if (metric.getDomainName().equalsIgnoreCase("pdhSQLDP")) {
            val = getPDHSQLPDMetric(metric);
        } else {
            try {
                val = super.getValue(metric);
                if (metric.isAvail()) {
                    val = new MetricValue(Metric.AVAIL_UP);
                }
            } catch (MetricNotFoundException ex) {
                if (metric.isAvail()) {
                    val = new MetricValue(Metric.AVAIL_DOWN);
                } else {
                    throw ex;
                }
            }
        }
        return val;
    }

    @Override
    protected String getAttributeName(Metric metric) {
        //avoiding Metric parse errors on ':' in DATA_PREFIX.
        if (metric.getDomainName().equals(DATA_DOMAIN)) {
            return DATA_PREFIX + metric.getAttributeName();
        } else {
            return metric.getAttributeName();
        }
    }

    @Override
    public String translate(String template, ConfigResponse config) {
        if (log.isDebugEnabled()) {
            log.debug("[translate] >> template=" + template);
            for (String key : config.getKeys()) {
                if (key.toLowerCase().startsWith("app")) {
                    log.debug("[translate]  > " + key + "=" + config.getValue(key));
                }
            }
        }

        template = super.translate(template, config);

        template = StringUtil.replace(template, "__percent__", "%");

        // default value for .net server
        final String prop = DotNetDetector.PROP_APP;
        template = StringUtil.replace(template, "${" + prop + "}", config.getValue(prop, RUNTIME_NAME));

        log.debug("[translate] << template=" + template);

        return template;
    }

    private MetricValue getPDHSQLPDMetric(Metric metric) {
        if (sqlPidsCache.isEmpty()) {
            try {
                String[] instances = Pdh.getInstances(".NET Data Provider for SqlServer");
                Pattern regex = Pattern.compile("([^\\[]*)\\[([^\\]]*)\\]"); // name[pid]
                for (int i = 0; i < instances.length; i++) {
                    String instance = instances[i];
                    log.debug("[getPDHSQLPDMetric] instance = " + instance);
                    Matcher m = regex.matcher(instance);
                    if (m.find()) {
                        log.debug("->" + m);
                        log.debug("->" + m.group());
                        log.debug("->" + m.group(1));
                        log.debug("->" + m.group(2));
                        sqlPidsCache.put(m.group(1), m.group(2));
                    }
                }
            } catch (Win32Exception e) {
                log.debug("Error getting PIDs data for '.NET Data Provider for SqlServer': " + e, e);
            }
        }

        String appName = metric.getObjectPropString();
        String pid = sqlPidsCache.get(appName);
        MetricValue res;
        if (pid == null) {
            sqlPidsCache.clear();
            if (metric.isAvail()) {
                res = new MetricValue(Metric.AVAIL_DOWN);
            } else {
                res = MetricValue.NONE;
            }
        } else {
            String obj = "\\.NET Data Provider for SqlServer(" + appName + "[" + pid + "])";
            if (!metric.isAvail()) {
                obj += "\\" + metric.getAttributeName();
            } else {
                obj += "\\HardConnectsPerSecond";
            }
            log.debug("[getPDHSQLPDMetric] metric:'" + metric);
            log.debug("[getPDHSQLPDMetric] obj:'" + obj);
            res = getPDHMetric(obj, metric.isAvail());
            if ((res.getValue() == Metric.AVAIL_DOWN) && (metric.isAvail())) {
                sqlPidsCache.clear();
            }
        }
        return res;
    }

    private MetricValue getPDHMetric(Metric metric) {
        String obj = "\\" + metric.getObjectPropString();
        if (!metric.isAvail()) {
            obj += "\\" + metric.getAttributeName();
        }
        log.debug("[getPDHMetric] metric:'" + metric);
        log.debug("[getPDHMetric] obj:'" + obj);
        return getPDHMetric(obj, metric.isAvail());
    }

    private MetricValue getPDHMetric(String obj, boolean avail) {
        MetricValue res;
        try {
            Double val = new Pdh().getFormattedValue(obj);
            res = new MetricValue(val);
            if (avail) {
                res = new MetricValue(Metric.AVAIL_UP);
            }
        } catch (Win32Exception ex) {
            if (avail) {
                res = new MetricValue(Metric.AVAIL_DOWN);
                log.info("error on obj:'" + obj + "' :" + ex.getLocalizedMessage(), ex);
            } else {
                res = MetricValue.NONE;
                log.info("error on obj:'" + obj + "' :" + ex.getLocalizedMessage());
            }
        }
        return res;
    }
}
