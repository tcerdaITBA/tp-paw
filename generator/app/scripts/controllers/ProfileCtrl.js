'use strict';
define(['productSeek', 'services/authService', 'services/sessionService', 'controllers/ChangePictureModalCtrl'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'user', 'collections', 'createdProducts', 'votedProducts', 'sessionService', 'authService','$uibModal', function($scope, user, collections, createdProducts, votedProducts, session, auth, $uibModal) {
		$scope.user = user;
		$scope.collections = collections;
		$scope.createdProducts = createdProducts;
		$scope.votedProducts = votedProducts;

		var isEmpty = function(set) {
			console.log(set);
			return set.count == 0;
			
		}	
	
		$scope.firstActiveTab = function() {
			if(!isEmpty(collections))
				return 1;
			else if(!isEmpty(createdProducts))
				return 2;
			else if(!isEmpty(votedProducts))
				return 3;
			else
				return 1;
		}

		$scope.isProfileOwner = function() {
			if(!auth.isLoggedIn())
				return false;
			else if (session.getUser().id != user.id)
				return false;
			return true;
		}

		$scope.changePictureModal = function() {
			$uibModal.open({
				templateUrl: 'views/changePictureModal.html',
				controller: 'ChangePictureModalCtrl',
				size: 'md',
			});
		};
	}]);
});