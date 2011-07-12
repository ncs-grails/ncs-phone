// the following is run after the page is loaded.
$(document).ready(function(){
	
    // load default visible sections
	loadItemInformation();
	loadLookup();
	loadTasksArea();
	
	$("#leftAccordion").accordion({ fillSpace: true });
	$("#rightAccordion").accordion({ fillSpace: true });	

	// Ajax Loading...
    $("h3", "#leftAccordion").click(function(e) {
        var contentDiv = $(this).next("div");
        var href = $(this).find("a").attr("href");
        if (href.length > 1) {
        	contentDiv.load($(this).find("a").attr("href"));
        }
    });
    
	// Ajax Loading...
    $("h3", "#rightAccordion").click(function(e) {
        var contentDiv = $(this).next("div");
        var href = $(this).find("a").attr("href");
        if (href.length > 1) {
        	contentDiv.load($(this).find("a").attr("href"));
        }
    });
	    
    // start up the heartbeat
    heartbeat($("#heartBeatUrl").val());
    
});

// heart beat, this keeps the authentication session alive.
function heartbeat(url) {
	// 5 minute delay
	var delay = 1000 * 60 * 5;

	// get heartbeat from remote server
	$.ajax({
		url: url,
		failure: function() {
			alert('hearbeat: failed to contact server.');			
		}
	});

	// recursive loop
	var functionCall = "heartbeat('" + url + "');";
	window.setTimeout(functionCall, delay);
}

// Item Information
function loadItemInformation() {
	$("#itemInformation").next("div").load($("#itemInformation").find("a").attr("href"));	
}

function loadTasksArea() {
	var url = $("input[name='tasks-menu-action']").val();
	$("#taskArea").hide();
	$("#taskArea").load(url, function(){
		$("#taskArea").show('fast');
	});
}

// Call Functions
function newCall() {
	var url = $("input[name='call-create-action']").val();
	var items = $("#trackedItemInstanceList").val();
	data = {
			items: items
	}
    $("#newTaskForm").hide();
	$("#newTaskForm").load(url, data, function(){
		$("#newTaskButtons").hide('fast');
		$("#newTaskForm").show('fast');
		$("#numberDialed").focus();
	});
	return false;
}

function cancelTask() {
	$("#newTaskForm").hide('fast');
	$("#newTaskForm").html('');
	$("#newTaskButtons").show('fast');
	return false;
}

function callSaveSuccess() {
	cancelTask();
	loadCallLog();
	loadCallResultGrid();
}

function callSaveFailure() {
	alert("Failed to save the call.  Perhaps your login session timed out?")
}

function loadCallLog() {
    $("#callHistory").next("div").load($("#callHistory").find("a").attr("href"));
}

function loadCallResultGrid() {
    $("#callResultGrid").next("div").load($("#callResultGrid").find("a").attr("href"));
}

// Adminstrivia Functions
function newAdmin() {
	var url = $("input[name='admin-create-action']").val();
	var items = $("#trackedItemInstanceList").val();
	data = {
			items: items
	}
    $("#newTaskForm").hide();
	$("#newTaskForm").load(url, data, function(){
		$("#newTaskButtons").hide('fast');
		$("#newTaskForm").show('fast');
	});
	return false;
}

function adminSaveSuccess() {
	cancelTask();
	loadAdminLog();
}

function adminSaveFailure() {
	alert("Failed to save the administrative time.  Perhaps your login session timed out?")
}
function loadAdminLog() {
	$("#adminHistory").next("div").load($("#adminHistory").find("a").attr("href"));
}

// Finish Up
function newFinishUp() {
	var url = $("input[name='finishUp-action']").val();
	var items = $("#trackedItemInstanceList").val();
	
	// add the ID param
	url += '/' + items;
	
    $("#newTaskForm").hide();
    $("#newTaskForm").load(url, function(){
		$("#newTaskButtons").hide('fast');
		$("#newTaskForm").show('fast');
	});
	return false;
}

function itemCallResultSaveSuccess() {
	
	var url = $("#reportLink").attr("href");
	
	window.location.replace(url);	
}

function itemCallResultSaveFailure() {
	alert("Failed to close out the case.  Perhaps your login session timed out?")
}

// Look Up
function loadLookup() {
	$("#lookup").next("div").load($("#lookup").find("a").attr("href"));
}

// Final Result Changed
function resultChanged() {
	
	var currentValue = $("#associatedResult\\.id").val();
	var refusedResultIds = $("#refusedResultIds").val();
	var refusedResultIdList = refusedResultIds.split(',');
	
	var refusal = false;
	
	for (x in refusedResultIdList) {
		var resultId = refusedResultIdList[x];
		if (resultId == currentValue) {
			refusal = true;
		}
	}
	
	
	if (currentValue == "null") {
		$("#closeOutButton").hide();
	} else {
		$("#closeOutButton").show();
	}
	
	
	if (refusal) {
		$("#refusalReasonContainer").show();
	} else {
		$("#refusalReasonContainer").hide();
	}
	
	return false;
}