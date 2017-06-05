
/* Scripts
-----------------------------------------------------------------------------*/ 
jQuery(document).ready(function($) {


    $('.dropdown-toggle').dropdown();


	// Scroll to Element
	$('[data-scroll]').click(function() {
		var destiny = $(this).attr("data-scroll");
		$('html, body').animate({
			scrollTop: $('#' + destiny).offset().top
		}, 800);
	});


	// Equal Heights
	$('[data-height]').each(function(){
		var baseHeight = 0;
		var target = $(this).attr('data-height');
		$(target, this).each(function(){

			if( $(this).height() > baseHeight )
				baseHeight = $(this).height();
		});
		$(target, this).height(baseHeight);
	});


	// Auto Center Elements
	$('[data-center]').each(function(){
		var axis = $(this).attr('data-center');
		$(this).css('position','absolute');

		if ( axis.indexOf("y") >= 0 ) {
			$(this).css('top', Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + 'px');
		}
		if ( axis.indexOf("x") >= 0 ) {
			$(this).css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + 'px');
		}
	});


	// NavBar Collapse on click out
	$(this).on('click', function(){
		var opened = $(".navbar-collapse").hasClass("collapse in");
		if (opened === true) {
			$('.collapse').collapse('hide');
		}
	});

	$('.view-button').on('click', function(){
		window.location.href = "view.php";
	});

});
