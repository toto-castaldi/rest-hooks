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

        var trace = function () {
          if (config.log) {
            console.log(arguments);
          }
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

    			trace('confirm ' + email + ' with token ' + token);

    			$.ajax({
    				type: 'PUT',
    				url: buildUrl('confirmToken') + '/' + token,
    				data: JSON.stringify({
    				    'email' : email
    				}),
    				success: function (response) {
    					trace(response);
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
              timeout: 20000,
              cache : false,
      				type: 'POST',
      				url: buildUrl('user'),
      				data: JSON.stringify({
      				    'email' : email,
      				    'password' : password,
      				    'urlBaseConfirm' :  document.location.protocol + '//' + document.location.host + document.location.pathname + '#confirm',
      				    'skipEmailSend' : false
      				}),
      				success: function (response) {
      					trace(response);
      					$('#signup').addClass('hidden');
      				},
              error : function (XMLHttpRequest, textStatus, errorThrown) {
                trace('error', XMLHttpRequest, textStatus, errorThrown);

                if (XMLHttpRequest.readyState == 4) {
                      // HTTP error (can be checked by XMLHttpRequest.status and XMLHttpRequest.statusText)
                      notie.alert(3, 'Error (' + XMLHttpRequest.status + ', ' + XMLHttpRequest.statusText + ')', 5);
                  }
                  else if (XMLHttpRequest.readyState == 0) {
                      // Network error (i.e. connection refused, access denied due to CORS, etc.)
                      notie.alert(3, 'Network Error', 5);
                  }
                  else {
                      // something weird is happening
                      notie.alert(3, 'Error !', 5);
                  }
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
