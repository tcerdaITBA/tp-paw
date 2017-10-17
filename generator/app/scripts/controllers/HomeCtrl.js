'use strict';
define(['productSeek', 'directives/productItem', 'services/restService', 'directives/loading'], function(productSeek) {

	productSeek.controller('HomeCtrl', ['$scope', '$routeParams', 'productsData', 'categories', 'sortCriterias', 'defaultSortCriteria', function($scope, $routeParams, productsData, categories, sortCriterias, defaultSortCriteria) {
        $scope.products = productsData.products;
        $scope.category = $routeParams.category;
        $scope.orderBy = $routeParams.orderBy || defaultSortCriteria.orderBy;
        $scope.order = $routeParams.order || defaultSortCriteria.order;
        $scope.sortCriterias = sortCriterias;
        $scope.categories = [];
        
        var urlBuilder = function(params) {
            var category;
            
            if (params.category === 'all')
                category = '';
            else    
                category = params.category ? 'category=' + params.category : $scope.category ? 'category=' + $scope.category : '';
            var orderBy = params.orderBy || $scope.orderBy;
            var order = params.order || $scope.order;
            
            return '/?orderBy=' + orderBy + '&order=' + order + (category.length ? '&' + category : '');
        }
        
        angular.forEach(sortCriterias, function(sortCriteria) {
            sortCriteria['active'] = $scope.orderBy === sortCriteria.orderBy;
            sortCriteria['url'] = urlBuilder({orderBy: sortCriteria.orderBy, order: sortCriteria.order});
        });
        
        $scope.categories.push({
            name: 'all', 
            active: $scope.category ? false : true, // 'all' active if not in category page
            url: urlBuilder({category: 'all'})
        });
        
        angular.forEach(categories, function(category) {
            $scope.categories.push({
                name: category,
                active: $scope.category === category,
                url: urlBuilder({category: category}),
                imageUrl: 'images/' + category + '.svg'
            });
        });
        
        $scope.setActiveSortCriteria = function(sortCriteria) {
            for (var i = 0; i < $scope.sortCriterias.length; i++) {
                var sc = $scope.sortCriterias[i];
                sc.active = sc === sortCriteria;
            }
                
        }
        
        $scope.setActiveCategory = function(category) {
            for (var i = 0; i < $scope.categories.length; i++) {
                var c = $scope.categories[i];
                c.active = c === category;
            }
        }        
	}]);
});