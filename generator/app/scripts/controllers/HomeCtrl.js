'use strict';
define(['productSeek', 'directives/productItem', 'services/restService', 'services/snackbarService', 'directives/loading', 'services/titleService', 'services/authService'], function(productSeek) {

	productSeek.controller('HomeCtrl', ['$scope', '$routeParams', '$translate', 'titleService', 'restService', 'snackbarService', 'authService', 'productsData', 'categories', 'categoriesImage', 'sortCriterias', 'defaultSortCriteria', 
		function($scope, $routeParams, $translate, titleService, restService, snackbarService, authService, productsData, categories, categoriesImage, sortCriterias, defaultSortCriteria) {
		
		if ($routeParams.category) {
			$translate($routeParams.category).then(function(title) {
				titleService.setTitle(title);
			});
		}
		else {
			titleService.setDefaultTitle();
		}

		$scope.isLoggedIn = authService.isLoggedIn();

		$scope.$on('user:updated', function() {
			$scope.isLoggedIn = authService.isLoggedIn();
		});

		$scope.popoverTemplate = 'views/popovers/loginRequiredPost.html';

		$scope.products = productsData.products;
		$scope.category = $routeParams.category;
		$scope.orderBy = $routeParams.orderBy || defaultSortCriteria.orderBy;
		$scope.order = $routeParams.order || defaultSortCriteria.order;
		$scope.page = $routeParams.page || 1;
		$scope.pageSize = $routeParams.pageSize || 10;
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
			url: urlBuilder({category: 'all'}), 
			imageUrl: categoriesImage['all']
		});
		
		angular.forEach(categories, function(category) {
			$scope.categories.push({
				name: category,
				active: $scope.category === category,
				url: urlBuilder({category: category}),
				imageUrl: categoriesImage[category]
			});
		});
		
		$scope.setActiveSortCriteria = function(sortCriteria) {
			for (var i = 0; i < $scope.sortCriterias.length; i++) {
				var sc = $scope.sortCriterias[i];
				sc.active = sc === sortCriteria;
			}  
		};
		
		$scope.setActiveCategory = function(category) {
			for (var i = 0; i < $scope.categories.length; i++) {
				var c = $scope.categories[i];
				c.active = c === category;
			}
		};
		
		$scope.disableScroll = $scope.products.length < $scope.pageSize;

		var showedNoConnection = false;

		$scope.loadMoreProducts = function() {
			$scope.scrollBusy = true;
			var params = {};
			params.category = $scope.category;
			params.page = $scope.page + 1;
			params.pageSize = $scope.pageSize;
			params.orderBy = $scope.orderBy;
			params.order = $scope.order;

			restService.getProducts(params)
			.then(function(productsData) {
				showedNoConnection = false;
				$scope.page++;
				
				if (productsData.count != 0) {
					$scope.scrollBusy = false;
					$scope.products.push.apply($scope.products, productsData.products);
					$scope.disableScroll = productsData.count < $scope.pageSize;
				} else {
					$scope.disableScroll = true;
				}
			})
			.catch(function() {
				$scope.scrollBusy = false;
				if (!showedNoConnection) {
					showedNoConnection = true;
					snackbarService.showNoConnection();
				}
			});
		};
	}]);
});