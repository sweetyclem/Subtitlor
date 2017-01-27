<!DOCTYPE html>
<html>
<head>
<title>Editer les sous-titres</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	<h1>Editer les sous titres</h1>
    <form method="post" action="/Subtitlor/edit">    
        <input type="submit" style="position:fixed; top: 10px; right: 10px;" />
        <a style="position:fixed; top: 40px; right: 40px;" href="/Subtitlor/download">Télécharger</a>
	    <table>
	        <c:forEach items="${ subtitles }" var="line" varStatus="loop">
	        	<tr style="border: solid 1px black">
	        		<td>
	        		<%-- If it's not text, don't display the line --%>
	        		<c:choose>
	        			<c:when test="${ line.matches('^[^a-z]+') || (empty line) }">
	        			</c:when>
	        		<%-- If it's text display the original line --%>
		        		<c:otherwise>
		        			<c:out value="${ line }"/>
		        		</td>
		        		<td>
		        		</c:otherwise>
	        		</c:choose>
	        		<%-- If it's not text, don't display the line --%>
	        		<c:choose>
		        		<c:when test="${ line.matches('^[^a-z]+') || (empty line) }">
		        		</c:when>
		        		<%-- If it's text display an input for the translated line --%>
		        		<c:otherwise>	        		
		        			<input type="text" name="line${ loop.index }" id="line${ loop.index }" size="35" value='<c:if test="${ line != translation[loop.index] }">${ translation[loop.index] }</c:if>' />
		        		</c:otherwise>
	        		</c:choose>
	        		</td>
	        	</tr>
	    	</c:forEach>
	    </table>
	    
    </form>
</body>
</html>