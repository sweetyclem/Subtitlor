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
	    <table>
	        <c:forEach items="${ subtitles }" var="line" varStatus="loop">
	        	<tr style="border: solid 1px black">
	        		<td><c:out value="${ line }" /></td>
	        		<td>
	        		<c:choose>
	        		<c:when test="${ line.matches('[^a-z]+') }">
	        			<c:out value="${ line }" />
	        		</c:when>
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