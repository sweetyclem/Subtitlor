<html>
<body>
	<%@ include file="header.jsp" %>
	<h1>T�l�chargement</h1>
	<c:out value="${ fileName }  ${ filePath }"></c:out>
	<p><c:out value="Le fichier ${ fileName } a �t� t�l�charg� !" /><br>
	</p>
</body>
</html>