<html>
<body>
	<%@ include file="header.jsp" %>
	<h1>Téléchargement</h1>
	<c:out value="${ fileName }  ${ filePath }"></c:out>
	<p><c:out value="Le fichier ${ fileName } a été téléchargé !" /><br>
	</p>
</body>
</html>