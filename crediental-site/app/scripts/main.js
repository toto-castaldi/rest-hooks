'use strict';

/*globals notie */

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

        var formLoading = function (subSectionId) {
          $('#' + subSectionId + ' form input[type=submit]').isLoading('');
        };

        var formLoaded = function (subSectionId) {
          $('#' + subSectionId + ' form input[type=submit]').isLoading('hide');
        };

        var openSubSection = function (subSectionId) {
          $('.subsection').hide();
          $('#' + subSectionId).show();
        };

        var closeAllSubsection = function () {
          $('.subsection').hide();
        };

        $('.subsection').each(function (index, subsection) {
          var id = $(subsection).attr('id');
          $('#links a[href=#' + id + ']').click(function () {
            openSubSection(id);
          });
        });
    		if (window.location.hash !== '' && window.location.hash.indexOf('&') === -1) {
          closeAllSubsection();
    			$(window.location.hash).show();
    		}

        var genericError = function (subSectionId) {
          return function (XMLHttpRequest, textStatus, errorThrown) {
          trace('error', XMLHttpRequest, textStatus, errorThrown);
          if (subSectionId) {
            formLoaded(subSectionId);
          }

          if (XMLHttpRequest.readyState === 4) {
                // HTTP error (can be checked by XMLHttpRequest.status and XMLHttpRequest.statusText)
                notie.alert(3, 'Error (' + XMLHttpRequest.status + ', ' + XMLHttpRequest.statusText + ')', 5);
            }
            else if (XMLHttpRequest.readyState === 0) {
                // Network error (i.e. connection refused, access denied due to CORS, etc.)
                notie.alert(3, 'Network Error', 5);
            }
            else {
                // something weird is happening
                notie.alert(3, 'Error !', 5);
            }
          };
        };


        //confirmation email
    		if (window.location.hash.startsWith('#confirm?')) {
          (function () {
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
                notie.alert(1, 'Success! You are confirmed', 1.5);
      				},
              error : genericError,
      				contentType:'application/json;charset=UTF-8',
      			  	dataType: 'json'
      			});
          })();
    		}

        //reset password open e prefill
    		if (window.location.hash.startsWith('#reset-password?')) {
          (function () {
      			var email = getParameterByName('e');
      			var token = getParameterByName('t');

      			trace('password-lost ' + email + ' with token ' + token);

            $('#reset-password form input[name=email]').val(email);
            $('#reset-password form input[name=token]').val(token);

            openSubSection('reset-password');
          }());
    		}

        //reset password submit
        $('#reset-password form').validate({
          rules : {
            password : {
              required : true
            }
          }
        });

    		$('#reset-password form').submit(function( event ) {
          if ($(event.target).valid()) {
      			var email = $('#reset-password form input[name=email]').val();
      			var token = $('#reset-password form input[name=token]').val();
      			var password = $('#reset-password form input[name=password]').val();

            formLoading('reset-password');

      			$.ajax({
              timeout: 20000,
              cache : false,
      				type: 'PUT',
      				url: buildUrl('lostToken/' + token),
      				data: JSON.stringify({
      				    'email' : email,
      				    'password' : password
      				}),
      				success: function (response) {
      					trace(response);
                notie.alert(1, 'Password correctly changed', 1.5);
                formLoaded('reset-password');
                closeAllSubsection();
      				},
              error : genericError('signup'),
      				contentType:'application/json;charset=UTF-8',
      			  dataType: 'json'
      			});
          }
    			event.preventDefault();
    		});

        //signup
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

            formLoading('signup');

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
                notie.alert(1, 'Success! Check you email', 1.5);
                formLoaded('signup');
                closeAllSubsection();
      				},
              error : genericError('signup'),
      				contentType:'application/json;charset=UTF-8',
      			  dataType: 'json'
      			});
          }
    			event.preventDefault();
    		});

        //password-lost
        $('#password-lost form').validate({
          rules : {
            email : {
              required : true,
              email : true
            }
          }
        });

    		$('#password-lost form').submit(function( event ) {
          if ($(event.target).valid()) {
      			var email = $('#password-lost form input[name=email]').val();

            formLoading('password-lost');

      			$.ajax({
              timeout: 20000,
              cache : false,
      				type: 'POST',
      				url: buildUrl('lostToken'),
      				data: JSON.stringify({
      				    'email' : email,
      				    'baseUrl' :  document.location.protocol + '//' + document.location.host + document.location.pathname + '#reset-password',
      				    'skipEmailSend' : false
      				}),
      				success: function (response) {
      					trace(response);
                notie.alert(1, 'Check you email', 1.5);
                formLoaded('password-lost');
                closeAllSubsection();
      				},
              error : genericError('password-lost'),
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
