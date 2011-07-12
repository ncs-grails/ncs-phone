<div id="newTaskForm"></div>
<div id="newTaskButtons">
	<button id="newCall" onclick="return newCall();">
		<img src="${resource(dir:'images', file:'phone.png')}" width="64" height="64" /><br/>
		Log a Call
	</button>
	<button id="newAdmin" onclick="return newAdmin();">
		<img src="${resource(dir:'images', file:'administrivia.png')}" width="64" height="64" /><br/>
		Log Administrative Time
	</button>
	<button id="finishUpButton" onclick="return newFinishUp();">
		<img src="${resource(dir:'images', file:'checkered_flag.png')}" width="64" height="64" /><br/>
		Finish Up
	</button>
</div>