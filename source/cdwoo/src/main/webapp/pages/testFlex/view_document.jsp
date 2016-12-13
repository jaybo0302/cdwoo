<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<% 
  String documentName = (String)request.getSession().getAttribute("documentName");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>FlexPaper</title>         
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
      <style type="text/css" media="screen"> 
        a,div{width:100%;height:100%}
        html, body  { height:100%; }
        body { margin:0; padding:0; overflow:auto; }   
        #flashContent { display:none; }
      </style> 
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/flexpaper_flash_debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/flexpaper_flash.js"></script>
<body>
  <div style="position:absolute;left:10px;top:10px;" class="flexContent">
          <a id="viewerPlaceHolder" style="width:1920px;height:1080px;display:block"></a>
          <script type="text/javascript">
           var fp = new FlexPaperViewer( 
             '${pageContext.request.contextPath}/tool/FlexPaperViewer',
             'viewerPlaceHolder', { config : {
             SwfFile : escape("/cdwooFile/document/<%=documentName%>"),
             Scale : 0.6, 
             ZoomTransition : 'easeOut',
             ZoomTime : 0.5,
             ZoomInterval : 0.2,
             FitPageOnLoad : true,
             FitWidthOnLoad : false,
             FullScreenAsMaxWindow : false,
             ProgressiveLoading : false,
             MinZoomSize : 0.2,
             MaxZoomSize : 5,
             SearchMatchAll : false,
             InitViewMode : 'Portrait',
             PrintPaperAsBitmap : false,
             ViewModeToolsVisible : true,
             ZoomToolsVisible : true,
             NavToolsVisible : true,
             CursorToolsVisible : true,
             SearchToolsVisible : true,
               localeChain: 'en_US'
             }});
          </script>
        </div>
</body>
</html>