<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>upload-video</title>
<link href="../demos/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../demos/css/uploader.css" rel="stylesheet" />
<link rel="stylesheet" href="../demos/css/demo.css" rel="stylesheet" />
<link href="../demos/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
      .sr-only {
          position: absolute;
          width: auto;
          height: auto;
          padding-top: 0px;
          margin-top: 20px;
          border: 0;
          clip: auto;
      }
</style>
</head>
<script src="../../resources/js/jquery.min.js"></script>
<script type="text/javascript" src="../demos/js/bootstrap.min.js"> </script>
<script type="text/javascript" src="../demos/js/demo.js"></script>
<script type="text/javascript" src="../src/dmuploader.js"></script>
<body>
  <button id="upload"> upload </button>
  <div class="modal fade" id="uploadModal" >
    <div class="modal-dialog" style="width:70%;height:800px;background:#00C5CD">
      <!-- <iframe src="http://local.fang.com:8080/cdwoo/video-upload/demos/index.html" style="width:100%;height:100%">
      </iframe> -->
      <div role="document">
          <div class="container demo-wrapper">
          <div class="page-header">
            <h1>UPLOAD <small></small></h1>
          </div>
          <div class="row demo-columns">
            <div class="col-md-6">
              <!-- D&D Zone-->
              <div id="drag-and-drop-zone" class="uploader" style="background:#FFFFFF">
                <div>Drag &amp; Drop Video Here</div>
                <div class="or">-or-</div>
                <div class="browser">
                  <label>
                    <span>Click to open the file Browser</span>
                    <input type="file" name="files[]" title='Click to add Files'>
                  </label>
                </div>
              </div>
              <!-- /D&D Zone -->
    
              <!-- Debug box -->
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h3 class="panel-title">Debug</h3>
                </div>
                <div class="panel-body demo-panel-debug">
                  <ul id="demo-debug">
                  </ul>
                </div>
              </div>
              <!-- /Debug box -->
            </div>
            <!-- / Left column -->
    
            <div class="col-md-6">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h3 class="panel-title">Uploads</h3>
                </div>
                <div class="panel-body demo-panel-files" id='demo-files'>
                  <span class="demo-note">No Files have been selected/droped yet...</span>
                </div>
              </div>
            </div>
            <!-- / Right column -->
          </div>
        </div>
      </div>
   </div>
  </div>
  
  <dir id="pathShowArea">
  </dir>
</body>
<script type="text/javascript">
    var xhrAbort;
	$("#upload").click(function(){
		$("#uploadModal").modal("show");
	});
	function cancleUpload(){
		alert("		cancle upload");
		xhrAbort.abort();
	}
    $('#drag-and-drop-zone').dmUploader({
        dataType: 'json',
        allowedTypes: '*',
        extFilter: 'mp4;mkv;rmvb;mov;wmv;flv;3gp;avi;rm',
        maxFileSize: 1024000000,
        url:"http://local.fang.com:8080/cdwoo/upload/uploadDocument.do",
        /*extFilter: 'jpg;png;gif',*/
        onInit: function(){
          $.danidemo.addLog('#demo-debug', 'default', 'Plugin initialized correctly');
        },
        onBeforeUpload: function(id){
          $.danidemo.addLog('#demo-debug', 'default', 'Starting the upload of #' + id);
//           $.danidemo.updateFileStatus(id, 'default', 'Uploading...');
        },
        onNewFile: function(id, file){
          $.danidemo.addFile('#demo-files', id, file);
        },
        onComplete: function(){
          $.danidemo.addLog('#demo-debug', 'default', 'All pending tranfers completed');
        },
        onUploadProgress: function(id, percent){
          var percentStr = percent + '%';
          $.danidemo.updateFileProgress(id, percentStr);
        },
        onUploadSuccess: function(id, data, fileName, filePath){
          $.danidemo.addLog('#demo-debug', 'success', 'Upload of file #' + id + ' completed');
          $.danidemo.addLog('#demo-debug', 'info', 'Server Response for file #' + id + ': ' + filePath);
//           $.danidemo.updateFileStatus(id, 'success', 'Upload Complete');
          $.danidemo.updateFileProgress(id, '100%');
          
          $("#pathShowArea").append("<div class='video-uploaded' id='video-uploaded' >"+
          		"<div class='video-modify-poster' id='video-modify-poster'>设置视频封面</div>"+
        		"<div class='video-feedback'>"+
        		"<div id='video-feedback-status' class='video-feedback-status'><a class='video-success' href='"+filePath+"' target='_blank'><i></i><img src='http://s3.bj.xs3cnc.com/tel400/bdp.core.hb/abef059cb1f5408f8b30b6b4c241d74b.jpg' height='100%' width='100%'></a></div>"+
              	"<div class='video-feedback-middle'>"+
              	"	<div class='video-feedback-mtop'>"+
              	"		<div id='video-feedback-name' class='video-name oneline'>"+fileName+"</div>"+
              	"		<i></i>"+
              	"	</div>"+
              	"	<div class='video-feedback-mbottom'>"+
              	"		<div class='video-delete' id='video-delete'>删除</div>"+
              	"		<div class='video-feedback-retry'><span>重新上传</span><input name='video_file' id='retry_video_file' type='file'></div>"+
              	"	</div>"+
              	"</div>"+
              	"<button class='new-btn cancel video-feedback-btn' id='video-feedback-btn'>添加到正文</button>"+
                "</div>");
        },
        onUploadError: function(id, message){
//           $.danidemo.updateFileStatus(id, 'error', message);
          $.danidemo.addLog('#demo-debug', 'error', 'Failed to Upload file #' + id + ': ' + message);
        },
        onFileTypeError: function(file){
          $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' cannot be added: must be an video');
        },
        onFileSizeError: function(file){
          $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' cannot be added: size excess limit');
        },
        onFileExtError: function(file){
          $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' has a Not Allowed Extension');
        },
        onFallbackMode: function(message){
          $.danidemo.addLog('#demo-debug', 'info', 'Browser not supported(do something else here!): ' + message);
        }
    });
</script>
</html>