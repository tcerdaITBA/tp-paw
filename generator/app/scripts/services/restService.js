'use strict';
define(['productSeek', 'jquery'], 
	function(productSeek) {
		return productSeek.factory('restService', function($http, url) {
			function doGet(baseUrl, params, successCB, errorCB) {
				$http.get(baseUrl + '?' + jQuery.param(params)).success(successCB).error(errorCB);
			}
			
			return {
				// TODO: capaz se puede usar algo tipo un Builder para hacerlo más copado
				getProducts: function(category, page, pageSize, orderBy, order, success, error) {
					var params = {category: category, page: page, per_page: pageSize, sorted_by: orderBy, order: order};
					doGet(url + '/products', params, success, error);
				},
				getProduct: function(id, success, error) {
					doGet(url + '/products/' + id, {}, success, error);
				},
				getComments: function(id, page, pageSize, success, error) {
					doGet(url + '/products/' + id + '/comments', {page: page, per_page: pageSize}, success, error);
				},
				getCollectionsForUser: function(id, success, error) {
					doGet(url + '/users/' + id + '/collections', {}, success, error);
				},
				getUser: function(id, success, error) {
					doGet(url + '/users/' + id, {}, success, error);
				},
				getVotedByUser: function(id, success, error) {
					doGet(url + '/users/' + id + '/voted', success, error);
				},
				getPostedByUser: function(id, success, error) {
					doGet(url + '/users/' + id + '/posted', success, error);
				},
				getProductVoters: function(id, page, pageSize, success, error) {
					doGet(url + '/product/' + id + '/voters', {page:page, per_page: pageSize}, success, error);
				},
				searchProducts: function(query, page, pageSize, success, error) {
					doGet(url + '/search/products', {q: query, page: page, per_page: pageSize}, success, error);
				},
				searchUsers: function(query, page, pageSize, success, error) {
					doGet(url + '/search/users', {q: query, page: page, per_page: pageSize}, success, error);
				},
			}
		});
	}
);

