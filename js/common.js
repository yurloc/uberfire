
  // slider
  $('#da-slider').cslider({
	autoplay    : true,
	 interval    : 6000 
  });
  // lightbox gallery
  $(".gallery:first a[rel^='prettyPhoto']").prettyPhoto({animation_speed:'normal',theme:'light_square',slideshow:3000, autoplay_slideshow: true});
  // social sharing
  $('#shareme').sharrre({
	  share: {
		googlePlus: true,
        twitter: true,
        facebook: false,
		pinterest: false
	  },
	  buttons: {
		googlePlus: {size: 'tall'},
		twitter: {count: 'vertical'}
	  },
	  enableHover: true,
	  enableCounter: true,
	  enableTracking: true
	});
	// twitter feed
	$(".tweet").tweet({
            username: "uberfire_org",
            join_text: "auto",
            avatar_size: 32,
            count: 4,
            auto_join_text_default: " ", 
            auto_join_text_ed: " ",
            auto_join_text_ing: " ",
            auto_join_text_reply: " ",
            auto_join_text_url: " ",
            loading_text: "loading tweets..."
        });