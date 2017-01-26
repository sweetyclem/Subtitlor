<!DOCTYPE html>
<html>
<head>
<title>Editer les sous-titres</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	<c:if test="${ !empty file }">
	<p><c:out value="${ file }" /><br>
	</c:if>
    <form method="post" action="/Subtitlor/edit">    
        <input type="submit" style="position:fixed; top: 10px; right: 10px;" />
	    <table>
	        <c:forEach items="${ subtitles }" var="line" varStatus="status">
	        	<tr style="border: solid 1px black">
	        		<td><c:out value="${ line }" /></td>
	        		<td>
	        		<c:choose>
	        		<c:when test="${ line.matches('[^0-9]+') }">
	        		<input type="text" name="line${ status.index }" id="line${ status.index }" size="35" />
	        		</c:when>
	        		<c:otherwise>
	        		<c:out value="${ line }" />
	        		</c:otherwise>
	        		</c:choose>
	        		</td>
	        	</tr>
	    	</c:forEach>
	    </table>
    </form>
</body>
</html>