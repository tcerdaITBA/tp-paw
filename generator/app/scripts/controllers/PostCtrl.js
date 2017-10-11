'use strict';
define(['productSeek', 'directives/ngFileRead'], function(productSeek) {

	productSeek.controller('PostCtrl', ['$scope', 'categories', 'productImagesCount', 'productVideosCount', function($scope, categories, productImagesCount, productVideosCount) {
		$scope.categories = categories;
		
		$scope.youtubeRegex = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
		
		$scope.product = {};
		$scope.product.category = $scope.categories[categories.indexOf('other')]; // Medio hardcodeado
		$scope.product.images = new Array(productImagesCount);
        $scope.product.videoLinks = new Array(productVideosCount);
				
		$scope.doSubmit = function() {
			var empty = true;
			angular.forEach($scope.product.images, function(img) {
				if (img)
					empty = false;
			});
			angular.forEach($scope.product.videoLinks, function(video) {
				if (video)
					empty = false;
			});
			$scope.noImagesError = empty;
			
			console.log($scope.product);
		};
		
		$scope.deleteImage = function(index) {
			$scope.product.images[index] = null;
		};
	}]);
});