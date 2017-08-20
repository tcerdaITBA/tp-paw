'use strict';
define(['productSeek', 'directives/productItem', 'services/restService'], function(productSeek) {

	productSeek.controller('HomeCtrl', ['$scope', 'productsData', function($scope, productsData) {
        $scope.products = productsData.products;
	}]);
});