$(document).ready(function() {
	$('#datepicker').datepicker({
		format : "dd/mm/yyyy",
		autoclose : true,
		daysOfWeekHighlighted : "1,3,5",
		todayHighlight : true
	}).on('changeDate', function(e) {
		$('#datepicker2').datepicker('setStartDate', $('#datepicker').datepicker('getDate'));
	});
	
	$('#datepicker2').datepicker({
		format : "dd/mm/yyyy",
		autoclose : true,
		daysOfWeekHighlighted : "1,3,5",
		todayHighlight : true
	}).on('changeDate', function(e) {
		$('#datepicker').datepicker('setEndDate', $('#datepicker2').datepicker('getDate'));
	});
});



function onNextPageClick() {
	$(document).ready(function() {
		var pageNo = $('#pageNo').val();
		$('#pageNo').val(parseInt(pageNo, 10) + 1);
		$('#form')[0].submit();
	});
}

function onPreviousPageClick() {
	$(document).ready(function() {
		var pageNo = $('#pageNo').val();
		$('#pageNo').val(parseInt(pageNo, 10) - 1);
		$('#form')[0].submit();
	});
}