<!DOCTYPE html>
<html>
<head>
<title>Editer les sous-titres</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	<h1>Editer les sous titres</h1>

	
    <form method="post" action="/Subtitlor/edit?file=${ fileName }">    
        <input class="btn" type="submit" value="Enregistrer" style="position:fixed; top: 90px; right: 100px;" />
        <%-- Display the download link only if there's a translation --%>
        <c:if test="${ !empty newFile || !empty translatedFiless }">
        	<a class="btn" style="position:fixed; top: 130px; right: 100px;" href="/Subtitlor/download">TÚlÚcharger</a>
	    </c:if>
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