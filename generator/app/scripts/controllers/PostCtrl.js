'use strict';
define(['productSeek', 'directives/ngFileRead'], function(productSeek) {

	productSeek.controller('PostCtrl', ['$scope', 'productImagesCount', 'productVideosCount', function($scope, productImagesCount, productVideosCount) {
		$scope.product = {};
		
		$scope.product.images = new Array(productImagesCount); // TODO image count en service constante
        $scope.product.videoLinks = new Array(productVideosCount);
				
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