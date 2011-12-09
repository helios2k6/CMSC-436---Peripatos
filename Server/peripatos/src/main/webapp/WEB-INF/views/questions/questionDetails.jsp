<%@include file="../header.jsp"%>

<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBOLlOrvtbuaMxL1e4otVeSpoE_CUeWT6k&sensor=false">
	
</script>

<script type="text/javascript">
	function cancelQuestion() {
		history.go(-1);

	}

	function deleteQuestion() {
		$("<input>").attr({
			type: 'hidden',
			name: '_method',
			value: 'DELETE'
		}).appendTo("#question");
	}

	function handleDoubleClick(location, map) {
		//Set the longitude and latitude of the form
		$("input[name='location.latitude']").val(location.lat());
		$("input[name='location.longitude']").val(location.lng());

		//Place marker for fun
		var marker = new google.maps.Marker({
			title : 'Question Location Set Here',
			position : location,
			map : map
		});
	}

	$(function() {
		//Load Google Maps
		var startLatLong = new google.maps.LatLng(38.989603, -76.935775);

		var mapOptions = {
			zoom : 16,
			center : startLatLong,
			mapTypeId : google.maps.MapTypeId.ROADMAP

		};

		var map = new google.maps.Map($("#questionMapArea")[0], mapOptions);

		google.maps.event.addListener(map, 'dblclick', function(event) {
			handleDoubleClick(event.latLng, map);
		});
	});
</script>
</head>
<body>
	<div class="pageTitleClass">Question Details</div>
	<%@include file="/WEB-INF/views/banner.jsp"%>

	<div id="mainBodyAreaClass">
		<div class="formAreaClass">
			<form:form method="POST" modelAttribute="question">
				<table class="tableClass">
					<tr>
						<td>Title:</td>
						<td class="tableTextInput"><form:input path="title" value="${question.title}" /></td>
					</tr>
					<tr>
						<td>Body:</td>
						<td class="tableTextAreaInput"><form:textarea path="body" value="${question.body}" /></td>
					</tr>
					<tr>
						<td>Latitude:</td>
						<td class="tableTextInput"><form:input path="location.latitude" value="${question.location.latitude}" /></td>
					</tr>
					<tr>
						<td>Longitude:</td>
						<td class="tableTextInput"><form:input path="location.longitude" value="${question.location.longitude}" /></td>
					</tr>
				</table>
				<div class="formFooterArea">
					<input type="submit" name="saveQuestion" value="Save" /> <input type="button" name="cancel" value="Cancel"
						onClick="cancelQuestion()" />
					<c:if test="${newQuestion != true}">
						<input type="submit" name="delete" value="Delete" onClick="deleteQuestion()" />
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
	<div id="questionMapArea"></div>


</body>
</html>