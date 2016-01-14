$(function() {
	$('.mdl-collapse__content').each(function() {
		var content = $(this);
		content.css('margin-top', -content.height());
	})

	$(document.body).on('click', '.mdl-collapse__button', function() {
		$(this).parent('.mdl-collapse').toggleClass('mdl-collapse--opened');
	})
});