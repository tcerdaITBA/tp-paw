'use strict';
define(['productSeek', 'directives/ngFileRead', 'services/restService'], function(productSeek) {

	productSeek.controller('PostCtrl', ['$scope', 'categories', 'productImagesCount', 'productVideosCount', 'restService', function($scope, categories, productImagesCount, productVideosCount, restService) {
		$scope.categories = categories;
		
		$scope.youtubeRegex = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
		
		$scope.product = {};
		$scope.product.category = $scope.categories[categories.indexOf('other')]; // Medio hardcodeado
		$scope.product.images = new Array(productImagesCount);
        $scope.product.videos = new Array(productVideosCount);
				
		$scope.doSubmit = function() {
			var empty = true;
			angular.forEach($scope.product.images, function(img) {
				if (img)
					empty = false;
			});
			angular.forEach($scope.product.videos, function(video) {
				if (video)
					empty = false;
			});
			$scope.noImagesError = empty;
			
			if ($scope.postForm.$valid && !$scope.noImagesError) {
				// TODO: pasar los links a solo ids
				// Formulario validado.
				console.log("Valid form");
				restService.postProduct($scope.product);
			} else {
				console.log("Invalid form");
			}
		};
		
		$scope.deleteImage = function(index) {
			$scope.product.images[index] = null;
		};
		
		$scope.deleteLogo = function() {
			$scope.product.logo = null;
		}
	}]);
});