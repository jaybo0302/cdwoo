(function($) {
	$.fn.extend({
		"simpleupload" : function(hook, businessLineData) {
			this.fileupload({
				dataType : 'json',
				add : function(e, data) {
					$.each(data.files, function(index, file) {
						$.ajax({
							url : '//itbs.light.fang.com/olapservice/xs3Upload/simpleUpload.do',
							type : 'POST',
							data : {
								businessLineData : businessLineData,
								name : file.name,
								size : file.size,
								type : file.type,
							},
							dataType: 'jsonp',
							xhrFields: {
			                      withCredentials: true
							},
							crossDomain : true,
						}).success(function(res) {
							if (res.hasErrors) {
								hook(false, res.errorMessage);
							} else {
								var obj = res.data;
								console.log(obj.url);
								$.ajax({
									url : obj.url,
									type : 'PUT',
									data : file,
									processData : false,
									contentType : file.type,
									headers : {
										'x-amz-acl' : 'public-read'
									},
									crossDomain : true,
								}).success(function(xml, textStatus, xhr){
					               console.log('Done');
					           	   if ('200'==xhr.status) {
					           		  $.ajax({
					           			 url: '//itbs.light.fang.com/olapservice/xs3Upload/checkXS3FileType.do',
					           			  type: 'POST',
					          		      data : {
					          		    	  filePath : obj.url.split("?")[0],
					          		      },
					          		      dataType : 'jsonp'
					           		  }).success(function(res){
					           			if (res.hasErrors) {
											hook(false, res.errorMessage);
										} else {
											hook(true, obj.url.split("?")[0].replace("s3.bj.xs3cnc.com","imgoaim.soufunimg.com"));
										}
									  });
					           	   } else {
					           		   console.log("upload to xs3 failed");
					           		   hook(false, "upload failed");
					           	   }
					            }).error(function(XMLHttpRequest, textStatus, errorThrown) {
									console.log("XMLHttpRequest.readyState==>" + XMLHttpRequest.readyState+",textStatus==>"+textStatus + " error");
									hook(false, " upload failed");
					            });
							}
						}).error(function() {
							console.log("get url failed");
							hook(false, "upload failed");
						});
					});
				}
			});
		}	
	});
})(jQuery);