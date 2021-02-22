$(document).ready(function() {
	const queryString = window.location.search;

	const urlParams = new URLSearchParams(queryString);
	const notify = urlParams.get('notify');
	
	if(notify){
		switch(notify){
			case "editss":
				showNotification('<b>Edit expense successfully!</b><br/><small>Your edit has been recorded</small>', 'success', 'nc-check-2');
				break;
			case 'editfl':
				showNotification('<b>Edit expense fail!</b><br/><small>Please try again!</small>', 'danger', 'nc-simple-remove');
				break;
			case 'deletess':
				showNotification('<b>Delete expense successfully!</b><br/><small>Your expense has been deleted</small>', 'success', 'nc-check-2');
				break;
			case 'deletefl':
				showNotification('<b>Delete expense fail!</b><br/><small>Please try again!</small>', 'danger', 'nc-simple-remove');
				break;
			case 'recss':
				showNotification('<b>Record expense successfully!</b><br/><small>Your changes has been recorded</small>', 'success', 'nc-check-2');
				break;
			case 'recfl':
				showNotification('<b>Delete expense fail!</b><br/><small>Please try again!</small>', 'danger', 'nc-simple-remove');
				break;
			case 'limitsc':
				showNotification('<b>Set quota limit successfully!</b><br/><small>Your changes has been recorded</small>', 'success', 'nc-check-2');
				break;
			case 'limitfl':
				showNotification('<b>Set quota limit fail!</b><br/><small>Please try again!</small>', 'danger', 'nc-simple-remove');
				break;
		}
		
	}
});

const showNotification = (msg, typename, icon) => {
		    $.notify({
		        icon: "nc-icon " + icon,
		        message: msg

		    }, {
		        type: typename,
		        showProgressbar: true,
		        delay: 5000,
		        placement: {
		            from: 'top',
		            align: 'right'
		        },
				onClosed: function(){
					const nextURL = window.location.href.split('?')[0];
					const nextTitle = 'Expense Manager';
					const nextState = { additionalInformation: '' };
					
					console.log(nextURL);
					
					// This will create a new entry in the browser's history, without reloading
					window.history.pushState(nextState, nextTitle, nextURL);
					
					// This will replace the current entry in the browser's history, without reloading
					window.history.replaceState(nextState, nextTitle, nextURL);
				}
		    });
		}