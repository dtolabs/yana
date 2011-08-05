
<%@ page import="yana.node.Node" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'node.label', default: 'Node')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'node.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'node.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'node.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="hostname" title="${message(code: 'node.hostname.label', default: 'Hostname')}" />
                        
                            <g:sortableColumn property="osName" title="${message(code: 'node.osName.label', default: 'Os Name')}" />
                        
                            <g:sortableColumn property="osFamily" title="${message(code: 'node.osFamily.label', default: 'Os Family')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${nodeInstanceList}" status="i" var="nodeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${nodeInstance.id}">${fieldValue(bean: nodeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: nodeInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: nodeInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: nodeInstance, field: "hostname")}</td>
                        
                            <td>${fieldValue(bean: nodeInstance, field: "osName")}</td>
                        
                            <td>${fieldValue(bean: nodeInstance, field: "osFamily")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${nodeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
