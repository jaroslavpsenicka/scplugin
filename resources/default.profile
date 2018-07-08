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
            <value type="string">mapping</value>
            <value type="string">defaultValue</value>
            <value type="string">validationExpression</value>
        </completion>
        <completion for="/attributes/mapping">
            <value type="enum">LIST</value>
            <value type="enum">MAP</value>
            <value type="enum">STRING</value>
        </completion>
        <completion for="/attributes/type">
            <value type="enum">LIST</value>
            <value type="enum" notes="data structure">MAP</value>
            <value type="enum" notes="short text">STRING</value>
            <value type="enum" notes="longer piece of text">TEXT</value>
        </completion>
        <completion for="/emailConfigurations">
            <value type="integer" defaultValue="100">priority</value>
            <value type="string" defaultValue="mailbox@csas.cz">mailbox</value>
            <value type="boolean" defaultValue="true">expression</value>
            <value type="string" notes="email sender">emailFromFieldName</value>
            <value type="string">emailSubjectFieldName</value>
            <value type="string">emailContentFieldName</value>
        </completion>
        <completion for="/emailConfigurations/emailFromFieldName">
            <value type="attributeName" of="STRING"/>
        </completion>
    </completions>
</profile>