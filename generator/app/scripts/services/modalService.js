'use strict';
define(['productSeek', 'services/restService', 'services/sessionService'], function(productSeek) {
    productSeek.service('modalService', ['$uibModal', 'restService', 'sessionService', function($uibModal, restService, sessionService) {
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
				size: 'sm',
                resolve: {
                    product: function() {
                        return product;
                    },
                    collections: function() {
                        console.log('asdasdas');
                        console.log(collections);
                        return collections.collections;
                    }
                }
			});            
        }
    }]);
});