'use strict';
define(['productSeek'], function(productSeek) {
	productSeek.service('titleService', ['$window', function($window) {
		this.setTitle = function(title) {
			$window.document.title = title + ' - Product Seek';
		};

		this.setDefaultTitle = function() {
			$window.document.title = 'Product Seek';
		};
	}]);
});