'use strict';
define(['productSeek', 'services/restService', 'services/authService'], function(productSeek) {
    productSeek.service('modalService', ['$uibModal', 'restService', 'authService', function($uibModal, restService, authService) {
		this.signInModal = function() {
			return $uibModal.open({
				templateUrl: 'views/modals/signInModal.html',
				controller: 'SignInModalCtrl',
				size: 'sm'
			});
		};

		this.signUpModal = function() {
			return $uibModal.open({
				templateUrl: 'views/modals/signUpModal.html',
				controller: 'SignUpModalCtrl',
				size: 'sm'
			});
		};
        
        this.collectionModal = function(product, collections) {
			return $uibModal.open({
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
        };
        
        this.deleteModal = function(product) {
            return $uibModal.open({
                templateUrl: 'views/modals/deleteModal.html',
                controller: 'DeleteModalCtrl',
                size: 'sm',
                resolve: {
                    product: function() {
                        return product;
                    }
                }
            });
        };
        
		this.changePictureModal = function() {
			return $uibModal.open({
				templateUrl: 'views/modals/changePictureModal.html',
				controller: 'ChangePictureModalCtrl',
				size: 'md',
			});
		};

		this.changePasswordModal = function() {
			return $uibModal.open({
				templateUrl: 'views/modals/changePasswordModal.html',
				controller: 'ChangePasswordModalCtrl',
				size: 'md',
			});
		};
    }]);
});