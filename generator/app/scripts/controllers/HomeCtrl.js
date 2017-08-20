'use strict';
define(['productSeek', 'directives/productItem', 'services/restService'], function(productSeek) {

	productSeek.controller('HomeCtrl', ['$scope', '$routeParams', 'productsData', 'categories', function($scope, $routeParams, productsData, categories) {
        $scope.products = productsData.products;
        $scope.category = $routeParams.category;
        $scope.categories = [];
        
        $scope.categories.push({
            name: 'all', 
            active: $scope.category ? false : true,
            url: '/',
            hasImage: false,
        });
        
        angular.forEach(categories, function(category) {
            $scope.categories.push({
                name: category,
                active: $scope.category === category,
                url: '/?category=' + category,
                hasImage: true,
            });
        });
        
        $scope.setActiveCategory = function(category) {
            for (var i = 0; i < $scope.categories.length; i++) {
                var c = $scope.categories[i];
                c.active = c.name === category;
            }
        }
	}]);
});