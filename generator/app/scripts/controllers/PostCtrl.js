'use strict';
define(['productSeek', 'directives/ngFileRead'], function(productSeek) {

	productSeek.controller('PostCtrl', ['$scope', 'categories', 'productImagesCount', 'productVideosCount', function($scope, categories, productImagesCount, productVideosCount) {
		$scope.product = {};
		$scope.categories = categories;
		$scope.product.images = new Array(productImagesCount);
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