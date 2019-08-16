<html>
<head>
    <title>Twitter_test</title>
</head>
<body>
<div>
    Twitter
</div>
<form method="post" action="/item">
    <input type="text" name="channel" placeholder="Write channel">
    <br>
    <label>Example : @nasa </label>
    <br>
    <input type="submit" value="Получить последние 10 твитов">
</form>
<div>
    <#list itemsFromServer as items>
        <h3>Tweet</h3>
        ${items.getTitle()}<br/>
        ${items.getData()}<br/>
        ${items.getText()}<br/>
        <#if items.getUrl() == "none">
            <label>none</label>
        <#else>
            <img src="${items.getUrl()}">
        </#if>
    </#list>
</div>

</body>
</html>