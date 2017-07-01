'use strict';
define(['productSeek', 'jquery'], 
	function(productSeek) {
		return productSeek.factory('restService', function($http, url) {
			return {
				// TODO: capaz se puede usar algo tipo un Builder para hacerlo más copado
				getProducts: function(category, page, pageSize, orderBy, order, successCallback, errorCallback) {
					var params = jQuery.param(
						{category: category, page: page, per_page: pageSize, sorted_by: orderBy, order: order}
					);
					$http.get(url + '/products?' + params).success(successCallback).error(errorCallback);
				},
				getProduct: function(id, successCallback, errorCallback) {
					$http.get(url + '/products/' + id).success(successCallback).error(errorCallback);
				},
				getCollectionsForUser: function(id, successCallback, errorCallback) {
					$http.get(url + '/users/' + id + '/collections').success(successCallback).error(errorCallback);
				},
				getUser: function(id, successCallback, errorCallback) {
					$http.get(url + '/users/' + id).success(successCallback).error(errorCallback);
				},
				getProductVoters: function(id, page, pageSize) {
					var params = jQuery.param({page: page, per_page: pageSize})
					$http.get(url + '/product/' + id + '/voters?' + params).success(successCallback).error(errorCallback);
				}
			}
		});
	}
);
