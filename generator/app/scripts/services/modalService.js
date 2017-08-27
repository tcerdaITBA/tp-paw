'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.service('modalService', ['$uibModal', function($uibModal) {
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
    }]);
});