/*
* This contains all the scripts required to make the frontend work
* Author: Patrick Chiang
*/

// The html module blocks
var modules = new Array();

// The js module objects
var mods = new Array();

// Current module ID for recursing
var moduleId;

// Store hearbeat interval
var heartbeat;

// Store mod items
var modStore;

// Address for backend
var BACKEND_URL = "http://localhost:3070/backend";

// Interval between each heartbeat
var HEARTBEAT_INTERVAL = 200;

window.onload = function() {
	loadWorkspace();
	$("#addModule").mousedown(addModule);
	$("#saveWorkspace").mousedown(saveWorkspace);
	$("#loadWorkspace").mousedown(loadWorkspace);
	$("#backendReady").mousedown(backendReady);
	window.onclick = modulesChanged;
	heartbeat = setInterval(getInfoFromServer, HEARTBEAT_INTERVAL);

	$(".mode .btn-group .btn").click(changeMode);

	$(".status .btn-group .btn").click(enable);
};

function changeMode() {
	$(this).addClass("active").siblings(".btn").removeClass("active");
	$(".status button").removeClass("active");
	$(".status .btn-disable").addClass("active");

	$.ajax({
		url : BACKEND_URL + "?status=0"
	});
}

function enable() {
	$(this).addClass("active").siblings(".btn").removeClass("active");

	if ($(this).hasClass("btn-enable")) {
		$.ajax({
			url : BACKEND_URL + "?status=" + $(".mode .btn-group .btn.active").data("index")
		});
	} else {
		$.ajax({
			url : BACKEND_URL + "?status=0"
		});
	}
}

function modulesChanged() {
	modStore = JSON.parse(localStorage.getItem("savedata"));
}

function backendReady() {
	if (heartbeat)
		clearInterval(heartbeat);
	heartbeat = setInterval(getInfoFromServer, HEARTBEAT_INTERVAL);

	$(".status button").removeClass("active");
	$(".status .btn-disable").addClass("active");

	$.ajax({
		url : BACKEND_URL + "?status=0"
	});
}

function getInfoFromServer() {
	$.ajax({
		url : BACKEND_URL,
		success : function(data, status, xhr) {
			if (modStore == null)
				modulesChanged();
			console.log(data);
			displayModules(data);
			$(".connection").html('Connected <i class="fa fa-check"></i>');
		},
		error : function(xhr, status, error) {
			console.log("Backend not responding.");
			// Cancel and wait until backend comes back up
			clearInterval(heartbeat);
			$(".connection").html('Disconnected <i class="fa fa-exclamation"></i>');
		}
	});
}

function displayModules(data) {
	$.each(JSON.parse(data), function(i, m) {
		for (var j = 0; j < modStore.length; j++) {
			var mod = modStore[j];

			if (mod == null)
				continue;

			if (mod.moduleId == m.Module.toString() && mod.channelId == m.Channel.toString()) {
				if (mod.modType == i) {
					var nameOfModule = j;
					if ($.isNumeric(m.Value))
						$("#module" + nameOfModule + " .valueNum").html(m.Value.toFixed(3));
					else
						$("#module" + nameOfModule + " .valueNum").html(m.Value + "");
				} else if (i == "PWM" && (mod.modType == "Jaguar" || mod.modType == "Talon" || mod.modType == "Victor")) {
					var nameOfModule = j;
					$("#module" + nameOfModule + " .valueNum").html(m.Value.toFixed(3));
				}
			}
		}
	});
}

/*
 * Set up the plumbing: connections, connectors, endpoints..etc.
 */
function prepare(moduleName, inputNr, outputNr) {
	jsPlumb.importDefaults({
		// default drag options
		DragOptions : {
			cursor : "pointer",
			zIndex : 2000
		},
		EndpointStyles : [{
			fillStyle : "white"
		}, {
			fillStyle : "white"
		}],
		Endpoints : [["Dot", {
			radius : 7
		}], ["Dot", {
			radius : 9
		}]]
	});

	// this is the paint style for the connecting lines..
	var connectorPaintStyle = {
		lineWidth : 4,
		strokeStyle : "#504A4B", //deea18 = yellow
		joinstyle : "round",
		outlineColor : "#EAEDEF",
		outlineWidth : 2
	},
	// .. and this is the hover style.
	connectorHoverStyle = {
		lineWidth : 4,
		strokeStyle : "white"
	}, endpointHoverStyle = {
		fillStyle : "#504A4B"
	},
	// the definition of endpoints
	sourceEndpoint = {
		endpoint : "Dot",
		paintStyle : {
			strokeStyle : "#504A4B",
			fillStyle : "transparent",
			radius : 10,
			lineWidth : 2
		},
		isSource : true,
		isTarget : true,
		connector : ["Flowchart", {
			stub : 30,
			gap : 0,
			cornerRadius : 5,
			alwaysRespectStubs : true
		}],
		cssClass : "endpointDragger",
		connectorStyle : connectorPaintStyle,
		hoverPaintStyle : endpointHoverStyle,
		connectorHoverStyle : connectorHoverStyle,
		dragOptions : {}
	};

	// Create the connections
	var allSourceEndpoints = [], allTargetEndpoints = [];
	_addEndpoints = function(toId, sourceAnchors, targetAnchors) {
		for (var i = 0; i < sourceAnchors.length; i++) {
			// convert from module save code -> actual connections
			var sourceUUID = toId + "x" + sourceAnchors[i].toString().replace(/\./g, "").replace(/\,/g, "").replace(/-/g, "");
			allSourceEndpoints.push(jsPlumb.addEndpoint(toId, sourceEndpoint, {
				anchor : sourceAnchors[i],
				uuid : sourceUUID
			}));
		}
		for (var j = 0; j < targetAnchors.length; j++) {
			var targetUUID = toId + "x" + targetAnchors[j].toString().replace(/\./g, "").replace(/\,/g, "").replace(/-/g, "");
			allTargetEndpoints.push(jsPlumb.addEndpoint(toId, sourceEndpoint, {
				anchor : targetAnchors[j],
				uuid : targetUUID
			}));
		}
	};

	var oldEndpoints = jsPlumb.getEndpoints(moduleName);
	if (oldEndpoints !== undefined) {
		for (var i = 0; i < oldEndpoints.length; i++)
			jsPlumb.deleteEndpoint(oldEndpoints[i]);
	}

	// Evenly distribute locations of endpoints on module
	for (var i = 0; i < inputNr; i++) {
		_addEndpoints(moduleName, [[0, (i + 1) / (inputNr + 1), -1, 0]], []);
	}

	for (var i = 0; i < outputNr; i++) {
		_addEndpoints(moduleName, [], [[1, (i + 1) / (outputNr + 1), 1, 0]]);
	}

	// listen for clicks on connections to delete connections on click.
	jsPlumb.bind("click", function(conn, originalEvent) {
		jsPlumb.detach(conn);
	});

	jsPlumb.bind("connection", function(info) {
	});
}

/*
 * Clone a module
 */
function addModule() {
	var module = $("#template").clone();
	prepareModule(module);
}

/*
 * Send query to PHP file to load the workspace file
 */
function loadWorkspace() {
	// Intro
	$("#overlay div").html("Loading Workspace...");
	displayOverlay();

	// clear workspace
	$("#workspace").html("");
	jsPlumb.reset();

	dataToWorkspace(localStorage.getItem("savedata"));
	$("#overlay").fadeOut(1000, "easeInOutExpo");
}

/*
 * Set the workspace up according to the save file
 */
function dataToWorkspace(data) {
	if (data == null || data == "") {
		alert("No saved workspace found.");
		$("#overlay").fadeOut(1000, "easeInOutExpo");
		return;
	}

	modules = [];
	// Empty the HTML modules blocks
	var mods = [];
	// Empty the JS module øbjects
	mods = JSON.parse(data);

	// reset positions & values
	for (var i = 0; i < mods.length; i++) {
		if (mods[i] == null) {
			modules[modules.length] = null;
			continue;
		}// ALWAYS remember to filter out possibly empty modules (deleted)
		var module = $("#template").clone();

		// reset positions
		module.css({
			left : mods[i].x,
			top : mods[i].y
		});

		// setup module
		prepareModule(module);

		// reset values
		var nameOfModule = module.attr("id");
		$("#" + nameOfModule + " .moduleType").val(mods[i].modType);
		$("#" + nameOfModule + " .moduleId").val(mods[i].moduleId);
		$("#" + nameOfModule + " .channelId").val(mods[i].channelId);
		$("#" + nameOfModule + " .valueNum").val(mods[i].valueNum);

		// initialize module according to type
		selectModuleType.call($("#" + nameOfModule + " .moduleType"));
	}

	// connect endpoints AFTER all modules have their positions
	for (var i = 0; i < mods.length; i++) {
		if (mods[i] == null) {
			continue;
		}

		for (var j = 0; j < mods[i].conn.length; j++) {
			var pair = mods[i].conn[j];
			jsPlumb.connect({
				uuids : [pair.sourceId, pair.targetId],
				editable : true
			});
		}
	}

	$(".inputValue").each(function() {
		if ($(this).val() != "Input Value")
			$(this).removeClass("placeholder");
	});

	// Outro for "Loading..."
	$("#overlay").fadeOut(1000, "easeInOutExpo");
}

/*
 * Turn modules into JS objects and posts to PHP for save
 */
function saveWorkspace() {
	// Intro
	$("#overlay div").html("Saving Workspace...");
	displayOverlay();

	mods = [];
	// blank out JS objects

	// recreate module as a JS object
	for (var i = 0; i < modules.length; i++) {
		var mod = new Object();

		if (modules[i] == null) {
			mods.push(null);
			// create an empty slot in the array
			continue;
		}

		// get the full html name of the module
		var modName = modules[i].attr("id");

		// pixels from left
		mod.x = $("#" + modName).css("left");
		// pixels from top
		mod.y = $("#" + modName).css("top");
		// module type
		mod.modType = $("#" + modName + " .moduleType").val();
		// module ID
		mod.moduleId = $("#" + modName + " .moduleId").val();
		// channel ID
		mod.channelId = $("#" + modName + " .channelId").val();
		// value
		mod.valueNum = $("#" + modName + " .valueNum").val();

		// connections
		mod.conn = new Array();
		var connections = jsPlumb.getConnections({
			source : [modName]
		});
		for (var j = 0; j < connections.length; j++) {
			var pair = new Object();
			// source point
			pair.sourceId = connections[j].endpoints[0].getUuid();
			//  target point
			pair.targetId = connections[j].endpoints[1].getUuid();
			mod.conn.push(pair);
		}

		// this is just one module
		mods.push(mod);
	}
	moduleId = 0;

	// JS objects array -> JSON
	var jsonData = JSON.stringify(mods);

	localStorage.setItem("savedata", jsonData);
	$("#overlay").fadeOut(1000, "easeInOutExpo");

	modStore = JSON.parse(localStorage.getItem("savedata"));
}

/*
 * Request backend for discovery information for modules
 */
function deploy() {
	saveWorkspace();

	// Intro
	$("#overlay div").html("Deploying Modules...");
	displayOverlay();

	// deploy code
	modStore = JSON.parse(localStorage.getItem("savedata"));
}

/*
 * Creates a module, set it up, and pushes it into the main array
 */
function prepareModule(module) {
	// name the module
	var nameOfModule = "module" + modules.length;
	module.attr({
		"id" : nameOfModule,
		"class" : "module"
	});

	// it floats when you hover your mouse over it
	module.hover(function() {
		$("[id^=module]").css("z-index", "-10");
		module.css("z-index", "10");
	});

	// current length of modules array
	var moduleIndex = modules.length;

	// add module to workspace
	modules[moduleIndex] = module;
	module.appendTo("#workspace");

	// make draggable
	jsPlumb.draggable(module, {});

	// module type & select box setup
	var modType = $("#" + nameOfModule + " .moduleType");

	// handle when the value is changed
	modType.change(selectModuleType);

	// kill with fire when close button is clicked
	// (but don't delete its space, the space remains)
	$("#" + nameOfModule + " .closeBtn").click(function() {
		var oldEndpoints = jsPlumb.getEndpoints(nameOfModule);
		if (oldEndpoints !== undefined) {
			for (var i = 0; i < oldEndpoints.length; i++)
				jsPlumb.deleteEndpoint(oldEndpoints[i]);
		}

		$("#" + nameOfModule).remove();
		modules[moduleIndex] = null;
		//.splice(moduleIndex, 1);
	});
}

/*
 * Called when the module type has been selected
 */
function selectModuleType() {
	var nameOfModule = $(this).parent().parent().attr("id");
	var selectedOption = "#" + nameOfModule + " .moduleType";

	// call prepare, give it the correct endpoints
	prepare(nameOfModule, $(selectedOption + " option:selected").data("inputs"), $(selectedOption + " option:selected").data("outputs"));

	var modCategory = $(this).data("type");
	if (modCategory == "display") {

	}

	modStore = JSON.parse(localStorage.getItem("savedata"));
}

/*
 * Display the dark overlay
 */
function displayOverlay() {
	$("#overlay").css("display", "table");
}

/*
 * Is n a number?
 */
function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}

/*
 * Get random integer between min and max
 */
function getRandomInt(min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
}