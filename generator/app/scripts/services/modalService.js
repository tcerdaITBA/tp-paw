'use strict';
define(['productSeek', 'services/restService', 'services/authService'], function(productSeek) {
    productSeek.service('modalService', ['$uibModal', 'restService', 'authService', function($uibModal, restService, authService) {
		this.signInModal = function() {
			$uibModal.open({
				templateUrl: 'views/modals/signInModal.html',
				controller: 'SignInModalCtrl',
				size: 'sm'
			});
		};

		this.signUpModal = function() {
			$uibModal.open({
				templateUrl: 'views/modals/signUpModal.html',
				controller: 'SignUpModalCtrl',
				size: 'sm'
			});
		};
        
        this.collectionModal = function(product, collections) {
			$uibModal.open({
				templateUrl: 'views/modals/collectionModal.html',
				controller: 'CollectionModalCtrl',
				size: 'md',
                resolve: {
                    product: function() {
                        return product;
                    },
                    collections: function() {
                        return restService.getCollectionsForUser(authService.loggedUser.id);
                    }
                }
			});            
        }
    }]);
});