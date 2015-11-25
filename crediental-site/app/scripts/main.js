'use strict';

function getParameterByName(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)'),
        results = regex.exec(location.search);
    if (results === null) {
    	results = regex.exec(location.hash);
    }
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

$().ready(
  function () {
    $.ajax({
    	type: 'GET',
    	url: '/config',
    	success: function (config) {
    		var buildUrl = function (resourceName) {
    			return config.protocol + '://' + config.host + config.port + config.context + resourceName;
    		};


    		$('#links a[href=#signup]').click(function () {
    			$('#signup').removeClass('hidden');
    		});

    		if (window.location.hash !== '' && window.location.hash.indexOf('&') === -1) {
    			$(window.location.hash).removeClass('hidden');
    		}

    		if (window.location.hash.startsWith('#confirm?')) {
    			var email = getParameterByName('e');
    			var token = getParameterByName('t');

    			console.log('confirm ' + email + ' with token ' + token);

    			$.ajax({
    				type: 'PUT',
    				url: buildUrl('confirmToken') + '/' + token,
    				data: JSON.stringify({
    				    'email' : email
    				}),
    				success: function (response) {
    					console.log(response);
    				},
    				contentType:'application/json;charset=UTF-8',
    			  	dataType: 'json'
    			});
    		}

        $('#signup form').validate({
          rules : {
            email : {
              required : true,
              email : true
            },
            password : {
              required : true
            }
          }
        });

    		$('#signup form').submit(function( event ) {
          if ($(event.target).valid()) {
      			var email = $('#signup form input[name=email]').val();
      			var password = $('#signup form input[name=password]').val();

      			$.ajax({
      				type: 'POST',
      				url: buildUrl('user'),
      				data: JSON.stringify({
      				    'email' : email,
      				    'password' : password,
      				    'urlBaseConfirm' :  document.location.protocol + '//' + document.location.host + document.location.pathname + '#confirm',
      				    'skipEmailSend' : false
      				}),
      				success: function (response) {
      					console.log(response);
      					$('#signup').addClass('hidden');
      				},
      				contentType:'application/json;charset=UTF-8',
      			  	dataType: 'json'
      			});
          }
    			event.preventDefault();
    		});


    	},
    	dataType: 'json'
    });
  }
);
