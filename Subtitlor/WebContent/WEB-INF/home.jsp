<html>
<body>
	<%@ include file="header.jsp" %>
	<h1>Bienvenue dans l'éditeur de sous titres</h1>
	<c:choose>
		<c:when test="${ exists == false }">
		<p><c:out value="Le fichier ${ file } a été uploadé !" /><br>
			<a href="/Subtitlor/edit?file=${ file }">Editer</a>
		</p>
		</c:when>
		<c:when test="${ exists == true && !empty file}">
		<p><c:out value="Le fichier ${ file } existe déjà" /><br>
			<a href="/Subtitlor/edit?file=${ file }">Editer</a>
		</p>
		</c:when>
	</c:choose>
	<h2>Télécharger un fichier srt</h2>
	<form action="<c:url value="/home"/>" method="post" enctype="multipart/form-data">
		<fieldset>
                <legend>Envoi de fichier</legend>
				<br>
                <label for="file">Emplacement du fichier</label>
                <input type="file" name="file" />
                <br />
                <br>
                <input type="submit" value="Envoyer"/>
                <br />                
		</fieldset>
	</form>
	<h2>Sous titres existants</h2>
	<ul>
	<c:if test="${ !empty error }">
		<c:out value="${ error }"></c:out>
	</c:if>
	<c:forEach items="${ srtFiles }" var="file" varStatus="loop">
		<li>
			<c:out value="${ file.name }"></c:out>
			<a href="/Subtitlor/edit?file=${ file.name }">Editer</a>
		</li>
	</c:forEach>
	</ul>
</body>
</html>