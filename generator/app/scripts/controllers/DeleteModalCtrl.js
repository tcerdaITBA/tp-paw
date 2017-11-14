define(['productSeek', 'services/restService', 'services/snackbarService'], function(productSeek) {
'use strict';
    productSeek.controller('DeleteModalCtrl', ['$scope', '$uibModalInstance', 'restService', 'snackbarService', 'product', function($scope, $uibModalInstance, restService, snackbarService, product) {
        
        $scope.product = product;

        $scope.delete = function() {
            restService.deleteProduct(product.id)
            .then(function() {
                snackbarService.showSnackbar('productDeleted');
                $uibModalInstance.close(true);
            });
        };
        
        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');            
        };
        
    }]);

});