'use strict';
define(['productSeek', 'directives/ngFileRead'], function(productSeek) {

	productSeek.controller('PostCtrl', ['$scope', function($scope) {
		$scope.product = {};
		
		$scope.product.images = new Array(4); // TODO image count en service constante
				
		$scope.post = function() {
			console.log($scope.product);
		};
		
		$scope.fileRead = function(data) {
			console.log(data);
		};
		
		$scope.deleteImage = function(index) {
			$scope.product.images[index] = null;
		};
	}]);
});