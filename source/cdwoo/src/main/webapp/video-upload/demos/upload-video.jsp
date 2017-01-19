<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../demos/css/bootstrap.min.css" rel="stylesheet">

</head>
<script src="../../resources/js/jquery.min.js"></script>
<script type="text/javascript" src="../demos/js/bootstrap.min.js"> </script>
<body>
  <button id="upload"> upload </button>
  
  <div class="modal fade" id="uploadModal" >
    <div class="modal-dialog" style="width:70%;height:800px">
      <iframe src="http://local.fang.com:8080/cdwoo/video-upload/demos/index.html" style="width:100%;height:100%">
      </iframe>
   </div>
  </div>
  
  <dir id="pathShowArea">
    这里显示视频的信息···</br>
  </dir>
</body>
<script type="text/javascript">
	$("#upload").click(function(){
		$("#uploadModal").modal("show");
	})
</script>
</html>