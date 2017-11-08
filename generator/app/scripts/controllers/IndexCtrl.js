'use strict';
define(['productSeek', 'jquery', 'services/authService', 'services/sessionService', 'services/modalService', 'controllers/SignInModalCtrl', 'controllers/SignUpModalCtrl', 'directives/focusIf', 'services/restService'], function(productSeek) {
	
	productSeek.controller('IndexCtrl', ['sessionService', 'authService', 'modalService', 'restService', '$scope', '$location', function(session, auth, modal, restService, $scope, $location) {
		var searchMinLength = 3;
		$scope.showSuggestions = false;
		$scope.emptyResults = false;
		$scope.isLoggedIn = auth.isLoggedIn();
		$scope.loggedUser = auth.getLoggedUser();

		$scope.logOut = function() {
			auth.logOut();
		};

		var focusIndex = -1;
		$scope.searchFieldFocus = false;
		$scope.focus = [];

		$scope.searchHistory = session.getSearchHistory();
		$scope.searchSuggestions = [];

		$scope.$on('searchHistory:updated', function() {
			$scope.searchHistory = session.getSearchHistory();
		});
		
		$scope.$on('user:picture', function(event, picture) {
			$scope.loggedUser.picture_url = picture;
		});

		$scope.search = function(q) {
			// TODO: error message if empty, too short, too long...
			if (q && searchMinLength <= q.length && q.length <= 64) {
				$scope.showSuggestions = false;
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
			$scope.showSuggestions = true;
			focusIndex = -1;
		};
		
		$(document).click(function(e) {
			var container = $('#search-box');
			if (!container.is(event.target) && container.has(event.target).length === 0) {
				$scope.showSuggestions = false;
				$scope.$apply();
			}
		});

		$scope.arrowControl = function(e) {
			if (e.keyCode == 38) { // arrow up
				e.preventDefault();
				if (focusIndex <= 0) { // Focus back to input
					$scope.focus[0] = false;
					$scope.searchFocus();
				} else {
					$scope.focus[focusIndex--] = false;
					$scope.focus[focusIndex] = true;
				}
			}
			else if (e.keyCode == 40) { // arrow down
				e.preventDefault();
				$scope.searchFieldFocus = false;
				if (focusIndex >= 0)
					$scope.focus[focusIndex] = false;

				focusIndex = (focusIndex < $scope.searchHistory.length - 1 ? focusIndex + 1 : focusIndex);
				$scope.focus[focusIndex] = true;
			}
		};

		$scope.autocompleteSearch = function() {
			if ($scope.query && $scope.query.length >= searchMinLength) {
				restService.searchProducts($scope.query).then(function(data) {
					$scope.emptyResults = data.products.length == 0;
					$scope.searchSuggestions = data.products;
				});
			}
		};
		
		$scope.signInModal = modal.signInModal;
		$scope.signUpModal = modal.signUpModal;
	}]);
});
