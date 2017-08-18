'use strict';
define(['productSeek', 'services/restService', 'directives/productItem'], function(productSeek, restService, productItem) {

	productSeek.controller('HomeCtrl', ['$scope', 'restService', function($scope, restService) {
		$scope.homePageText = 'NICE HOMEPAGE';

        restService.getProducts({page: 1, pageSize: 20})
        .then(function(data) {
			console.log(data);
			$scope.productsData = data;	
		})
        .catch(function(data) {
			$scope.error = true; // Algo para comunicar mensaje de problema de conexión
		});
	}]);
});