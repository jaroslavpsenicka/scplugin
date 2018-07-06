<profile>
    <name>Default Profile</name>
    <description>one-size-fits-all</description>
    <completions>
        <completion for="/">
            <value type="string" required="true">name</value>
            <value type="string" required="true">label</value>
            <value type="string">description</value>
            <value type="integer" defaultValue="1" required="true">revision</value>
            <value type="string">caseType</value>
            <value type="string" required="true">createdBy</value>
            <value type="long">createDate</value>
            <value type="string" defaultValue="\&quot;Proces \&quot; + _case.cidla  + \&quot;, vytvořeno \&quot; + (formatDate(createdAt, \&quot;full\&quot;))">presentationSubject</value>
            <value type="array">emailConfigurations</value>
            <value type="array">configurations</value>
            <value type="object">header</value>
            <value type="object" required="true">overview</value>
            <value type="array" required="true">attributes</value>
            <value type="array" required="true">tasks</value>
        </completion>
        <completion for="/attributes">
            <value type="string" required="true">name</value>
            <value type="string" required="true">type</value>
            <value type="string">description</value>
            <value type="enum">mapping</value>
            <value type="string">defaultValue</value>
        </completion>
        <completion for="/attributes/mapping">
            <value type="string">ACCOUNT</value>
            <value type="string">CLIENT</value>
            <value type="string">CLUID</value>
        </completion>
        <completion for="/emailConfigurations">
            <value type="integer" defaultValue="100">priority</value>
            <value type="string" defaultValue="mailbox@csas.cz">mailbox</value>
            <value type="boolean" defaultValue="true">expression</value>
            <value type="string">emailFromFieldName</value>
            <value type="string">emailSubjectFieldName</value>
            <value type="string">emailContentFieldName</value>
        </completion>
        <completion for="/emailConfigurations/emailFromFieldName">
            <value type="attributeName" of="STRING"/>
        </completion>
    </completions>
</profile>