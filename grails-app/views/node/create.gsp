

<%@ page import="yana.node.Node" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'node.label', default: 'Node')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${nodeInstance}">
            <div class="errors">
                <g:renderErrors bean="${nodeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="node.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${nodeInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="node.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${nodeInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="osName"><g:message code="node.osName.label" default="Os Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'osName', 'errors')}">
                                    <g:textField name="osName" value="${nodeInstance?.osName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="osFamily"><g:message code="node.osFamily.label" default="Os Family" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'osFamily', 'errors')}">
                                    <g:select name="osFamily" from="${nodeInstance.constraints.osFamily.inList}" value="${nodeInstance?.osFamily}" valueMessagePrefix="node.osFamily"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
