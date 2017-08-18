'use strict';
define(['productSeek', 'services/restService', 'directives/productItem'], function(productSeek, restService, productItem) {

	productSeek.controller('HomeCtrl', ['$scope', 'restService', function($scope, restService) {
		$scope.homePageText = 'NICE HOMEPAGE';
		// TODO: categoría, orden, sort, pageSize
		restService.getProducts(undefined, 1, 20, undefined, undefined,
		function(data) {
			console.log(data);
			$scope.productsData = data;	
		}, 
		function(data) {
			$scope.error = true; // Algo para comunicar mensaje de problema de conexión
		})
	}]);
});