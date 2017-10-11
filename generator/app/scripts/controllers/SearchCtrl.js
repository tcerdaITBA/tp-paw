'use strict';
define(['productSeek', 'directives/productItem', 'directives/userItem'], function(productSeek) {

	productSeek.controller('SearchCtrl', ['$scope', 'productsData', 'usersData', 'query', function($scope, productsData, usersData, query) {
    	$scope.query = query;
		$scope.tabs = [false, false];

		$scope.products = productsData.products;
		$scope.users = usersData.users;
		
		if (productsData.count > 0)
			$scope.tabs[0] = true;
		else if (usersData.count > 0)
			$scope.tabs[1] = true;
		else
			$scope.tabs[0] = true;
      
    	$scope.categories = []
		
		// Fill present categories
		var products = productsData.products;
		for (var i = 0; i < products.length; i++) {
			var newCategory = {name: products[i].category, checked: true};
			if (!listHasCategory($scope.categories, newCategory.name)) {
				$scope.categories.push(newCategory);
			}
		}
		
		$scope.resetFilters = function() {
			angular.forEach($scope.categories, function(item) {
				console.log(item)
				item.checked = true;
			});
		};
	}]);
	
	productSeek.filter('categoryFilter', function() {
		return function(items, categories) {
			var filtered = [];
			angular.forEach(items, function(item) {
				if (listHasCategory(categories, item.category))
					filtered.push(item);
			});
			return filtered;
		}
	});
	
	function listHasCategory(categories, cName) {
		for (var j = 0; j < categories.length; j++) {
			if (categories[j].name == cName && categories[j].checked)
				return true;
		}
		return false;
	}
});