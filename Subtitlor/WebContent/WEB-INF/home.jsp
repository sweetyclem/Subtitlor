<html>
<body>
	<%@ include file="header.jsp" %>
	<h1>Bienvenue dans l'éditeur de sous titres</h1>
	<c:if test="${ !empty file }">
	<p><c:out value="Le fichier ${ file } a été uploadé !" /><br>
	<a href="/Subtitlor/edit?file=${ file }">Editer</a>
	</p>
	<br>
	</c:if>
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
</body>
</html>