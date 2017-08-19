'use strict';
define(['productSeek'], function(productSeek) {

	productSeek.factory('sessionService', function($window) {
		var Session = {}
		Session._user = JSON.parse($window.localStorage.getItem('session.user'));
		Session._accessToken = JSON.parse($window.localStorage.getItem('session.accessToken'));

		Session.getUser = function(){
			return this._user;
		};

		Session.setUser = function(user){
			this._user = user;
			$window.localStorage.setItem('session.user', JSON.stringify(user));
			return this;
		};

		Session.getAccessToken = function(){
			return this._accessToken;
		};

		Session.setAccessToken = function(token){
			this._accessToken = token;
			$window.localStorage.setItem('session.accessToken', JSON.stringify(token));
			return this;
		};

		Session.destroy = function destroy(){
			this.setUser(null);
			this.setAccessToken(null);
		};

		return Session;
	});

});