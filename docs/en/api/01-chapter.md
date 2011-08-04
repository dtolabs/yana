# YANA API

Yana provides a Web API for use with your application.
This describes the Yana API version `1`.
 
## URLs

The Yana server has a "Base URL", where you access the server. 
Your Yana Server URL may look like: `http://myserver:8080`.

The root URL path for all calls to the API in this version is:

    $YANA_SERVER_URL/api

In this document we will leave off the `$YANA_SERVER_URL/api` 
and simply display URLs as `/...`.

## XML

The Yana API calls require XML for input and output.  
(JSON support is planned for a future API version.)

## Authentication

None currently. TBD

## Response Format

### Item Lists ###

Some API requests will return an item list as a result.  
These are typically in the form:

    <list>
        <[item] ...>
        <[item] ...>
    </list>

When an API path declares its results as an "Item List" this 
is the format that will be returned.


## API Contents

### Listing Nodes ###
List the nodes that exist.

URL:

    /nodes

Required parameters:

None.

Result:  An Item List of `nodes`. Each `node` is of the form:

    <node id="ID">
        <name>Node Name</name>
        <description>...</description>
        <osName>...</osName>
        <osFamily>...</osFamily>
        <attributes />
        <externalAttributes />
	<tags/>
    </node>

### Listing Nodes

Export the node definitions for in XML format. Same as list above.

URL:

    /nodes

### Adding a Node ###

Import node definition in XML format.

URL:

    /nodes

Method: `POST`

Required Content-Type: `multipart/form-data`

multipart MIME request part containing the content.

Result:

A status result. 

    <results>
       <result>Created new node. id: 1</result>
    </results>

A Node element will be of the form [node-v10.html](node-v10):

    <!-- ID may not exist if the node was not created yet -->    
    <node id="[ID]">
        <name>Node Name</name>
        <description>...</description>
        <osName>...</osName>
        <osFamily>...</osFamily>
        <attributes>
           <attribute id="[ID]">
             <name>aName</name>
             <value>aValue</name>
           </attribute>
        </attributes>
        <externalAttributes />
        <tags>
          <tag id="[ID]">
            <name>aName</name>
          </tag>
        <tags>
    </node>

### Getting a Node Definition ###

Export a single node definition in XML format.

URL:

    /nodes/[ID]

Method: `GET`

### Deleting a Node Definition ###

Delete a single node definition.

URL:

    /nodes/[ID]

Method: `DELETE`

Result:

The common `result` element described in the 
[Response Format](#response-format) section, 
indicating success or failure and any messages.

If successful, then the `result` will contain a `message`
element with the result message:
    <results>
       <result>Node removed. id: 1</result>
    </results>


