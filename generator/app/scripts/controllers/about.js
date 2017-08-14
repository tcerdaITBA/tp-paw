define(['productSeek', 'services/restService'], function(productSeek, restService) {

    'use strict';
    productSeek.controller('about', ['$scope', 'restService', function($scope, restService) {
        $scope.abouts = [{text: 'Product Seek', likes: 0}];       

        $scope.addAbout = function() {
            $scope.abouts.push({text: $scope.aboutText, likes: 0});
            $scope.aboutText = '';
        };

        $scope.like = function(about) {
            about.likes += 1;
        }

        $scope.unlike = function(about) {
            if (about.likes > 0)
                about.likes -= 1;
        }
				
		$scope.test = function() {
			restService.getProducts({page: 2, per_page: 3})
			.then(function(data) { // success
				console.log(data);
			})
			.catch(function(data) { // error
				console.log('ERROR GETTING PRODUCTS');
			});
			
			restService.getProduct(33)
			.then(function(data) {
				console.log(data);
			})
			.catch(function(data) {
				console.log('ERROR GETTING PRODUCTS');
			});
		}
    }]);

});
