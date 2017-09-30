'use strict';
define(['productSeek', 'services/authService', 'controllers/ChangePasswordModalCtrl', 'controllers/ChangePictureModalCtrl', 'directives/collectionItem', 'directives/productItem'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'user', 'collections', 'createdProducts', 'votedProducts', 'authService','$uibModal', function($scope, user, collections, createdProducts, votedProducts, authService, $uibModal) {
		$scope.user = user;
		$scope.collections = collections.collections;
		$scope.createdProducts = createdProducts.products;
		$scope.votedProducts = votedProducts.products;

		var updateProfileOwner = function() {
			if (!authService.isLoggedIn())
				return false;
			else if (authService.loggedUser.id !== user.id)
				return false;
			return true;
		};
        
        var updateProductOwner = function(products) {
            for (var i = 0; i < products.length; i++) {
                var p = products[i]
                if (!authService.isLoggedIn() || authService.loggedUser.id !== p.creator_id)
                    p.isOwnerLogged = false;
                else
                    p.isOwnerLogged = true;                
            }
        };
        
        var removeProductFrom = function(product, array) {
            var idx = -1;
            
            for (var i = 0; i < array.length && idx === -1; i++)
                if (array[i].id === product.id)
                    idx = i;
            
            if (idx != -1)
                array.splice(idx, 1);
        };
        
        $scope.isProfileOwner = updateProfileOwner();
        updateProductOwner($scope.createdProducts);
        updateProductOwner($scope.votedProducts);
        
        $scope.removeProduct = function(product) {
            removeProductFrom(product, $scope.createdProducts);
            removeProductFrom(product, $scope.votedProducts);
        };
        
		var isEmpty = function(set) {
			return set.count === 0;
		};

        $scope.$on('user:updated', function() {
            $scope.isProfileOwner = updateProfileOwner();
            updateProductOwner($scope.createdProducts);
            updateProductOwner($scope.votedProducts);
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