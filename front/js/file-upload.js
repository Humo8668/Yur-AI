function hideAlerts() {
	$("#process-result-alerts #correct-alert").css("display", "none");
	$("#process-result-alerts #wrong-alert").css("display", "none");
	$("#process-result-alerts #error-alert").css("display", "none");
}

/*
data:
	text: "<some text>",
	index: [{start: <error start index>, end: <error end index>}, ... ]
*/
function getErrorMarkedTextHtml(data) {
	var html = data.text;
	var errWords = [];

	for(var i = 0; i < data.index.length; i++) {
		var se = data.index[i];
		//var before = html.substr(0, data.index[i].start);
		var error = html.substr(data.index[i].start, data.index[i].end - data.index[i].start);
		errWords.push(error);
		//var after = html.substr(data.index[i].end, html.length - data.index[i].end);
		//error = "<span class='bg-danger'>" + error + "</span>";
		//html = before + error + after;
	}

	for (var i = errWords.length - 1; i >= 0; i--) {
		html = html.replace(errWords[i], "<span class='bg-warning'>"+errWords[i]+"</span>");
	}
	return html;
}

function onFileUpload() {
	const formData = new FormData();
	formData.append("file", file.files[0]);
	$("#fm #upload-file-btn").prop("disabled", "true");
	$("#fm #uploading-spinner").css("display", "block");
	// ***
	hideAlerts();
	// ***
	$.ajax({
	    url: 'http://localhost:4000/document/process',
	    type: 'POST',
	    data: formData,
	    contentType: false,
        processData: false,
	    headers: {
	        "Authorization": "Bearer " + window.jwtToken
	    },
	    success:function(results) {
	    	if(typeof results.index !== 'undefined' && results.index.length > 0){
	    		$("#process-result-alerts #wrong-alert").css("display", "block");
	    		$("#process-result-alerts #wrong-alert .cropped-text").empty();
	    		$("#process-result-alerts #wrong-alert .cropped-text").append(getErrorMarkedTextHtml(results));
	    	} else if(typeof results.index === 'undefined' || results.index.length <= 0) {
	    		$("#process-result-alerts #correct-alert").css("display", "block");
	    	}
	    },
	    error: function (jqXhr, textStatus, errorThrown) {
	    	$("#process-result-alerts #error-alert").css("display", "block");
	    	$("#process-result-alerts #error-alert .cropped-text").text(errorThrown); 
	    },
	    complete: function (jqXhr, textStatus) {
	    	$("#fm #upload-file-btn").prop("disabled", null)
			$("#fm #uploading-spinner").css("display", "none");
			getUploadedDocuments(function(docsArray){
				drawUploadedFilesList(docsArray);
			});
	    }
	});
}

function refreshToken(onTokenRefershed) {
	$.ajax({
	    url: 'http://localhost:4000/authenticate',
	    type: 'POST',
	    data: JSON.stringify({
	    	"username": "Emil",
    		"password": "1234asdf"
	    }),
	    contentType: "application/json",
	    timeout: 1000,
	    success:function(results) {
	    	window.jwtToken = results.token;
	    	console.log(results);
	    	if(typeof onTokenRefershed === 'function')
	    		onTokenRefershed();
	    },
	    error: function (jqXhr, textStatus, errorThrown) {
	    	alert("Error on getting token: " + errorThrown);
	    }
	});
}

function getUploadedDocuments(onLoaded) {
	$.ajax({
	    url: 'http://localhost:4000/document/getall',
	    type: 'GET',
	    headers: {
	        "Authorization": "Bearer " + window.jwtToken
	    },
	    contentType: "application/json",
	    timeout: 1000,
	    success:function(results) {
	    	console.log(results);
	    	if(typeof onLoaded === 'function')
    			onLoaded(results);
	    },
	    error: function (jqXhr, textStatus, errorThrown) {
	    	alert("Error on getting documents list: " + errorThrown);
	    	if(typeof onLoaded === 'function')
    			onLoaded([]);
	    }
	});
}

function drawUploadedFilesList(filesArr) {
	$("#recent-updates").empty();
	for (var i = filesArr.length - 1; i >= 0; i--) {
		var html = 
			'<div class="media text-muted pt-3">'+
	          	'<svg class="bd-placeholder-img mr-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"/><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>'+
	          	'<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">'+
	          	'<strong class="d-block text-gray-dark">'+filesArr[i].documentName+'</strong>' + 
	          	'<b>Verdict:</b> Playing ping pong all night long, everything\'s all neon and hazy. Yeah, she\'s so in demand. She\'s sweet as pie but if you break her heart. But down to earth. It\'s time to face the music I\'m no longer your muse. I guess that I forgot I had a choice.' + 
	            '</p>'+ 
	            '<h6>'+filesArr[i].uploaded_at+'</h6>'
            '</div>';
		$("#recent-updates").append(html);

	}
}


$(document).ready(function() {
	hideAlerts();
	$("#fm #uploading-spinner").css("display", "none");
	refreshToken(function() {
		getUploadedDocuments(function(docsArray){
			drawUploadedFilesList(docsArray);
		});
	});
});