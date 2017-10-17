'use strict';
define(['productSeek', 'directives/ngFileRead', 'services/restService'], function(productSeek) {

	productSeek.controller('PostCtrl', ['$scope', '$location', 'categories', 'productImagesCount', 'productVideosCount', 'restService', function($scope, $location, categories, productImagesCount, productVideosCount, restService) {
		$scope.categories = categories;
		
		$scope.youtubeRegex = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))([\w-]{11})(?:\S+)?$/;
        
        $scope.URLregex = "#([a-z]([a-z]|\d|\+|-|\.)*):(\/\/(((([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?((\[(|(v[\da-f]{1,}\.(([a-z]|\d|-|\.|_|~)|[!\$&'\(\)\*\+,;=]|:)+))\])|((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|(([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=])*)(:\d*)?)(\/(([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*|(\/((([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)|((([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)|((([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)){0})(\?((([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\xE000-\xF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\x00A0-\xD7FF\xF900-\xFDCF\xFDF0-\xFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?#iS"
		
		$scope.product = {};
		$scope.product.category = $scope.categories[categories.indexOf('other')]; // Medio hardcodeado
		$scope.product.images = new Array(productImagesCount);
        $scope.product.videos = new Array(productVideosCount);

        var checkNoImagesError = function() {
 			var empty = true;
            
			angular.forEach($scope.product.images, function(img) {
				if (img) {
					empty = false;
                }
			});
            
			angular.forEach($scope.product.videos, function(video) {
				if (video) {
					empty = false;
                }
			});
            
			$scope.noImagesError = empty;           
        };
        
        var extractIds = function() {
            $scope.product.videoIds = [];
            
            angular.forEach($scope.product.videos, function(video) {
                if (video) {
                    var id = $scope.youtubeRegex.exec(video)[1];
                    $scope.product.videoIds.push(id);
                }
            });
        };
        
        $scope.$watchCollection('product.images', checkNoImagesError);
        $scope.$watchCollection('product.videos', checkNoImagesError);
        
		$scope.doSubmit = function() {
            checkNoImagesError();
			
			if ($scope.postForm.$valid && !$scope.noImagesError) {
				console.log("Valid form");
                extractIds();
				restService.postProduct($scope.product)
                .then(function(data) {
                    $location.url('/product/' + data.id);
                });
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