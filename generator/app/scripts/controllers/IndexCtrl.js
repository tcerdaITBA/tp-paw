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

		// y si cambia la historia? No se actualiza. VER. Hay que hacer get de nuevo al 
		// guardar una nueva cosa
		$scope.searchHistory = session.getSearchHistory();

		$scope.search = function() {
			// TODO: error message if empty, too short, too long...
			if ($scope.query && 3 <= $scope.query.length && $scope.query.length <= 64) {
				session.saveToSearchHistory($scope.query);
				//$location.url('/search/products?q=' + $scope.query.text);
			}
		};

        $scope.$on('user:updated', function() {
            $scope.isLoggedIn = auth.isLoggedIn();
            $scope.loggedUser = auth.getLoggedUser();
        });
        
		$scope.searchFocus = function() {
			$scope.showSuggestions = true;
		};

		$scope.searchBlur = function() {
			$scope.showSuggestions = false;
		};

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
			} 
			else if (e.keyCode == 40) { // arrow down
				$scope.searchFieldFocus = false;
				$scope.focusElems[focusIndex] = false;
				// TODO: agregar most popular products
				focusIndex = (focusIndex < $scope.searchHistory.length - 1 ? focusIndex + 1 : focusIndex);
				$scope.focusElems[focusIndex] = true;
			}
			// console.log(focusIndex);
			// console.log($scope.focusElems);
		}

		$scope.signInModal = modal.signInModal;
		$scope.signUpModal = modal.signUpModal;
	}]);
});
