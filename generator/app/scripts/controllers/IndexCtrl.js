'use strict';
define(['productSeek', 'jquery', 'services/authService', 'services/sessionService', 'services/modalService', 'controllers/SignInModalCtrl', 'controllers/SignUpModalCtrl', 'directives/focusIf'], function(productSeek) {

	productSeek.controller('IndexCtrl', ['sessionService', 'authService', 'modalService', '$scope', '$location', function(session, auth, modal, $scope, $location) {
		$scope.showSuggestions = false;
		$scope.isLoggedIn = auth.isLoggedIn();
		$scope.loggedUser = auth.getLoggedUser();

		// No me anduvo con $scope.logOut = auth.logOut
		$scope.logOut = function() {
			auth.logOut();
		};

		var focusIndex = -1;
		$scope.searchFieldFocus = false;
		$scope.focusElems = [];

		$scope.searchHistory = session.getSearchHistory();

		$scope.search = function() {
			// TODO: error message if empty, too short, too long...
			if ($scope.query && 3 <= $scope.query.length && $scope.query.length <= 64) {
				session.saveToSearchHistory($scope.query);
				$scope.searchHistory = session.getSearchHistory();
				$location.url('#/search?q=' + $scope.query);
			}
		};

		$scope.$on('user:updated', function() {
			$scope.isLoggedIn = auth.isLoggedIn();
			$scope.loggedUser = auth.getLoggedUser();
		});

		$scope.searchFocus = function() {
			$scope.showSuggestions = true;
		};
		
		$(document).click(function(e) {
			console.log(e);
			var container = $('.search-form-container');
			if (!container.is(event.target) && container.has(event.target).length === 0) {
				$scope.showSuggestions = false;
				focusIndex = -1;	
				$scope.$apply();
			}
		});

		$scope.searchKeyDown = function(e) {
			if (e.keyCode == 38) { // arrow up
				if (focusIndex <= 0) { // Focus back to input
					focusIndex = -1;
					$scope.focusElems[0] = false;
					$scope.searchFieldFocus = true;
				} else {
					$scope.focusElems[focusIndex--] = false;
					$scope.focusElems[focusIndex] = true;
				}
				e.preventDefault();
			} 
			else if (e.keyCode == 40) { // arrow down
				$scope.searchFieldFocus = false;
				$scope.focusElems[focusIndex] = false;
				// TODO: agregar most popular products
				focusIndex = (focusIndex < $scope.searchHistory.length - 1 ? focusIndex + 1 : focusIndex);
				$scope.focusElems[focusIndex] = true;
				e.preventDefault();
			}
		}

		$scope.signInModal = modal.signInModal;
		$scope.signUpModal = modal.signUpModal;
	}]);
});
