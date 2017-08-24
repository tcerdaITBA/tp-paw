'use strict';
define(['productSeek', 'services/sessionService'], function(productSeek) {

	var logInError = function(error) {
		session.destroy();
	} 
	
	return productSeek.factory('authService', ['$http', 'url', 'sessionService', function($http, url, session) {
		var AuthService = {};
		AuthService.loggedUser = null;

		AuthService.logIn = function(username, password) {
			var credentials = { j_username: username, j_password: password };
			var self = this;
			return $http.post(url + '/login', credentials)
				.then(function(response) {
					session.setAccessToken(response.headers('X-AUTH-TOKEN'));
					return $http.get(url + '/user', {headers: {'X-AUTH-TOKEN': session.getAccessToken()}});
				})
				.then(function(response) {
					return response.data;
				})
				.then(function(data) {
					self.loggedUser = data;
					return data;
				})
				.catch(logInError);
		};

		AuthService.isLoggedIn = function() {
			return this.loggedUser !== null;
		};

		AuthService.logOut = function() {
			session.destroy();
			this.loggedUser = null;
		};
		
		AuthService.getLoggedUser = function() {
			return this.loggedUser;
		}
		
		return AuthService;
	}]);

});