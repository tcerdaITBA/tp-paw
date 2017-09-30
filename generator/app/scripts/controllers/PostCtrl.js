'use strict';
define(['productSeek', 'directives/ngFileRead'], function(productSeek) {

	productSeek.controller('PostCtrl', ['$scope', function($scope) {
		$scope.product = {};
		
		$scope.product.images = []; // TODO image count en service constante
		for (var i = 0; i < 4; i++)
			$scope.product.images[i] = {
				data: '#',
				show: false
			};	
				
		$scope.post = function() {
			console.log($scope.product);
		};
		
		$scope.fileRead = function(data) {
			console.log(data);
		};
		
		$scope.deleteImage = function(input, index) {
			input.value = "";
			$scope.product.images[index].data = '#';
		};
	}]);
});