<profile>
    <name>my</name>
    <description>generated</description>
    <completions>
        <completion for="/">
            <value type="array" required="true" notes="datové položky">attributes</value>
            <value type="string" required="true" notes="datum vytvoření (libovolná hodnota)">createDate</value>
            <value type="string" required="true" notes="autor procesu">createdBy</value>
            <value type="string" required="true" notes="lidsky čitelné jméno procesu">label</value>
            <value type="string" required="true" notes="technické jméno procesu">name</value>
            <value type="object" required="true" notes="náhled dat case">overview</value>
            <value type="integer" required="true" notes="verze procesu (může být 1)" defaultValue="1">revision</value>
            <value type="string" required="true" notes="kod procesu pro JEF" defaultValue="CTXXX">caseType</value>
            <value type="array" notes="role uživatele">authorizedRoles</value>
            <value type="string" icon="nodes/enum">categorizationExpression</value>
            <value type="array" notes="předpis SLA">configurations</value>
            <value type="string" notes="popis procesu (dokumentace)">description</value>
            <value type="string" notes="AVOS doména úkolu">domain</value>
            <value type="array" notes="konfigurace příchozích emailů">emailConfigurations</value>
            <value type="array">expressions</value>
            <value type="object" notes="data v záhlaví úkolu">header</value>
            <value type="array">importConfigurations</value>
            <value type="string" notes="stručný popis případu">presentationSubject</value>
            <value type="object" notes="definice požadavku (typicky pro pobočku)">requisition</value>
            <value type="array" notes="simulátor služeb">serviceMocks</value>
            <value type="string" notes="nastavení skartace dokončených procesů">shredding</value>
            <value type="array">tags</value>
            <value type="array" notes="vlastní úkoly">tasks</value>
            <value type="array" notes="přechody mezi úkoly">transitions</value>
            <value type="array">uniqueHashAttributes</value>
            <value type="string">uniqueHashAttributesExpression</value>
            <value type="object">validationType</value>
        </completion>

        <completion for="/attributes">
            <value type="string" required="true" notes="jméno atributu">name</value>
            <value type="object" required="true" notes="datový typ">type</value>
            <value type="object" notes="výchozí hodnota">defaultValue</value>
            <value type="string" notes="popis atributu (dokumentace)">description</value>
            <value type="string" notes="významový typ (pro DWH)">mapping</value>
            <value type="string" notes="datový typ prvků LIST a MAP">structureType</value>
            <value type="string" notes="validace (true pro OK)" icon="nodes/enum">validationExpression</value>
        </completion>
        <completion for="/attributes/mapping">
            <value type="enum">CLUID</value>
            <value type="enum">CLIENT</value>
            <value type="enum">FULL_ACCOUNT_NUMBER</value>
            <value type="enum">ACCOUNT</value>
            <value type="enum">EVIDENCE_NUMBER</value>
            <value type="enum">CGP_ID</value>
            <value type="enum">PRODUCT</value>
            <value type="enum">PRODUCTS</value>
            <value type="enum">DUID</value>
            <value type="enum">RECORD</value>
            <value type="enum">PERSONAL_ID</value>
            <value type="enum">REGISTRATION_NUMBER</value>
            <value type="enum">COMPANY</value>
            <value type="enum">COMPLAINT</value>
            <value type="enum">RECORDS</value>
            <value type="enum">CLIENTS</value>
            <value type="enum">CASETYPE</value>
            <value type="enum">PARTY_TYPE</value>
            <value type="enum">AGREEMENT_NUMBER</value>
            <value type="enum">ACCOUNT_NUMBER</value>
            <value type="enum">ACCOUNT_PREFIX</value>
            <value type="enum">ACCOUNT_BANKCODE</value>
            <value type="enum">LOAN</value>
            <value type="enum">SALES_CHANNEL</value>
            <value type="enum">TRANSACTIONS</value>
            <value type="enum">SELECTED_TRANSACTIONS</value>
            <value type="enum">COMPLAINT_DESCRIPTION</value>
            <value type="enum">CARD_TRANSACTION_CLAIM_DESCRIPTION</value>
            <value type="enum">WAY_OF_DOC_SEND</value>
            <value type="enum">ADDITIONAL_INFO_TYPES</value>
            <value type="enum">ADDITIONAL_INFO_NOTE</value>
            <value type="enum">COMPLAINT_OR_REQUEST_CHOICE</value>
            <value type="enum">SOLVE_COMPLAINT</value>
        </completion>
        <completion for="/attributes/type">
            <value type="enum">NUMBER</value>
            <value type="enum">DECIMAL</value>
            <value type="enum">STRING</value>
            <value type="enum">TEXT</value>
            <value type="enum">BOOLEAN</value>
            <value type="enum">DATE</value>
            <value type="enum">LIST</value>
            <value type="enum">MAP</value>
        </completion>
        <completion for="/authorizedRoles">
            <value type="enum">BRASIL_UNO_SmartCase_User</value>
        </completion>

        <completion for="/configurations">
            <value type="string">condition</value>
            <value type="object">estimatedCompletionDate</value>
        </completion>
        <completion for="/configurations/condition"/>
        <completion for="/configurations/estimatedCompletionDate">
            <value type="string" required="true">duration</value>
            <value type="string" required="true">unit</value>
        </completion>
        <completion for="/configurations/estimatedCompletionDate/duration">
            <value type="enum" notes="1 měsíc">P1M</value>
            <value type="enum" notes="2 dny">P2D</value>
            <value type="enum" notes="1 den a 12 hodin">P1DT12H</value>
            <value type="enum" notes="8 hodin">PT8H</value>
            <value type="enum" notes="5 minut">PT5M</value>
        </completion>
        <completion for="/configurations/estimatedCompletionDate/unit">
            <value type="enum" notes="pracovní dny (po pátku je pondělí)">LABOURUNIT</value>
            <value type="enum" notes="kalendářní dny (po pátku je sobota)">CALENDARUNIT</value>
        </completion>
        <completion for="/createDate">
            <value type="currentTime"/>
        </completion>
        <completion for="/createdBy">
            <value type="userName"/>
        </completion>
        <completion for="/domain">
            <value type="enum" notes="default">IMP</value>
            <value type="enum" notes="alternative">OPS</value>
        </completion>
        <completion for="/emailConfigurations">
            <value type="string">attachmentRecordType</value>
            <value type="string">bodyRecordType</value>
            <value type="object">documentRepository</value>
            <value type="string" notes="attr pro ulozeni těla zprávy" icon="css/atrule">emailContentFieldName</value>
            <value type="string" notes="attr pro ulozeni odesilatele" icon="css/atrule">emailFromFieldName</value>
            <value type="string" notes="attr pro ulozeni předmětu zprávy" icon="css/atrule">emailSubjectFieldName
            </value>
            <value type="boolean" notes="podmínka pro použití konfigurace" icon="nodes/enum" defaultValue="true">
                expression
            </value>
            <value type="string" notes="schránka příchozí pošty" defaultValue="mailbox@csas.cz">mailbox</value>
            <value type="integer" defaultValue="100">priority</value>
        </completion>
        <completion for="/emailConfigurations/attachmentRecordType"/>
        <completion for="/emailConfigurations/bodyRecordType"/>
        <completion for="/emailConfigurations/documentRepository">
            <value type="enum">MSE</value>
            <value type="enum">ESIGNATURE</value>
            <value type="enum">MORTGAGES_SELL</value>
            <value type="enum">DS_ATTACHMENT</value>
            <value type="enum">CEM</value>
        </completion>
        <completion for="/emailConfigurations/emailContentFieldName">
            <value type="attributeName" of="STRING"/>
        </completion>
        <completion for="/emailConfigurations/emailFromFieldName">
            <value type="attributeName" of="STRING"/>
        </completion>
        <completion for="/emailConfigurations/emailSubjectFieldName">
            <value type="attributeName" of="STRING"/>
        </completion>

        <completion for="/expressions">
            <value type="string" notes="technické jméno výrazu" required="true">name</value>
            <value type="string" notes="popis výrazu (dokumentace)">description</value>
            <value type="string" notes="text výrazu" icon="nodes/enum">expression</value>
            <value type="string" notes="typ výrazu">type</value>
        </completion>
        <completion for="/expressions/type">
            <value type="enum" notes="výraz JS">PLAIN</value>
            <value type="enum" notes="výraz MATH.JS">MATH</value>
        </completion>

        <completion for="/header">
            <value type="array">items</value>
        </completion>
        <completion for="/header/items">
            <value type="string" notes="technické jméno" required="true">name</value>
            <value type="string" icon="nodes/enum">expression</value>
            <value type="string" notes="lidsky čitelné jméno">label</value>
        </completion>

        <completion for="/importConfigurations">
            <value type="string">agendaCode</value>
            <value type="string">charset</value>
            <value type="array">columnAttributeMappings</value>
            <value type="string">expression</value>
            <value type="object">randomizeDuration</value>
            <value type="object">rowsPerCase</value>
            <value type="object">separator</value>
            <value type="object">type</value>
        </completion>
        <completion for="/importConfigurations/agendaCode"/>
        <completion for="/importConfigurations/charset"/>
        <completion for="/importConfigurations/columnAttributeMappings">
            <value type="string">attributeName</value>
            <value type="object">cidla</value>
            <value type="integer">columnId</value>
            <value type="string">header</value>
            <value type="object">required</value>
            <value type="object">valueType</value>
        </completion>
        <completion for="/importConfigurations/columnAttributeMappings/attributeName"/>
        <completion for="/importConfigurations/columnAttributeMappings/cidla"/>
        <completion for="/importConfigurations/columnAttributeMappings/columnId"/>
        <completion for="/importConfigurations/columnAttributeMappings/header"/>
        <completion for="/importConfigurations/columnAttributeMappings/required"/>
        <completion for="/importConfigurations/columnAttributeMappings/valueType">
            <value type="enum">NUMBER</value>
            <value type="enum">DECIMAL</value>
            <value type="enum">STRING</value>
            <value type="enum">TEXT</value>
            <value type="enum">BOOLEAN</value>
            <value type="enum">DATE</value>
            <value type="enum">LIST</value>
            <value type="enum">MAP</value>
        </completion>
        <completion for="/importConfigurations/expression"/>
        <completion for="/importConfigurations/randomizeDuration">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/importConfigurations/randomizeDuration/expression"/>
        <completion for="/importConfigurations/randomizeDuration/type">
            <value type="enum">FIXED</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/importConfigurations/randomizeDuration/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/importConfigurations/rowsPerCase"/>
        <completion for="/importConfigurations/separator"/>
        <completion for="/importConfigurations/type">
            <value type="enum">MANUAL</value>
            <value type="enum">CSOPS</value>
        </completion>
        <completion for="/overview">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="array">actions</value>
            <value type="array">activities</value>
            <value type="string">avosDomain</value>
            <value type="string">description</value>
            <value type="array">expressions</value>
            <value type="object">header</value>
            <value type="array">transitions</value>
        </completion>
        <completion for="/overview/actions">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">type</value>
            <value type="array">confirmations</value>
            <value type="string">createTaskExpression</value>
            <value type="string">description</value>
            <value type="string">editable</value>
            <value type="array">justifications</value>
            <value type="boolean">primary</value>
            <value type="boolean">skipValidation</value>
            <value type="object">suspendUntil</value>
            <value type="string">visible</value>
        </completion>
        <completion for="/overview/actions/confirmations">
            <value type="string">cancelButton</value>
            <value type="string">condition</value>
            <value type="string">note</value>
            <value type="string">okButton</value>
            <value type="string">severity</value>
            <value type="string">text</value>
            <value type="string">title</value>
        </completion>
        <completion for="/overview/actions/confirmations/cancelButton"/>
        <completion for="/overview/actions/confirmations/condition"/>
        <completion for="/overview/actions/confirmations/note"/>
        <completion for="/overview/actions/confirmations/okButton"/>
        <completion for="/overview/actions/confirmations/severity"/>
        <completion for="/overview/actions/confirmations/text"/>
        <completion for="/overview/actions/confirmations/title"/>
        <completion for="/overview/actions/createTaskExpression"/>
        <completion for="/overview/actions/description"/>
        <completion for="/overview/actions/editable"/>
        <completion for="/overview/actions/label"/>
        <completion for="/overview/actions/name"/>
        <completion for="/overview/actions/primary"/>
        <completion for="/overview/actions/skipValidation"/>
        <completion for="/overview/actions/suspendUntil">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/overview/actions/suspendUntil/expression"/>
        <completion for="/overview/actions/suspendUntil/type">
            <value type="enum">NOW</value>
            <value type="enum">END</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/overview/actions/suspendUntil/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/overview/actions/type">
            <value type="enum">NEXT</value>
            <value type="enum">REJECT</value>
            <value type="enum">RELEASE</value>
            <value type="enum">SAVE</value>
            <value type="enum">STOP</value>
            <value type="enum">COMPLAIN</value>
            <value type="enum">COMMENTS</value>
            <value type="enum">CLOSE</value>
            <value type="enum">SUSPEND</value>
            <value type="enum">POSTPONE_REQUISITION</value>
            <value type="enum">TRANSFORM_CASE_TYPE</value>
            <value type="enum">REFRESH_OVERVIEW</value>
            <value type="enum">CONTINUE_REQUISITION</value>
            <value type="enum">URGE</value>
            <value type="enum">CREATE_TASK</value>
        </completion>
        <completion for="/overview/actions/visible"/>
        <completion for="/overview/activities">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="string" required="true">visible</value>
            <value type="string">bluePrism</value>
            <value type="string">description</value>
            <value type="object">estimatedDuration</value>
            <value type="array">fields</value>
            <value type="array">messages</value>
        </completion>
        <completion for="/overview/activities/bluePrism"/>
        <completion for="/overview/activities/description"/>
        <completion for="/overview/activities/fields">
            <value type="object" required="true">editor</value>
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">selector</value>
            <value type="integer" defaultValue="1">areaWidth</value>
            <value type="string">description</value>
            <value type="string">editable</value>
            <value type="string">errorLabel</value>
            <value type="boolean">forceInit</value>
            <value type="integer" defaultValue="0">labelWidth</value>
            <value type="string">notes</value>
            <value type="string">required</value>
            <value type="string">visible</value>
            <value type="integer" defaultValue="1">width</value>
        </completion>
        <completion for="/overview/activities/fields/areaWidth"/>
        <completion for="/overview/activities/fields/description"/>
        <completion for="/overview/activities/fields/editable"/>
        <completion for="/overview/activities/fields/editor">
            <value type="string" required="true">name</value>
            <value type="array">properties</value>
        </completion>
        <completion for="/overview/activities/fields/editor/name"/>
        <completion for="/overview/activities/fields/editor/properties">
            <value type="string" required="true">name</value>
            <value type="object">value</value>
        </completion>
        <completion for="/overview/activities/fields/editor/properties/name">
            <value type="editorPropertyName"/>
        </completion>
        <completion for="/overview/activities/fields/errorLabel"/>
        <completion for="/overview/activities/fields/forceInit"/>
        <completion for="/overview/activities/fields/label"/>
        <completion for="/overview/activities/fields/labelWidth"/>
        <completion for="/overview/activities/fields/name"/>
        <completion for="/overview/activities/fields/notes"/>
        <completion for="/overview/activities/fields/required"/>
        <completion for="/overview/activities/fields/selector">
            <value type="object">attributes</value>
        </completion>
        <completion for="/overview/activities/fields/visible"/>
        <completion for="/overview/activities/fields/width"/>
        <completion for="/overview/activities/label"/>
        <completion for="/overview/activities/messages">
            <value type="string" required="true">condition</value>
            <value type="string" required="true">name</value>
            <value type="string" required="true">text</value>
            <value type="object" required="true">type</value>
            <value type="boolean">closureEnabled</value>
            <value type="string">description</value>
            <value type="array">watchedAttributes</value>
        </completion>
        <completion for="/overview/activities/messages/closureEnabled"/>
        <completion for="/overview/activities/messages/condition"/>
        <completion for="/overview/activities/messages/description"/>
        <completion for="/overview/activities/messages/name"/>
        <completion for="/overview/activities/messages/text"/>
        <completion for="/overview/activities/messages/type">
            <value type="enum">MESSAGE</value>
            <value type="enum">WARNING</value>
            <value type="enum">ERROR</value>
            <value type="enum">INSTRUCTION</value>
        </completion>
        <completion for="/overview/activities/name"/>
        <completion for="/overview/activities/visible"/>
        <completion for="/overview/avosDomain"/>
        <completion for="/overview/description"/>
        <completion for="/overview/expressions">
            <value type="string" required="true">name</value>
            <value type="string">description</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/overview/expressions/description"/>
        <completion for="/overview/expressions/expression"/>
        <completion for="/overview/expressions/name"/>
        <completion for="/overview/expressions/type">
            <value type="enum">PLAIN</value>
            <value type="enum">MATH</value>
        </completion>
        <completion for="/overview/header">
            <value type="array">items</value>
        </completion>
        <completion for="/overview/header/items">
            <value type="string" required="true">name</value>
            <value type="string">expression</value>
            <value type="string">label</value>
        </completion>
        <completion for="/overview/header/items/expression"/>
        <completion for="/overview/header/items/label"/>
        <completion for="/overview/header/items/name"/>
        <completion for="/overview/label"/>
        <completion for="/overview/name"/>
        <completion for="/overview/transitions">
            <value type="object" required="true">from</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">to</value>
            <value type="string">condition</value>
            <value type="string">description</value>
            <value type="string">expression</value>
            <value type="string">expressions</value>
            <value type="string">outputAttributes</value>
        </completion>
        <completion for="/overview/transitions/condition"/>
        <completion for="/overview/transitions/description"/>
        <completion for="/overview/transitions/expression"/>
        <completion for="/overview/transitions/expressions"/>
        <completion for="/overview/transitions/from">
            <value type="string" required="true">name</value>
            <value type="object">type</value>
        </completion>
        <completion for="/overview/transitions/from/name"/>
        <completion for="/overview/transitions/from/type">
            <value type="enum">ACTIVITY</value>
            <value type="enum">START</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/overview/transitions/name"/>
        <completion for="/overview/transitions/outputAttributes"/>
        <completion for="/overview/transitions/to">
            <value type="string" required="true">name</value>
            <value type="object">type</value>
        </completion>
        <completion for="/overview/transitions/to/name"/>
        <completion for="/overview/transitions/to/type">
            <value type="enum">ACTIVITY</value>
            <value type="enum">START</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/presentationSubject"/>
        <completion for="/requisition">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="string" required="true">refTaskName</value>
            <value type="array">actions</value>
            <value type="array">activities</value>
            <value type="string">avosDomain</value>
            <value type="string">description</value>
            <value type="array">expressions</value>
            <value type="object">header</value>
            <value type="array">transitions</value>
        </completion>
        <completion for="/requisition/actions" ref="/overview/actions"/>
        <completion for="/requisition/actions/confirmations" ref="/overview/actions/confirmations"/>
        <completion for="/requisition/actions/confirmations/cancelButton"/>
        <completion for="/requisition/actions/confirmations/condition"/>
        <completion for="/requisition/actions/confirmations/note"/>
        <completion for="/requisition/actions/confirmations/okButton"/>
        <completion for="/requisition/actions/confirmations/severity"/>
        <completion for="/requisition/actions/confirmations/text"/>
        <completion for="/requisition/actions/confirmations/title"/>
        <completion for="/requisition/actions/createTaskExpression"/>
        <completion for="/requisition/actions/description"/>
        <completion for="/requisition/actions/editable"/>
        <completion for="/requisition/actions/label"/>
        <completion for="/requisition/actions/name"/>
        <completion for="/requisition/actions/primary"/>
        <completion for="/requisition/actions/skipValidation"/>
        <completion for="/requisition/actions/suspendUntil">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/requisition/actions/suspendUntil/expression"/>
        <completion for="/requisition/actions/suspendUntil/type">
            <value type="enum">NOW</value>
            <value type="enum">END</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/requisition/actions/suspendUntil/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/requisition/actions/type">
            <value type="enum">NEXT</value>
            <value type="enum">REJECT</value>
            <value type="enum">RELEASE</value>
            <value type="enum">SAVE</value>
            <value type="enum">STOP</value>
            <value type="enum">COMPLAIN</value>
            <value type="enum">COMMENTS</value>
            <value type="enum">CLOSE</value>
            <value type="enum">SUSPEND</value>
            <value type="enum">POSTPONE_REQUISITION</value>
            <value type="enum">TRANSFORM_CASE_TYPE</value>
            <value type="enum">REFRESH_OVERVIEW</value>
            <value type="enum">CONTINUE_REQUISITION</value>
            <value type="enum">URGE</value>
            <value type="enum">CREATE_TASK</value>
        </completion>
        <completion for="/requisition/actions/visible"/>
        <completion for="/requisition/activities">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="string" required="true">visible</value>
            <value type="string">bluePrism</value>
            <value type="string">description</value>
            <value type="object">estimatedDuration</value>
            <value type="array">fields</value>
            <value type="array">messages</value>
        </completion>
        <completion for="/requisition/activities/bluePrism"/>
        <completion for="/requisition/activities/description"/>
        <completion for="/requisition/activities/fields">
            <value type="object" required="true">editor</value>
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">selector</value>
            <value type="integer" defaultValue="1">areaWidth</value>
            <value type="string">description</value>
            <value type="string">editable</value>
            <value type="string">errorLabel</value>
            <value type="boolean">forceInit</value>
            <value type="integer" defaultValue="0">labelWidth</value>
            <value type="string">notes</value>
            <value type="string">required</value>
            <value type="string">visible</value>
            <value type="integer" defaultValue="1">width</value>
        </completion>
        <completion for="/requisition/activities/fields/areaWidth"/>
        <completion for="/requisition/activities/fields/description"/>
        <completion for="/requisition/activities/fields/editable"/>
        <completion for="/requisition/activities/fields/editor">
            <value type="string" required="true">name</value>
            <value type="array">properties</value>
        </completion>
        <completion for="/requisition/activities/fields/editor/name"/>
        <completion for="/requisition/activities/fields/editor/properties">
            <value type="string" required="true">name</value>
            <value type="object">value</value>
        </completion>
        <completion for="/requisition/activities/fields/editor/properties/name"/>
        <completion for="/requisition/activities/fields/errorLabel"/>
        <completion for="/requisition/activities/fields/forceInit"/>
        <completion for="/requisition/activities/fields/label"/>
        <completion for="/requisition/activities/fields/labelWidth"/>
        <completion for="/requisition/activities/fields/name"/>
        <completion for="/requisition/activities/fields/notes"/>
        <completion for="/requisition/activities/fields/required"/>
        <completion for="/requisition/activities/fields/selector">
            <value type="object">attributes</value>
        </completion>
        <completion for="/requisition/activities/fields/visible"/>
        <completion for="/requisition/activities/fields/width"/>
        <completion for="/requisition/activities/label"/>
        <completion for="/requisition/activities/messages">
            <value type="string" required="true">condition</value>
            <value type="string" required="true">name</value>
            <value type="string" required="true">text</value>
            <value type="object" required="true">type</value>
            <value type="boolean">closureEnabled</value>
            <value type="string">description</value>
            <value type="array">watchedAttributes</value>
        </completion>
        <completion for="/requisition/activities/messages/closureEnabled"/>
        <completion for="/requisition/activities/messages/condition"/>
        <completion for="/requisition/activities/messages/description"/>
        <completion for="/requisition/activities/messages/name"/>
        <completion for="/requisition/activities/messages/text"/>
        <completion for="/requisition/activities/messages/type">
            <value type="enum">MESSAGE</value>
            <value type="enum">WARNING</value>
            <value type="enum">ERROR</value>
            <value type="enum">INSTRUCTION</value>
        </completion>
        <completion for="/requisition/activities/name"/>
        <completion for="/requisition/activities/visible"/>
        <completion for="/requisition/avosDomain"/>
        <completion for="/requisition/description"/>
        <completion for="/requisition/expressions">
            <value type="string" required="true">name</value>
            <value type="string">description</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/requisition/expressions/description"/>
        <completion for="/requisition/expressions/expression"/>
        <completion for="/requisition/expressions/name"/>
        <completion for="/requisition/expressions/type">
            <value type="enum">PLAIN</value>
            <value type="enum">MATH</value>
        </completion>
        <completion for="/requisition/header">
            <value type="array">items</value>
        </completion>
        <completion for="/requisition/header/items">
            <value type="string" required="true">name</value>
            <value type="string">expression</value>
            <value type="string">label</value>
        </completion>
        <completion for="/requisition/header/items/expression"/>
        <completion for="/requisition/header/items/label"/>
        <completion for="/requisition/header/items/name"/>
        <completion for="/requisition/label"/>
        <completion for="/requisition/name"/>
        <completion for="/requisition/refTaskName"/>
        <completion for="/requisition/transitions">
            <value type="object" required="true">from</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">to</value>
            <value type="string">condition</value>
            <value type="string">description</value>
            <value type="string">expression</value>
            <value type="string">expressions</value>
            <value type="string">outputAttributes</value>
        </completion>
        <completion for="/requisition/transitions/condition"/>
        <completion for="/requisition/transitions/description"/>
        <completion for="/requisition/transitions/expression"/>
        <completion for="/requisition/transitions/expressions"/>
        <completion for="/requisition/transitions/from">
            <value type="string" required="true">name</value>
            <value type="object">type</value>
        </completion>
        <completion for="/requisition/transitions/from/name">
            <value type="activityName"/>
        </completion>
        <completion for="/requisition/transitions/from/type">
            <value type="enum">ACTIVITY</value>
            <value type="enum">START</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/requisition/transitions/name"/>
        <completion for="/requisition/transitions/outputAttributes"/>
        <completion for="/requisition/transitions/to">
            <value type="string" required="true">name</value>
            <value type="object">type</value>
        </completion>
        <completion for="/requisition/transitions/to/name">
            <value type="activityName"/>
        </completion>
        <completion for="/requisition/transitions/to/type">
            <value type="enum">ACTIVITY</value>
            <value type="enum">START</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/revision"/>
        <completion for="/serviceMocks">
            <value type="string" required="true">condition</value>
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">request</value>
            <value type="object" required="true">response</value>
            <value type="string">description</value>
        </completion>
        <completion for="/serviceMocks/condition"/>
        <completion for="/serviceMocks/description"/>
        <completion for="/serviceMocks/label"/>
        <completion for="/serviceMocks/name"/>
        <completion for="/shredding"/>
        <completion for="/tasks">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="boolean">startImmediately</value>
            <value type="array">actions</value>
            <value type="array">activities</value>
            <value type="string">avosDomain</value>
            <value type="array">configurations</value>
            <value type="object">defaultConfiguration</value>
            <value type="string">description</value>
            <value type="string">description</value>
            <value type="array">excludedOwner</value>
            <value type="array">expressions</value>
            <value type="object">header</value>
            <value type="array">initiator</value>
            <value type="string">onCaseTypeChangeExpression</value>
            <value type="string">onCompleteNotificationMsg</value>
            <value type="array">onCompleteNotifier</value>
            <value type="array">ponkConfigurations</value>
            <value type="array">runners</value>
            <value type="array">taskNotificationConfigurations</value>
            <value type="array">transitions</value>
        </completion>
        <completion for="/tasks/actions">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">type</value>
            <value type="array">confirmations</value>
            <value type="string">createTaskExpression</value>
            <value type="string">description</value>
            <value type="string">editable</value>
            <value type="array">justifications</value>
            <value type="boolean">primary</value>
            <value type="boolean">skipValidation</value>
            <value type="object">suspendUntil</value>
            <value type="string">visible</value>
        </completion>
        <completion for="/tasks/actions/confirmations">
            <value type="string">cancelButton</value>
            <value type="string">condition</value>
            <value type="string">note</value>
            <value type="string">okButton</value>
            <value type="string">severity</value>
            <value type="string">text</value>
            <value type="string">title</value>
        </completion>
        <completion for="/tasks/actions/confirmations/cancelButton"/>
        <completion for="/tasks/actions/confirmations/condition"/>
        <completion for="/tasks/actions/confirmations/note"/>
        <completion for="/tasks/actions/confirmations/okButton"/>
        <completion for="/tasks/actions/confirmations/severity"/>
        <completion for="/tasks/actions/confirmations/text"/>
        <completion for="/tasks/actions/confirmations/title"/>
        <completion for="/tasks/actions/createTaskExpression"/>
        <completion for="/tasks/actions/description"/>
        <completion for="/tasks/actions/editable"/>
        <completion for="/tasks/actions/label"/>
        <completion for="/tasks/actions/name"/>
        <completion for="/tasks/actions/primary"/>
        <completion for="/tasks/actions/skipValidation"/>
        <completion for="/tasks/actions/suspendUntil">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/actions/suspendUntil/expression"/>
        <completion for="/tasks/actions/suspendUntil/type">
            <value type="enum">NOW</value>
            <value type="enum">END</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/tasks/actions/suspendUntil/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/actions/type">
            <value type="enum">NEXT</value>
            <value type="enum">REJECT</value>
            <value type="enum">RELEASE</value>
            <value type="enum">SAVE</value>
            <value type="enum">STOP</value>
            <value type="enum">COMPLAIN</value>
            <value type="enum">COMMENTS</value>
            <value type="enum">CLOSE</value>
            <value type="enum">SUSPEND</value>
            <value type="enum">POSTPONE_REQUISITION</value>
            <value type="enum">TRANSFORM_CASE_TYPE</value>
            <value type="enum">REFRESH_OVERVIEW</value>
            <value type="enum">CONTINUE_REQUISITION</value>
            <value type="enum">URGE</value>
            <value type="enum">CREATE_TASK</value>
        </completion>
        <completion for="/tasks/actions/visible"/>
        <completion for="/tasks/activities">
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="string" required="true">visible</value>
            <value type="string">bluePrism</value>
            <value type="string">description</value>
            <value type="object">estimatedDuration</value>
            <value type="array">fields</value>
            <value type="array">messages</value>
        </completion>
        <completion for="/tasks/activities/bluePrism"/>
        <completion for="/tasks/activities/description"/>
        <completion for="/tasks/activities/fields">
            <value type="object" required="true">editor</value>
            <value type="string" required="true">label</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">selector</value>
            <value type="integer" defaultValue="1">areaWidth</value>
            <value type="string">description</value>
            <value type="string">editable</value>
            <value type="string">errorLabel</value>
            <value type="boolean">forceInit</value>
            <value type="integer" defaultValue="0">labelWidth</value>
            <value type="string">notes</value>
            <value type="string">required</value>
            <value type="string">visible</value>
            <value type="integer" defaultValue="1">width</value>
        </completion>
        <completion for="/tasks/activities/fields/areaWidth"/>
        <completion for="/tasks/activities/fields/description"/>
        <completion for="/tasks/activities/fields/editable"/>
        <completion for="/tasks/activities/fields/editor">
            <value type="string" required="true">name</value>
            <value type="array">properties</value>
        </completion>
        <completion for="/tasks/activities/fields/editor/name">
            <value type="editorName"/>
        </completion>
        <completion for="/tasks/activities/fields/editor/properties">
            <value type="string" required="true">name</value>
            <value type="object">value</value>
        </completion>
        <completion for="/tasks/activities/fields/editor/properties/name">
            <value type="editorPropertyName"/>
        </completion>
        <completion for="/tasks/activities/fields/errorLabel"/>
        <completion for="/tasks/activities/fields/forceInit"/>
        <completion for="/tasks/activities/fields/label"/>
        <completion for="/tasks/activities/fields/labelWidth"/>
        <completion for="/tasks/activities/fields/name"/>
        <completion for="/tasks/activities/fields/notes"/>
        <completion for="/tasks/activities/fields/required"/>
        <completion for="/tasks/activities/fields/selector">
            <value type="object">attributes</value>
        </completion>
        <completion for="/tasks/activities/fields/selector/attributes">
            <value type="selectorName"/>
        </completion>
        <completion for="/tasks/activities/fields/selector/attributes/*">
            <value type="string" required="true">name</value>
        </completion>
        <completion for="/tasks/activities/fields/selector/attributes/*/name">
            <value type="selectorAttributeName"/>
        </completion>
        <completion for="/tasks/activities/fields/visible"/>
        <completion for="/tasks/activities/fields/width"/>
        <completion for="/tasks/activities/label"/>
        <completion for="/tasks/activities/messages">
            <value type="string" required="true">condition</value>
            <value type="string" required="true">name</value>
            <value type="string" required="true">text</value>
            <value type="object" required="true">type</value>
            <value type="boolean">closureEnabled</value>
            <value type="string">description</value>
            <value type="array">watchedAttributes</value>
        </completion>
        <completion for="/tasks/activities/messages/closureEnabled"/>
        <completion for="/tasks/activities/messages/condition"/>
        <completion for="/tasks/activities/messages/description"/>
        <completion for="/tasks/activities/messages/name"/>
        <completion for="/tasks/activities/messages/text"/>
        <completion for="/tasks/activities/messages/type">
            <value type="enum">MESSAGE</value>
            <value type="enum">WARNING</value>
            <value type="enum">ERROR</value>
            <value type="enum">INSTRUCTION</value>
        </completion>
        <completion for="/tasks/activities/name"/>
        <completion for="/tasks/activities/visible"/>
        <completion for="/tasks/avosDomain"/>
        <completion for="/tasks/configurations">
            <value type="string" required="true">condition</value>
            <value type="object" required="true">dueDate</value>
            <value type="object" required="true">latestAssigningDate</value>
            <value type="string">businessAdministrator</value>
            <value type="string">businessAdministratorExpression</value>
            <value type="integer">calendarId</value>
            <value type="object">earliestAssigningDate</value>
            <value type="object">expirationDate</value>
            <value type="object">expirationNotification</value>
            <value type="object">firstEscalation</value>
            <value type="string">notificationRole</value>
            <value type="string">potentialOwner</value>
            <value type="string">potentialOwnerExpression</value>
            <value type="object">queueDifference</value>
            <value type="object">secondEscalation</value>
        </completion>
        <completion for="/tasks/configurations/businessAdministrator"/>
        <completion for="/tasks/configurations/businessAdministratorExpression"/>
        <completion for="/tasks/configurations/calendarId"/>
        <completion for="/tasks/configurations/condition"/>
        <completion for="/tasks/configurations/dueDate">
            <value type="string" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/configurations/dueDate/duration"
                    ref="/configurations/estimatedCompletionDate/duration"/>
        <completion for="/tasks/configurations/dueDate/expression"/>
        <completion for="/tasks/configurations/dueDate/type">
            <value type="enum">NOW</value>
            <value type="enum">END</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/tasks/configurations/dueDate/unit" ref="/configurations/estimatedCompletionDate/unit"/>
        <completion for="/tasks/configurations/earliestAssigningDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="object">endOfMonth</value>
            <value type="object">endOfMonthShiftForward</value>
            <value type="string">expression</value>
            <value type="object">morningTime</value>
            <value type="object">workDayShiftForward</value>
        </completion>
        <completion for="/tasks/configurations/earliestAssigningDate/endOfMonth"/>
        <completion for="/tasks/configurations/earliestAssigningDate/endOfMonthShiftForward"/>
        <completion for="/tasks/configurations/earliestAssigningDate/expression"/>
        <completion for="/tasks/configurations/earliestAssigningDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/configurations/earliestAssigningDate/workDayShiftForward"/>
        <completion for="/tasks/configurations/expirationDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="object">endOfMonth</value>
            <value type="string">expression</value>
        </completion>
        <completion for="/tasks/configurations/expirationDate/endOfMonth"/>
        <completion for="/tasks/configurations/expirationDate/expression"/>
        <completion for="/tasks/configurations/expirationDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/configurations/expirationNotification">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
        </completion>
        <completion for="/tasks/configurations/expirationNotification/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/configurations/firstEscalation">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
        </completion>
        <completion for="/tasks/configurations/firstEscalation/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/configurations/latestAssigningDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/configurations/latestAssigningDate/expression"/>
        <completion for="/tasks/configurations/latestAssigningDate/type">
            <value type="enum">FIXED</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/tasks/configurations/latestAssigningDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/configurations/notificationRole"/>
        <completion for="/tasks/configurations/potentialOwner"/>
        <completion for="/tasks/configurations/potentialOwnerExpression"/>
        <completion for="/tasks/configurations/secondEscalation">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
        </completion>
        <completion for="/tasks/configurations/secondEscalation/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/defaultConfiguration">
            <value type="string" required="true">condition</value>
            <value type="object" required="true">dueDate</value>
            <value type="object" required="true">latestAssigningDate</value>
            <value type="string">businessAdministrator</value>
            <value type="string">businessAdministratorExpression</value>
            <value type="integer">calendarId</value>
            <value type="object">earliestAssigningDate</value>
            <value type="object">expirationDate</value>
            <value type="object">expirationNotification</value>
            <value type="object">firstEscalation</value>
            <value type="string">notificationRole</value>
            <value type="string">potentialOwner</value>
            <value type="string">potentialOwnerExpression</value>
            <value type="object">queueDifference</value>
            <value type="object">secondEscalation</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/businessAdministrator"/>
        <completion for="/tasks/defaultConfiguration/businessAdministratorExpression"/>
        <completion for="/tasks/defaultConfiguration/calendarId"/>
        <completion for="/tasks/defaultConfiguration/condition"/>
        <completion for="/tasks/defaultConfiguration/dueDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/dueDate/expression"/>
        <completion for="/tasks/defaultConfiguration/dueDate/type">
            <value type="enum">NOW</value>
            <value type="enum">END</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/dueDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/earliestAssigningDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="object">endOfMonth</value>
            <value type="object">endOfMonthShiftForward</value>
            <value type="string">expression</value>
            <value type="object">morningTime</value>
            <value type="object">workDayShiftForward</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/earliestAssigningDate/endOfMonth"/>
        <completion for="/tasks/defaultConfiguration/earliestAssigningDate/endOfMonthShiftForward"/>
        <completion for="/tasks/defaultConfiguration/earliestAssigningDate/expression"/>
        <completion for="/tasks/defaultConfiguration/earliestAssigningDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/earliestAssigningDate/workDayShiftForward"/>
        <completion for="/tasks/defaultConfiguration/expirationDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="object">endOfMonth</value>
            <value type="string">expression</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/expirationDate/endOfMonth"/>
        <completion for="/tasks/defaultConfiguration/expirationDate/expression"/>
        <completion for="/tasks/defaultConfiguration/expirationDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/expirationNotification">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/expirationNotification/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/firstEscalation">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/firstEscalation/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/latestAssigningDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/latestAssigningDate/expression"/>
        <completion for="/tasks/defaultConfiguration/latestAssigningDate/type">
            <value type="enum">FIXED</value>
            <value type="enum">PATTERN</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/latestAssigningDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/notificationRole"/>
        <completion for="/tasks/defaultConfiguration/potentialOwner"/>
        <completion for="/tasks/defaultConfiguration/potentialOwnerExpression"/>
        <completion for="/tasks/defaultConfiguration/secondEscalation">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
        </completion>
        <completion for="/tasks/defaultConfiguration/secondEscalation/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/description"/>
        <completion for="/tasks/excludedOwner">
            <value type="string" required="true">condition</value>
            <value type="object" required="true">type</value>
            <value type="string">expression</value>
            <value type="boolean">sendAutoClaimNotification</value>
            <value type="string">taskName</value>
        </completion>
        <completion for="/tasks/excludedOwner/condition"/>
        <completion for="/tasks/excludedOwner/expression"/>
        <completion for="/tasks/excludedOwner/sendAutoClaimNotification"/>
        <completion for="/tasks/excludedOwner/taskName"/>
        <completion for="/tasks/excludedOwner/type">
            <value type="enum">LO</value>
            <value type="enum">FO</value>
            <value type="enum">CI</value>
            <value type="enum">EX</value>
        </completion>
        <completion for="/tasks/expressions">
            <value type="string" required="true">name</value>
            <value type="string">description</value>
            <value type="string">expression</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/expressions/description"/>
        <completion for="/tasks/expressions/expression"/>
        <completion for="/tasks/expressions/name"/>
        <completion for="/tasks/expressions/type">
            <value type="enum">PLAIN</value>
            <value type="enum">MATH</value>
        </completion>
        <completion for="/tasks/header">
            <value type="array">items</value>
        </completion>
        <completion for="/tasks/header/items">
            <value type="string" required="true">name</value>
            <value type="string">expression</value>
            <value type="string">label</value>
        </completion>
        <completion for="/tasks/header/items/expression"/>
        <completion for="/tasks/header/items/label"/>
        <completion for="/tasks/header/items/name"/>
        <completion for="/tasks/initiator">
            <value type="string" required="true">condition</value>
            <value type="object" required="true">type</value>
            <value type="string">expression</value>
            <value type="boolean">sendAutoClaimNotification</value>
            <value type="string">taskName</value>
        </completion>
        <completion for="/tasks/initiator/condition"/>
        <completion for="/tasks/initiator/expression"/>
        <completion for="/tasks/initiator/sendAutoClaimNotification"/>
        <completion for="/tasks/initiator/taskName"/>
        <completion for="/tasks/initiator/type">
            <value type="enum">LO</value>
            <value type="enum">FO</value>
            <value type="enum">CI</value>
            <value type="enum">EX</value>
        </completion>
        <completion for="/tasks/label"/>
        <completion for="/tasks/name"/>
        <completion for="/tasks/onCaseTypeChangeExpression"/>
        <completion for="/tasks/onCompleteNotificationMsg"/>
        <completion for="/tasks/onCompleteNotifier">
            <value type="string" required="true">condition</value>
            <value type="object" required="true">type</value>
            <value type="string">expression</value>
            <value type="boolean">sendAutoClaimNotification</value>
            <value type="string">taskName</value>
        </completion>
        <completion for="/tasks/onCompleteNotifier/condition"/>
        <completion for="/tasks/onCompleteNotifier/expression"/>
        <completion for="/tasks/onCompleteNotifier/sendAutoClaimNotification"/>
        <completion for="/tasks/onCompleteNotifier/taskName"/>
        <completion for="/tasks/onCompleteNotifier/type">
            <value type="enum">LO</value>
            <value type="enum">FO</value>
            <value type="enum">CI</value>
            <value type="enum">EX</value>
        </completion>
        <completion for="/tasks/ponkConfigurations">
            <value type="string" required="true">cluidExpression</value>
            <value type="string" required="true">condition</value>
            <value type="string">accountExpression</value>
            <value type="array">activitiesExpression</value>
            <value type="string">agreementNumberExpression</value>
            <value type="string">applicationNumberExpression</value>
            <value type="string">caseTypeExpression</value>
            <value type="string">clientTaskReference</value>
            <value type="string">createdByExpression</value>
            <value type="string">descriptionExpression</value>
            <value type="object">documentsExpressions</value>
            <value type="string">escalationDateExpression</value>
            <value type="string">expirationDateExpression</value>
            <value type="string">exportTypeExpression</value>
            <value type="string">labelExpression</value>
            <value type="string">notificationsExpression</value>
            <value type="string">productClassificationExpression</value>
            <value type="string">sourceSystemExpression</value>
        </completion>
        <completion for="/tasks/ponkConfigurations/accountExpression"/>
        <completion for="/tasks/ponkConfigurations/agreementNumberExpression"/>
        <completion for="/tasks/ponkConfigurations/applicationNumberExpression"/>
        <completion for="/tasks/ponkConfigurations/caseTypeExpression"/>
        <completion for="/tasks/ponkConfigurations/clientTaskReference"/>
        <completion for="/tasks/ponkConfigurations/cluidExpression"/>
        <completion for="/tasks/ponkConfigurations/condition"/>
        <completion for="/tasks/ponkConfigurations/createdByExpression"/>
        <completion for="/tasks/ponkConfigurations/descriptionExpression"/>
        <completion for="/tasks/ponkConfigurations/escalationDateExpression"/>
        <completion for="/tasks/ponkConfigurations/expirationDateExpression"/>
        <completion for="/tasks/ponkConfigurations/exportTypeExpression"/>
        <completion for="/tasks/ponkConfigurations/labelExpression"/>
        <completion for="/tasks/ponkConfigurations/notificationsExpression"/>
        <completion for="/tasks/ponkConfigurations/productClassificationExpression"/>
        <completion for="/tasks/ponkConfigurations/sourceSystemExpression"/>
        <completion for="/tasks/runners">
            <value type="string" required="true">condition</value>
            <value type="string" required="true">name</value>
            <value type="array">configurations</value>
            <value type="string">description</value>
            <value type="object">responseMapping</value>
            <value type="array">serviceProperties</value>
        </completion>
        <completion for="/tasks/runners/condition"/>
        <completion for="/tasks/runners/configurations">
            <value type="string" required="true">condition</value>
            <value type="string">cron</value>
            <value type="object">expirationDate</value>
            <value type="string">firstExecutionExpression</value>
            <value type="string">repeatUntil</value>
            <value type="object">runImmediately</value>
        </completion>
        <completion for="/tasks/runners/configurations/condition"/>
        <completion for="/tasks/runners/configurations/cron"/>
        <completion for="/tasks/runners/configurations/expirationDate">
            <value type="object" required="true">duration</value>
            <value type="object" required="true">unit</value>
        </completion>
        <completion for="/tasks/runners/configurations/expirationDate/unit">
            <value type="enum">LABOURUNIT</value>
            <value type="enum">CALENDARUNIT</value>
        </completion>
        <completion for="/tasks/runners/configurations/firstExecutionExpression"/>
        <completion for="/tasks/runners/configurations/repeatUntil"/>
        <completion for="/tasks/runners/configurations/runImmediately"/>
        <completion for="/tasks/runners/description"/>
        <completion for="/tasks/runners/name"/>
        <completion for="/tasks/runners/responseMapping">
            <value type="object" required="true">type</value>
            <value type="string">error</value>
            <value type="string">statusCode</value>
            <value type="string">success</value>
            <value type="array">values</value>
        </completion>
        <completion for="/tasks/runners/responseMapping/error"/>
        <completion for="/tasks/runners/responseMapping/statusCode"/>
        <completion for="/tasks/runners/responseMapping/success"/>
        <completion for="/tasks/runners/responseMapping/type">
            <value type="enum">XPATH</value>
            <value type="enum">JSONPATH</value>
        </completion>
        <completion for="/tasks/runners/responseMapping/values">
            <value type="string" required="true">attribute</value>
            <value type="string" required="true">path</value>
            <value type="object">onError</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/runners/responseMapping/values/attribute"/>
        <completion for="/tasks/runners/responseMapping/values/onError"/>
        <completion for="/tasks/runners/responseMapping/values/path"/>
        <completion for="/tasks/runners/responseMapping/values/type">
            <value type="enum">STRING</value>
            <value type="enum">LIST</value>
            <value type="enum">MAP</value>
            <value type="enum">JSON</value>
            <value type="enum">NUMBER</value>
            <value type="enum">DECIMAL</value>
        </completion>
        <completion for="/tasks/runners/serviceProperties">
            <value type="string" required="true">name</value>
            <value type="string" required="true">value</value>
        </completion>
        <completion for="/tasks/runners/serviceProperties/name"/>
        <completion for="/tasks/runners/serviceProperties/value"/>
        <completion for="/tasks/taskNotificationConfigurations">
            <value type="object">eventType</value>
            <value type="string">expression</value>
            <value type="string">notificationTemplate</value>
            <value type="string">notificationType</value>
            <value type="string">notifierRoleExpression</value>
            <value type="string">notifierUsernameExpression</value>
        </completion>
        <completion for="/tasks/taskNotificationConfigurations/eventType">
            <value type="enum">REQUISITION_CREATED</value>
            <value type="enum">REQUISITION_COMPLETED</value>
            <value type="enum">HT_COMPLETED</value>
            <value type="enum">HT_REJECTED</value>
            <value type="enum">HT_STOPPED</value>
            <value type="enum">HT_RELEASED</value>
            <value type="enum">HT_CREATED</value>
            <value type="enum">HT_DELEGATED</value>
            <value type="enum">HT_SUSPENDED</value>
            <value type="enum">HT_RESTORED</value>
            <value type="enum">HT_ESCALATED</value>
            <value type="enum">HT_EXPIRED</value>
            <value type="enum">HT_TAKEN</value>
            <value type="enum">HT_CLAIMED</value>
            <value type="enum">HT_SET_PRIORITY</value>
            <value type="enum">HT_SET_OUTPUT</value>
            <value type="enum">HT_CANCELLED</value>
            <value type="enum">HT_ACTIVATED</value>
            <value type="enum">ACTIVITY_CLOSED</value>
            <value type="enum">ACTIVITY_OPENED</value>
            <value type="enum">ATTRIBUTE_CHANGED</value>
            <value type="enum">NODE_STARTED</value>
            <value type="enum">PROCESS_DEPLOYED</value>
            <value type="enum">CASE_CREATED</value>
            <value type="enum">CASE_COMPLETED</value>
            <value type="enum">CASE_WAITING</value>
            <value type="enum">CASE_RUNNING</value>
            <value type="enum">CASE_UPDATED</value>
            <value type="enum">CASE_RECONCILE</value>
            <value type="enum">CASE_PHASE_CHANGED</value>
            <value type="enum">RUNNER_CREATED</value>
            <value type="enum">RUNNER_SCHEDULED</value>
            <value type="enum">RUNNER_RUNNING</value>
            <value type="enum">RUNNER_COMPLETED</value>
            <value type="enum">RUNNER_REPEATING</value>
            <value type="enum">RUNNER_FAILING</value>
            <value type="enum">RUNNER_EXPIRED</value>
            <value type="enum">RUNNER_HUMANTASK</value>
            <value type="enum">RUNNER_TERMINATED</value>
            <value type="enum">CLIENT_TASK_CREATED</value>
            <value type="enum">CLIENT_TASK_UPDATED</value>
            <value type="enum">CLIENT_TASK_COMPLETED</value>
            <value type="enum">CLIENT_ACTIVITY_CREATED</value>
            <value type="enum">CLIENT_ACTIVITY_UPDATED</value>
            <value type="enum">CLIENT_ACTIVITY_DELETED</value>
            <value type="enum">CLIENT_ACTIVITY_TERMINATED</value>
            <value type="enum">CLIENT_FILE_CREATED</value>
            <value type="enum">CLIENT_FILE_UPDATED</value>
            <value type="enum">CLIENT_FILE_DELETED</value>
        </completion>
        <completion for="/tasks/taskNotificationConfigurations/expression"/>
        <completion for="/tasks/taskNotificationConfigurations/notificationTemplate"/>
        <completion for="/tasks/taskNotificationConfigurations/notificationType"/>
        <completion for="/tasks/taskNotificationConfigurations/notifierRoleExpression"/>
        <completion for="/tasks/taskNotificationConfigurations/notifierUsernameExpression"/>
        <completion for="/tasks/transitions">
            <value type="object" required="true">from</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">to</value>
            <value type="string">condition</value>
            <value type="string">description</value>
            <value type="string">expression</value>
            <value type="string">expressions</value>
            <value type="string">outputAttributes</value>
        </completion>
        <completion for="/tasks/transitions/condition"/>
        <completion for="/tasks/transitions/description"/>
        <completion for="/tasks/transitions/expression"/>
        <completion for="/tasks/transitions/expressions"/>
        <completion for="/tasks/transitions/from">
            <value type="string" required="true">name</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/transitions/from/name">
            <value type="activityName"/>
        </completion>
        <completion for="/tasks/transitions/from/type">
            <value type="enum">ACTIVITY</value>
            <value type="enum">START</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/tasks/transitions/name">
            <value type="activityName"/>
        </completion>
        <completion for="/tasks/transitions/outputAttributes"/>
        <completion for="/tasks/transitions/to">
            <value type="string" required="true">name</value>
            <value type="object">type</value>
        </completion>
        <completion for="/tasks/transitions/to/name">
            <value type="activityName"/>
        </completion>
        <completion for="/tasks/transitions/to/type">
            <value type="enum">ACTIVITY</value>
            <value type="enum">START</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/transitions">
            <value type="object" required="true">from</value>
            <value type="string" required="true">name</value>
            <value type="object" required="true">to</value>
            <value type="string">casePhase</value>
            <value type="string">condition</value>
            <value type="string">description</value>
            <value type="string">expressions</value>
            <value type="string">outputAttributes</value>
            <value type="object">transitionType</value>
        </completion>
        <completion for="/transitions/casePhase"/>
        <completion for="/transitions/condition"/>
        <completion for="/transitions/description"/>
        <completion for="/transitions/expressions"/>
        <completion for="/transitions/from">
            <value type="string">name</value>
            <value type="object">type</value>
        </completion>

        <completion for="/transitions/from/type">
            <value type="enum">TASK</value>
            <value type="enum">START</value>
            <value type="enum">WAIT</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/transitions/name"/>
        <completion for="/transitions/outputAttributes"/>
        <completion for="/transitions/to">
            <value type="string">name</value>
            <value type="object">type</value>
        </completion>
        <completion for="/transitions/to/name">
            <value type="taskName"/>
        </completion>
        <completion for="/transitions/from/name">
            <value type="taskName"/>
        </completion>
        <completion for="/transitions/to/type">
            <value type="enum">TASK</value>
            <value type="enum">START</value>
            <value type="enum">WAIT</value>
            <value type="enum">END</value>
        </completion>
        <completion for="/transitions/transitionType">
            <value type="enum">STANDARD</value>
            <value type="enum">EXPIRED</value>
        </completion>
        <completion for="/uniqueHashAttributesExpression"/>
        <completion for="/validationType">
            <value type="enum">VALIDATE_ONLY</value>
            <value type="enum">VALIDATE_COMPLETE</value>
            <value type="enum">VALIDATE_FULL</value>
        </completion>
    </completions>
</profile>
