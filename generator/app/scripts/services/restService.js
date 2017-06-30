'use strict';
define(['productSeek'], 
	function(productSeek) {
		return productSeek.factory('restService', function($http, url) {
			return {
				getProducts: function(category, page, pageSize, orderBy, sort, successCallback, errorCallback) {
					$http.get(url + '/products').success(successCallback).error(errorCallback);
				},
				getProduct: function(id, successCallback, errorCallback) {
					$http.get(url + '/products/' + id).success(successCallback).error(errorCallback);
				}
			}
		});
	}
);