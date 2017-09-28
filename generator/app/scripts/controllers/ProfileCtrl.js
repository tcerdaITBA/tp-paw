'use strict';
define(['productSeek', 'services/authService', 'services/sessionService', 'controllers/ChangePasswordModalCtrl', 'controllers/ChangePictureModalCtrl', 'directives/collectionItem', 'directives/productItem'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'user', 'collections', 'createdProducts', 'votedProducts', 'sessionService', 'authService','$uibModal', function($scope, user, collections, createdProducts, votedProducts, session, authService, $uibModal) {
		$scope.user = user;
		$scope.collections = collections;
		$scope.createdProducts = createdProducts;
		$scope.votedProducts = votedProducts;

		var updateProfileOwner = function() {
			if (!authService.isLoggedIn())
				return false;
			else if (session.getUser().id != user.id)
				return false;
			return true;
		};
        
        $scope.isProfileOwner = updateProfileOwner();   
        
		var isEmpty = function(set) {
			return set.count == 0;
		};

        $scope.$on('user:updated', function() {
            $scope.isProfileOwner = updateProfileOwner();
        });        

		$scope.firstActiveTab = function() {
			if (!isEmpty(collections))
				return 1;
			else if (!isEmpty(createdProducts))
				return 2;
			else if (!isEmpty(votedProducts))
				return 3;
			else
				return 1;
		};

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