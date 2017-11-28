'use strict';
define(['productSeek', 'services/authService', 'services/modalService', 'controllers/ChangePasswordModalCtrl', 'controllers/ChangePictureModalCtrl', 'directives/collectionItem', 'directives/productItem'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'user', 'collections', 'createdProducts', 'votedProducts', 'authService', 'modalService', '$uibModal', function($scope, user, collections, createdProducts, votedProducts, authService, modalService, $uibModal) {
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
        
        var updateCollectionOwner = function(collections) {
            for(var i = 0; i< collections.length; i++) {
                var c = collections[i]
                if (!authService.isLoggedIn())
//                    Falta ver si el usuario loggeado es el creador
                    c.isOwnerLogged = false;
                else
                    c.isOwnerLogged = true;  
            }
        }
        
        var findIndexById = function(item, array) {
            for (var i = 0; i < array.length; i++)
                if (array[i].id === item.id)
                    return i;
            
            return -1;
        };
        
        var removeItemFrom = function(item, array) {
            var idx = findIndexById(item, array);

            if (idx != -1)
                array.splice(idx, 1);
        };
        
        $scope.isProfileOwner = updateProfileOwner();
        updateProductOwner($scope.createdProducts);
        updateProductOwner($scope.votedProducts);
        
        $scope.removeProduct = function(product) {
            removeItemFrom(product, $scope.createdProducts);
            removeItemFrom(product, $scope.votedProducts);
            
            for (var i = 0; i < $scope.collections.length; i++)
                removeItemFrom(product, $scope.collections[i].products);
        };
        
        $scope.productVoted = function(product, voted) {
            if ($scope.isProfileOwner)
                voted ? $scope.votedProducts.push(product) : removeItemFrom(product, $scope.votedProducts);
        };
        
        $scope.addedToCollection = function(product, collection) {  // note that the collection already has the product added
            if ($scope.isProfileOwner) {
                var idx = findIndexById(collection, $scope.collections);
                idx === -1 ? $scope.collections.push(collection) : $scope.collections[idx] = collection;
            }
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

        $scope.changePictureModal = modalService.changePictureModal;

        $scope.$on('user:picture', function(event, picture) {
			$scope.user.picture_url = picture;
        });
        
        $scope.changePasswordModal = modalService.changePasswordModal;        
	}]);
});