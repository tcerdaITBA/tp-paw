'use strict';
define(['productSeek'], function(productSeek) {
	
	productSeek.factory('sessionService', function($window) {
		var Session = {};
		var MAX_SEARCH_COUNT = 4;
		Session._user = JSON.parse($window.localStorage.getItem('session.user')) || JSON.parse($window.sessionStorage.getItem('session.user'));
		
		Session._accessToken = JSON.parse($window.localStorage.getItem('session.accessToken')) || JSON.parse($window.sessionStorage.getItem('session.accessToken'));
		
		Session._searchHistory = JSON.parse($window.localStorage.getItem('session.history'));
		
		if (!Session._searchHistory)
			Session._searchHistory = [];

		Session.getUser = function(){
			return this._user;
		};

		Session.setUser = function(user, isLocalStorage){
			this._user = user;
			
			if (isLocalStorage)
				$window.localStorage.setItem('session.user', JSON.stringify(user));
			else
				$window.sessionStorage.setItem('session.user', JSON.stringify(user));
			return this;
		};

		Session.getAccessToken = function(){
			return this._accessToken;
		};

		Session.setAccessToken = function(token, isLocalStorage){
			this._accessToken = token;
			if (isLocalStorage)
				$window.localStorage.setItem('session.accessToken', JSON.stringify(token));
			else
				$window.sessionStorage.setItem('session.accessToken', JSON.stringify(token));
			return this;
		};

		Session.destroy = function destroy(){
			this.setUser(null);
			this.setUser(null, true);
			this.setAccessToken(null);
			this.setAccessToken(null, true);
			this.cleanSearchHistory();
		};
		
		Session.getSearchHistory = function() {
			return this._searchHistory;
		}
		
		Session.saveToSearchHistory = function (query) {
			var index = this._searchHistory.indexOf(query);
			
			if (index != -1) // Do not save repeated, delete and save on top.
				this._searchHistory.splice(index, 1);
			
			this._searchHistory.unshift(query);
			this._searchHistory = this._searchHistory.slice(0, MAX_SEARCH_COUNT);
			$window.localStorage.setItem('session.history', JSON.stringify(this._searchHistory));
		};
		
		Session.cleanSearchHistory = function() {
			this._searchHistory = [];
			$window.localStorage.setItem('session.history', JSON.stringify(this._searchHistory));
		};

		return Session;
	});

});