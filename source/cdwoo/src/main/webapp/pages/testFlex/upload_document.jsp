<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%String contextPath = (String) request.getContextPath(); %>
<title>UPLOAD-TEST</title>
</head>
<script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.min.js"></script>
<body>
  <form id="fileForm">
    <input type="file" name="documentFile"/>
    <span id="nameSpan"></span>
  </form>
  <input type="button"  value="提交文档"  id="uploadBt"/>
  <input type="button"  value="查看文档"  id="viewBt"/>
</body>
 
<script type="text/javascript">
    var docName = "";
	$("#uploadBt").click(function(){
		var formData = new FormData($("#fileForm")[0]);
		$.ajax({
	         url: "<%=contextPath %>/upload/uploadDocument.do" ,  
	         type: 'POST',  
	         data: formData,
	         async: false,
	         cache: false,  
	         contentType: false,  
	         processData: false,  
	         success: function (result) {
	        	 if (result.hasErrors) {
	        		 alert(result.errorMessage);
	        	 }else {
	        		 docName = result.data;
	        		$("#nameSpan").html(result.data);
	        	 }
	         }
	    });
	});
	
	$("#viewBt").click(function(){
		window.open("<%=contextPath %>/upload/getDocument.do?documentName="+docName); 
	});
</script>
</html>