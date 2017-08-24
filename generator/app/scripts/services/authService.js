'use strict';
define(['productSeek', 'services/sessionService'], function(productSeek) {


	return productSeek.factory('authService', ['$http', 'url', 'sessionService', '$q', function($http, url, session, $q) {
		var AuthService = {};
		AuthService.loggedUser = null;

		AuthService.logIn = function(username, password) {
			var credentials = { j_username: username, j_password: password };
			var self = this;
			return $http({
					method: 'POST',
					url: url + '/login',
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					transformRequest: function(obj) {
						var str = [];
						for (var p in obj)
							str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
						return str.join("&");
					},
					data: {j_username: username, j_password: password}
				})
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
				.catch(function(response) {
				session.destroy();
				return $q.reject(response.data);
			});
		};

		AuthService.isLoggedIn = function() {
			return !!this.loggedUser;
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