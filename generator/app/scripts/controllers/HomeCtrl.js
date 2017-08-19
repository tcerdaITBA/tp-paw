'use strict';
define(['productSeek', 'services/restService', 'directives/productItem'], function(productSeek) {

	productSeek.controller('HomeCtrl', ['$scope', 'restService', function($scope, restService) {
        restService.getProducts({page: 1, pageSize: 15})
        .then(function(data) {
			console.log(data);
			$scope.productsData = data;	
		})
        .catch(function(data) {
			$scope.error = true; // Algo para comunicar mensaje de problema de conexión
		});
	}]);
});