'use strict';
define(['productSeek', 'services/modalService'], function(productSeek) {

	productSeek.controller('PopoverCtrl', ['$scope', 'modalService', function($scope, modalService) {
		$scope.signInModal = modalService.signInModal;
		$scope.signUpModal = modalService.signUpModal;
	}]);

});