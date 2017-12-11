'use strict';
define(['productSeek', 'services/authService', 'services/modalService', 'services/restService', 'directives/collectionItem', 'directives/productItem', 'services/titleService'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'titleService', 'user', 'collections', 'createdProducts', 'votedProducts', 'authService', 'modalService', 'restService','$uibModal', function($scope, titleService, user, collections, createdProducts, votedProducts, authService, modalService, restService, $uibModal) {
		titleService.setTitle(user.name.charAt(0).toUpperCase() + user.name.slice(1));

		$scope.user = user;
		$scope.collections = collections.collections;
		$scope.createdProducts = createdProducts.products;
		$scope.votedProducts = votedProducts.products;
		$scope.zrpTranslation = {
			user: $scope.user.name
		};
		
		var updateProfileOwner = function() {
			if (!authService.isLoggedIn() || authService.loggedUser.id !== user.id)
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
        
        $scope.removeCollection = function(collection) {
        	removeItemFrom(collection, $scope.collections);
        };

        $scope.removeProduct = function(product) {
            removeItemFrom(product, $scope.createdProducts);
            removeItemFrom(product, $scope.votedProducts);
            
            for (var i = 0; i < $scope.collections.length; i++)
                removeItemFrom(product, $scope.collections[i].products);
        };
        
        $scope.productVoted = function(product, voted) {
        	angular.forEach($scope.collections, function(collection) {
        		var idx = findIndexById(product, collection.products);

        		if (idx !== -1)
        			collection.products[idx].voters_count += voted ? 1 : -1;
        	});

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

		$scope.enableAddCollection = function() {
			$scope.addCollectionEnabled = true;
		};
		
		$scope.submitNewCollection = function(form) {
			if (form.$invalid)
				return;
			console.log($scope.newCollectionName);
			
			restService.createCollection($scope.newCollectionName)
			.then(function() {
				console.log("Collection created");
			})
			.catch(function() {
				console.log("errrorrr colleciton");
			});
		};
		
        $scope.changePictureModal = modalService.changePictureModal;

        $scope.$on('user:picture', function(event, picture) {
			$scope.user.picture_url = picture;
        });
        
        $scope.changePasswordModal = modalService.changePasswordModal;   
		
		var collectionsPage = 1, uploadedPage = 1, votedPage = 1;
		
		$scope.loadMoreCollections = function() {
			$scope.collectionScrollBusy = true;
			collectionsPage++;
			var params = {page: collectionsPage};
			
			restService.getCollectionsForUser(user.id, params)
			.then(function(data) {
				$scope.collectionScrollBusy = false;
				if (data.count != 0) {
					$scope.collections.push.apply($scope.collections, data.collections); 
				} else {
					$scope.collectionsDisable = true;
				}
			});
		};
		
		$scope.loadMoreUploaded = function() {
			$scope.uploadedScrollBusy = true;
			uploadedPage++;
			var params = {page: uploadedPage};
			
			restService.getPostedByUser(user.id, params)
			.then(function(data) {
				$scope.uploadedScrollBusy = false;
				if (data.count != 0) {
					$scope.createdProducts.push.apply($scope.createdProducts, data.products); 
				} else {
					$scope.uploadedDisable = true;
				}
			});
		};
		
		$scope.loadMoreVoted = function() {
			$scope.votedScrollBusy = true;
			votedPage++;
			var params = {page: votedPage};
			
			restService.getVotedByUser(user.id, params)
			.then(function(data) {
				$scope.votedScrollBusy = false;
				if (data.count != 0) {
					$scope.votedProducts.push.apply($scope.votedProducts, data.products); 
				} else {
					$scope.votedDisable = true;
				}
			});
		};
		
		
	}]);
});