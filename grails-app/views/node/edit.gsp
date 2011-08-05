

<%@ page import="yana.node.Node" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'node.label', default: 'Node')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${nodeInstance}">
            <div class="errors">
                <g:renderErrors bean="${nodeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${nodeInstance?.id}" />
                <g:hiddenField name="version" value="${nodeInstance?.version}" />
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
                                  <label for="hostname"><g:message code="node.hostname.label" default="Hostname" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'hostname', 'errors')}">
                                    <g:textField name="hostname" value="${nodeInstance?.hostname}" />
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
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tags"><g:message code="node.tags.label" default="Tags" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'tags', 'errors')}">
                                    
<ul>
<g:each in="${nodeInstance?.tags?}" var="t">
    <li><g:link controller="tag" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="tag" action="create" params="['node.id': nodeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'tag.label', default: 'Tag')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="attributes"><g:message code="node.attributes.label" default="Attributes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'attributes', 'errors')}">
                                    
<ul>
<g:each in="${nodeInstance?.attributes?}" var="a">
    <li><g:link controller="attribute" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="attribute" action="create" params="['node.id': nodeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'attribute.label', default: 'Attribute')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="externalAttributes"><g:message code="node.externalAttributes.label" default="External Attributes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: nodeInstance, field: 'externalAttributes', 'errors')}">
                                    <g:select name="externalAttributes" from="${yana.attributes.Attributes.list()}" multiple="yes" optionKey="id" size="5" value="${nodeInstance?.externalAttributes*.id}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
