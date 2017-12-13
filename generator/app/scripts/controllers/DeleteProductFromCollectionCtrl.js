define(['productSeek', 'services/restService', 'services/snackbarService'], function(productSeek) {
'use strict';
    productSeek.controller('DeleteProductFromCollectionCtrl', ['$scope', '$uibModalInstance', 'restService', 'snackbarService', 'product', 'favList', function($scope, $uibModalInstance, restService, snackbarService, product, favList) {
        
        $scope.product = product;
        $scope.favList = favList;

        $scope.delete = function() {
            restService.deleteProductFromCollection(favList.id, product.id)
            .then(function() {
                snackbarService.showSnackbar('ProductFormCollectionDeleted');
                $uibModalInstance.close(true);
            });
        };
        
        $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');            
        };
        
    }]);

});