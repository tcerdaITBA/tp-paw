define(['productSeek', 'services/restService'], function(productSeek) {
'use strict';
    productSeek.controller('DeleteModalCtrl', ['$scope', '$uibModalInstance', 'restService', 'product', function($scope, $uibModalInstance, restService, product) {
        
        $scope.product = product;

        $scope.delete = function() {
            restService.deleteProduct(product.id)
            .then(function() {
                // feedback
                $uibModalInstance.close(true);
            });
        };
        
        $scope.cancel = function() {
            console.log(product);
            $uibModalInstance.dismiss('cancel');            
        };
        
    }]);

});