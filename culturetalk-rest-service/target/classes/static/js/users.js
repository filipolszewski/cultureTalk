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