'use strict';
define(['productSeek', 'directives/productItem'], function(productSeek) {

	productSeek.controller('SearchCtrl', ['$scope', 'productsData', 'usersData', function($scope, productsData, usersData) {
    	$scope.query = 'google';
		
		$scope.tabs = [false, false];
		
		console.log(productsData);
		console.log(usersData);
		
		$scope.products = productsData.products;
		$scope.users = usersData.users;
		
	}]);
});