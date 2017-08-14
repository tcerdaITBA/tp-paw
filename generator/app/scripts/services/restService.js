'use strict';
define(['productSeek', 'jquery'], 
	function(productSeek) {
		return productSeek.factory('restService', function($http, url) {
			function doGet(baseUrl, params, successCB, errorCB) {
				return  $http.get(baseUrl + '?' + jQuery.param(params))
						.then(function(response) {
							return response.data;
						})
						.catch(function(response) {
							return response.data;
						});
			}
			
			return {
				// TODO: capaz se puede usar algo tipo un Builder para hacerlo más copado
				getProducts: function(category, page, pageSize, orderBy, order) {
					var params = {category: category, page: page, per_page: pageSize, sorted_by: orderBy, order: order};
					return doGet(url + '/products', params);
				},
				getProduct: function(id) {
					return doGet(url + '/products/' + id, {});
				},
				getComments: function(id, page, pageSize) {
					return doGet(url + '/products/' + id + '/comments', {page: page, per_page: pageSize});
				},
				getCollectionsForUser: function(id) {
					return doGet(url + '/users/' + id + '/collections', {});
				},
				getUser: function(id) {
					return doGet(url + '/users/' + id, {});
				},
				getVotedByUser: function(id) {
					return doGet(url + '/users/' + id + '/voted');
				},
				getPostedByUser: function(id) {
					return doGet(url + '/users/' + id + '/posted');
				},
				getProductVoters: function(id, page, pageSize) {
					return doGet(url + '/product/' + id + '/voters', {page:page, per_page: pageSize});
				},
				searchProducts: function(query, page, pageSize) {
					return doGet(url + '/search/products', {q: query, page: page, per_page: pageSize});
				},
				searchUsers: function(query, page, pageSize) {
					return doGet(url + '/search/users', {q: query, page: page, per_page: pageSize});
				},
			}
		});
	}
);

