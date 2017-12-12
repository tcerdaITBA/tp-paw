'use strict';
define(['productSeek', 'services/authService', 'services/modalService', 'services/restService', 'directives/collectionItem', 'directives/productItem', 'services/titleService', 'services/snackbarService'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'titleService', 'user', 'collections', 'createdProducts', 'votedProducts', 'authService', 'modalService', 'restService', 'snackbarService', '$uibModal', 'pageSize', function($scope, titleService, user, collections, createdProducts, votedProducts, authService, modalService, restService, snackbarService, $uibModal, pageSize) {
		titleService.setTitle(user.name.charAt(0).toUpperCase() + user.name.slice(1));

		$scope.user = user;
		$scope.collections = collections.collections;
		$scope.createdProducts = createdProducts.products;
		$scope.votedProducts = votedProducts.products;
		$scope.zrpTranslation = {
			user: $scope.user.name
		};
		
		$scope.element = angular.element;

		var updateProfileOwner = function() {
			if (!authService.isLoggedIn() || authService.loggedUser.id !== user.id)
				return false;
			return true;
		};
        
        var updateProductOwner = function(products) {
            for (var i = 0; i < products.length; i++) {
                var p = products[i];
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


        $scope.$watchCollection('createdProducts', function() {
	        updateProductOwner($scope.createdProducts);
        });

        $scope.$watchCollection('votedProducts', function() {
	        updateProductOwner($scope.votedProducts);
        });
        
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
			console.log("enable")
		};
		
		$scope.disableAddCollection = function() {
			console.log($scope.addCollectionEnabled);
			console.log($scope.newCollectionName);
			$scope.addCollectionEnabled = false;
			$scope.newCollectionName = undefined;
		}
		
		$scope.submitNewCollection = function(name) {
			$scope.repeatedCollectionError = false;
			if (!(name && name.length >= 4 && name.length <= 64))
				return;
			$scope.submittingCollection = true;
			restService.createCollection(name)
			.then(function(collection) {
				$scope.addCollectionEnabled = false;
				$scope.collections.push(collection)
				$scope.submittingCollection = false;
			})
			.catch(function(error) {
				switch (error.status) {
					case -1: // Sin conexión
						snackbarService.showNoConnection();
						break;
					case 409: // Conflict - Nombre repetido
						$scope.repeatedCollectionError = true;
						break;
				}
				$scope.submittingCollection = false;
			});
		};
		
        $scope.changePictureModal = modalService.changePictureModal;

        $scope.$on('user:picture', function(event, picture) {
			$scope.user.picture_url = picture;
        });

		$scope.activeTab = $scope.firstActiveTab();
        
		$scope.setTab = function(tab) {
			console.log(tab);
			$scope.activeTab = tab;
		};

        $scope.changePasswordModal = modalService.changePasswordModal;   
		$scope.collectionScrollBusy = $scope.uploadedScrollBusy = $scope.votedScrollBusy = false;
		$scope.collectionsDisable = $scope.collections.length < pageSize;
		$scope.uploadedDisable = $scope.createdProducts.length < pageSize;
		$scope.votedDisable = $scope.votedProducts.length < pageSize;

		var collectionsPage = 1, uploadedPage = 1, votedPage = 1;

		$scope.loadMoreCollections = function() {
			$scope.collectionScrollBusy = true;
			collectionsPage++;
			var params = {page: collectionsPage, 'pageSize': pageSize};
			
			restService.getCollectionsForUser(user.id, params)
			.then(function(data) {
				$scope.collectionScrollBusy = false;
				if (data.count != 0) {
					$scope.collections.push.apply($scope.collections, data.collections);
					$scope.collectionsDisable = data.count < pageSize;
				} else {
					$scope.collectionsDisable = true;
				}
			});
		};

		$scope.loadMoreUploaded = function() {
			$scope.uploadedScrollBusy = true;
			uploadedPage++;
			var params = {page: uploadedPage, 'pageSize': pageSize};
			
			restService.getPostedByUser(user.id, params)
			.then(function(data) {
				$scope.uploadedScrollBusy = false;
				if (data.count != 0) {
					$scope.createdProducts.push.apply($scope.createdProducts, data.products); 
					$scope.uploadedDisable = data.count < pageSize;
				} else {
					$scope.uploadedDisable = true;
				}
			});
		};
		
		$scope.loadMoreVoted = function() {
			$scope.votedScrollBusy = true;
			votedPage++;
			var params = {page: votedPage, 'pageSize': pageSize};
			
			restService.getVotedByUser(user.id, params)
			.then(function(data) {
				$scope.votedScrollBusy = false;
				if (data.count != 0) {
					$scope.votedProducts.push.apply($scope.votedProducts, data.products); 
					$scope.votedDisable = data.count < pageSize;
				} else {
					$scope.votedDisable = true;
				}
			});
		};
		
		
	}]);
});