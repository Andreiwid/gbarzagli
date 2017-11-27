var message = $("#message").val();
if (message != undefined && message != null) {
	Materialize.toast(message, 2000, 'rounded');
}
