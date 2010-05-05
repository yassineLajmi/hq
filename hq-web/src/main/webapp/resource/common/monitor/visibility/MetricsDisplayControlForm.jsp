<%@ page language="java" %>
<%@ page errorPage="/common/Error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/hq.tld" prefix="hq" %>

<%--
  NOTE: This copyright does *not* cover user programs that use HQ
  program services by normal system calls through the application
  program interfaces provided as part of the Hyperic Plug-in Development
  Kit or the Hyperic Client Development Kit - this is merely considered
  normal use of the program, and does *not* fall under the heading of
  "derived work".
  
  Copyright (C) [2004-2008], Hyperic, Inc.
  This file is part of HQ.
  
  HQ is free software; you can redistribute it and/or modify
  it under the terms version 2 of the GNU General Public License as
  published by the Free Software Foundation. This program is distributed
  in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
  even the implied warranty of MERCHANTABILITY or FITNESS FOR A
  PARTICULAR PURPOSE. See the GNU General Public License for more
  details.
  
  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
  USA.
 --%>

<tiles:importAttribute name="form" ignore="true"/>
<tiles:importAttribute name="mode" ignore="true"/>
<tiles:importAttribute name="eid" ignore="true"/>
<tiles:importAttribute name="view" ignore="true"/>

<c:if test="${not empty mode}">
  <input type="hidden" name="mode" value="<c:out value="${mode}"/>"/>
</c:if>
<c:if test="${not empty eid}">
  <input type="hidden" name="eid" value="<c:out value="${eid}"/>"/>
</c:if>
<c:if test="${not empty view}">
  <input type="hidden" name="view" value="<c:out value="${view}"/>"/>
</c:if>

<c:choose>
  <c:when test="${not empty form}">
    <%-- used only for forms that are not MetricsDisplayForm --%>
    <tiles:importAttribute name="formName"/>
    <c:set var="readOnly" value="${form.readOnly}"/>
    <c:set var="rangeBegin" value="${form.rb}"/>
    <c:set var="rangeEnd" value="${form.re}"/>
    <c:set var="showBaseline" value="false"/>
  </c:when>
  <c:otherwise>
    <c:set var="formName" value="MetricsDisplayForm"/>
    <c:set var="readOnly" value="${MetricsDisplayForm.readOnly}"/>
    <c:set var="rangeBegin" value="${MetricsDisplayForm.rb}"/>
    <c:set var="rangeEnd" value="${MetricsDisplayForm.re}"/>
    <c:set var="highlighted" value="${MetricsDisplayForm.h}"/>
    <c:set var="showBaseline" value="${MetricsDisplayForm.showBaseline}"/>
  </c:otherwise>
</c:choose>

<hq:dateFormatter var="rb" value="${rangeBegin}"/>
<hq:dateFormatter var="re" value="${rangeEnd}"/>

<!-- Table Content -->
<table width="100%" cellspacing="0" border="0" class="MonitorToolbar">
<c:if test="${showBaseline}">
  <tr valign="middle">
    <td width="20%" align="right"><b><fmt:message key="resource.common.monitor.visibility.metricsToolbar.HighlightMetricsLabel"/></b></td>
    <td width="80%">
      <table width="100%" cellpadding="0" cellspacing="3" border="0">
        <tr>
          <td>
            <html:select styleClass="FilterFormText" property="hv">
              <html:option value=""/>
              <html:option value="1" key="resource.common.monitor.visibility.metricsToolbar.LowValue"/>
              <html:option value="2" key="resource.common.monitor.visibility.metricsToolbar.AverageValue"/>
              <html:option value="3" key="resource.common.monitor.visibility.metricsToolbar.PeakValue"/>
              <html:option value="4" key="resource.common.monitor.visibility.metricsToolbar.LastValue"/>
            </html:select>
          </td>
          <td><fmt:message key="resource.common.monitor.visibility.metricsToolbar.is"/></td>
          <td nowrap>
            <html:select styleClass="FilterFormText" property="hp">
              <html:option value=""/>
              <html:option value="5" key="resource.common.monitor.visibility.metricsToolbar.5%"/>
              <html:option value="10" key="resource.common.monitor.visibility.metricsToolbar.10%"/>
              <html:option value="20" key="resource.common.monitor.visibility.metricsToolbar.20%"/>
              <html:option value="30" key="resource.common.monitor.visibility.metricsToolbar.30%"/>
              <html:option value="40" key="resource.common.monitor.visibility.metricsToolbar.40%"/>
              <html:option value="50" key="resource.common.monitor.visibility.metricsToolbar.50%"/>
              <html:option value="60" key="resource.common.monitor.visibility.metricsToolbar.60%"/>
              <html:option value="70" key="resource.common.monitor.visibility.metricsToolbar.70%"/>
              <html:option value="80" key="resource.common.monitor.visibility.metricsToolbar.80%"/>
              <html:option value="90" key="resource.common.monitor.visibility.metricsToolbar.90%"/>
              <html:option value="100" key="resource.common.monitor.visibility.metricsToolbar.100%"/>
            </html:select> 
            <html:select styleClass="FilterFormText" property="ht">
              <html:option value=""/>
              <hq:optionMessageList property="highlightThresholdMenu" baseKey="resource.common.monitor.visibility.metricsToolbar" filter="true"/>
            </html:select>
          </td>
          <td><html:image property="highlight" page="/images/4.0/icons/accept.png" border="0"/></td>
<c:choose>
  <c:when test="${highlighted}">
          <td width="100%"><html:link href="javascript:document.${formName}.submit()" onclick="clickLink('${formName}', 'clear')"><fmt:message key="resource.common.monitor.visibility.metricsToolbar.ClearHighlightingBtn"/></html:link></td>
  </c:when>
  <c:otherwise>
          <td width="100%">&nbsp;</td>
  </c:otherwise>
</c:choose>
        </tr>
      </table>
    </td>
  </tr>
</c:if>
  <tr valign="middle">
    <td class="boldText" style="text-align:right;"><fmt:message key="resource.common.monitor.visibility.metricsToolbar.MetricDisplayRangeLabel"/></td>
<c:choose>
  <c:when test="${readOnly}">
    <td>
      <table width="100%" cellpadding="0" cellspacing="3" border="0">
        <tr>
          <td><html:image property="prevRange" page="/images/tbb_pageleft.gif" border="0"/></td>
          <td nowrap><fmt:message key="resource.common.monitor.visibility.metricsToolbar.DateRange"><fmt:param value="${rb}"/><fmt:param value="${re}"/></fmt:message></td>
          <td><html:image property="nextRange" page="/images/tbb_pageright.gif" border="0"/></td>
          <td width="100%" style="padding-left: 5px;">
          <a href='<html:rewrite page="/ResourceCurrentHealth.do?eid=${eid}&view=${view}&alertDefaults=true"/>'>
            <c:if test="${form.a != 1 || (rangeEnd - rangeBegin) > 172800000}">
              <fmt:message key="resource.common.monitor.visibility.now"/></a>&nbsp;<fmt:message key="common.label.Pipe"/>&nbsp;
          </c:if>
          <html:link href="javascript:moveIt(dojo.byId('advancedDisplay'), '-20px', '25px');showAdvanced();"><fmt:message key="resource.common.monitor.visibility.metricsToolbar.EditRangeBtn"/></html:link> </td>
        </tr>
      </table>
    </td>
  </c:when>
  <c:otherwise>
    <td>
      <table width="100%" cellpadding="0" cellspacing="3" border="0">
        <tr>
          <td><html:image property="prevRange" page="/images/tbb_pageleft.gif" border="0"/></td>
          <td><fmt:message key="resource.common.monitor.visibility.metricsToolbar.Last"/></td>
          <td nowrap>
            <html:select styleClass="FilterFormText" property="rn" styleId="simpleRn">
              <html:optionsCollection property="rnMenu"/>
            </html:select> 
            <html:select styleClass="FilterFormText" property="ru" styleId="simpleRu">
<!--
              <html:option value="1" key="resource.common.monitor.visibility.metricsToolbar.CollectionPoints"/>
-->
              <html:option value="2" key="resource.common.monitor.visibility.config.Minutes"/>
              <html:option value="3" key="resource.common.monitor.visibility.config.Hours"/>
              <html:option value="4" key="resource.common.monitor.visibility.metricsToolbar.Days"/>
            </html:select>
          </td>
          <td><html:image property="range" page="/images/4.0/icons/accept.png" border="0"/></td>
          <td width="100%" style="padding-left: 5px;">
            <a href='<html:rewrite page="/ResourceCurrentHealth.do?eid=${eid}&view=${view}&alertDefaults=true"/>'>
            <c:if test="${form.a != 1 || (rangeEnd - rangeBegin) > 172800000}">
              <fmt:message key="resource.common.monitor.visibility.now"/></a>&nbsp;<fmt:message key="common.label.Pipe"/>&nbsp;
            </c:if>
			<html:link href="javascript:moveIt(dojo.byId('advancedDisplay'), '-20px', '25px');showAdvanced();"><fmt:message key="resource.common.monitor.visibility.metricsToolbar.AdvancedSettingsBtn"/></html:link>
          </td>
        </tr>
      </table>
    </td>
<logic:messagesPresent property="rn">
  <tr>
    <td>&nbsp;</td>
    <td colspan="3" class="ErrorField"><span class="ErrorFieldContent"><html:errors property="rn"/></span></td>
  </tr>
</logic:messagesPresent>
  </c:otherwise>
</c:choose>
  </tr>
</table>
  <div id="advancedAnchor" style="position: relative; visibility: hidden;"></div>
  <div id="advancedDisplay" class="dialog" style="padding:15px;width:600px;height:150px;display:none;">
    <tiles:insert definition=".resource.common.monitor.visibility.embeddedMetricDisplayRange">
      <c:if test="${not empty form}">
        <tiles:put name="form" beanName="form"/>
        <tiles:put name="formName" beanName="formName"/>
      </c:if>
    </tiles:insert>
  </div>
<!--  /  -->
<script type="text/javascript">
  function hideAdvanced() {
    var advancedDiv = dojo.byId('advancedDisplay');
    advancedDiv.style.display='none';
    new Effect.MoveBy(advancedDiv.parentNode, 0, 0 );
  }

  function moveIt(obj, mvTop, mvLeft) {
	obj.style.position = "absolute";
	obj.style.top = mvTop;
	obj.style.left = mvLeft;
    obj.style.display = '';
}

onloads.push( hideAdvanced );

</script>