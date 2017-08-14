'use strict';
define(['productSeek', 'jquery'], 
	function(productSeek) {
		return productSeek.factory('restService', function($http, url) {
			function doGet(baseUrl, params) {
				var params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
				return  $http.get(baseUrl + params)
						.then(function(response) {
							return response.data;
						})
						.catch(function(response) {
							return response.data;
						});
			}
			
			return {
				getProducts: function(params) {
					return doGet(url + '/products', params);
				},
				getProduct: function(id) {
					return doGet(url + '/products/' + id, {});
				},
				getComments: function(id, params) {
					return doGet(url + '/products/' + id + '/comments', params);
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
				getProductVoters: function(id, params) {
					return doGet(url + '/product/' + id + '/voters', params);
				},
				searchProducts: function(query, params) {
					return doGet(url + '/search/products', params);
				},
				searchUsers: function(query, params) {
					return doGet(url + '/search/users', params);
				}
			}
		});
	}
);

