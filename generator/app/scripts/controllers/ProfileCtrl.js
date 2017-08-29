'use strict';
define(['productSeek', 'services/authService', 'services/sessionService', 'controllers/ChangePasswordModalCtrl', 'controllers/ChangePictureModalCtrl', 'directives/collectionItem'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'user', 'collections', 'createdProducts', 'votedProducts', 'sessionService', 'authService','$uibModal', function($scope, user, collections, createdProducts, votedProducts, session, auth, $uibModal) {
		$scope.user = user;
		$scope.collections = collections;
		$scope.createdProducts = createdProducts;
		$scope.votedProducts = votedProducts;

        console.log(collections);
        
		var isEmpty = function(set) {
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
			console.log("entra a esta funcion");
			if(!auth.isLoggedIn())
				return false;
			else if (session.getUser().id != user.id)
				return false;
			return true;
		}

		$scope.changePictureModal = function() {
			$uibModal.open({
				templateUrl: 'views/modals/changePictureModal.html',
				controller: 'ChangePictureModalCtrl',
				size: 'md',
			});
		};

		$scope.changePasswordModal = function() {
			$uibModal.open({
				templateUrl: 'views/modals/changePasswordModal.html',
				controller: 'ChangePasswordModalCtrl',
				size: 'md',
			});
		};
	}]);

});