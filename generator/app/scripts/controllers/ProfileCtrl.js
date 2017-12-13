'use strict';
define(['productSeek', 'services/authService', 'services/modalService', 'services/restService', 'directives/collectionItem', 'directives/productItem', 'services/titleService', 'services/snackbarService'], function(productSeek) {

	productSeek.controller('ProfileCtrl', ['$scope', 'titleService', 'user', 'collections', 'createdProducts', 'votedProducts', 'authService', 'modalService', 'restService', 'snackbarService', '$uibModal', 'pageSize', function($scope, titleService, user, collections, createdProducts, votedProducts, authService, modalService, restService, snackbarService, $uibModal, pageSize) {
		titleService.setTitle(user.name.charAt(0).toUpperCase() + user.name.slice(1));

		$scope.user = user;
		$scope.collections = collections.collections;
		$scope.createdProducts = createdProducts.products;
		$scope.votedProducts = votedProducts.products;

		$scope.unretrievedCollections = collections.totalCount - $scope.collections.length;
		$scope.unretrievedCreatedProducts = createdProducts.totalCount - $scope.createdProducts.length;
		$scope.unretrievedVotedProducts = votedProducts.totalCount - $scope.votedProducts.length;
		
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

		$scope.newCollection = {};
		
		$scope.enableAddCollection = function() {
			$scope.addCollectionEnabled = true;
		};
		
		$scope.disableAddCollection = function(form) {
			$scope.addCollectionEnabled = false;
			$scope.newCollection = {};
			form.$setPristine();
		}
		
		$scope.submitNewCollection = function(name, form) {
			$scope.repeatedCollectionError = false;
			if (!(name && name.length >= 4 && name.length <= 64))
				return;
			$scope.submittingCollection = true;
			restService.createCollection(name)
			.then(function(collection) {
				$scope.addCollectionEnabled = false;
				$scope.collections.push(collection)
				$scope.submittingCollection = false;
				$scope.disableAddCollection(form);
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
				$scope.disableAddCollection(form);
			});
		};
		
		$scope.changePictureModal = modalService.changePictureModal;

		$scope.$on('user:picture', function(event, picture) {
			$scope.user.picture_url = picture;
		});

		$scope.activeTab = $scope.firstActiveTab();
		
		$scope.setTab = function(tab) {
			$scope.activeTab = tab;
		};

		$scope.changePasswordModal = modalService.changePasswordModal;   
		$scope.collectionScrollBusy = $scope.uploadedScrollBusy = $scope.votedScrollBusy = false;
		$scope.collectionsDisable = $scope.collections.length < pageSize;
		$scope.uploadedDisable = $scope.createdProducts.length < pageSize;
		$scope.votedDisable = $scope.votedProducts.length < pageSize;

		var collectionsPage = 1, uploadedPage = 1, votedPage = 1;
		var showedNoConnectionCollections = false;
		var showedNoConnectionUploaded = false;
		var showedNoConnectionVoted = false;

		$scope.loadMoreCollections = function() {
			$scope.collectionScrollBusy = true;
			var params = {page: collectionsPage + 1, 'pageSize': pageSize};
			
			restService.getCollectionsForUser(user.id, params)
			.then(function(data) {
				collectionsPage++;
				showedNoConnectionCollections = false;
				$scope.collectionScrollBusy = false;
				if (data.count != 0) {
					$scope.collections.push.apply($scope.collections, data.collections);
					$scope.unretrievedCollections -= data.count;
					$scope.collectionsDisable = data.count < pageSize;
				} else {
					$scope.collectionsDisable = true;
				}
			})
			.catch(function() {
				$scope.collectionScrollBusy = false;
				if (!showedNoConnectionCollections) {
					showedNoConnectionCollections = true;
					snackbarService.showNoConnection();
				}
			});
		};

		$scope.loadMoreUploaded = function() {
			$scope.uploadedScrollBusy = true;
			var params = {page: uploadedPage + 1, 'pageSize': pageSize};
			
			restService.getPostedByUser(user.id, params)
			.then(function(data) {
				uploadedPage++;
				showedNoConnectionUploaded = false;
				$scope.uploadedScrollBusy = false;
				if (data.count != 0) {
					$scope.createdProducts.push.apply($scope.createdProducts, data.products); 
					$scope.unretrievedCreatedProducts -= data.count;
					$scope.uploadedDisable = data.count < pageSize;
				} else {
					$scope.uploadedDisable = true;
				}
			})
			.catch(function() {
				$scope.uploadedScrollBusy = false;
				if (!showedNoConnectionUploaded) {
					showedNoConnectionUploaded = true;
					snackbarService.showNoConnection();
				}
			});
		};
		
		$scope.loadMoreVoted = function() {
			$scope.votedScrollBusy = true;
			var params = {page: votedPage + 1, 'pageSize': pageSize};
			console.log(votedPage + 1);
			restService.getVotedByUser(user.id, params)
			.then(function(data) {
				votedPage++;
				showedNoConnectionVoted = false;
				$scope.votedScrollBusy = false;
				if (data.count != 0) {
					$scope.votedProducts.push.apply($scope.votedProducts, data.products); 
					$scope.unretrievedVotedProducts -= data.count;
					$scope.votedDisable = data.count < pageSize;
				} else {
					$scope.votedDisable = true;
				}
			})
			.catch(function() {
				$scope.votedScrollBusy = false;
				if (!showedNoConnectionVoted) {
					showedNoConnectionVoted = true;
					snackbarService.showNoConnection();
				}
			});
		};
	}]);
});