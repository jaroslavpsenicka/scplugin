<profile>
    <name>Default Profile</name>
    <completions>
        <completion for="/">
            <value type="string">name</value>
            <value type="string">label</value>
            <value type="string">description</value>
            <value type="integer" defaultValue="1">revision</value>
            <value type="string">caseType</value>
            <value type="string">createdBy</value>
            <value type="long">createDate</value>
            <value type="string" defaultValue="\&quot;Proces \&quot; + _case.cidla  + \&quot;, vytvořeno \&quot; + (formatDate(createdAt, \&quot;full\&quot;))">presentationSubject</value>
            <value type="array">emailConfigurations</value>
            <value type="array">configurations</value>
            <value type="object">header</value>
            <value type="array">attributes</value>
            <value type="array">tasks</value>
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