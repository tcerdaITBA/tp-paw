'use strict';
define(['productSeek', 'services/sessionService'], function(productSeek) {

	return productSeek.factory('authService', ['$http', 'url', 'sessionService', function($http, url, session) {
		var AuthService = {};

		// TODO ver lo de onStateChange para comunicar si la autenticación fue exitosa o no
		// o devolver la promise para que se encargue de todo el controller
		AuthService.logIn = function(username, password) {
			var credentials = { j_username: username, j_password: password };
			$http.post(url + '/login', credentials)
			.then(
				function success(response) {
					session.setAccessToken(response.headers('X-AUTH-TOKEN'));
				},
				function error(response) {
					// Error stuff
					session.destroy();
				}
			);
		};

		AuthService.isLoggedIn = function() {
			return session.getAccessToken() !== null;
		};

		AuthService.logOut = function() {
			session.destroy();
		};
		
		AuthService.getLoggedUser = function() {
			if (this.isLoggedIn()) {
				$http.post(url + '/user', {headers: {'X-AUTH-TOKEN': session.getAccessToken()}});
			}
		}
		
		return AuthService;
	}]);

});