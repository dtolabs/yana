
<%@ page import="yana.node.Attribute" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'attribute.label', default: 'Attribute')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'attribute.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'attribute.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="value" title="${message(code: 'attribute.value.label', default: 'Value')}" />
                        
                            <g:sortableColumn property="dataType" title="${message(code: 'attribute.dataType.label', default: 'Data Type')}" />
                        
                            <th><g:message code="attribute.node.label" default="Node" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${attributeInstanceList}" status="i" var="attributeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${attributeInstance.id}">${fieldValue(bean: attributeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: attributeInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: attributeInstance, field: "value")}</td>
                        
                            <td>${fieldValue(bean: attributeInstance, field: "dataType")}</td>
                        
                            <td>${fieldValue(bean: attributeInstance, field: "node")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${attributeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
