'use strict';
define(['productSeek', 'directives/productItem', 'directives/userItem', 'services/sessionService', 'services/titleService', 'services/restService', 'services/snackbarService'], function(productSeek) {

	productSeek.controller('SearchCtrl', ['$scope', '$rootScope', '$translate', 'titleService', 'productsData', 'usersData', 'query', 'sessionService', 'restService', 'snackbarService',
		function($scope, $rootScope, $translate, titleService, productsData, usersData, query, session, restService, snackbarService) {
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
	  
		$scope.setTab = function(tab) {
			var other = tab == 1 ? 0 : 1;
			$scope.tabs[tab] = true;
			$scope.tabs[other] = false;
		};

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

		var productsPage = 1, usersPage = 1;
		$scope.productsDisable = $scope.products.length == $scope.totalProducts;
		$scope.usersDisable = $scope.users.length == $scope.totalUsers;

		var showedNoConnectionProducts = false;
		var showedNoConnectionUsers = false;

		$scope.loadMoreProducts = function() {
			$scope.productsScrollBusy = true;

			restService.searchProducts(query, productsPage + 1)
			.then(function(data) {
				productsPage++;
				showedNoConnectionProducts = false;
				$scope.productsScrollBusy = false;
				$scope.products.push.apply($scope.products, data.products);
				$scope.productsDisable = $scope.products.length >= $scope.totalProducts;
			})
			.catch(function() {
				$scope.productsScrollBusy = false;
				if (!showedNoConnectionProducts) {
					showedNoConnectionProducts = true;
					snackbarService.showNoConnection();
				}
			});
		};

		$scope.loadMoreUsers = function() {
			$scope.usersScrollBusy = true;

			restService.searchUsers(query, usersPage + 1)
			.then(function(data) {
				usersPage++;
				showedNoConnectionUsers = false;
				$scope.usersScrollBusy = false;
				$scope.users.push.apply($scope.users, data.users);
				$scope.usersDisable = $scope.users.length >= $scope.totalUsers;
			})
			.catch(function() {
				$scope.usersScrollBusy = false;
				if (!showedNoConnectionUsers) {
					showedNoConnectionUsers = true;
					snackbarService.showNoConnection();
				}
			});
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