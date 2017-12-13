define(['productSeek', 'services/restService', 'services/snackbarService'], function(productSeek) {
'use strict';
	productSeek.controller('DeleteModalCtrl', ['$scope', '$uibModalInstance', 'restService', 'snackbarService', 'product', function($scope, $uibModalInstance, restService, snackbarService, product) {
		
		$scope.product = product;

		$scope.delete = function() {
			$scope.deleting = true;
			restService.deleteProduct(product.id)
			.then(function() {
				$scope.deleting = false;
				snackbarService.showSnackbar('productDeleted');
				$uibModalInstance.close(true);
			})
			.catch(function() {
				$scope.deleting = false;
				snackbarService.showNoConnection();
			});
		};
		
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');            
		};
		
	}]);

});