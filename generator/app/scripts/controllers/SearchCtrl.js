'use strict';
define(['productSeek', 'directives/productItem', 'directives/userItem', 'services/sessionService', 'services/titleService'], function(productSeek) {

	productSeek.controller('SearchCtrl', ['$scope', '$rootScope', '$translate', 'titleService', 'productsData', 'usersData', 'query', 'sessionService', function($scope, $rootScope, $translate, titleService, productsData, usersData, query, session) {
		$translate('title.search').then(function(title) {
			titleService.setTitle(title);
		});

		$scope.translationData = {
			keyword: query
		};

    	$scope.query = query;
		$scope.tabs = [false, false];

		$scope.products = productsData.products;
		$scope.users = usersData.users;
		$scope.totalProducts = productsData.totalCount;
		$scope.totalUsers = usersData.totalCount;
		
		session.saveToSearchHistory(query);
		$rootScope.$broadcast('searchHistory:updated');

		if (productsData.count > 0)
			$scope.tabs[0] = true;
		else if (usersData.count > 0)
			$scope.tabs[1] = true;
		else
			$scope.tabs[0] = true;
      
    	$scope.categories = [];
		
		// Fill present categories
		var products = productsData.products;
		for (var i = 0; i < products.length; i++) {
			var newCategory = {name: products[i].category, checked: false};
			if (!listHasCategory($scope.categories, newCategory.name, true)) {
				$scope.categories.push(newCategory);
			}
		}
		
		$scope.resetFilters = function() {
			angular.forEach($scope.categories, function(item) {
				item.checked = false;
			});
		};
		
		$scope.sendMail = function(event) {
			window.location.href = "mailto:" + user.email;
			event.stopPropagation();
		};
	}]);
	
	productSeek.filter('categoryFilter', function() {
		return function(items, categories) {
			var filtered = [];
			angular.forEach(items, function(item) {
				if (listHasCategory(categories, item.category)) {
					filtered.push(item);
				}
			});

			return filtered.length ? filtered : items;
		}
	});
	
	function listHasCategory(categories, cName, ignoreCheck) {
		for (var j = 0; j < categories.length; j++) {
			if (categories[j].name == cName && (categories[j].checked || ignoreCheck))
				return true;
		}
		return false;
	}
});