'use strict';
define(['productSeek', 'jquery', 'services/authService', 'services/sessionService', 'services/modalService', 'controllers/SignInModalCtrl', 'controllers/SignUpModalCtrl', 'directives/focusIf', 'services/restService'], function(productSeek) {
	
	productSeek.controller('IndexCtrl', ['sessionService', 'authService', 'modalService', 'restService', '$scope', '$location', function(session, auth, modal, restService, $scope, $location) {
		var searchMinLength = 3;
		$scope.showSuggestions = false;
		$scope.isLoggedIn = auth.isLoggedIn();
		$scope.loggedUser = auth.getLoggedUser();

		$scope.logOut = function() {
			auth.logOut();
		};

		var focusIndex = -1;
		$scope.searchFieldFocus = false;

		$scope.searchHistory = session.getSearchHistory();
		$scope.searchSuggestions = [];

		$scope.$on('searchHistory:updated', function() {
			$scope.searchHistory = session.getSearchHistory();
		});
		
		$scope.$on('user:picture', function(event, picture) {
			$scope.loggedUser.picture_url = picture;
		});

		$scope.search = function(q) {
			if ($scope.searchForm.$valid) {
				$scope.searchFieldFocus = false;
				$location.url('/search?q=' + q);
			}
		};

		$scope.$on('user:updated', function() {
			$scope.isLoggedIn = auth.isLoggedIn();
			$scope.loggedUser = auth.getLoggedUser();
		});
		
		$scope.$on('$locationChangeStart', function(event) {
			if ($location.path() == '/post')
				$scope.hidePost = true;
			else
				$scope.hidePost = false;
		});

		$scope.searchFocus = function() {
			$scope.searchFieldFocus = true;
			focusIndex = -1;
		};
		
		$(document).click(function(e) {
			var container = $('#search-box');
			if (!container.is(event.target) && container.has(event.target).length === 0) {
				// blur search suggestions
				$scope.searchFieldFocus = false;
				focusIndex = -1;	
				$scope.$apply();
			}
		});

		$scope.arrowControl = function(e) {
			switch (e.keyCode) {
				case 38: // arrow up
					e.preventDefault();
					if (focusIndex <= 0) // Focus back to input
						$scope.searchFocus();
					else
						focusIndex--;
					break;
					
				case 40: // arrow down
					e.preventDefault();
					$scope.searchFieldFocus = false;
					focusIndex = (focusIndex < $scope.searchHistory.length + $scope.searchSuggestions.length - 1 ? focusIndex + 1 : focusIndex);
					break;
				
				default: // vuelve a escribir en el cuadro de busqueda
					$scope.searchFocus();
					break;
			}
		};

		$scope.autocompleteSearch = function() {
			if ($scope.query && $scope.query.length >= searchMinLength) {
				restService.searchProducts($scope.query).then(function(data) {
					$scope.searchSuggestions = data.products;
				});
			} else {
				$scope.searchSuggestions = [];
			}
		};
		
		$scope.showSuggestions = function() {
			return ($scope.searchFieldFocus || focusIndex >= 0) && ($scope.searchSuggestions.length || $scope.searchHistory.length);
		}
		
		// Chequea si el item de autocomplete tiene que estar focused
		$scope.autocompleteFocus = function(index) {
			return index == focusIndex
		}
		
		// Chequea si el item del historial tiene que estar focused	
		$scope.historyFocus = function(index) {
			return index + $scope.searchSuggestions.length == focusIndex
		}
		
		$scope.signInModal = modal.signInModal;
		$scope.signUpModal = modal.signUpModal;
	}]);
});
