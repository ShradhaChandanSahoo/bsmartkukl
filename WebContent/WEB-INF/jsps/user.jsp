<%@include file="/WEB-INF/decorators/taglibs.jsp"%>
<div class="row">
 <FORM action="./usersubmit" METHOD="GET">
<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0>
 	<TR id="nameid">
 			<TD>Name</TD>
 			<TD>
 				<INPUT TYPE=TEXT NAME="name">
 			</TD>
 	</TR>
 	<TR id="usernameid">
 			<TD>User Name</TD>
 			<TD>
 				<INPUT TYPE=TEXT NAME="username">
 			</TD>
 	</TR>
 	<TR id="passwordid">
 			<TD>Password</TD>
 			<TD>
 				<INPUT TYPE=password NAME="password">
 			</TD>
 	</TR>
 	<c:forEach var="entry" items="${data}"> 
 	
 	<TR id="${entry.field_name}id">
 			<TD>${entry.label_name}:</TD>
 			<TD>
 				<INPUT TYPE=${entry.field_type} NAME=${entry.field_name}>
 			</TD>
 		</TR>
</c:forEach>
 	
</TABLE>
 <INPUT TYPE=SUBMIT VALUE=Submit>
 </FORM>
 <hr size="2">
 <table border="1">
 
 
 </table>
 
 
</div>