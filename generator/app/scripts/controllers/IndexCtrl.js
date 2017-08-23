'use strict';
define(['productSeek', 'jquery', 'services/authService', 'services/sessionService'], function(productSeek) {

	productSeek.controller('IndexCtrl', ['sessionService', 'authService', '$scope', '$location', function(session, auth, $scope, $location) {
        $location.path('#/');
		
		$scope.isLoggedIn = auth.isLoggedIn;
		
		// y si cambia la historia? Se actualiza? VER
		$scope.searchHistory = session.getSearchHistory();
		
		$scope.search = function() {
			// TODO: error message if empty, too short, too long...
			if ($scope.query && 3 <= $scope.query.length && $scope.query.length <= 64) {
				session.saveToSearchHistory($scope.query);
//				$location.url('/search/products?q=' + $scope.query.text);
			}
		}
		
	}]);
});
