type = ['', 'info', 'success', 'warning', 'danger'];


heroNotifications = {

		showNotification: function(from, align, bg_color, icon, message) {
	    	var color = "#f5f5f5";
	    	if(bg_color == 'warning'){
	    		color = 3;
	    	}else if(bg_color == 'success'){
	    		color =2;
	    	}else if(bg_color == 'failure'){
	    		color = 4;
	    	}else if(bg_color == 'info'){
	    		color = 1;
	    	}
	        $.notify({
	            icon: icon,
	            message: message

	        }, {
	            type: type[color],
	            timer: 10,
	            placement: {
	                from: from,
	                align: align
	            }
	        });
	    },

		showWarning: function(message) {
			var color = 3;
		    $.notify({
		        icon: "notifications",
	            title:"Warning",
		        message: message
		
		    }, {
		        type: type[color],
		        timer: 10,
		        placement: {
		            from: notification_from,
		            align: notification_align
		        }
		    });
		},
	    
		showSuccess: function(message) {
	    	var color = 2;
	        $.notify({
	            icon: "done_all",
	            title:"Success",
	            message: message

	        }, {
	            type: type[color],
	            timer: 10,
	            placement: {
	                from: notification_from,
	                align: notification_align
	            }
	        });
	    },

		showFailure: function(message) {
			var color = 4;
		    $.notify({
		        icon: "clear",
		        title:"Error",
		        message: message
		
		    }, {
		        type: type[color],
		        timer: 10,
		        placement: {
		            from: notification_from,
		            align: notification_align
		        }
		    });
		},
	    
	    showInfo: function(message) {
	    	var color = 1;
	    	
	        $.notify({
	            icon: "chrome_reader_mode",
	            message: message

	        }, {
	            type: type[color],
	            timer: 10,
	            placement: {
	                from: notification_from,
	                align: notification_align
	            }
	        });
	    }



}
